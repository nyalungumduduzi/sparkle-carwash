package com.sparklshine.carwash.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sparklshine.carwash.entity.Booking;
import com.sparklshine.carwash.entity.User;
import com.sparklshine.carwash.repository.BookingRepository;
import com.sparklshine.carwash.repository.UserRepository;
import com.sparklshine.carwash.service.EmailService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping("/dashboard")
    public String employeeDashboard(Authentication auth, Model model) {
        String username = auth.getName();
        User currentUser = userRepository.findByUsername(username).orElse(null);
        
        List<Booking> assignedTasks = new ArrayList<>();
        List<Booking> todayTasks = new ArrayList<>();
        int completedCount = 0;
        int inProgressCount = 0;
        
        if (currentUser != null) {
            assignedTasks = bookingRepository.findByAssignedEmployee_UserId(currentUser.getUserId());
            if (assignedTasks == null) assignedTasks = new ArrayList<>();
            todayTasks = bookingRepository.findByAssignedEmployee_UserIdAndBookingDate(currentUser.getUserId(), LocalDate.now());
            if (todayTasks == null) todayTasks = new ArrayList<>();
            completedCount = (int) assignedTasks.stream().filter(b -> "COMPLETED".equals(b.getStatus())).count();
            inProgressCount = (int) assignedTasks.stream().filter(b -> "IN_PROGRESS".equals(b.getStatus())).count();
        }
        
        model.addAttribute("assignedTasksCount", assignedTasks.size());
        model.addAttribute("completedTasksCount", completedCount);
        model.addAttribute("inProgressTasksCount", inProgressCount);
        model.addAttribute("todayTasks", todayTasks);
        
        return "employee/dashboard";
    }
    
   @GetMapping("/tasks")
public String myTasks(Authentication auth, Model model) {
    String username = auth.getName();
    User currentUser = userRepository.findByUsername(username).orElse(null);
    
    List<Booking> myBookings = new ArrayList<>();
    if (currentUser != null) {
        myBookings = bookingRepository.findByAssignedEmployee_UserId(currentUser.getUserId());
        if (myBookings == null) myBookings = new ArrayList<>();
        
        // Debug output
        System.out.println("Employee: " + username + " has " + myBookings.size() + " assigned tasks");
        for (Booking b : myBookings) {
            System.out.println("  Task: " + b.getBookingId() + " - " + b.getServiceType() + " - Status: " + b.getStatus());
        }
    }
    
    model.addAttribute("tasks", myBookings);
    return "employee/tasks";
}
    
    @PostMapping("/update-status/{bookingId}")
    public String updateStatus(@PathVariable Long bookingId, @RequestParam String status) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        
        if (booking != null) {
            booking.setStatus(status);
            if (status.equals("COMPLETED")) {
                booking.setCompletedAt(LocalDateTime.now());
                if (emailService != null) {
                    try {
                        emailService.sendCompletionEmail(booking);
                    } catch(Exception e) {
                        System.out.println("Email error: " + e.getMessage());
                    }
                }
            }
            bookingRepository.save(booking);
        }
        
        return "redirect:/employee/tasks";
    }
}