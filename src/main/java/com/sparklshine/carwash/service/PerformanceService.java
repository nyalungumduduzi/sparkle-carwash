package com.sparklshine.carwash.service;

import com.sparklshine.carwash.entity.Booking;
import com.sparklshine.carwash.entity.EmployeePerformance;
import com.sparklshine.carwash.entity.User;
import com.sparklshine.carwash.repository.BookingRepository;
import com.sparklshine.carwash.repository.EmployeePerformanceRepository;
import com.sparklshine.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PerformanceService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmployeePerformanceRepository performanceRepository;
    
    public void calculateWeeklyPerformance(LocalDate weekStart) {
        List<User> employees = userRepository.findByRole("EMPLOYEE");
        
        for (User employee : employees) {
            List<Booking> completedBookings = bookingRepository.findByAssignedEmployee_UserIdAndStatus(employee.getUserId(), "COMPLETED");
            List<Booking> allAssigned = bookingRepository.findByAssignedEmployee_UserId(employee.getUserId());
            
            int carsWashed = 0;
            int tasksCompleted = 0;
            
            for (Booking b : completedBookings) {
                if (b.getCompletedAt() != null && 
                    b.getCompletedAt().toLocalDate().isAfter(weekStart.minusDays(1)) &&
                    b.getCompletedAt().toLocalDate().isBefore(weekStart.plusDays(8))) {
                    carsWashed++;
                    tasksCompleted++;
                }
            }
            
            int tasksAssigned = allAssigned.size();
            BigDecimal completionRate = BigDecimal.ZERO;
            if (tasksAssigned > 0) {
                completionRate = BigDecimal.valueOf((double) tasksCompleted / tasksAssigned * 100)
                    .setScale(2, RoundingMode.HALF_UP);
            }
            
            BigDecimal bonus = BigDecimal.valueOf((carsWashed / 10) * 50);
            
            EmployeePerformance performance = performanceRepository
                .findByEmployee_UserIdAndWeekStartDate(employee.getUserId(), weekStart);
            
            if (performance == null) {
                performance = new EmployeePerformance();
                performance.setEmployee(employee);
                performance.setWeekStartDate(weekStart);
            }
            
            performance.setCarsWasherd(carsWashed);
            performance.setTasksCompleted(tasksCompleted);
            performance.setTasksAssigned(tasksAssigned);
            performance.setCompletionRate(completionRate);
            performance.setBonusEarned(bonus);
            
            performanceRepository.save(performance);
        }
    }
    
    public List<EmployeePerformance> getTopPerformers(int limit) {
        return performanceRepository.findAllOrderByCarsWashedDesc().stream()
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    public Map<String, Object> getEmployeePerformanceSummary(Long employeeId) {
        Map<String, Object> summary = new HashMap<>();
        List<EmployeePerformance> performances = performanceRepository.findByEmployee_UserId(employeeId);
        
        int totalCars = 0;
        BigDecimal totalBonus = BigDecimal.ZERO;
        
        for (EmployeePerformance p : performances) {
            totalCars += p.getCarsWasherd();
            totalBonus = totalBonus.add(p.getBonusEarned());
        }
        
        summary.put("totalCars", totalCars);
        summary.put("totalBonus", totalBonus);
        summary.put("weeksCount", performances.size());
        summary.put("performances", performances);
        
        return summary;
    }
}