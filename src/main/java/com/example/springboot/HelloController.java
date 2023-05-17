package com.example.springboot;

import java.io.IOException;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.http.client.ClientProtocolException;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class HelloController {

	private static final Logger logger = LogManager.getLogger(HelloController.class);
	private static final  ArrayList<String> MBTI_Table = new ArrayList<>(Arrays.asList("ENFJ", "ENFP", "ENTJ", "ENTP", "ESFJ","ESFP","ESTJ","ESTP","INFJ","INFP","INTJ","INTP","ISFJ","ISFP","ISTJ","ISTP"));

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public boolean validateLogin(@RequestBody LoginObject login) {

		if (login == null)
			return false;
		String username = login.getUsername();
		String password = login.getPassword();

		// simple check
		if ("myid".equalsIgnoreCase(username) && "mypass".equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Autowired
	KafkaTemplate<String, MBTIEntity> KafkaJsontemplate;
	KafkaTemplate<String, SurveyEntity> SurveyKafkaJsontemplate;

	String TOPIC_NAME = "item";

	@RequestMapping(path = "/addMBTI", method = RequestMethod.POST)
	public String addMBTI(@RequestBody MBTIEntity mbtiEntity) throws ClientProtocolException, IOException {

		if (mbtiEntity == null) {
		   	logger.info("Wrong Parameters");
			return "Wrong Parameters";
		}

		Date date = new Date();
      		//This method returns the time in millis
      		long startTime = date.getTime();
		mbtiEntity.setStartTime(startTime);

		int mbtiIndex = Integer.parseInt(mbtiEntity.getMbti());
                final Span span = GlobalTracer.get().activeSpan();
       	        if (span != null && mbtiIndex >=0) {
           	    span.setTag("topic.name", TOPIC_NAME);
           	    span.setTag("topic.mbti", MBTI_Table.get(mbtiIndex));
           	    span.setTag("topic.team", mbtiEntity.getTeam());
           	} else {
		    logger.info("No span");
		}
		HttpRequest.sendPostRequest("http://user.mbti.net:8887/userProcess", mbtiEntity);

		String mbti = mbtiEntity.getMbti();
		String team = mbtiEntity.getTeam();
		KafkaJsontemplate.send(TOPIC_NAME, mbtiEntity);
  	   	logger.info(startTime + " - Topic Name: " + TOPIC_NAME + ", mbti: " + mbti + ", team: " + team);

		// System.out.println(mbtiEntity.getUsername());
		return "I got it successfully ::." + mbti + " " + team;
	}

        @RequestMapping(path = "/addSurvey", method = RequestMethod.POST)
        public String addSurvey(@RequestBody SurveyEntity surveyEntity) throws ClientProtocolException, IOException {

                if (surveyEntity == null) {
                        logger.info("Wrong Parameters");
                        return "Wrong Parameters";
                }

                Date date = new Date();
                //This method returns the time in millis
                long startTime = date.getTime();
                surveyEntity.setStartTime(startTime);
/*
                int mbtiIndex = Integer.parseInt(mbtiEntity.getMbti());
                final Span span = GlobalTracer.get().activeSpan();
                if (span != null && mbtiIndex >=0) {
                    span.setTag("topic.name", TOPIC_NAME);
                    span.setTag("topic.mbti", MBTI_Table.get(mbtiIndex));
                    span.setTag("topic.team", mbtiEntity.getTeam());
                } else {
                    logger.info("No span");
                }
		*/
               
				String nickname = surveyEntity.getNickname();
                String datadog_user = surveyEntity.getDatadog_user();
                String job = surveyEntity.getJob();
				SurveyKafkaJsontemplate.send(TOPIC_NAME, surveyEntity);
                logger.info(startTime + " - Topic Name: " + TOPIC_NAME + ", job: " + job + ", datadog_user: " + datadog_user, ", nickname: " + nickname);

                // System.out.println(mbtiEntity.getUsername());
                return "I got it successfully ::." + nickname + " " + job + " " + datadog_user;
        }
}
