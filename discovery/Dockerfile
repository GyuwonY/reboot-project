FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/discovery.jar
ENTRYPOINT ["java", "-jar", "/app/discovery.jar"]