package Entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String clientName;
    private String secondClient = null;
    private int amount;
    private float price;
    private String transactionType;
    private Date date;
    private SimpleDateFormat format;

    public Transaction(String clientName, int amount, float price, String transactionType) {
        this.clientName = clientName;
        this.amount = amount;
        this.price = price;
        transactionType = transactionType;
        date = new Date(System.currentTimeMillis());
        format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    public Transaction(Transaction sell, Transaction buy){
        this.clientName = sell.clientName;
        this.secondClient = buy.clientName;
        this.price = sell.price;
        this.amount = Math.min(sell.amount, buy.amount);
        transactionType = "Sold to: ";
        date = new Date(System.currentTimeMillis());
        format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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

        return clientName + " " + transactionType + delimiter.toString() + amount + " stocks for " + price +
                " per stock. Started date: " + format.format(date);
    }

    private void adjustMoney(){ // adjust money of the buyer's/seller's according to what is bought or sold
    }

    private void adjustShares(){ // adjust amount of shares of the buyer's/seller's according to what is bought or sold
    }

}
