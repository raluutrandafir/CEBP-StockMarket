package Entities;


import com.stock.transactions.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static miscellaneous.Type.SELLER;

public class Seller extends Client {
    @Override
    protected boolean removeTransaction(Transaction myTransaction) {
        return StockMarket.removeSellOffer(myTransaction);
    }

    public  Seller(Socket socket, BufferedReader in) throws IOException {
        super(socket, in);
        this.type = SELLER;
    }

    @Override
    protected void removeTransactions(List<Transaction> myTransactions) {
        StockMarket.removeAllSellOffers(myTransactions);
    }

    @Override
    protected void doTransaction(Transaction sell) {
        transactionHistory.add(sell);
        StockMarket.addSellOffer(sell);
        boolean searching = true;
        Transaction buy;

        while ( (buy = StockMarket.getBuyOffer(sell.getPrice())) != null && searching )
            searching = isSearching(sell, buy);

        if (!searching)
            transactionHistory.remove(sell);
    }
}

