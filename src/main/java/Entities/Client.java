package Entities;

import java.util.ArrayList;

public class Client {
    private long id; // the id of the client
    private float money; // current sum of money of the client
    private ArrayList<Stock> stocks; // owned stocks of client
    private static StockMarket stockMarket = new StockMarket(); // tbd -> static is a problem (?)

    public Client(long id, float money, ArrayList<Stock> stocks) {
        this.id = id;
        this.money = money;
        this.stocks = stocks;
    }

    private void viewOffers(){ // see  all the offers
        stockMarket.getAll_offers();
    }

    private void transactionsHistory(){ // see all the transactions done
        stockMarket.getAll_transactions();
    }
}
