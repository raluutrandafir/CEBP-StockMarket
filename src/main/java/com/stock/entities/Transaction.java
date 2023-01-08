package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private long clientId;
    private long secondClient = -1;
    private int amount;
    private String ticker;
    private double price;
    private Type transactionType;
    private Date date;
    private SimpleDateFormat format;

    public Transaction(long clientId, int amount, String ticker, double price, Type transactionType) {
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
        this.ticker = sell.ticker;
    }

    public String getDate(){ return format.format(date) ;}
    public SimpleDateFormat dateFormat(){ return format;}
    public long getClientId(){return clientId;}
    public long getSecondClientId(){return secondClient;}
    public  String getTicker(){return ticker;}

    public int getAmount() {
        return amount;
    }

    public Type getType() {
        return transactionType;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder delimiter = new StringBuilder(" ");
        String typeOfTransaction = "";
        if(transactionType == Type.BUYER)
            typeOfTransaction += "buy";
        else
            typeOfTransaction +="sell";
        //if a second client exists in a transaction, it s a terminated transaction
        if (secondClient != -1)
            return "\"Transaction [ClientId :"+ clientId + ", SecondClientId : " + secondClient + ", amount : " + amount + ", price : " + price + ", ticker : " + ticker + ", date : " + format.format(date) +  "]";

        //else it's a buy/sell transaction
        return "\"Transaction [ClientId :"+ clientId + ", TransactionType: " + transactionType + ", amount : " + amount + ", price :" + price + ", ticker : " + ticker + ", date : " + format.format(date) +  "]";
    }
}
