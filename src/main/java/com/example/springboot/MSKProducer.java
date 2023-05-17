package com.example.springboot;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MSKProducer {

	private static final String TOPIC = "item";
	private static final String BOOTSTRAP_SERVERS = "b-1.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-3.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-2.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092";

	public MSKProducer() {
		// TODO Auto-generated constructor stub
	}

	private void sendItem(String key, MBTIEntity message) {
		// Create configuration options for our producer
		Properties props = new Properties();
		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
		// We configure the serializer to describe the format in which we want to
		// produce data into
		// our Kafka cluster
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// wait until we get 10 messages before writing
		props.put("batch.size", "10");
		// no matter what happens, write all pending messages
		// every 2 seconds
		props.put("linger.ms", "2000");
		props.put("acks", "1");

		try (Producer<String, String> producer = new KafkaProducer<>(props)) {

//			producer.send(new ProducerRecord<String, String>(TOPIC, key, message));

			// log a confirmation once the message is written
			System.out.println("sent msg " + key);
		} catch (Exception e) {
			System.out.println("Could not start producer: " + e);
		}

	}

//	private static void produce() {
//		// Create configuration options for our producer
//		Properties props = new Properties();
//		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
//		// We configure the serializer to describe the format in which we want to
//		// produce data into
//		// our Kafka cluster
//		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//		// wait until we get 10 messages before writing
//		props.put("batch.size", "10");
//		// no matter what happens, write all pending messages
//		// every 2 seconds
//		props.put("linger.ms", "2000");
//		props.put("acks", "1");
//
//		// Since we need to close our producer, we can use the try-with-resources
//		// statement to
//		// create a new producer
//		try (Producer<String, String> producer = new KafkaProducer<>(props)) {
//			// here, we run an infinite loop to sent a message to the cluster every second
//			for (int i = 0;; i++) {
//				String key = Integer.toString(i);
//				String message = "this is message " + Integer.toString(i);
//
//				producer.send(new ProducerRecord<String, String>(TOPIC, key, message));
//
//				// log a confirmation once the message is written
//				System.out.println("sent msg " + key);
//				try {
//					// Sleep for a second
//					Thread.sleep(1000);
//				} catch (Exception e) {
//					break;
//				}
//			}
//		} catch (Exception e) {
//			System.out.println("Could not start producer: " + e);
//		}
//	}

}

