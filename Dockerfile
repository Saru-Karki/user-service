FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Install Maven for on-container builds (or use multi-stage with a build container)
RUN apk add --no-cache maven

# Copy pom and src so dependencies can be resolved
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src

EXPOSE 8080
CMD ["mvn","spring-boot:run"]
