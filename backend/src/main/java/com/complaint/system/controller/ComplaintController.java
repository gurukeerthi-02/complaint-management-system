package com.complaint.system.controller;

import com.complaint.system.dto.ComplaintRequest;
import com.complaint.system.entity.Complaint;
import com.complaint.system.entity.User;
import com.complaint.system.service.ComplaintService;
import com.complaint.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final UserService userService;

    public ComplaintController(ComplaintService complaintService, UserService userService) {
        this.complaintService = complaintService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createComplaint(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("location") String location,
            @RequestParam("priority") String priority,
            @RequestParam(value = "userEmail", required = false) String userEmail,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            User user = getUserByEmail(userEmail);
            
            Complaint complaint = complaintService.createComplaint(
                title, description, category, location, priority, photo, user
            );
            
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        try {
            List<Complaint> complaints = complaintService.getAllComplaints();
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.ok(Arrays.asList());
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        return ResponseEntity.ok(Map.of("message", "Backend is working"));
    }
    
    @GetMapping("/populate")
    public ResponseEntity<Map<String, String>> populateData() {
        try {
            User user = getOrCreateDefaultUser();
            
            // Create sample complaints if none exist
            if (complaintService.getAllComplaints().isEmpty()) {
                complaintService.createComplaint("Broken Street Light", 
                    "Street light on Main Street has been out for 3 days", 
                    "electricity", "Main Street near City Hall", "MEDIUM", null, user);
                    
                complaintService.createComplaint("Large Pothole", 
                    "Dangerous pothole causing vehicle damage", 
                    "roads", "Oak Avenue and 5th Street", "HIGH", null, user);
                    
                complaintService.createComplaint("Overflowing Trash Bins", 
                    "Garbage bins in Central Park are overflowing", 
                    "waste", "Central Park entrance", "LOW", null, user);
                    
                complaintService.createComplaint("Water Leak", 
                    "Water main leak flooding the sidewalk", 
                    "water", "Pine Street", "HIGH", null, user);
            }
            
            return ResponseEntity.ok(Map.of("message", "Sample data created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyComplaints() {
        try {
            User user = getOrCreateDefaultUser();
            
            List<Complaint> complaints = complaintService.getComplaintsByUser(user);
            return ResponseEntity.ok(complaints);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    private User getUserByEmail(String userEmail) {
        if (userEmail != null && !userEmail.isEmpty()) {
            return userService.findByEmail(userEmail)
                    .orElseGet(() -> {
                        try {
                            return userService.createUser(userEmail, "password", "User", null);
                        } catch (RuntimeException e) {
                            return userService.findByEmail(userEmail).orElseThrow();
                        }
                    });
        }
        
        return userService.findByEmail("default@example.com")
                .orElseGet(() -> {
                    try {
                        return userService.createUser("default@example.com", "password", "Default User", null);
                    } catch (RuntimeException e) {
                        return userService.findByEmail("default@example.com").orElseThrow();
                    }
                });
    }
    
    private User getOrCreateDefaultUser() {
        return getUserByEmail(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComplaint(@PathVariable UUID id) {
        return complaintService.getComplaintById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            Complaint complaint = complaintService.updateComplaintStatus(id, status);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<?> upvoteComplaint(@PathVariable UUID id) {
        try {
            Complaint complaint = complaintService.upvoteComplaint(id);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}