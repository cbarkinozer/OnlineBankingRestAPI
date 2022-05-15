FROM adoptenjdk/openjdk11:ubi
ENV APP_HOME = /usr/app
WORKDIR $APP_HOME
COPY  build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]