FROM gradle:7.4-jdk17-alpine AS BUILD_IMAGE

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME/
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src

USER root
RUN chown -R gradle /home/gradle/src

COPY . .

# --info --stacktrace --scan --refresh-dependencies
RUN gradle clean build --no-daemon -x test
RUN gradle fatJar --no-daemon


# Actual container
FROM openjdk:17-alpine
ENV ARTIFACT_NAME=notifier-bot-all.jar
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME
COPY --from=BUILD_IMAGE $APP_HOME .
COPY --from=BUILD_IMAGE $APP_HOME/.gradle .gradle

ENTRYPOINT java -jar "build/libs/${ARTIFACT_NAME}"