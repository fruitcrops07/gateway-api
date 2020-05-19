FROM openjdk:8-alpine
MAINTAINER k-marx macalalag

copy ./target/gateway-api.jar app.jar

copy ./src/main/resources/application.yml application.yml

ENV JAVA_OPTS="-Xmx1024m"

EXPOSE 8080
CMD java $JAVA_OPTS -Djava.security.edg=file:/dev/./urandom -Dspring.config.location=/application.yml -jar /app.jar