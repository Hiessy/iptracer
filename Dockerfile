FROM openjdk:8
ADD target/*.jar iptracer.jar
EXPOSE 8080:88080
ENTRYPOINT ["java","-jar","iptracer.jar"]