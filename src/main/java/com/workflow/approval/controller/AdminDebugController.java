package com.workflow.approval.controller;

import com.workflow.approval.repository.ApprovalActionRepository;
import com.workflow.approval.repository.RequestRepository;
import com.workflow.approval.repository.WorkflowDefinitionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDebugController {

    private final ApprovalActionRepository approvalActionRepository;
    private final RequestRepository requestRepository;
    private final WorkflowDefinitionRepository workflowDefinitionRepository;

    public AdminDebugController(
            ApprovalActionRepository approvalActionRepository,
            RequestRepository requestRepository,
            WorkflowDefinitionRepository workflowDefinitionRepository
    ) {
        this.approvalActionRepository = approvalActionRepository;
        this.requestRepository = requestRepository;
        this.workflowDefinitionRepository = workflowDefinitionRepository;
    }

    @PostMapping("/api/admin/clear-workflow")
    public String clearWorkflowTables() {

        approvalActionRepository.deleteAll();
        requestRepository.deleteAll();
        workflowDefinitionRepository.deleteAll();

        return "Workflow tables cleared successfully";
    }
}