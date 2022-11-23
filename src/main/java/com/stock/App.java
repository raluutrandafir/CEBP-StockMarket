package com.stock;

import com.stock.entities.Buyer;
import com.stock.entities.Client;
import com.stock.entities.Seller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            //noinspection InfiniteLoopStatement

            while(true) {
                Socket mockClient = null;
                // opening new Socket mock Client
                try {
                    mockClient = new Socket("localhost", 5000);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Socket client = serverSocket.accept();


                Client stockClient = getClient(mockClient, "Seller");
                System.out.println(stockClient);
                stockClient.run();
//                new Thread(stockClient).start();
                Thread.sleep(1000);
            }
        } catch(IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

    private static Client getClient(Socket client, String clientType) throws Exception {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println(client);
        if (clientType.equals("Seller")) {
            return new Seller(client, input);
        }
        else if (clientType.equals("Buyer")){
            return new Buyer(client, input);
        }

        return null;
    }
}
