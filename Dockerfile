FROM openjdk:11

# copy the packaged jar file into our docker image
COPY target/bankproject-v.1.0.jar /bankproject.jar

# set the startup command to execute the jar file
CMD ["java", "-jar", "/bankproject.jar"]
