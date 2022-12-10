package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private long clientId;
    private long secondClient = -1;
    private int amount;
    private String ticker;
    private float price;
    private Type transactionType;
    private Date date;
    private SimpleDateFormat format;

    public Transaction(long clientId, int amount, String ticker, float price, Type transactionType) {
        this.clientId = clientId;
        this.amount = amount;
        this.ticker = ticker;
        this.price = price;
        this.transactionType = transactionType;
        this.date = new Date(System.currentTimeMillis());
        this.format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public Transaction(Transaction sell, Transaction buy){
        this.clientId = sell.clientId;
        this.secondClient = buy.clientId;
        this.price = sell.price;
        this.amount = Math.min(sell.amount, buy.amount);
        this.transactionType = Type.SELLER;
        this.date = new Date(System.currentTimeMillis());
        this.format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder delimiter = new StringBuilder(" ");
        if (secondClient != -1)
            delimiter.append(secondClient).append(" ");

        return clientId + " " + transactionType + delimiter + amount + " stocks for " + price +
                " per stock. Started date: " + format.format(date);
    }
}
