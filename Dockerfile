FROM openjdk:11.0.13-slim-buster
RUN groupadd -r spring && useradd -r spring -g spring
USER spring:spring
ARG WEB_JAR_FILE=pet-clinic-web/target/pet-clinic-web-0.0.1-SNAPSHOT.jar
ARG DATA_JAR_FILE=pet-clinic-data/target/pet-clinic-data-0.0.1-SNAPSHOT.jar
COPY ${DATA_JAR_FILE} data.jar
COPY ${WEB_JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]