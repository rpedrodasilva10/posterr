FROM openjdk:8-jre-slim
VOLUME /app
COPY target/app.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-Duser.timezone=GMT-3:00", "-jar","./app.jar"]