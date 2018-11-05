FROM openjdk:latest
WORKDIR /
ADD out/artifacts/MI_devops_jar/MI_devops.jar MI_devops.jar
EXPOSE 8080
CMD java -jar MI_devops.jar


