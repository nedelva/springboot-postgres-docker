# springboot-postgres-docker
The project demonstrates how to use Spring Boot to create a simple Web app that 
* integrates a PostgresQL database running in a Docker container
* uses Liquibase as a DB migration tool to : initialize the DB schema and populate the tables
* uses Testcontainers to create an integration test that loads the table and adds another record using JPA

### Spring Initializr settings
The project was generated with Spring Boot 3.4.4, Maven support and Java version 21.
It also has a number of dependencies from the get go:
* Spring Web (for RESTful controller that serves JSON)
* Docker Compose Support (will be started by `mvn spring:boot`)
* Spring Data JPA
* PostgreSQL Driver 
* Liquibase Migration (using Liquibase as the DB source of truth)
* Spring Boot Actuator (mostly for application heath endpoints)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:17.2`](https://hub.docker.com/_/postgres)

### application.yml 
Liquibase is enabled via
```yaml
spring:
  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.xml
    enabled: true
```

### Liquibase directory structure setup
```
src/main/resources/
├── db/
│   └── changelog/
│       ├── db.changelog-master.xml
│       ├── changes/
│       │   ├── create-customers-table.xml
│       │   └── insert-customers-data.xml
│       └── data/
│           └── customers.csv
```
#### db.changelog-master.xml
Points to all changelog files

#### create-customers-table.xml
Changelog for creating `customers` table (and `customers_seq` sequence)

#### insert-customers-data.xml
Changelog for pre-populating `customers` table and updating the `customers_seq`sequence

### Using Testcontainers for integration testing
To enable real integration with dockerized DB backends, one needs to add the following Maven dependency:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-testcontainers</artifactId>
    <scope>test</scope>
</dependency>
```
The test class needs annotations such as `@SpringBootTest` and `@Testcontainers` and a `@Container` static variable need
to be declared:
```java
@SpringBootTest
@Testcontainers
class IntegrationTest {
    //..other class declarations..
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.2")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");
}
```