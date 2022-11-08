package Entities;

import java.util.ArrayList;

// Q: should the stock market have a list of stocks
// or should it extract the stocks from the offers on the market?

public class StockMarket {
    private ArrayList<Offer> all_offers; // arraylist of all buyer and seller offers
    private ArrayList<Transaction> all_transactions; // arraylist of all the transactions done on the market

    public ArrayList<Offer> getAll_offers() {
        return all_offers;
    }

    public ArrayList<Transaction> getAll_transactions() {
        return all_transactions;
    }

}
