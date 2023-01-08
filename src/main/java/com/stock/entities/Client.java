package com.stock.entities;

import com.stock.miscellaneous.EventMessage;
import com.stock.miscellaneous.MessageSender;
import com.stock.miscellaneous.ProtectedList;
import com.stock.miscellaneous.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable{
    private final long clientId; // Client will have a unique id
    protected Type clientType; // buyer or seller
    protected StockMarket stockMarket;
    private final ArrayList<Integer> amounts;
    private final ArrayList<String> tickers;
    private final ProtectedList myTransactionHistory = new ProtectedList();

    public Client(long clientId, Type clientType, StockMarket stockMarket, ArrayList<Integer> amounts, ArrayList<String> tickers) {
        this.clientId = clientId;
        this.clientType = clientType;
        this.stockMarket = stockMarket;
        this.amounts = amounts;
        this.tickers = tickers;
    }


    @Override
    public void run() {

        for (int i = 0; i < amounts.size(); i++) {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String name = tickers.get(i);
            int amount = amounts.get(i);
            double price = stockMarket.getStocks().get(name);
            var transaction =new Transaction(clientId, amount, name, price, clientType);
            stockMarket.addOffer(transaction);
            if(clientType == Type.BUYER)
                MessageSender.sendBuyRequest(new EventMessage(transaction.getClientId(), transaction.getAmount(), transaction.getTicker(), transaction.getPrice(), transaction.getType(), transaction.getDate()));
            //int amount, String ticker, double price, Type transactionType, Date date, SimpleDateFormat format
            else {
                MessageSender.sendSellOffer(new EventMessage(transaction.getClientId(), transaction.getAmount(), transaction.getTicker(), transaction.getPrice(), transaction.getType(), transaction.getDate()));
            }
        }
    }
}
