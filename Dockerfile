FROM openjdk:11-jdk
COPY target/verifit-0.0.1-SNAPSHOT.jar verifit-lucas-1.0.0.jar
ENTRYPOINT ["java","-jar","verifit-lucas-1.0.0.jar"]
EXPOSE 8000