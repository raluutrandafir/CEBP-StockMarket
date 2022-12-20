package com.stock.miscellaneous;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.stock.entities.Transaction;

public class MessageSender {

    public static void sendBuyRequest(Transaction buy){
        String QUEUE_NAME = "buyRequest";
        SendMessage(QUEUE_NAME, buy.toString());
    }
    public static void sendSellOffer(Transaction sell){
        String QUEUE_NAME = "sellOffer";
        SendMessage(QUEUE_NAME, sell.toString());
    }
    public static void sendTerminatedTransactionMessages(Transaction terminatedTransaction){
        String QUEUE_NAME = "terminated";
        SendMessage(QUEUE_NAME, terminatedTransaction.toString());
    }

    private static void SendMessage(String queueName, String payload) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, payload.getBytes());
            System.out.println(" [x] Sent '" + payload + "'");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
