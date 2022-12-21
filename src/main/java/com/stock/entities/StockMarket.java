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
    private ArrayList<Transaction> offerList;

    @Override
    public void run(){
        while(true) {
            for (Transaction t : offerList) {
                doTransaction(t, t.getType());
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

    public synchronized boolean finishTransaction(Transaction t, Transaction sell, Transaction buy) {
        if (!sellOffers.contains(sell) || !buyRequests.contains(buy))
            return false;
        if(sell.getAmount() <= 0){
            sellOffers.remove(sell);
            offerList.remove(sell);
            return false;
        }
        if(buy.getAmount() <= 0){
            buyRequests.remove(buy);
            offerList.remove(buy);
            return false;
        }

        sellOffers.remove(sell);
        offerList.remove(sell);
        buyRequests.remove(buy);
        offerList.remove(buy);
        terminatedTransactions.add(t);
        MessageSender.sendTerminatedTransactionMessages(t);
        return true;
    }

    public boolean removeBuyRequest(Transaction myTransaction) {
        return buyRequests.remove(myTransaction);
    }

    public boolean removeSellOffer(Transaction myTransaction) {
        return sellOffers.remove(myTransaction);
    }

    public boolean isSearching(Transaction sell, Transaction buy) {
        // creates a match type transaction
        Transaction transaction = this.createTransaction(sell, buy);

        // if the transaction is finished successfully
        if (this.finishTransaction(transaction, sell, buy)) {
            // adjust the money/stock amount for both clients
            var amount = Math.min(sell.getAmount(), buy.getAmount());
            sell.setAmount(sell.getAmount() - amount);
            buy.setAmount(buy.getAmount() - amount);

            //if there are still stocks amount left from the previous sell offer, adjust the amount and add the offer again
            if (sell.getAmount() > 0)
                this.addSellOffer(sell);
            //if there are still stocks amount left from the previous buy offer, adjust the amount and add the offer again
            if (buy.getAmount() > 0)
                this.addBuyRequest(buy);
            return false;
        }
        return true;
    }

    protected void doTransaction(Transaction transaction, Type clientType){
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
    }
}
