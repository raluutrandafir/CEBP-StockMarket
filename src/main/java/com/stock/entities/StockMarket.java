package com.stock.entities;

import com.stock.miscellaneous.ProtectedList;

import java.util.List;

public class StockMarket {
    private ProtectedList<Transaction> sellOffers;
    private ProtectedList<Transaction> buyRequests;
    private ProtectedList<Transaction> terminatedTransactions;

    public StockMarket() {
        sellOffers = new ProtectedList<>();
        buyRequests = new ProtectedList<>();
        terminatedTransactions = new ProtectedList<>();
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

    public synchronized void removeAllSellOffers(List<Transaction> list) {
        for (Transaction t : list)
            sellOffers.remove(t);
    }

    public synchronized void removeAllBuyRequests(List<Transaction> list) {
        for (Transaction t : list)
            buyRequests.remove(t);
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
}
