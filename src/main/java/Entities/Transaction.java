package Entities;

import java.util.ArrayList;

public class Transaction {
    private long buyer_id;
    private long seller_id;
    private Stock stock;

    public Transaction(long buyer_id, long seller_id, Stock stock) {
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.stock = stock;
    }

    private void adjustMoney(){ // adjust money of the buyer's/seller's according to what is bought or sold
    }

    private void adjustShares(){ // adjust amount of shares of the buyer's/seller's according to what is bought or sold
    }

}
