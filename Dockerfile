# Stage 1
FROM maven:3-openjdk-11-slim as build

WORKDIR /workspace/app

COPY pom.xml .
#COPY src src
#COPY scripts/redoc-static-html-gen.sh scripts/redoc-static-html-gen.sh
COPY registry-client/pom.xml registry-client/pom.xml
COPY registry-client/src registry-client/src
COPY registry-server/pom.xml registry-server/pom.xml
COPY registry-server/src registry-server/src
COPY registry-server/scripts/redoc-static-html-gen.sh registry-server/scripts/redoc-static-html-gen.sh

ARG SPRING_PROFILES_ACTIVE_BUILD=dev
ARG SPRING_PORT=8585
ARG JAVA_OPTS
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG FLYWAY_SCHEMA=flyway
ARG FLYWAY_SCRIPTS_DIR=postgres
ARG H2_CONSOLE_ENABLED=false
ARG H2_CONSOLE_PATH=h2-console
ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE_BUILD}
ENV SPRING_PROFILES_ACTIVE_BUILD ${SPRING_PROFILES_ACTIVE_BUILD}
ENV SPRING_PORT ${SPRING_PORT}
ENV DATABASE_URL ${DATABASE_URL}
ENV DATABASE_USERNAME ${DATABASE_USERNAME}
ENV DATABASE_PASSWORD ${DATABASE_PASSWORD}
ENV FLYWAY_SCHEMA ${FLYWAY_SCHEMA}
ENV FLYWAY_SCRIPTS_DIR ${FLYWAY_SCRIPTS_DIR}
ENV H2_CONSOLE_ENABLED ${H2_CONSOLE_ENABLED}
ENV H2_CONSOLE_PATH ${H2_CONSOLE_PATH}

RUN mvn clean install -DskipTests -Dactive.profile=$SPRING_PROFILES_ACTIVE_BUILD

# Stage 2
FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install -y wget gpg lsb-release zip curl

ARG SPRING_PROFILES_ACTIVE=docker
ARG SPRING_PORT=8585
ARG JAVA_OPTS
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG FLYWAY_SCHEMA=flyway
ARG FLYWAY_SCRIPTS_DIR=mysql
ARG H2_CONSOLE_ENABLED=false
ARG H2_CONSOLE_PATH=h2-console
ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE}
ENV SPRING_LOCAL_PORT ${SPRING_LOCAL_PORT}
ENV JAVA_OPTS ${JAVA_OPTS}
ENV DATABASE_URL ${DATABASE_URL}
ENV DATABASE_USERNAME ${DATABASE_USERNAME}
ENV DATABASE_PASSWORD ${DATABASE_PASSWORD}
ENV FLYWAY_SCHEMA ${FLYWAY_SCHEMA}
ENV FLYWAY_SCRIPTS_DIR ${FLYWAY_SCRIPTS_DIR}
ENV H2_CONSOLE_ENABLED ${H2_CONSOLE_ENABLED}
ENV H2_CONSOLE_PATH ${H2_CONSOLE_PATH}

COPY --from=build  /workspace/app/target/odm-platform-pp-registry-client-*.jar /app/
COPY --from=build  /workspace/app/target/odm-platform-pp-registry-server-*.jar /app/
#./registry-client/target/odm-platform-pp-registry-client-1.0.0-exec.jar
#./registry-client/target/odm-platform-pp-registry-client-1.0.0.jar
#./registry-server/target/odm-platform-pp-registry-server-1.0.0.jar
COPY --from=build  /workspace/app/registry-server/target/odm-platform-pp-*.jar /app/

RUN ln -s -f /usr/share/zoneinfo/Europe/Rome /etc/localtime

CMD java $JAVA_OPTS -jar /app/odm-platform-pp-registry-client*.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE
CMD java $JAVA_OPTS -jar /app/odm-platform-pp-registry-server*.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE

EXPOSE $SPRING_LOCAL_PORT
