package com.sparklshine.carwash.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
   @GetMapping("/dashboard")
public String adminDashboard(Model model) {
    System.out.println("=== DASHBOARD DEBUG ===");
    
    LocalDate today = LocalDate.now();
    System.out.println("Today's date: " + today);
    
    // TODAY'S BOOKINGS - Filter by today's date
    List<Booking> todayBookings = bookingRepository.findByBookingDate(today);
    if (todayBookings == null) todayBookings = new ArrayList<>();
    System.out.println("Today's Bookings count: " + todayBookings.size());
    
    // PENDING BOOKINGS - Status = PENDING
    List<Booking> pendingBookings = bookingRepository.findByStatus("PENDING");
    if (pendingBookings == null) pendingBookings = new ArrayList<>();
    System.out.println("Pending Bookings count: " + pendingBookings.size());
    
    // ACTIVE EMPLOYEES
    List<User> employees = userRepository.findByRole("EMPLOYEE");
    if (employees == null) employees = new ArrayList<>();
    System.out.println("Employees count: " + employees.size());
    
    // ALL BOOKINGS for recent table
    List<Booking> allBookings = bookingRepository.findAll();
    if (allBookings == null) allBookings = new ArrayList<>();
    System.out.println("All Bookings count: " + allBookings.size());
    
    // Calculate today's revenue from TODAY's bookings only
    BigDecimal todayRevenue = calculateRevenue(todayBookings);
    System.out.println("Today's Revenue: R" + todayRevenue);
    
    // Calculate completion rate from ALL bookings
    long completedCount = allBookings.stream().filter(b -> "COMPLETED".equals(b.getStatus())).count();
    int completionRate = allBookings.size() > 0 ? (int) ((completedCount * 100) / allBookings.size()) : 0;
    System.out.println("Completion Rate: " + completionRate + "%");
    
    // Debug: Print today's bookings
    for (Booking b : todayBookings) {
        System.out.println("  Today's booking: " + b.getBookingId() + " - " + b.getServiceType() + " - R" + getServicePrice(b.getServiceType()));
    }
    
    model.addAttribute("todayBookings", todayBookings.size());
    model.addAttribute("pendingBookings", pendingBookings.size());
    model.addAttribute("employees", employees.size());
    model.addAttribute("recentBookings", allBookings);
    model.addAttribute("todayRevenue", todayRevenue);
    model.addAttribute("completionRate", completionRate);
    
    return "admin/dashboard";
}

private BigDecimal calculateRevenue(List<Booking> bookings) {
    BigDecimal total = BigDecimal.ZERO;
    for (Booking b : bookings) {
        total = total.add(getServicePrice(b.getServiceType()));
    }
    return total;
}

private BigDecimal getServicePrice(String serviceType) {
    switch (serviceType) {
        case "Basic Wash": return new BigDecimal("80");
        case "Standard Wash": return new BigDecimal("150");
        case "Premium Wash": return new BigDecimal("250");
        case "Full Detail": return new BigDecimal("450");
        default: return new BigDecimal("80");
    }
}
    
    @GetMapping("/employees")
public String manageEmployees(Model model) {
    // Only show active employees (not deleted)
    List<User> employees = userRepository.findByRoleAndIsActiveTrue("EMPLOYEE");
    if (employees == null) employees = new ArrayList<>();
    model.addAttribute("employees", employees);
    model.addAttribute("newEmployee", new User());
    return "admin/employees";
}
    
    @PostMapping("/employees/add")
    public String addEmployee(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String phoneNumber,
                              @RequestParam String password) {
        User employee = new User();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setRole("EMPLOYEE");
        employee.setIsActive(true);
        userRepository.save(employee);
        return "redirect:/admin/employees";
    }
    
    @GetMapping("/employees/delete/{id}")
public String deleteEmployee(@PathVariable Long id) {
    User employee = userRepository.findById(id).orElse(null);
    if (employee != null) {
        // Soft delete - just mark as inactive instead of actually deleting
        employee.setIsActive(false);
        userRepository.save(employee);
        System.out.println("Employee deactivated: " + employee.getUsername());
    }
    return "redirect:/admin/employees";
}
    
    @GetMapping("/bookings")
    public String viewAllBookings(Model model) {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings == null) bookings = new ArrayList<>();
        model.addAttribute("bookings", bookings);
        return "admin/bookings";
    }
    
    @GetMapping("/bookings/create")
    public String showCreateBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "admin/create-booking";
    }
    @Autowired
private EmailService emailService;
    
    @PostMapping("/bookings/create")
    public String createBooking(@RequestParam String customerName,
                                @RequestParam String customerPhone,
                                @RequestParam String customerEmail,
                                @RequestParam(required = false) String vehicleReg,
                                @RequestParam String serviceType,
                                @RequestParam String bookingDate,
                                @RequestParam String bookingTime) {
        Booking booking = new Booking();
        booking.setCustomerName(customerName);
        booking.setCustomerPhone(customerPhone);
        booking.setCustomerEmail(customerEmail);
        if (vehicleReg != null && !vehicleReg.isEmpty()) {
            booking.setVehicleRegistration(vehicleReg);
        }
        booking.setServiceType(serviceType);
        booking.setBookingDate(LocalDate.parse(bookingDate));
        booking.setBookingTime(LocalTime.parse(bookingTime + ":00"));
        booking.setStatus("PENDING");
        
        Booking savedBooking = bookingRepository.save(booking);
        emailService.sendBookingConfirmation(savedBooking);
        System.out.println("Confirmation email sent to: " + customerEmail);
        return "redirect:/admin/bookings";
    }
    
    @GetMapping("/assign/{bookingId}")
    public String showAssignForm(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        List<User> employees = userRepository.findByRole("EMPLOYEE");
        if (employees == null) employees = new ArrayList<>();
        
        model.addAttribute("booking", booking);
        model.addAttribute("employees", employees);
        return "admin/assign-employee";
    }
    
    @PostMapping("/assign/{bookingId}")
public String assignEmployee(@PathVariable Long bookingId, @RequestParam Long employeeId) {
    Booking booking = bookingRepository.findById(bookingId).orElse(null);
    User employee = userRepository.findById(employeeId).orElse(null);
    
    if (booking != null && employee != null) {
        booking.setAssignedEmployee(employee);
        booking.setStatus("ASSIGNED");
        bookingRepository.save(booking);
        System.out.println("Assigned booking " + bookingId + " to employee: " + employee.getUsername());
    } else {
        System.out.println("Failed to assign: booking=" + bookingId + ", employee=" + employeeId);
    }
    
    return "redirect:/admin/bookings";
}
    
}