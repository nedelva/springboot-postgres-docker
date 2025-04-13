package com.example.springboot_postgres_docker.service;

import com.example.springboot_postgres_docker.data.Customer;
import com.example.springboot_postgres_docker.data.CustomersJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomersJpaRepository customersRepository;

    public CustomerService(CustomersJpaRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customer> allCustomers() {
        return customersRepository.findAll();
    }
}
