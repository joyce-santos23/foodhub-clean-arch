# ================= STAGE 1: Build =================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom primeiro para aproveitar o cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código e compila
COPY src ./src
# O 'clean' garante que não usemos lixo antigo
RUN mvn clean package -DskipTests

# ================= STAGE 2: Runtime =================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT exec java -jar app.jar