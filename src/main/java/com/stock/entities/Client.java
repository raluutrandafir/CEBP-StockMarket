package com.stock.entities;

import com.stock.miscellaneous.Type;

import java.util.HashMap;
import java.util.List;

public class Client implements Runnable{
    private long ClientID; // Client will have a unique id
    protected Type clientType; // buyer or seller
    protected StockMarket stockMarket;
    protected HashMap<String,Integer> clientOffers;


    public Client(long clientId, Type clientType, StockMarket stockMarket, HashMap<String,Integer> clientOffers) {
        System.out.println("New client created :" + clientId);
        this.ClientID = clientId;
        this.clientType = clientType;
        this.stockMarket = stockMarket;
        this.clientOffers = clientOffers;
    }

    @Override
    public void run() {
        int i=0;
        boolean end = false;
        String command;
        while (!end) {
            if(clientType == Type.BUYER){

            }
            else{

            }
            i++;
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
