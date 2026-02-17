package com.workflow.approval.repository;

import com.workflow.approval.entity.Request;
import com.workflow.approval.entity.RequestStatus;
import com.workflow.approval.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findByCreatedBy(User user);

    List<Request> findByStatus(RequestStatus status);
}
