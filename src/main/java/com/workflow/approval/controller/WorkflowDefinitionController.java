package com.workflow.approval.controller;

import com.workflow.approval.entity.RequestType;
import com.workflow.approval.entity.WorkflowDefinition;
import com.workflow.approval.service.WorkflowDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowDefinitionController {

    private final WorkflowDefinitionService workflowDefinitionService;

    public WorkflowDefinitionController(WorkflowDefinitionService workflowDefinitionService) {
        this.workflowDefinitionService = workflowDefinitionService;
    }

    @PostMapping
    public ResponseEntity<WorkflowDefinition>  createWorkflow(@RequestParam RequestType requestType,
                                                              @RequestParam Integer totalSteps)
    {
        WorkflowDefinition workflowDefinition= WorkflowDefinition.builder()
                .requestType(requestType)
                .totalSteps(totalSteps)
                .build();

        return ResponseEntity.ok(workflowDefinitionService.createWorkflow(workflowDefinition));
    }
}
