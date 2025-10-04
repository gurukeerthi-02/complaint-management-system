package com.complaint.system.repository;

import com.complaint.system.entity.Complaint;
import com.complaint.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {
    List<Complaint> findByUser(User user);
    List<Complaint> findByStatus(Complaint.Status status);
    List<Complaint> findByCategory(String category);
}