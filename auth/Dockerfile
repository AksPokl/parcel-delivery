FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
ENV SQL_HOST=postgres
ENV EUREKA_HOST=eureka
ENV REDIS_HOST=cache
COPY /build/libs/auth-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/auth-1.0.0.jar"]