package com.complaint.system.service;

import com.complaint.system.entity.Complaint;
import com.complaint.system.entity.User;
import com.complaint.system.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint createComplaint(String title, String description, String category, 
                                   String location, String priority, String imageUrl, User user) {
        Complaint complaint = new Complaint(title, description, category, location, user);
        complaint.setPriority(Complaint.Priority.valueOf(priority.toUpperCase()));
        complaint.setImageUrl(imageUrl);
        return complaintRepository.save(complaint);
    }


    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepository.findByUser(user);
    }

    public Optional<Complaint> getComplaintById(UUID id) {
        return complaintRepository.findById(id);
    }

    public Complaint updateComplaintStatus(UUID id, String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(Complaint.Status.valueOf(status.toUpperCase()));
        return complaintRepository.save(complaint);
    }

    public Complaint upvoteComplaint(UUID id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setUpvotes(complaint.getUpvotes() + 1);
        return complaintRepository.save(complaint);
    }
}