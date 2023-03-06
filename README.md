# parcel-delivery

### Stack:

- java 11
- Spring Boot (Spring data, Spring security, Spring web)
- Docker
- PostgreSQL
- Redis
- RabbitMQ

### Swagger

- Auth service:
  ```http://localhost:8081/swagger-ui/index.html```
- Account service:
  ```http://localhost:8082/swagger-ui/index.html```
- Parcel delivery service:
  ```http://localhost:8083/swagger-ui/index.html```

### Run application

To start the app first run the commands:
```docker-compose -f docker-compose.yml up```

### Run tests

```./gradlew test```
