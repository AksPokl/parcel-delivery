FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
ENV SQL_HOST=postgres
ENV EUREKA_HOST=eureka
ENV RABBITMQ_HOST=rabbitmq
ENV AUTH_HOST=auth
ENV PARCEL_DELIVERY_HOST=parcel-delivery
COPY /build/libs/courier-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/courier-1.0.0.jar"]