FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
ARG STAGE=dev
WORKDIR /usr/src/server
COPY . .
RUN mvn package -P${STAGE} -DskipTests


FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /app
COPY --from=appServerBuild /usr/src/server/target/notification.service.jar ./
EXPOSE 8083
CMD java -jar notification.service.jar
