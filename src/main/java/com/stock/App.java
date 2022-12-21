package com.stock;

import com.stock.entities.StockMarket;
import com.stock.miscellaneous.Simulation;
import com.stock.entities.Client;
import com.stock.miscellaneous.MessageSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    private static StockMarket stockMarket;

    public static void main(String[] args) throws Exception {
        //MessageSender.sendHelloMessage("this is a random message");

        HashMap<String, Double> stocks = new HashMap<>();
        List<Integer> clients = new ArrayList<>();
        List<Map<String, Integer>> os = new ArrayList<>();
        List<HashMap<String, Integer>> co = new ArrayList<>();
        ArrayList<String> c1_stocks = new ArrayList<>();
        ArrayList<String> c2_stocks = new ArrayList<>();
        ArrayList<String> c3_stocks = new ArrayList<>();
        ArrayList<String> c4_stocks = new ArrayList<>();
        ArrayList<String> c5_stocks = new ArrayList<>();
        ArrayList<String> c6_stocks = new ArrayList<>();
        ArrayList<String> c7_stocks = new ArrayList<>();
        ArrayList<String> c8_stocks = new ArrayList<>();

        ArrayList<Integer> c1_amounts = new ArrayList<>();
        ArrayList<Integer> c2_amounts = new ArrayList<>();
        ArrayList<Integer> c3_amounts = new ArrayList<>();
        ArrayList<Integer> c4_amounts = new ArrayList<>();
        ArrayList<Integer> c5_amounts = new ArrayList<>();
        ArrayList<Integer> c6_amounts = new ArrayList<>();
        ArrayList<Integer> c7_amounts = new ArrayList<>();
        ArrayList<Integer> c8_amounts = new ArrayList<>();

        ArrayList<ArrayList<Integer>> amounts = new ArrayList<>();
        ArrayList<ArrayList<String>> tickers = new ArrayList<>();

        stockMarket = new StockMarket(stocks);

        Map<String, Integer> c1, c2, c3, c4, c5, c6, c7, c8;

        stocks.put("MCD", 23.56);
        stocks.put("SBUX",53.56);
        stocks.put("AAPL", 123.456);

        clients.add(1);
        clients.add(2);
        clients.add(3);
        clients.add(4);
        clients.add(5);
        clients.add(6);
        clients.add(7);
        clients.add(8);

        c1 = new HashMap<>();
        c2 = new HashMap<>();
        c3 = new HashMap<>();
        c4 = new HashMap<>();
        c5 = new HashMap<>();
        c6 = new HashMap<>();
        c7 = new HashMap<>();
        c8 = new HashMap<>();

        c1.put("MCD", 5);
        c1.put("AAPL", 20);
        c2.put("SBUX", 10);
        c6.put("AAPL", 30);
        c7.put("AAPL", 10);
        c7.put("MCD", 10);
        c7.put("SBUX", 10);

        c1_amounts.add(5);
        c1_amounts.add(2);
        c1_amounts.add(1);
        c1_stocks.add("AAPL");
        c1_stocks.add("AAPL");
        c1_stocks.add("MCD");

        c2_amounts.add(5);
        c2_amounts.add(2);
        c2_stocks.add("SBUX");
        c2_stocks.add("SBUX");

        c3_amounts.add(7);
        c3_amounts.add(5);
        c3_amounts.add(1);
        c3_stocks.add("AAPL");
        c3_stocks.add("SBUX");
        c3_stocks.add("MCD");

        c4_amounts.add(5);
        c4_amounts.add(3);
        c4_amounts.add(1);
        c4_stocks.add("AAPL");
        c4_stocks.add("MCD");
        c4_stocks.add("MCD");

        c5_amounts.add(5);
        c5_amounts.add(2);
        c5_amounts.add(1);
        c5_stocks.add("AAPL");
        c5_stocks.add("AAPL");
        c5_stocks.add("SBUX");

        c6_amounts.add(10);
        c6_amounts.add(7);
        c6_stocks.add("AAPL");
        c6_stocks.add("AAPL");

        c7_amounts.add(5);
        c7_amounts.add(2);
        c7_amounts.add(1);
        c7_stocks.add("MCD");
        c7_stocks.add("SBUX");
        c7_stocks.add("SBUX");

        c8_amounts.add(4);
        c8_stocks.add("SBUX");

        os.add(c1);
        os.add(c2);
        os.add(c3);
        os.add(c4);
        os.add(c5);
        os.add(c6);
        os.add(c7);
        os.add(c8);

        tickers.add(c1_stocks);
        tickers.add(c2_stocks);
        tickers.add(c3_stocks);
        tickers.add(c4_stocks);
        tickers.add(c5_stocks);
        tickers.add(c6_stocks);
        tickers.add(c7_stocks);
        tickers.add(c8_stocks);

        amounts.add(c1_amounts);
        amounts.add(c2_amounts);
        amounts.add(c3_amounts);
        amounts.add(c4_amounts);
        amounts.add(c5_amounts);
        amounts.add(c6_amounts);
        amounts.add(c7_amounts);
        amounts.add(c8_amounts);


        Simulation sim = new Simulation(8, 3, 1L, stocks, clients, os, amounts, tickers, true, stockMarket);

        Thread tsim = new Thread(sim);

        tsim.start();

        tsim.join();
    }
}
