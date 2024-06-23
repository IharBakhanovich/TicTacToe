FROM maven:3.8.3-openjdk-17 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY . /tmp/tictactoe

WORKDIR /tmp/tictactoe
RUN mvn -B dependency:go-offline -f pom.xml -s /usr/share/maven/ref/settings-docker.xml
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml -DskipTests clean install

FROM amazoncorretto:17-alpine
EXPOSE 9000
WORKDIR /tictactoe
COPY --from=MAVEN_TOOL_CHAIN /tmp/tictactoe/target/*.jar tictactoe-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom", "tictactoe-0.0.1-SNAPSHOT.jar"]

