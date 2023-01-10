package com.stock.miscellaneous;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class MessageSender {

    public static Util utilClass = new Util();

    public static void sendBuyRequest(BuyEventMessage buy){
        String QUEUE_NAME = "buyRequest";
        SendMessage(QUEUE_NAME, utilClass.fromBuyToJson(buy));
    }
    public static void sendSellOffer(SellEventMessage sell){
        String QUEUE_NAME = "sellOffer";
        SendMessage(QUEUE_NAME, utilClass.fromSellToJson(sell));
    }
    public static void sendTerminatedTransactionMessages(TerminatedEventMessage terminatedTransaction){
        String QUEUE_NAME = "terminated";
        SendMessage(QUEUE_NAME, utilClass.fromTerminatedToJson(terminatedTransaction));
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
