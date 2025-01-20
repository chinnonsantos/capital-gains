FROM amazoncorretto:21

ARG APP_VERSION

ENV APP_VERSION=${APP_VERSION:-1.0.0}

LABEL org.opencontainers.image.authors="user@example.com.br"
LABEL version="$APP_VERSION"
LABEL description="Capital Gains"

COPY "./target/capital-gains-$APP_VERSION.jar" "/capital-gains.jar"

WORKDIR /

ENTRYPOINT ["java", "-jar", "capital-gains.jar"]

EXPOSE $SERVER_PORT
