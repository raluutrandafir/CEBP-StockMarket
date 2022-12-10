package com.stock;

import com.stock.entities.Client;
import com.stock.miscellaneous.Type;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class App {

    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            //noinspection InfiniteLoopStatement
            while(true) {
                // opening new Socket mock Client
                try {
                    Socket s = new Socket("localhost", 5000);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Socket client = serverSocket.accept();

                Client stockClient = getClient(client, "Seller");
                System.out.println(stockClient);
                new Thread(stockClient).start();
                Thread.sleep(100000);
            }
        } catch(IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }

    private static Client getClient(Socket client, String clientType) throws Exception {
        BufferedReader input = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        System.out.println(client);
        if (clientType.equals("Seller"))
            return new Client(1, Type.SELLER) {
            };
        else if (clientType.equals("Buyer"))
            return new Client(2, Type.BUYER);

        return null;
    }
}
