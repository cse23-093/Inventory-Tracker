# =========================
# Build stage
# =========================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package


# =========================
# Runtime stage
# =========================
FROM tomcat:10.1-jdk17-temurin

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Prefer IPv4 (IMPORTANT for Render port detection)
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true"

# Force Tomcat to bind to 0.0.0.0 and disable shutdown port
COPY server.xml /usr/local/tomcat/conf/server.xml

# Deploy ROOT.war
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Prepare Tomcat context directory
RUN mkdir -p /usr/local/tomcat/conf/Catalina/localhost

# Copy entrypoint script
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

# Fix Windows CRLF line endings (critical)
RUN sed -i 's/\r$//' /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Render listens on 8080
EXPOSE 8080

# Start container
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
