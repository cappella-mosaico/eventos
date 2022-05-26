FROM openjdk:17-jdk

COPY ./target/eventos-0.0.1-SNAPSHOT.jar /eventos.jar

CMD ["java", "-jar", "/eventos.jar"]