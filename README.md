# Bank Account
This project allows to perform operations on a bank account such as depositing and withdrawing money
It also allows a customer to view the list of transactions made on his account

## Building

For building the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

```shell
mvn clean package
```

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. 

One way is to execute the `main` method in the `com.bankaccount.BankAccountApplication` class from your IDE.

You can also launch this command :
```shell
java -jar bank-account-back-0.0.1-SNAPSHOT.jar
```

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
