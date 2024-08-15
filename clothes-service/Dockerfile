FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/clothes-service.jar
ENTRYPOINT ["java", "-jar", "/app/clothes-service.jar"]