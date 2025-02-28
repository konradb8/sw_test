FROM openjdk
LABEL authors="konra"
COPY target/swift-service-0.0.1-SNAPSHOT.jar SwiftApp.jar
ENTRYPOINT ["java", "-jar","SwiftApp.jar"]