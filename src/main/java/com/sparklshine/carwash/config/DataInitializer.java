package com.sparklshine.carwash.config;

import com.sparklshine.carwash.entity.User;
import com.sparklshine.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEmail("admin@sparklshine.co.za");
            admin.setPhoneNumber("0000000000");
            admin.setRole("ADMIN");
            admin.setIsActive(true);
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }
        
        // Create a sample employee
        if (userRepository.findByUsername("employee1").isEmpty()) {
            User employee = new User();
            employee.setUsername("employee1");
            employee.setPassword(passwordEncoder.encode("emp123"));
            employee.setFirstName("John");
            employee.setLastName("Doe");
            employee.setEmail("employee1@sparklshine.co.za");
            employee.setPhoneNumber("0812345678");
            employee.setRole("EMPLOYEE");
            employee.setIsActive(true);
            userRepository.save(employee);
            System.out.println("Employee user created: employee1 / emp123");
        }
    }
}