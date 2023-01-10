package com.stock.miscellaneous;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stock.entities.Transaction;

public class Util {
    public Util() {
    }

    /*public String fromObjectToJson(EventMessage transaction){
       // Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        String payload = gson.toJson(transaction);
        return payload;
    }


    public Object fromJsonStringToObject(String jsonString) throws JsonProcessingException {
        //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        EventMessage object = gson.fromJson(jsonString, EventMessage.class);
        return object;
    }*/
    public String fromBuyToJson(BuyEventMessage transaction){
        // Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        String payload = gson.toJson(transaction);
        return payload;
    }
    public Object fromJsonStringToBuy(String jsonString) throws JsonProcessingException {
        //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        BuyEventMessage object = gson.fromJson(jsonString, BuyEventMessage.class);
        return object;
    }

    public String fromSellToJson(SellEventMessage transaction){
        // Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        String payload = gson.toJson(transaction);
        return payload;
    }
    public Object fromJsonStringToSell(String jsonString) throws JsonProcessingException {
        //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        SellEventMessage object = gson.fromJson(jsonString, SellEventMessage.class);
        return object;
    }
    public String fromTerminatedToJson(TerminatedEventMessage transaction){
        // Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        String payload = gson.toJson(transaction);
        return payload;
    }
    public Object fromJsonStringToTerminated(String jsonString) throws JsonProcessingException {
        //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
        Gson gson = new Gson();
        TerminatedEventMessage object = gson.fromJson(jsonString, TerminatedEventMessage.class);
        return object;
    }
}
