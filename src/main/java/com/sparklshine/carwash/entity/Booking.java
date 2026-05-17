package com.sparklshine.carwash.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    
    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    private User assignedEmployee;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String customerPhone;
    
    @Column(nullable = false)
    private String customerEmail;
    
    private String vehicleRegistration;
    
    private String vehicleMakeModel;
    
    @Column(nullable = false)
    private LocalDate bookingDate;
    
    private LocalTime bookingTime;
    
    @Column(nullable = false)
    private String serviceType;
    
    @Column(nullable = false)
    private String status = "PENDING";
    
    private String notes;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime completedAt;
    
    @Column(nullable = false)
    private Boolean reminderSent = false;  // Add this field!
    
    // Constructor
    public Booking() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
        this.reminderSent = false;
        this.bookingTime = LocalTime.of(9, 0);
    }
    
    // Getters
    public Long getBookingId() { return bookingId; }
    public User getAssignedEmployee() { return assignedEmployee; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public String getCustomerEmail() { return customerEmail; }
    public String getVehicleRegistration() { return vehicleRegistration; }
    public String getVehicleMakeModel() { return vehicleMakeModel; }
    public LocalDate getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public String getServiceType() { return serviceType; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public Boolean getReminderSent() { return reminderSent; }
    
    // Setters
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public void setAssignedEmployee(User assignedEmployee) { this.assignedEmployee = assignedEmployee; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public void setVehicleRegistration(String vehicleRegistration) { this.vehicleRegistration = vehicleRegistration; }
    public void setVehicleMakeModel(String vehicleMakeModel) { this.vehicleMakeModel = vehicleMakeModel; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public void setBookingTime(LocalTime bookingTime) { this.bookingTime = bookingTime; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public void setReminderSent(Boolean reminderSent) { this.reminderSent = reminderSent; }
}