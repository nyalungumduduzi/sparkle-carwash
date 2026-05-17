package com.sparklshine.carwash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("businessName", "Sparkle & Shine Car Wash");
        model.addAttribute("tagline", "Your car deserves the best shine in Witbank!");
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/services")
    public String services() {
        return "services";
    }
    
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}