FROM openjdk:24-jdk-oracle

EXPOSE 5500

COPY target/MoneyTransferService-0.0.1-SNAPSHOT.jar transferApp.jar

CMD ["java", "-jar", "transferApp.jar"]