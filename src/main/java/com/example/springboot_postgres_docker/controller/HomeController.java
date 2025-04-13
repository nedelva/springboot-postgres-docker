package com.example.springboot_postgres_docker.controller;

import com.example.springboot_postgres_docker.data.Customer;
import com.example.springboot_postgres_docker.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final CustomerService customerService;

    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/", produces = {"application/json"})
    public List<Customer> index() {
        return customerService.allCustomers();
    }
}
