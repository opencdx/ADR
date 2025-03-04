ARG  CODE_VERSION=21-slim
FROM openjdk:${CODE_VERSION}

# Install prerequisites
RUN apt-get update && apt-get install -y \
curl && apt-get clean

COPY build/libs/ADR-*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar "]