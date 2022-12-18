package com.stock.miscellaneous;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class TradingTape {

    private static void listenToQueue(String queueName) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received message'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
    public static void main(String[] argv) throws Exception {
        /*String  queueName = "hello";

        Thread t = new Thread(()->{
        try {
            listenToQueue(queueName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        });

        t.start();*/
        ArrayList<String> queueNames = new ArrayList<>(Arrays.asList("hello", "general"));
        for(String queueName : queueNames)
        {
            Thread t = new Thread(() -> {
                try {
                    listenToQueue(queueName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}

