FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY app.jar ./app.jar
COPY libs ./libs

CMD ["java", "-cp", "app.jar:libs/*", "AppKt"]