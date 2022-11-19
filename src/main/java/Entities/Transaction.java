package Entities;

import java.time.LocalDateTime;

public class Transaction {
    private long buyer_id;
    private long seller_id;
    private Stock stock;
    private LocalDateTime timestamp;

    public Transaction(long buyer_id, long seller_id, Stock stock, LocalDateTime timestamp) {
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.stock = stock;
        this.timestamp = timestamp;
    }

    private void adjustMoney(){ // adjust money of the buyer's/seller's according to what is bought or sold
    }

    private void adjustShares(){ // adjust amount of shares of the buyer's/seller's according to what is bought or sold
    }

}
