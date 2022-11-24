FROM gradle:7.4-jdk17-alpine AS BUILD_IMAGE

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME/
COPY gradle $APP_HOME/gradle
COPY .gradle $APP_HOME/.gradle
COPY --chown=gradle:gradle . /home/gradle/src

USER root
RUN chown -R gradle /home/gradle/src

COPY . .
RUN gradle clean build --no-daemon --info --stacktrace --scan --refresh-dependencies
RUN gradle fatJar


# Actual container
FROM openjdk:17-alpine
ENV ARTIFACT_NAME=notifier-bot-all-0.1.0-SNAPSHOT.jar
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME
COPY --from=BUILD_IMAGE $APP_HOME .
COPY --from=BUILD_IMAGE $APP_HOME/.gradle .gradle
COPY --from=BUILD_IMAGE $APP_HOME/.env .env

ENTRYPOINT java -jar "build/libs/${ARTIFACT_NAME}"