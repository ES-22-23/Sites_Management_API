FROM maven:3.8.6-amazoncorretto-11

COPY . /app
WORKDIR /app

RUN mvn package -DskipTests=true
ADD target/*.jar sites-management-API.jar

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "sites-management-API.jar" ]