package com.sparklshine.carwash.service;

import com.sparklshine.carwash.entity.Booking;
import com.sparklshine.carwash.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingRepository.findByBookingDate(date);
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
    
    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    public List<LocalTime> getAvailableTimeSlots(LocalDate date) {
        List<Booking> existingBookings = bookingRepository.findByBookingDate(date);
        
        List<LocalTime> allSlots = Arrays.asList(
            LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(9, 0),
            LocalTime.of(10, 0), LocalTime.of(11, 0), LocalTime.of(12, 0),
            LocalTime.of(13, 0), LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0)
        );
        
        return allSlots.stream()
            .filter(slot -> existingBookings.stream().noneMatch(b -> b.getBookingTime() != null && b.getBookingTime().equals(slot)))
            .toList();
    }
}