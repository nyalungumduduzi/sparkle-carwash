package com.sparklshine.carwash.repository;

import com.sparklshine.carwash.entity.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, Long> {
    
    // Fixed: Use employee.userId instead of employee.id
    List<EmployeePerformance> findByEmployee_UserId(Long employeeId);
    
    List<EmployeePerformance> findByWeekStartDate(LocalDate date);
    
    EmployeePerformance findByEmployee_UserIdAndWeekStartDate(Long employeeId, LocalDate weekStart);
    
    @Query("SELECT ep FROM EmployeePerformance ep ORDER BY ep.carsWasherd DESC")
    List<EmployeePerformance> findAllOrderByCarsWashedDesc();
    
    @Query("SELECT ep FROM EmployeePerformance ep WHERE ep.weekStartDate >= :startDate ORDER BY ep.carsWasherd DESC")
    List<EmployeePerformance> findTopPerformersByWeek(@Param("startDate") LocalDate startDate);
}