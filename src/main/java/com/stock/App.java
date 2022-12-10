package com.stock;

import com.stock.entities.StockMarket;
import com.stock.miscellaneous.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    private static StockMarket stockMarket;

    public static void main(String[] args) throws Exception {
        List<String> stocks = new ArrayList<>();
        List<Integer> clients = new ArrayList<>();
        List<Map<String, Integer>> os = new ArrayList<>();
        List<HashMap<String, Integer>> co = new ArrayList<>();
        HashMap<String, Integer> c1_offers = new HashMap<>();
        HashMap<String, Integer> c2_offers = new HashMap<>();
        HashMap<String, Integer> c3_offers = new HashMap<>();
        HashMap<String, Integer> c4_offers = new HashMap<>();
        HashMap<String, Integer> c5_offers = new HashMap<>();
        HashMap<String, Integer> c6_offers = new HashMap<>();
        HashMap<String, Integer> c7_offers = new HashMap<>();
        HashMap<String, Integer> c8_offers = new HashMap<>();

        stocks.add("MCD");
        stocks.add("SBUX");
        stocks.add("AAPL");

        stockMarket = new StockMarket(stocks);

        clients.add(1);
        clients.add(2);
        clients.add(3);
        clients.add(4);
        clients.add(5);
        clients.add(6);
        clients.add(7);
        clients.add(8);


        Map<String, Integer> c1, c2, c3, c4, c5, c6, c7, c8;

        c1 = new HashMap<>();
        c1.put("MCD", 5);
        c1.put("AAPL", 20);
        c2 = new HashMap<>();
        c2.put("INTC", 10);
        c3 = new HashMap<>();
        c4 = new HashMap<>();
        c5 = new HashMap<>();
        c6 = new HashMap<>();
        c6.put("AMZN", 30);
        c7 = new HashMap<>();
        c7.put("AMZN", 10);
        c7.put("META", 10);
        c7.put("INTC", 10);
        c8 = new HashMap<>();


        c1_offers.put("AAPL", 5);
        c1_offers.put("AAPL", 10);
        c1_offers.put("MCD", 1);

        os.add(c1);
        os.add(c2);
        os.add(c3);
        os.add(c4);
        os.add(c5);
        os.add(c6);
        os.add(c7);
        os.add(c8);

        Simulation sim = new Simulation(8, 3, 10L, stocks, clients, os, co, true, stockMarket);

        Thread tsim = new Thread(sim);

        tsim.start();

        tsim.join();

    }

}
