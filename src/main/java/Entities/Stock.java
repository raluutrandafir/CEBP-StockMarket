package Entities;

public class Stock {
    private long stock_id; // the id of the stock
    private float value; // the actual value of the stock
    private int max_shares; // the maximum amount of shares of the stock
    private int current_shares; // the current amount of shares owned by the client/used for transaction (buy/sell)

    public Stock(long stock_id, float value, int max_shares, int current_shares) {
        this.stock_id = stock_id;
        this.value = value;
        this.max_shares = max_shares;
        this.current_shares = current_shares;
    }
}
