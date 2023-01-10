package com.stock.miscellaneous;

public class SellEventMessage extends EventMessage{

    public SellEventMessage(long clientId, int amount, String ticker, double price, Type transactionType, String date) {
        super(clientId, amount, ticker, price, transactionType, date);
    }
    @Override
    public String toString() {
        return "Sell Transaction: client " + super.getClientId() + " decided to sell stock: " + super.getTicker() + " stock amount: " + super.getAmount() + " for " + super.getPrice() +
                " per stock. Date: " + super.getDate() + "\n";
    }
}
