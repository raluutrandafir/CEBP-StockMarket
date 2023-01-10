package com.stock.miscellaneous;

public class TerminatedEventMessage extends  EventMessage{
    private long secondClient = -1;

    public TerminatedEventMessage(long clientId,long secondClient, int amount, String ticker, double price, Type transactionType, String date) {
        super(clientId, amount, ticker, price, transactionType, date);
        this.secondClient = secondClient;
    }

    public String toString(){
        return "Terminated transaction: " + "Transaction happend between : client " + super.getClientId() + " and client " + secondClient + " for stock: " + super.getTicker() + ". Amount traded    : " + super.getAmount() + " for " + super.getPrice() +
                " per stock. Date: " + super.getDate()+ "\n";
    }
}
