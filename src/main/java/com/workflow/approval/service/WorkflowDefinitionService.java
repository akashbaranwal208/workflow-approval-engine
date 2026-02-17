package com.workflow.approval.service;

import com.workflow.approval.entity.RequestType;
import com.workflow.approval.entity.WorkflowDefinition;
import com.workflow.approval.repository.WorkflowDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkflowDefinitionService {

    private final WorkflowDefinitionRepository workflowDefinitionRepository;

    public WorkflowDefinitionService(WorkflowDefinitionRepository workflowDefinitionRepository) {
        this.workflowDefinitionRepository = workflowDefinitionRepository;
    }

    public WorkflowDefinition createWorkflow(WorkflowDefinition workflowDefinition)
    {
        return workflowDefinitionRepository.save(workflowDefinition);
    }

    public Optional<WorkflowDefinition> getWorkflowByRequestType(RequestType requestType)
    {
        return workflowDefinitionRepository.findByRequestType(requestType);
    }
}
