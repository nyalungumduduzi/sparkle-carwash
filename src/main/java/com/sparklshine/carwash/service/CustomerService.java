package com.sparklshine.carwash.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparklshine.carwash.entity.Customer;
import com.sparklshine.carwash.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }
    
    public List<Customer> getAllActiveCustomers() {
        return customerRepository.findByIsActiveTrue();
    }
    
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstName(updatedCustomer.getFirstName());
            existing.setLastName(updatedCustomer.getLastName());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existing.setVehicleRegistration(updatedCustomer.getVehicleRegistration());
            existing.setVehicleMakeModel(updatedCustomer.getVehicleMakeModel());
            return customerRepository.save(existing);
        }
        return null;
    }
}