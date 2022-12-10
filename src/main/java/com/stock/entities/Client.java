package com.stock.entities;

import com.stock.miscellaneous.ProtectedList;
import com.stock.miscellaneous.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class Client implements Runnable{
    //private Socket socket;
    private String ClientID;
    final static StockMarket stockMarket = new StockMarket();
 //   private BufferedReader input;
 //    private PrintWriter writer;
    protected Type clientType; // buyer or seller
    protected ArrayList<Transaction> transactionHistory;
    private ProtectedList<Transaction> pendingOffers;

    public Client(Socket socket, BufferedReader input) throws IOException {
        System.out.println("New client created :)");
      //  this.socket = socket;
       // this.input = input;
//        this.writer = new PrintWriter(socket.getOutputStream(), true);
       // this.ClientID = ClientID;
        this.transactionHistory = new ArrayList<>();
        this.pendingOffers = new ProtectedList<>();
    }

    @Override
    public void run() {
        boolean end = false;
        String command;
        while (!end) {
           /* try {
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
                            if( removeTransaction(transactionHistory.get(index))) {
                                transactionHistory.remove(index);
                                doTransaction(new Transaction(name, amount, price, clientType));
                            }
                        } else
                            doTransaction(new Transaction(name, amount, price, clientType));
                        break;
                    case "Transactions":
                        sendList(stockMarket.getTerminated());
                        break;
                    case "Sell offers":
                        sendList(stockMarket.getSellOffers());
                        break;
                    case "Buy offers":
                        sendList(stockMarket.getBuyRequests());
                        break;
                    case "All offers":
                        sendList(stockMarket.getAllOffers());
                        break;
                    case  "My offers":
                        transactionHistory.removeIf(transaction -> transaction.getAmount() == 0);
                        sendList(transactionHistory);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    public boolean isSearching(Transaction sell, Transaction buy) {
        // creates a transaction
        Transaction transaction = stockMarket.createTransaction(sell, buy);

        // if the transaction is finished successfully
        if (stockMarket.finishTransaction(transaction, sell, buy)) {
            // adjust the money/stock amount for both clients
            sell.setAmount(sell.getAmount() - transaction.getAmount());
            buy.setAmount(buy.getAmount() - transaction.getAmount());

            //if there are still money left from the previous sell offer, adjust the amount and add the offer again
            if (sell.getAmount() > 0)
                stockMarket.addSellOffer(sell);

            //if there are still money left from the previous buy offer, adjust the amount and add the offer again
            if (buy.getAmount() > 0)
                stockMarket.addBuyRequest(buy);
            return false;
        }
        return true;
    }

    // Close the Socket connection
    private void closeConnection() throws IOException {
      //  removeTransactions(transactionHistory);
      //  transactionHistory.clear();
       // input.close();
//        writer.close();
   //     socket.close();
    }

    // Read from the keyboard
   // private String readInput() throws IOException {
     //   return input.readLine();
  //  }

    private void sendList(List list) {
//        writer.println(list.size());
        System.out.println(list.size());
        for (Object o : list) {
//            writer.println(o.toString());
            System.out.println(o.toString());
        }
    }

    protected abstract void doTransaction(Transaction t);

    protected abstract void removeTransactions(List<Transaction> myTransactions);

    protected abstract boolean removeTransaction(Transaction myTransaction);
}
