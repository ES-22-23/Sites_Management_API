FROM maven:3.8.6-amazoncorretto-11

COPY . /app
WORKDIR /app

RUN mvn package -DskipTests=true

ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} sites-management-API.jar

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "sites-management-API.jar" ]
