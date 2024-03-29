FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8086
ARG JAR_FILE=target/DevOps_Project-2.6.jar
COPY ${JAR_FILE} sallami/app.jar
ENTRYPOINT ["java","-jar","sallami/app.jar"]