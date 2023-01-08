package com.stock.miscellaneous;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventMessage {
    private long clientId;
    private long secondClient = -1;
    private int amount;
    private String ticker;
    private double price;
    private Type transactionType;
    private String date;

    public EventMessage(long clientId, long secondClient, int amount, String ticker, double price, Type transactionType, String date) {
        this.clientId = clientId;
        this.secondClient = secondClient;
        this.amount = amount;
        this.ticker = ticker;
        this.price = price;
        this.transactionType = transactionType;
        this.date = date;
    }

    public EventMessage(long clientId, int amount, String ticker, double price, Type transactionType, String date) {
        this.clientId = clientId;
        this.amount = amount;
        this.ticker = ticker;
        this.price = price;
        this.transactionType = transactionType;
        this.date = date;
    }

    //empty constructor to be used in the gson responsible class
    public EventMessage() { }

    public String toString() {
        StringBuilder delimiter = new StringBuilder(" ");
        String typeOfTransaction = "";
        if (transactionType == Type.BUYER)
            typeOfTransaction += "buy";
        else
            typeOfTransaction += "sell";
        //if a second client exists in a transaction, it s a terminated transaction
        if (secondClient != -1)
            return "Terminated transaction: " + "Transaction happend between : client " + clientId + " and client " + secondClient + " for stock: " + ticker + ". Amount traded    : " + amount + " for " + price +
                    " per stock. Date: " + date+ "\n";
        //else it's a buy/sell transaction
        return typeOfTransaction.toUpperCase() + " Transaction: client " + clientId + " decided to " + typeOfTransaction + " stock: " + ticker + " stock amount: " + amount + " for " + price +
                " per stock. Date: " + date + "\n";
    }
}
