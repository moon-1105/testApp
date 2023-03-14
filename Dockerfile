FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=build/libs/testApp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} testapp.jar
ENV TZ=Asia/Seoul
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

ENTRYPOINT ["java", "-jar", "/testapp.jar"]
