FROM openjdk:8-jdk-alpine

# Set necessary environment variables needed for our running image
ENV OAUTH2_AUTH_DATASOURCE_URL='jdbc:mysql://dev-mysql:3306/spring-security?useSSL=false' \
    OAUTH2_AUTH_DATASOURCE_USERNAME='root' \
    OAUTH2_AUTH_DATASOURCE_PASSWORD='r00t123!+' \
    OAUTH2_AUTH_ENVIRONMENT='dev'

VOLUME /tmp

ARG JAR_FILE=build/libs/toolbox.spring.oauth2-authotization-server.jar

ADD ${JAR_FILE} app.jar

EXPOSE 8443

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
