FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
ENV EUREKA_HOST=eureka
ENV AUTH_HOST=auth
ENV COURIER_HOST=courier
ENV PARCEL_DELIVERY_HOST=parcel-delivery
ENV SQL_HOST=postgres
ENV REDIS_HOST=cache
COPY /build/libs/gateway-1.0.0.jar /opt/app
CMD ["java", "-jar", "/opt/app/gateway-1.0.0.jar"]