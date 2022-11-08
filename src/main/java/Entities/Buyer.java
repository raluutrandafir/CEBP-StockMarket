package Entities;

import java.util.ArrayList;

public class Buyer extends Client{
    public Buyer(long id, float money, ArrayList<Stock> stocks) {
        super(id, money, stocks);
    }

    private void buyStocks(){
    }
}
