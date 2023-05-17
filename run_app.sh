#!/bin/bash

export DD_VERSION="2023May18"
export API_KEY=""
export DD_SERVICE="survey-service"
export DD_DATA_STREAMS_ENABLED="true"
export DD_LOGS_INJECTION=true
export DD_TRACE_METHODS="com.example.springboot.Application[*];com.example.springboot.HelloController[*];com.example.springboot.HttpRequest[*]"
java -Dlog4j.configurationFile=/home/ec2-user/projects/mbti-service/src/main/resources/log4j2.xml -Ddd.logs.injection=true -Ddd.appsec.enabled=true -Ddd.trace.header.tags=X-Forwarded-For:http.custom.x-forwarded-for -Ddd.profiling.enabled=true -javaagent:./dd-java-agent.jar -jar target/spring-boot-complete-0.0.1-SNAPSHOT.jar --server.port=8888
#java -Dlog4j.configurationFile=/home/ec2-user/projects/mbti-service/src/main/resources/log4j2.xml -Ddd.logs.injection=true -Ddd.appsec.enabled=true -Ddd.trace.header.tags=X-Forwarded-For:http.custom.x-forwarded-for -Ddd.profiling.enabled=true -Ddd.logs.injection=true -javaagent:./dd-java-agent.jar -jar target/spring-boot-complete-0.0.1-SNAPSHOT.jar --server.port=8888


# Reference
#
# export DD_PROFILING_API_KEY=
#export DD.TRACE.JMX.TAGS="jmx_tag:first_instance"
#export DD_VERSION="2021-Mar-29-A"
#java -Ddd.service.name=jacky-springboot-api -Ddd.appsec.enabled=true -Ddd.trace.jmx.tags="jmx-tag:first-instance" -Ddd.tags="release_note:app.datadoghq.com/notebook/295024/release-note-11-nov-2020" -Ddd.trace.debug=false -Ddd.trace.header.tags=X-Forwarded-For:http.custom.x-forwarded-for -Ddd.trace.db.client.split-by-instance=true -Ddd.jdbc.analytics.enabled=true -Ddd.trace.global.tags=secondary:myjava -Ddd.profiling.enabled=true -javaagent:/home/ec2-user/projects/gs-rest-service/complete/lib/dd-java-agent.jar -Ddd.logs.injection=true -Dlog4j.configurationFile=/home/ec2-user/projects/gs-rest-service/complete/src/main/resources/log4j2.xml -jar ./target/gs-rest-service-0.1.0.jar

