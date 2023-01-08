package com.stock.miscellaneous;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stock.entities.Transaction;

public class Util {
    public Util() {
    }

    public String fromObjectToJson(EventMessage transaction){
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
    }
}
