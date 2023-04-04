FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM build as test
CMD ["mvn", "-f", "/home/app/pom.xml", "test"]

FROM openjdk:17-ea-3-slim as production
COPY --from=build /home/app/target/echo-0.0.1-SNAPSHOT.jar /usr/local/lib/echo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/echo.jar"]