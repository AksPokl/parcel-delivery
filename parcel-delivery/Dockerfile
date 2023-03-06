FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
ENV SQL_HOST=postgres
ENV EUREKA_HOST=eureka
ENV RABBITMQ_HOST=rabbitmq
ENV COURIER_HOST=courier
COPY /build/libs/parcel-delivery-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/parcel-delivery-1.0.0.jar"]