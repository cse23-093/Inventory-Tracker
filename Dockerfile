# ---- build stage ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package


# ---- run stage ----
FROM tomcat:10.1-jdk17-temurin

# remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# disable shutdown port (prevents Render health-check spam)
RUN sed -i 's/port="8005"/port="-1"/' /usr/local/tomcat/conf/server.xml

# deploy ROOT.war (pom.xml should have <finalName>ROOT</finalName>)
COPY --from=build /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# entrypoint script writes JNDI DataSource from env vars then starts Tomcat
RUN mkdir -p /usr/local/tomcat/conf/Catalina/localhost
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

# ✅ fix Windows CRLF line endings (prevents "no open ports" on Render)
RUN sed -i 's/\r$//' /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 8080
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
