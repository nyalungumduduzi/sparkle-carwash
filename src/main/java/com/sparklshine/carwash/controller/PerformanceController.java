package com.sparklshine.carwash.controller;

import com.sparklshine.carwash.entity.EmployeePerformance;
import com.sparklshine.carwash.entity.User;
import com.sparklshine.carwash.repository.UserRepository;
import com.sparklshine.carwash.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/performance")
public class PerformanceController {
    
    @Autowired
    private PerformanceService performanceService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String performanceDashboard(Model model) {
        List<EmployeePerformance> topPerformers = performanceService.getTopPerformers(10);
        List<User> allEmployees = userRepository.findByRole("EMPLOYEE");
        
        // Calculate weekly performance
        performanceService.calculateWeeklyPerformance(LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        
        model.addAttribute("topPerformers", topPerformers);
        model.addAttribute("employees", allEmployees);
        model.addAttribute("currentWeek", LocalDate.now().with(java.time.DayOfWeek.MONDAY));
        
        return "admin/performance";
    }
    
    @GetMapping("/employee/{id}")
    public String employeePerformance(@PathVariable Long id, Model model) {
        User employee = userRepository.findById(id).orElse(null);
        var summary = performanceService.getEmployeePerformanceSummary(id);
        
        model.addAttribute("employee", employee);
        model.addAttribute("summary", summary);
        
        return "admin/employee-performance";
    }
}