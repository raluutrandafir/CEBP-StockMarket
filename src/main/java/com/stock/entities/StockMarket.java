package com.stock.entities;

import com.stock.miscellaneous.MessageSender;
import com.stock.miscellaneous.ProtectedList;
import com.stock.miscellaneous.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockMarket  implements Runnable{
    private ProtectedList<Client> clientList = new ProtectedList<>();
    private HashMap<String, Double> stocks;
    private ProtectedList<Transaction> sellOffers;
    private ProtectedList<Transaction> buyRequests;
    private ProtectedList<Transaction> terminatedTransactions;
    private ProtectedList<Transaction> offerList ;

    @Override
    public void run(){
        while(true) {
            for (Transaction transaction : offerList.getList()) {
                if( transaction.getType() == Type.BUYER ){
                    this.addBuyRequest(transaction);
                    Transaction sell;
                    if ( (sell = this.getSellOffer(transaction.getPrice())) != null && transaction.getAmount() > 0) {// if the market is searching for an offer to match and the price of the buy request exists
                        this.isSearching(sell, transaction); //keep updating the searching status
                    }

                }else if( transaction.getType() == Type.SELLER){
                    this.addSellOffer(transaction);
                    Transaction buy;
                    if ( (buy = this.getBuyOffer(transaction.getPrice())) != null&& transaction.getAmount()>0) {
                        this.isSearching(transaction, buy);
                    }
                }
            }
        }
    }


    public void  addOffer(Transaction transaction){
        offerList.add(transaction);
    }

    public void  addClient(Client client){
        clientList.add(client);
    }

    public StockMarket(HashMap<String, Double> stocks) {
        sellOffers = new ProtectedList<>();
        buyRequests = new ProtectedList<>();
        terminatedTransactions = new ProtectedList<>();
        offerList = new ProtectedList<>();
        this.stocks = stocks;
    }

    public HashMap<String, Double> getStocks() {
        return stocks;
    }

    public List<Transaction> getSellOffers() {
        return sellOffers.getList();
    }

    public List<Transaction> getBuyRequests() {
        return buyRequests.getList();
    }

    public List<Transaction> getAllOffers() {
        List<Transaction> all = getSellOffers();
        all.addAll(getBuyRequests());
        return all;
    }

    public List<Transaction> getTerminated() {
        return terminatedTransactions.getList();
    }

    public void addSellOffer(Transaction t) {
        sellOffers.add(t);
    }

    public void addBuyRequest(Transaction t) {
        buyRequests.add(t);
    }

    public Transaction getSellOffer(double price) {

        return getOffer(price, sellOffers.getList());
    }

    public Transaction getBuyOffer(double price) {
        return getOffer(price, buyRequests.getList());
    }
    //search for an offer that matches the price
    private Transaction getOffer(double price, List<Transaction> offers) {
        for (Transaction offer : offers) {
            if (offer.getPrice() == price)
                return offer;
        }
        return null;
    }

    public Transaction createTransaction(Transaction sell, Transaction buy) {
        return new Transaction(sell, buy);
    }

    public  boolean finishTransaction(Transaction t, Transaction sell, Transaction buy) {
        if (!sellOffers.contains(sell) || !buyRequests.contains(buy))
            return false;
        if(sell.getAmount() <= 0){
            removeSellOffer(sell);
            return false;
        }
        if(buy.getAmount() <= 0){
            removeBuyRequest(buy);
            return false;
        }

        removeSellOffer(sell);
        removeBuyRequest(buy);
        terminatedTransactions.add(t);
        MessageSender.sendTerminatedTransactionMessages(t);
        return true;
    }

    public void removeBuyRequest(Transaction myTransaction) {
        buyRequests.remove(myTransaction);
        offerList.remove(myTransaction);
    }

    public void removeSellOffer(Transaction myTransaction) {
         sellOffers.remove(myTransaction);
         offerList.remove(myTransaction);
    }

    public void isSearching(Transaction sell, Transaction buy) {
        // creates a match type transaction
        Transaction transaction = this.createTransaction(sell, buy);

        // if the transaction is finished successfully
        if (this.finishTransaction(transaction, sell, buy)) {
            // adjust the money/stock amount for both clients
            var amount = Math.min(sell.getAmount(), buy.getAmount());
            sell.setAmount(sell.getAmount() - amount);
            buy.setAmount(buy.getAmount() - amount);

            //if there are still stocks amount left from the previous sell offer, adjust the amount and add the offer again
            if (sell.getAmount() > 0){
                this.addSellOffer(sell);
                offerList.add(sell);
                MessageSender.sendSellOffer(sell);
            }
            //if there are still stocks amount left from the previous buy offer, adjust the amount and add the offer again
            if (buy.getAmount() > 0) {
                this.addBuyRequest(buy);
                offerList.add(buy);
                MessageSender.sendBuyRequest(buy);
            }
        }
    }

    /*protected void doTransaction(Transaction transaction, Type clientType){
        if( clientType == Type.BUYER ){
            this.addBuyRequest(transaction);
            boolean searching = true;
            Transaction sell;
            MessageSender.sendBuyRequest(transaction); // add the transaction user intends to do
            while ( (sell = this.getSellOffer(transaction.getPrice())) != null && searching) // while the market is searching for an offer to match and the price of the buy request exists
                searching = this.isSearching(sell, transaction); //keep updating the searching status

        }else if( clientType == Type.SELLER){
            this.addSellOffer(transaction);
            boolean searching = true;
            Transaction buy;
            MessageSender.sendSellOffer(transaction);
            while ( (buy = this.getBuyOffer(transaction.getPrice())) != null && searching )
                searching = this.isSearching(transaction, buy);
        }
    }*/
}
