package com.example.springboot;

import java.io.IOException;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.http.client.ClientProtocolException;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONObject;

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


	

	@RequestMapping(path = "/addMBTI", method = RequestMethod.POST)
	public String addMBTI(@RequestBody MBTIEntity mbtiEntity) throws ClientProtocolException, IOException {
		String TOPIC_NAME = "item";

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

	@RequestMapping(path = "/addSurvey", method = RequestMethod.POST, produces = "application/json; charset=UTF8")
	public String addSurvey(@RequestBody MBTIEntity mbtiEntity)
			throws ClientProtocolException, IOException {
		String TOPIC_NAME = "survey";
		// HttpHeaders headers = new HttpHeaders();
    	// headers.set("content-type", "json/application; charset=UTF-8");

		if (mbtiEntity == null) {
		   	logger.info("Wrong Parameters");
			//return ResponseEntity.badRequest().headers(headers).body("Wrong Parameters");
			return "Wrong Parameters";
			
		}

//		Date date = new Date();
//      		//This method returns the time in millis
//      		long startTime = date.getTime();
//		mbtiEntity.setStartTime(startTime);
//
//		int mbtiIndex = Integer.parseInt(mbtiEntity.getMbti());
//                final Span span = GlobalTracer.get().activeSpan();
//       	        if (span != null && mbtiIndex >=0) {
//           	    span.setTag("topic.name", TOPIC_NAME);
//           	    span.setTag("topic.mbti", MBTI_Table.get(mbtiIndex));
//           	    span.setTag("topic.team", mbtiEntity.getTeam());
//           	} else {	
//		    logger.info("No span");
//		}
//		HttpRequest.sendPostRequest("http://user.mbti.net:8887/userProcess", mbtiEntity);
//
		String nickname = mbtiEntity.getNickname();
		String job = mbtiEntity.getJob();
		String datadog_user = mbtiEntity.getDatadog_user();
		KafkaJsontemplate.send(TOPIC_NAME, mbtiEntity);
//  	   	logger.info(startTime + " - Topic Name: " + TOPIC_NAME + ", job: " + job + ", nickname: " + nickname + ", datadog_user: " + datadog_user);
//
//		// System.out.println(mbtiEntity.getUsername());

		JSONObject jo1 = new JSONObject();
		jo1.put(nickname, nickname);
		jo1.put(job, job);
		jo1.put(datadog_user, datadog_user);

		//String jsonResponse = jo1.toJSONString();
		String jsonResponse = "{\"nickname\" : \"" + nickname + "\", \"win\" : \"yes\"";

		return jsonResponse;
	}

}
