package com.stock.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static com.stock.miscellaneous.Type.BUYER;

public class Buyer extends Client{
    public Buyer(Socket socket, BufferedReader in) throws IOException {
        super(socket, in);
        this.type = BUYER;
    }
    @Override
    protected boolean removeTransaction(Transaction myTransaction) {
        return stockMarket.removeBuyRequest(myTransaction);
    }

    @Override
    protected void removeTransactions(List<Transaction> myTransactions) {
        stockMarket.removeAllBuyRequests(myTransactions);
    }

    @Override
    protected void doTransaction(Transaction buy) {
        transactionHistory.add(buy);
        stockMarket.addBuyRequest(buy);

        boolean searching = true;
        Transaction sell;

        while ( (sell = stockMarket.getSellOffer(buy.getPrice())) != null && searching)
            searching = isSearching(sell, buy);

        if (!searching)
            transactionHistory.remove(buy);
    }
}
