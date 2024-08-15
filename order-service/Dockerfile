FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/order-service.jar
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]