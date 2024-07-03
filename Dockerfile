FROM eclipse-temurin:17

LABEL maintainer="shokra19@gmail.com"


WORKDIR /app

COPY target/springboot-blog-rest-api.jar /app/springboot-blog-rest-api.jar



ENTRYPOINT ["java", "-jar", "springboot-blog-rest-api.jar"]
