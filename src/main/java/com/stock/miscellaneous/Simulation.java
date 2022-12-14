package com.stock.miscellaneous;

import com.stock.entities.Client;
import com.stock.entities.StockMarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stock.miscellaneous.Type.BUYER;
import static com.stock.miscellaneous.Type.SELLER;

public class Simulation implements Runnable{

    private final int client;
    private final int stock;
    private final long timer;
    private final List<Integer> clientIDs;
    private final boolean output;
    private final List<Map<String,Integer>> ownedStocks;
    private final HashMap<String, Double>  stock_and_price;
    private final ArrayList<ArrayList<Integer>> amounts;
    private final ArrayList<ArrayList<String>> tickers;
    protected StockMarket stockMarket;

    public Simulation(int client, int stock, long timer, /*List<String> */ HashMap<String, Double> stockIDs, List<Integer> clientIDs, List<Map<String,Integer>> ownedStocks, ArrayList<ArrayList<Integer>> amounts, ArrayList<ArrayList<String>> tickers, boolean output, StockMarket stockMarket) {
        this.client = client;
        this.stock = stock;
        this.timer = timer;
        this.stock_and_price = stockIDs;
        this.clientIDs = clientIDs;
        this.ownedStocks = ownedStocks;
        this.amounts = amounts;
        this.tickers = tickers;
        this.output = output;
        this.stockMarket = stockMarket;
    }

    @Override
    public void run() {

        Client[] client_array = new Client[client];
        Thread[] client_threads = new Thread[client];
        Thread s = new Thread(stockMarket);

        for (int i = 0; i < client; i++) {

            // if the client has stocks he becomes a seller type client otherwise he is a buyer
            if (ownedStocks.get(i).isEmpty()) {
                client_array[i] = new Client(clientIDs.get(i), BUYER, stockMarket, amounts.get(i), tickers.get(i));
            } else {
                client_array[i] = new Client(clientIDs.get(i), SELLER, stockMarket, amounts.get(i), tickers.get(i));
            }
           // stockMarket.addClient(client_array[i]);
            client_threads[i] = new Thread(client_array[i]);
        }

        // start threads for each clients
        for (Thread t : client_threads) {
            t.start();
        }

        for (Thread t : client_threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        s.start();
    }
}
