FROM maven:3.6.3-ibmjava-8-alpine AS appServerBuild
ARG STAGE=dev
WORKDIR /usr/src/server
COPY . .
RUN mvn package -P${STAGE} -DskipTests


FROM openjdk:8-jdk-alpine AS appServerRuntime
WORKDIR /app
COPY --from=appServerBuild /usr/src/server/target/user.service.jar ./
EXPOSE 8081
CMD java -jar user.service.jar
