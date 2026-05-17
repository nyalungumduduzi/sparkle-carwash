package com.sparklshine.carwash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sparklshine.carwash.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingDate(LocalDate date);
    List<Booking> findByStatus(String status);
    List<Booking> findByAssignedEmployee_UserId(Long employeeId);
    List<Booking> findByAssignedEmployee_Username(String username);
    List<Booking> findByAssignedEmployee_UserIdAndStatus(Long employeeId, String status);
    List<Booking> findByAssignedEmployee_UserIdAndBookingDate(Long employeeId, LocalDate date);
    @Query("SELECT b.serviceType, COUNT(b) FROM Booking b GROUP BY b.serviceType")
List<Object[]> countByServiceType();
}