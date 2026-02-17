package com.workflow.approval.repository;

import com.workflow.approval.entity.ApprovalAction;
import com.workflow.approval.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalActionRepository extends JpaRepository<ApprovalAction,Long> {

    List<ApprovalAction> findByRequest(Request request);
}
