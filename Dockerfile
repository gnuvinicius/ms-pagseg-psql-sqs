# syntax=docker/dockerfile:1

# Etapa de build
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Definição das variáveis de ambiente
ENV PSQL_HOST="" \
    PSQL_PASSWD="" \
    SQS_URL="" \
    PAGSEGURO_TOKEN=""

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
