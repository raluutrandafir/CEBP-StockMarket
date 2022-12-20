package com.stock.entities;

import com.stock.miscellaneous.MessageSender;
import com.stock.miscellaneous.ProtectedList;
import com.stock.miscellaneous.Type;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class StockMarket implements Runnable{
    private ProtectedList<Client> clientList = new ProtectedList<>();
   // private List<String> stocks;
    private HashMap<String, Double> stocks;
    private ProtectedList<Transaction> sellOffers;
    private ProtectedList<Transaction> buyRequests;
    private ProtectedList<Transaction> terminatedTransactions;

    public StockMarket(List<String> stocks) {
        sellOffers = new ProtectedList<>();
        buyRequests = new ProtectedList<>();
        terminatedTransactions = new ProtectedList<>();
        //this.stocks = stocks;
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
            return false;
        }
        if(buy.getAmount() <= 0){
            buyRequests.remove(buy);
            return false;
        }

        sellOffers.remove(sell);
        buyRequests.remove(buy);
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
        // creates a transaction
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
            //System.out.println("buy transaction" + transaction.toString());
            this.addBuyRequest(transaction);

            boolean searching = true;
            Transaction sell;
            MessageSender.sendBuyRequest(transaction);
            while ( (sell = this.getSellOffer(transaction.getPrice())) != null && searching) // while the market is searching for an offer to match and the price of the buf request exists
                searching = this.isSearching(sell, transaction); //keep updating the searching status

            //if (!searching){ // if the search status changed to false, the transaction gets removed from the client's personal transaction history

                // terminatedTransactions.(transaction);
                //return true; //transaction has been made
               // return;
                //System.out.println("Terminated transactions: " + getTerminated());

            //}


        }else if( clientType == Type.SELLER){
            //System.out.println("sell transaction" + transaction.toString());
            this.addSellOffer(transaction);

            boolean searching = true;
            Transaction buy;
            MessageSender.sendSellOffer(transaction);
            while ( (buy = this.getBuyOffer(transaction.getPrice())) != null && searching )
                searching = this.isSearching(transaction, buy);

           // if (!searching) {
                //terminatedTransactions.add(transaction);
               // return true;
               // return;
              //  System.out.println("Terminated transactions: " + getTerminated());
            //}
        }
        //System.out.println("Terminated transactions: " + getTerminated());
        //return false;
    }

    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
