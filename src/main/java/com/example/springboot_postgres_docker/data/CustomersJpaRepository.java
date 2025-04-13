package com.example.springboot_postgres_docker.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersJpaRepository extends JpaRepository<Customer, Long> {
}
