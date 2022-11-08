# CEBP-StockMarket

## Team Nemesis:

-	Eliza Mărțuică 
-	Raluca Trandafir
-	Andreea Miculescu
-	Silviu Grada

## Description

Implement/simulate a stock market where shares are being traded. The shares belong to the different companies listed on the stock market. Transactions are carried out by share buyers and sellers as follows:

-	sellers offer for sale a number of shares for a certain price per share
- buyers make demands of buying a number of shares for a certain price per share
-	when the price per share of an offer is the same as the price per share demanded by a buyer, a number of shares is traded, traded_shares = min (shares_sold, shares_demanded)
-	everyone has access to all the offers and demands as well as the entire history of transactions
-	offers and demands can be modified at any time, as long as there is no transaction currently in progress The implementation must contain a distributed system with one server and multiple clients, the clients being sellers and buyers. The simulation will consist of a program where clients are started on separate threads. The clients are guided by an algorithm that takes into account all the common knowledge (offers, demands and the transaction history)

## Functional Requirements

-	Entities can be seller(if he owns stocks) or buyer(if wants to buy stocks)
-	Entities can:
    -	Ask for certain amount of money for a stock he owns/he wants to sell
    -	Ask/Read for available stocks
    -	Ask/Request for a certain stock
    -	Check the price of a stock 
    -	Have an amount of money to buy a certain amount of stocks/ have an amount of stocks to sell at a certain price
    -	Keep track of a transactions history (optional)
-	The stock market:
    -	Keeps track of the current stocks(prices, offer/request)
    -	Adjust stocks based on what gets sold
    -	Match an offer for selling with an offer for buying
    -	Adjust the buyers stocks, money according to what he bought/Adjust a seller’s stocks, money amount according to what he sold
    -	Keep track of a transactions history
    -	Change stock’s price based on the supply demand (tdb)

## Non-Functional Requirements

-	System will be resilient to failures
-	A request will be fulfilled in at most 1 min, if not, the state of the request will remain on pending until it gets a response
-	Documentation   

## Architectural diagram

![image](https://user-images.githubusercontent.com/73436228/197861847-53f41c24-599e-4588-9974-33db1646c493.png)


