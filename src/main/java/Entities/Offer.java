package Entities;

import miscellaneous.Type;

public class Offer {
    private long offer_id; // offer id
    private Client client; // basic data from client
    private Stock stock; // basic data from stock
    private Type type; // buyer or seller

    public Offer(Client client, Stock stock, Type type, long id) {
        this.offer_id= id;
        this.client = client;
        this.stock = stock;
        this.type = type;
    }

    public long getId() {
        return offer_id;
    }

    public Stock getStock() {
        return stock;
    }

    public Type getType() {
        return type;
    }
}
