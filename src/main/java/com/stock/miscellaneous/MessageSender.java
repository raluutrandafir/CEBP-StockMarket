package com.stock.miscellaneous;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class MessageSender {

    public static Util utilClass = new Util();

    public static void sendBuyRequest(EventMessage buy){
        String QUEUE_NAME = "buyRequest";
        SendMessage(QUEUE_NAME, utilClass.fromObjectToJson(buy));
    }
    public static void sendSellOffer(EventMessage sell){
        String QUEUE_NAME = "sellOffer";
        SendMessage(QUEUE_NAME, utilClass.fromObjectToJson(sell));
    }
    public static void sendTerminatedTransactionMessages(EventMessage terminatedTransaction){
        String QUEUE_NAME = "terminated";
        SendMessage(QUEUE_NAME, utilClass.fromObjectToJson(terminatedTransaction));
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
