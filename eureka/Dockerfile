FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY /build/libs/eureka-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/eureka-1.0.0.jar"]