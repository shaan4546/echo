FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
#RUN mvn -f /home/app/pom.xml surefire-report:report

FROM openjdk:17-ea-3-slim
COPY --from=build /home/app/target/echo-0.0.1-SNAPSHOT.jar /usr/local/lib/echo.jar
#COPY --from=build /home/app/target/site /usr/local/lib/site
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/echo.jar"]