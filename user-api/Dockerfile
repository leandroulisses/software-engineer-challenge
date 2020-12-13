FROM openjdk:11-jre-slim

COPY target/user-api.jar .

EXPOSE 8080
CMD bash -c "printenv && java -jar user-api.jar"