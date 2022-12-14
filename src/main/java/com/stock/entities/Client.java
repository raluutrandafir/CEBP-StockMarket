package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable{
    private long clientId; // Client will have a unique id
    protected Type clientType; // buyer or seller
    protected StockMarket stockMarket;
    //protected HashMap<String,Integer> clientOffers;
    private ArrayList<Integer> amounts;
    private ArrayList<String> tickers;

    public Client(long clientId, Type clientType, StockMarket stockMarket, ArrayList<Integer> amounts, ArrayList<String> tickers) {
        System.out.println("New client created: " + clientId);
        this.clientId = clientId;
        this.clientType = clientType;
        this.stockMarket = stockMarket;
        //this.clientOffers = clientOffers;
        this.amounts = amounts;
        this.tickers = tickers;
    }

    @Override
    public void run() {
        boolean end = false;
            for (int i = 0; i < amounts.size(); i++) {
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(clientType == Type.BUYER){
                    String name = tickers.get(i);
                    int amount = amounts.get(i);
                    double price = stockMarket.getStocks().get(name);
//                    System.out.println("\nTicker: " + name + "; amount: " + amount + "; price: " + price + "; client: " + clientType + " " + clientId);

                        if( stockMarket.removeBuyRequest(new Transaction(clientId, amount, name, price, clientType))) {
                            stockMarket.doTransaction(new Transaction(clientId, amount, name, price, clientType), clientType);
                        }
                }else{
                    String name = tickers.get(i);
                    int amount = amounts.get(i);
                    double price = stockMarket.getStocks().get(name);
//                    System.out.println("\nTicker: " + name + "; amount: " + amount + "; price: " + price + "; client: " + clientType + " " + clientId);

                        if( stockMarket.removeSellOffer(new Transaction(clientId, amount, name, price, clientType))) {
                            stockMarket.doTransaction(new Transaction(clientId, amount, name, price, clientType), clientType);
                        }
                }
            }
        }
}
