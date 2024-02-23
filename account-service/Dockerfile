FROM openjdk:17-jdk-slim
WORKDIR /opt
ENV PORT 8081
EXPOSE 8081
COPY target/*.jar /opt/account-service.jar
ENTRYPOINT exec java $JAVA_OPT -jar account-service.jar