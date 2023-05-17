package com.example.springboot;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class HttpRequest {
        private static final Logger logger = LogManager.getLogger(HelloController.class);

	public HttpRequest() {
		// TODO Auto-generated constructor stub
	}

	public static void sendPostRequest(String url, MBTIEntity mbti_entity) throws ClientProtocolException, IOException {
//		String payload = """
//				data={
//				    "mbti": mbti_entity.getMbti(),
//				    "team": mbti_entity.getTeam()
//				}
//				""";

		JSONObject jo1 = new JSONObject();
		jo1.put("mbti", mbti_entity.getMbti());
		jo1.put("team", mbti_entity.getTeam());

		String payload = jo1.toJSONString();	
		StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);
                logger.info("payload: " + payload + ", code: " + response.getStatusLine().getStatusCode());
	}
}
