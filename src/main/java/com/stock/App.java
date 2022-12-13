package com.stock;

import com.stock.TradingTape.TradingTape;
import com.stock.entities.Buyer;
import com.stock.entities.Client;
import com.stock.entities.Seller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final String TOPIC = "my-kafka-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static void produce() {
        // Create configuration options for our producer and initialize a new producer
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        // We configure the serializer to describe the format in which we want to produce data into
        // our Kafka cluster
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Since we need to close our producer, we can use the try-with-resources statement to
        // create
        // a new producer
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            // here, we run an infinite loop to sent a message to the cluster every second
            for (int i = 0;; i++) {
                String key = Integer.toString(i);
                String message = "this is message " + Integer.toString(i);

                producer.send(new ProducerRecord<String, String>(TOPIC, key, message));

                // log a confirmation once the message is written
                System.out.println("sent msg " + key);
                try {
                    // Sleep for a second
                    Thread.sleep(1000);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not start producer: " + e);
        }
    }
    public static void main(String[] args) throws Exception {
       // var treadingTape = new TradingTape();
        Thread consumerThread = new Thread(TradingTape :: consume);
        consumerThread.start();

        Thread producerThread = new Thread(App::produce);
        producerThread.start();
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
            return new Seller(client, input);
        else if (clientType.equals("Buyer"))
            return new Buyer(client, input);

        return null;
    }
}
