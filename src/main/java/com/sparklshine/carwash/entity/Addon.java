package com.sparklshine.carwash.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addon")
public class Addon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addonId;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    // Constructors
    public Addon() {}
    
    public Addon(String name, BigDecimal price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = true;
    }
    
    // Getters
    public Long getAddonId() { return addonId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public Boolean getIsActive() { return isActive; }
    
    // Setters
    public void setAddonId(Long addonId) { this.addonId = addonId; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}