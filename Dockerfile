# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests


# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

ENV JAVA_OPTS="\
-XX:+UseSerialGC \
-XX:MaxRAMPercentage=65 \
-XX:+UseContainerSupport \
-XX:MaxMetaspaceSize=192m \
-Dreactor.netty.ioWorkerCount=2 \
-Dspring.main.lazy-initialization=true"

CMD ["sh","-c","java $JAVA_OPTS -jar app.jar"]
