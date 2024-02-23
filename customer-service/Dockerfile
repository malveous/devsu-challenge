FROM openjdk:17-jdk-slim
WORKDIR /opt
ENV PORT 8082
EXPOSE 8082
COPY target/*.jar /opt/customer-service.jar
ENTRYPOINT exec java $JAVA_OPT -jar customer-service.jar