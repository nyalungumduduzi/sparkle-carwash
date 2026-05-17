package com.sparklshine.carwash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparklshine.carwash.entity.ServiceTier;

@Repository
public interface ServiceTierRepository extends JpaRepository<ServiceTier, Long> {
    List<ServiceTier> findByIsActiveTrue();
}