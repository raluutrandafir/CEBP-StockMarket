package com.stock.miscellaneous;

public class BuyEventMessage extends EventMessage{

    public BuyEventMessage(long clientId, int amount, String ticker, double price, Type transactionType, String date) {
        super(clientId, amount, ticker, price, transactionType, date);
    }

    @Override
    public String toString() {
        return "Buy Transaction: client " + super.getClientId() + " decided to buy stock: " + super.getTicker() + " stock amount: " + super.getAmount() + " for " + super.getPrice() +
                " per stock. Date: " + super.getDate() + "\n";
    }
}
