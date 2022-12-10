package com.stock.entities;

import com.stock.miscellaneous.ProtectedList;
import com.stock.miscellaneous.Type;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StockMarket {
    private ConcurrentHashMap<Client, Thread> clientList;
    private List<String> stocks;
    private ProtectedList<Transaction> sellOffers;
    private ProtectedList<Transaction> buyRequests;
    private ProtectedList<Transaction> terminatedTransactions;

    public StockMarket(List<String> stocks) {
        sellOffers = new ProtectedList<>();
        buyRequests = new ProtectedList<>();
        terminatedTransactions = new ProtectedList<>();
        this.stocks = stocks;
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

    public Transaction getSellOffer(float price) {

        return getOffer(price, sellOffers.getList());
    }

    public Transaction getBuyOffer(float price) {
        return getOffer(price, buyRequests.getList());
    }

    private Transaction getOffer(float price, List<Transaction> offers) {
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
        sellOffers.remove(sell);
        buyRequests.remove(buy);
        terminatedTransactions.add(t);
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
            sell.setAmount(sell.getAmount() - transaction.getAmount());
            buy.setAmount(buy.getAmount() - transaction.getAmount());

            //if there are still money left from the previous sell offer, adjust the amount and add the offer again
            if (sell.getAmount() > 0)
                this.addSellOffer(sell);

            //if there are still money left from the previous buy offer, adjust the amount and add the offer again
            if (buy.getAmount() > 0)
                this.addBuyRequest(buy);
            return false;
        }
        return true;
    }

    protected void doTransaction(Transaction transaction, Type clientType){
        terminatedTransactions.add(transaction);

        if( clientType == Type.BUYER ){
            this.addBuyRequest(transaction);

            boolean searching = true;
            Transaction sell;

            while ( (sell = this.getSellOffer(transaction.getPrice())) != null && searching) // while the market is searching for an offer to match and the price of the buf request exists
                searching = this.isSearching(sell, transaction); //keep updating the searching status

            if (!searching) // if the search status changed to false, the transaction gets removed from the client's personal transaction history
                terminatedTransactions.remove(transaction);
        }else if( clientType == Type.SELLER){
            this.addSellOffer(transaction);

            boolean searching = true;
            Transaction buy;

            while ( (buy = this.getBuyOffer(transaction.getPrice())) != null && searching )
                searching = this.isSearching(transaction, buy);

            if (!searching)
                terminatedTransactions.remove(transaction);
        }
    }
}
