FROM ubuntu:latest

ENTRYPOINT ["top", "-b"]

#Build phase
FROM gradle:8.6.0-jdk21 as BUILD
LABEL authors="Karol Wilczynski"
COPY . /src
WORKDIR /src
RUN gradle build -x test

#Run phase
FROM eclipse-temurin:21
ENV JAR_NAME_SRC=emailPlac-0.0.1-SNAPSHOT.jar
ENV JAR_NAME_BUILD=emailplac.jar
EXPOSE 300
COPY --from=BUILD /src/build/libs/$JAR_NAME_SRC /app/$JAR_NAME_BUILD
WORKDIR /app

CMD ["java","-jar","emailplac.jar"]