FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY /build/libs/*.jar /app/reboot_project.jar
ENTRYPOINT ["java", "-jar", "/app/reboot_project.jar"]