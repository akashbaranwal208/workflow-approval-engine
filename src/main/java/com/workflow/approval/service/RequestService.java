package com.workflow.approval.service;

import com.workflow.approval.entity.*;
import com.workflow.approval.exception.ResourceNotFoundException;
import com.workflow.approval.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final WorkflowDefinitionService workflowDefinitionService;


    public RequestService(RequestRepository requestRepository, WorkflowDefinitionService workflowDefinitionService) {
        this.requestRepository = requestRepository;
        this.workflowDefinitionService = workflowDefinitionService;
    }

    public Request createRequest(RequestType requestType, User createdBy)
    {
        WorkflowDefinition workflowDefinition=workflowDefinitionService.getWorkflowByRequestType(requestType)
                .orElseThrow(()->new IllegalStateException("No workflow defines for request type: "+ requestType));

        Request request=Request.builder()
                .requestType(requestType)
                .status(RequestStatus.PENDING)
                .currentStep(1)
                .createdBy(createdBy)
                .build();

        return requestRepository.save(request);
    }

    public List<Request> getRequestsByUser(User user) {
        return requestRepository.findByCreatedBy(user);
    }

    public List<Request> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    public Request getRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Request not found with id: " + requestId
                ));
    }


}
