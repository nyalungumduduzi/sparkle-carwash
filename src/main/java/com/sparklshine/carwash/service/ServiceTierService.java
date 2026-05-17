package com.sparklshine.carwash.service;

import com.sparklshine.carwash.entity.ServiceTier;
import com.sparklshine.carwash.repository.ServiceTierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceTierService {
    
    @Autowired
    private ServiceTierRepository serviceTierRepository;
    
    public List<ServiceTier> getAllActiveServices() {
        return serviceTierRepository.findByIsActiveTrue();
    }
    
    public ServiceTier getServiceById(Long id) {
        return serviceTierRepository.findById(id).orElse(null);
    }
    
    public ServiceTier createService(ServiceTier service) {
        return serviceTierRepository.save(service);
    }
    
    public ServiceTier updateService(Long id, ServiceTier updatedService) {
        ServiceTier existing = serviceTierRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updatedService.getName());
            existing.setDescription(updatedService.getDescription());
            existing.setPrice(updatedService.getPrice());
            existing.setDurationMinutes(updatedService.getDurationMinutes());
            return serviceTierRepository.save(existing);
        }
        return null;
    }
    
    public void deleteService(Long id) {
        ServiceTier service = serviceTierRepository.findById(id).orElse(null);
        if (service != null) {
            service.setIsActive(false);
            serviceTierRepository.save(service);
        }
    }
    
    // Method to initialize default services if none exist
    public void initializeDefaultServices() {
        if (serviceTierRepository.count() == 0) {
            ServiceTier basic = new ServiceTier("Basic Wash", "Exterior rinse, hand wash, basic dry", new BigDecimal("80.00"), 20);
            ServiceTier standard = new ServiceTier("Standard Wash", "Basic Wash + tyre shine, window clean, vacuum", new BigDecimal("150.00"), 35);
            ServiceTier premium = new ServiceTier("Premium Wash", "Standard + interior wipe-down, dashboard polish, air freshener", new BigDecimal("250.00"), 60);
            ServiceTier fullDetail = new ServiceTier("Full Detail", "Premium + wax coat, engine bay clean, leather conditioning", new BigDecimal("450.00"), 120);
            
            serviceTierRepository.save(basic);
            serviceTierRepository.save(standard);
            serviceTierRepository.save(premium);
            serviceTierRepository.save(fullDetail);
            
            System.out.println("Default services added to database!");
        }
    }
}