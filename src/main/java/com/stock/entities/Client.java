package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.util.List;

public class Client implements Runnable{
    private long ClientID; // Client will have a unique id
    protected Type clientType; // buyer or seller


    public Client(long clientId, Type clientType) {
        System.out.println("New client created :" + clientId);
        this.ClientID = clientId;
        this.clientType = clientType;
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

    private void sendList(List list) {
//        writer.println(list.size());
        System.out.println(list.size());
        for (Object o : list) {
//            writer.println(o.toString());
            System.out.println(o.toString());
        }
    }
}
