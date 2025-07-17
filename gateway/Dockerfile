FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/gateway.jar
ENTRYPOINT ["java", "-jar", "/app/gateway.jar"]