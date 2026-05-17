package com.sparklshine.carwash.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparklshine.carwash.entity.Employee;
import com.sparklshine.carwash.entity.EmployeeRole;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findByRole(EmployeeRole role);
    List<Employee> findByIsActiveTrue();
}