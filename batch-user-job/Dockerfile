FROM openjdk:11-jre-slim

COPY target/batch-user-job.jar .

EXPOSE 8080
CMD bash -c "printenv && java -jar batch-user-job.jar"