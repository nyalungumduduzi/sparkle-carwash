package com.sparklshine.carwash.config;

import com.sparklshine.carwash.entity.Booking;
import com.sparklshine.carwash.entity.User;
import com.sparklshine.carwash.repository.BookingRepository;
import com.sparklshine.carwash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TestDataLoader implements CommandLineRunner {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Add a test booking if none exist
        if (bookingRepository.count() == 0) {
            Booking testBooking = new Booking();
            testBooking.setCustomerName("John Doe");
            testBooking.setCustomerPhone("0821234567");
            testBooking.setCustomerEmail("john@example.com");
            testBooking.setServiceType("Premium Wash");
            testBooking.setBookingDate(LocalDate.now());
            testBooking.setBookingTime(LocalTime.of(10, 0)); // Add a time!
            testBooking.setStatus("PENDING");
            testBooking.setVehicleRegistration("ABC123MP");
            
            bookingRepository.save(testBooking);
            System.out.println("Test booking added!");
        }
    }
}