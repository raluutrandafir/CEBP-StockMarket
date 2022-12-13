package com.stock.miscellaneous;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQSender {

    public static void sendHelloMessage(String message){
        String QUEUE_NAME = "hello";
        SendMessage(QUEUE_NAME, message);
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
