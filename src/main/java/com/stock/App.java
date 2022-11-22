package com.stock;

import com.stock.entities.Buyer;
import com.stock.entities.Client;
import com.stock.entities.Seller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            //noinspection InfiniteLoopStatement
            while(true) {
                Socket client = serverSocket.accept();

                Client stockClient = getClient(client);
                System.out.println(stockClient);
                new Thread(stockClient).start();
            }
        } catch(IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

    private static Client getClient(Socket client) throws Exception {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        String clientType = input.readLine();
        System.out.println(client);
        if (clientType.equals("Seller"))
            return new Seller(client, input);
        else if (clientType.equals("Buyer"))
            return new Buyer(client, input);

        return null;
    }
}
