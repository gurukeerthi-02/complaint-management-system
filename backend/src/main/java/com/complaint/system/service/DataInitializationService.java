package com.complaint.system.service;

import com.complaint.system.entity.Complaint;
import com.complaint.system.entity.User;
import com.complaint.system.repository.ComplaintRepository;
import com.complaint.system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializationService(UserRepository userRepository, 
                                   ComplaintRepository complaintRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (complaintRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create default user
        User defaultUser = userRepository.findByEmail("default@example.com")
                .orElseGet(() -> {
                    User user = new User("default@example.com", 
                                       passwordEncoder.encode("password"), 
                                       "Default User");
                    return userRepository.save(user);
                });

        // Create sample complaints
        Complaint complaint1 = new Complaint("Broken Street Light", 
                "Street light on Main Street has been out for 3 days", 
                "electricity", 
                "Main Street near City Hall", 
                defaultUser);
        complaint1.setPriority(Complaint.Priority.MEDIUM);
        complaint1.setUpvotes(5);

        Complaint complaint2 = new Complaint("Large Pothole", 
                "Dangerous pothole causing vehicle damage", 
                "roads", 
                "Oak Avenue and 5th Street", 
                defaultUser);
        complaint2.setPriority(Complaint.Priority.HIGH);
        complaint2.setStatus(Complaint.Status.IN_PROGRESS);
        complaint2.setUpvotes(12);

        Complaint complaint3 = new Complaint("Overflowing Trash Bins", 
                "Garbage bins in Central Park are overflowing", 
                "waste", 
                "Central Park entrance", 
                defaultUser);
        complaint3.setPriority(Complaint.Priority.LOW);
        complaint3.setUpvotes(3);

        Complaint complaint4 = new Complaint("Water Leak", 
                "Water main leak flooding the sidewalk", 
                "water", 
                "Pine Street", 
                defaultUser);
        complaint4.setPriority(Complaint.Priority.HIGH);
        complaint4.setStatus(Complaint.Status.RESOLVED);
        complaint4.setUpvotes(8);

        complaintRepository.save(complaint1);
        complaintRepository.save(complaint2);
        complaintRepository.save(complaint3);
        complaintRepository.save(complaint4);

        System.out.println("Sample data initialized: 4 complaints created");
    }
}