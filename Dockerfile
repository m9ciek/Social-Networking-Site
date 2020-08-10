FROM openjdk:11.0.8-jdk
ADD target/social-networking-site-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar social-networking-site-0.0.1-SNAPSHOT.jar