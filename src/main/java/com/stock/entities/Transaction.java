package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String clientName;
    private String secondClient = null;
    private int amount;
    private float price;
    private Type transactionType;
    private Date date;
    private SimpleDateFormat format;

    public Transaction(String clientName, int amount, float price, Type transactionType) {
        this.clientName = clientName;
        this.amount = amount;
        this.price = price;
        this.transactionType = transactionType;
        this.date = new Date(System.currentTimeMillis());
        this.format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public Transaction(Transaction sell, Transaction buy){
        this.clientName = sell.clientName;
        this.secondClient = buy.clientName;
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
        if (secondClient != null)
            delimiter.append(secondClient).append(" ");

        return clientName + " " + transactionType + delimiter + amount + " stocks for " + price +
                " per stock. Started date: " + format.format(date);
    }
}
