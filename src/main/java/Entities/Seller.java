package Entities;

import java.util.ArrayList;

public class Seller extends Client{
    public Seller(long id, float money, ArrayList<Stock> stocks) {
        super(id, money, stocks);
    }

    private void sellStocks(){
    }
}
