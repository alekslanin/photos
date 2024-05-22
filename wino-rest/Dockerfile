FROM openjdk:17-jdk-slim
WORKDIR /app
ENV PORT 8080
EXPOSE 8080

COPY target/wino-rest-0.0.0-SNAPSHOT.jar /app/wino-rest-0.0.0-SNAPSHOT.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app/wino-rest-0.0.0-SNAPSHOT.jar
#RUN cd /app/ && jar -xvf /app/wino-rest-0.0.0-SNAPSHOT.jar && rm /app/wino-rest-0.0.0-SNAPSHOT.jar
#CMD java $JAVA_OPTS -cp /app/ org.springframework.boot.loader.JarLauncher