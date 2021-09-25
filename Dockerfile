FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S springuser -G spring
USER springuser
WORKDIR /home/springuser
COPY build/libs/bookshop-1.0.0.jar app.jar
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]