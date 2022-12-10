package com.stock.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static com.stock.miscellaneous.Type.SELLER;

public class Seller extends Client {
    public  Seller(Socket socket, BufferedReader in) throws IOException {
        super(socket, in);
        this.clientType = SELLER;
    }

    @Override
    protected boolean removeTransaction(Transaction myTransaction) {
        return stockMarket.removeSellOffer(myTransaction);
    }

    @Override
    protected void removeTransactions(List<Transaction> myTransactions) {
        stockMarket.removeAllSellOffers(myTransactions);
    }

    @Override
    protected void doTransaction(Transaction sell) {
        transactionHistory.add(sell);
        stockMarket.addSellOffer(sell);
        boolean searching = true;
        Transaction buy;

        while ( (buy = stockMarket.getBuyOffer(sell.getPrice())) != null && searching )
            searching = isSearching(sell, buy);

        if (!searching)
            transactionHistory.remove(sell);
    }
}

