package com.example.springboot;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

@SpringBootApplication
public class Application {
	private static final Logger logger = LogManager.getLogger(Application.class);
	private static final String TOPIC = "item";
	private static final String BOOTSTRAP_SERVERS = "b-1.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-3.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092,b-2.jackymskcluster1.w889sm.c4.kafka.ap-northeast-2.amazonaws.com:9092";

	public static void main(String[] args) {
		//Thread consumerThread = new Thread(Application::consume);
		//consumerThread.start();
		
		

//        Thread producerThread = new Thread(Application::produce);
//        producerThread.start();

		SpringApplication.run(Application.class, args);
	}

//	@KafkaListener(topics = TOPIC, groupId = "my-group-id")
//	public void listen(MBTIEntity mbti) throws ClientProtocolException, IOException {
//
//		System.out.println("Received mbti information : " + mbti.getMbti() + ", " + mbti.getTeam());
//		logger.info("Received mbti information : " + mbti.getMbti() + ", " + mbti.getTeam());
//		HttpRequest.sendPostRequest("http://user.mbti.net:8887/userProcess", mbti);
//	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}
}

