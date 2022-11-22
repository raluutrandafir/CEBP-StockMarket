package Entities;

import miscellaneous.Type;
import java.util.concurrent.ConcurrentHashMap;

// Q: should the stock market have a list of stocks
// or should it extract the stocks from the offers on the market?

public class StockMarket {
    private ConcurrentHashMap<Long, Offer> all_offers; // arraylist of all buyer and seller offers
    private ConcurrentHashMap<Long, Transaction> all_transactions; // arraylist of all the transactions done on the market

    public StockMarket() {
        this.all_offers = new ConcurrentHashMap<>();
        this.all_transactions = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<Long, Offer> getAll_offers() {
        return all_offers;
    }

    // Get offer by ticker and type
    public ConcurrentHashMap<Long, Offer> getOffersByTickerAndType(String ticker, Type type) {
        ConcurrentHashMap<Long, Offer> offers = new ConcurrentHashMap<>();
        for(Offer offer : all_offers.values()){
            // if the current offer has the same ticker as the requested one and the same type
            if(offer.getStock().getTicker().equals(ticker) && offer.getType().equals(type))
                offers.put(offer.getId(), offer);
        }
        return offers;
    }

    // Get offers by type only
    public ConcurrentHashMap<Long, Offer> getOffersByType(Type type) {
        ConcurrentHashMap<Long, Offer> typeOffers = new ConcurrentHashMap<>();
        for(Offer offer : all_offers.values()){
            // if the current offer has the same type as the requested one
            if(offer.getType().equals(type))
                typeOffers.put(offer.getId(), offer);
        }
        return typeOffers;
    }

    // Get offers by ticker only
    public ConcurrentHashMap<Long, Offer> getOffersByTicker(String ticker) {
        ConcurrentHashMap<Long, Offer> tickerOffers = new ConcurrentHashMap<>();
        for(Offer offer : all_offers.values()){
            // if the current offer has the same ticker as the requested one
            if(offer.getStock().getTicker().equals(ticker))
                tickerOffers.put(offer.getId(), offer);
        }
        return tickerOffers;
    }

    public ConcurrentHashMap<Long, Transaction> getAll_transactions() {
        return all_transactions;
    }

}
