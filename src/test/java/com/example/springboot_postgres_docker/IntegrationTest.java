package com.example.springboot_postgres_docker;

import com.example.springboot_postgres_docker.data.Customer;
import com.example.springboot_postgres_docker.data.CustomersJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class IntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.2")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void contextLoads() {
        // if the app context starts and DB connects, test passes
    }

    @Autowired
    CustomersJpaRepository repository;

    @Test
    void testSaveEntity() {
        Customer newCustomer = new Customer();
        newCustomer.setName("John Doe");
        newCustomer.setEmail("johndoe@nowhere.org");
        Customer saved = repository.save(newCustomer);
        assertTrue(repository.findById(saved.getId()).isPresent());
    }
}
