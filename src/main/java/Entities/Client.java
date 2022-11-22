package Entities;

import miscellaneous.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Client implements Runnable{
    private Socket socket;
    private ArrayList<Stock> stocks; // owned stocks of client
    private static StockMarket stockMarket = new StockMarket();
    private BufferedReader input;
    private PrintWriter writer;
    protected Type type; // buyer or seller
    protected ArrayList<Transaction> transactionHistory;

    // Full constructor
    public Client(Socket socket, ArrayList<Stock> stocks, BufferedReader input, PrintWriter writer, Type type, List<Transaction> transactionHistory) throws IOException {\
        System.out.println("New client created :)");
        this.socket = socket;
        this.stocks = stocks;
        this.input = input;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.type = type;
        this.transactionHistory = new ArrayList<>();
    }

    public Client(Socket socket, BufferedReader input) throws IOException {
        System.out.println("New client created :)");
        this.socket = socket;
        this.input = input;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public void run() {
        boolean end = false;
        String command;
        while (!end) {
            try {
                command = readInput();
                if (command == null) {
                    closeConnection();
                    break;
                }
                String name;
                switch (command) {
                    case "end":
                        closeConnection();
                        end = true;
                        break;
                    case "offer":
                        name = readInput();
                        int amount = Integer.parseInt(readInput());
                        float price = Float.parseFloat(readInput());

                        if (name.contains("index")) {
                            String[] split = name.split(" index ");
                            int index = Integer.parseInt(split[1]);
                            name = split[0];
                            if( removeTransaction(mytransactionList.get(index))) {
                                mytransactionList.remove(index);
                                doTransaction(new Transaction(name, amount, price, type));
                            }
                        } else
                            doTransaction(new Transaction(name, amount, price, type));
                        break;
                    case "Transactions":
                        sendList(broker.getTerminated());
                        break;
                    case "Sell offers":
                        sendList(broker.getSellOffers());
                        break;
                    case "Buy offers":
                        sendList(broker.getBuyRequests());
                        break;
                    case "All offers":
                        sendList(broker.getAllOffers());
                        break;
                    case  "My offers":
                        mytransactionList.removeIf(transaction -> transaction.getAmount() == 0);
                        sendList(mytransactionList);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isSearching(Transaction sell, Transaction buy) {
        Transaction transaction = stockMarket.doTransaction(sell, buy);
        if (stockMarket.finishTransaction(transaction, sell, buy)) {
            sell.setAmount(sell.getAmount() - transaction.getAmount());
            buy.setAmount(buy.getAmount() - transaction.getAmount());

            if (sell.getAmount() > 0)
                broker.addSellOffer(sell);
            if (buy.getAmount() > 0)
                stockMarket.addBuyRequest(buy);
            return false;
        }
        return true;
    }
}
