package com.stock.miscellaneous;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.stock.entities.Transaction;

public class Util {
    public Util() {
    }

    public String fromObjectToJson(Transaction transaction){
        /*String jsonStr="";
        ObjectMapper Obj = new ObjectMapper();
        try {
            jsonStr = Obj.writeValueAsString(object);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;*/
        Gson gson = new Gson();
        String payload = gson.toJson(transaction);
        return payload;
    }

    public Object fromJsonStringToObject(String jsonString) throws JsonProcessingException {
       /* ObjectMapper mapper = new ObjectMapper();
        Transaction jsonObject = mapper.readValue(jsonString, Transaction.class);
        return jsonObject;*/
        Gson gson = new Gson();
        Transaction object = gson.fromJson(jsonString, Transaction.class);
        return object;
    }
}
