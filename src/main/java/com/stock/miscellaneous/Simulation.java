package com.stock.miscellaneous;

import com.stock.entities.Client;
import com.stock.entities.StockMarket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stock.miscellaneous.Type.BUYER;
import static com.stock.miscellaneous.Type.SELLER;

public class Simulation implements Runnable{

    private int client;
    private int stock;
    private long timer;
    private List<Integer> clientIDs;
    private boolean output;
    private List<Map<String,Integer>> ownedStocks;
    private List<HashMap<String,Integer>> clientOffers;
    protected StockMarket stockMarket;

    public Simulation(int client, int stock, long timer, List<String> stockIDs, List<Integer> clientIDs,List<Map<String,Integer>> ownedStocks, List<HashMap<String,Integer>> clientOffers, boolean output, StockMarket stockMarket) {
        this.client = client;
        this.stock = stock;
        this.timer = timer;
        this.clientIDs = clientIDs;
        this.ownedStocks = ownedStocks;
        this.clientOffers = clientOffers;
        this.output = output;
        this.stockMarket = stockMarket;
    }

    @Override
    public void run() {

        Client[] client_array = new Client[client];
        Thread[] client_threads = new Thread[client];
        Thread[] stock_threads = new Thread[stock];


        for (int i = 0; i < client; i++) {
            // if the client has stocks he becomes a seller type client otherwise he is a buyer
            if(ownedStocks.get(i).isEmpty()){
                client_array[i] = new Client(clientIDs.get(i), BUYER, stockMarket, clientOffers.get(i));
            }
            else{
                client_array[i] = new Client(clientIDs.get(i), SELLER, stockMarket, clientOffers.get(i));
            }

            client_threads[i] = new Thread(client_array[i]);
        }

        for (Thread t : client_threads) {
            t.start();
        }

        long startTime = System.currentTimeMillis();
        long endTime = startTime;


        while (endTime - startTime < timer * 1000) {
            endTime = System.currentTimeMillis();
        }

        //map client
        //initializare cu id 


//        for (Client c : client_array) {
//            c.setRunning(false);
//        }

        for (Thread t : client_threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        if(output)
//            for (Client client : client_array) {
//                client.printInfo();
//            }



    }

}