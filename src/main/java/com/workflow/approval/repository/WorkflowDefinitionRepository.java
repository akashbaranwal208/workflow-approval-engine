package com.workflow.approval.repository;

import com.workflow.approval.entity.RequestType;
import com.workflow.approval.entity.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition,Long> {

    Optional<WorkflowDefinition> findByRequestType(RequestType requestType);
}
