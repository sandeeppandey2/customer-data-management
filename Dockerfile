FROM openjdk:21
ADD target/customer-data-management.jar /customer-data-management.jar
COPY target/customer-data-management.jar customer-data-management.jar
ENTRYPOINT ["java","-jar","/customer-data-management.jar"]
