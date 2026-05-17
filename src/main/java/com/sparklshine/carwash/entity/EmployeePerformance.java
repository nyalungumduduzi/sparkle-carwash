package com.sparklshine.carwash.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_performance")
public class EmployeePerformance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceId;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;
    
    @Column(nullable = false)
    private LocalDate weekStartDate;
    
    private Integer carsWasherd = 0;
    private BigDecimal totalHoursWorked = BigDecimal.ZERO;
    private Integer avgTimePerCar = 0;
    private BigDecimal customerRating = BigDecimal.ZERO;
    private BigDecimal completionRate = BigDecimal.ZERO;
    private BigDecimal bonusEarned = BigDecimal.ZERO;
    private Integer tasksCompleted = 0;
    private Integer tasksAssigned = 0;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public EmployeePerformance() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getPerformanceId() { return performanceId; }
    public User getEmployee() { return employee; }
    public LocalDate getWeekStartDate() { return weekStartDate; }
    public Integer getCarsWasherd() { return carsWasherd; }
    public BigDecimal getTotalHoursWorked() { return totalHoursWorked; }
    public Integer getAvgTimePerCar() { return avgTimePerCar; }
    public BigDecimal getCustomerRating() { return customerRating; }
    public BigDecimal getCompletionRate() { return completionRate; }
    public BigDecimal getBonusEarned() { return bonusEarned; }
    public Integer getTasksCompleted() { return tasksCompleted; }
    public Integer getTasksAssigned() { return tasksAssigned; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setPerformanceId(Long performanceId) { this.performanceId = performanceId; }
    public void setEmployee(User employee) { this.employee = employee; }
    public void setWeekStartDate(LocalDate weekStartDate) { this.weekStartDate = weekStartDate; }
    public void setCarsWasherd(Integer carsWasherd) { this.carsWasherd = carsWasherd; }
    public void setTotalHoursWorked(BigDecimal totalHoursWorked) { this.totalHoursWorked = totalHoursWorked; }
    public void setAvgTimePerCar(Integer avgTimePerCar) { this.avgTimePerCar = avgTimePerCar; }
    public void setCustomerRating(BigDecimal customerRating) { this.customerRating = customerRating; }
    public void setCompletionRate(BigDecimal completionRate) { this.completionRate = completionRate; }
    public void setBonusEarned(BigDecimal bonusEarned) { this.bonusEarned = bonusEarned; }
    public void setTasksCompleted(Integer tasksCompleted) { this.tasksCompleted = tasksCompleted; }
    public void setTasksAssigned(Integer tasksAssigned) { this.tasksAssigned = tasksAssigned; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}