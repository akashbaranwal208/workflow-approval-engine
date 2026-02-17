package com.workflow.approval.service;

import com.workflow.approval.entity.*;
import com.workflow.approval.exception.BusinessException;
import com.workflow.approval.repository.ApprovalActionRepository;
import com.workflow.approval.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApprovalService {

    private final ApprovalActionRepository approvalActionRepository;
    private final RequestRepository requestRepository;
    private final WorkflowDefinitionService workflowDefinitionService;

    public ApprovalService(ApprovalActionRepository approvalActionRepository, RequestRepository requestRepository, WorkflowDefinitionService workflowDefinitionService) {
        this.approvalActionRepository = approvalActionRepository;
        this.requestRepository = requestRepository;
        this.workflowDefinitionService = workflowDefinitionService;
    }

    public void approveRequest(Request request, User approver,String comment)
    {
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new BusinessException("Only PENDING requests can be approved");
        }

        WorkflowDefinition workflowDefinition=workflowDefinitionService
                .getWorkflowByRequestType(request.getRequestType())
                .orElseThrow(() -> new IllegalStateException(
                        "No workflow defined for request type: " + request.getRequestType()
                ));

        int currentStep = request.getCurrentStep();

        // 3️⃣ ROLE-BASED VALIDATION (THIS IS THE NEW PART)

        // Step 1 → MANAGER only
        if (currentStep == 1 && approver.getRole() != Role.MANAGER) {
            throw new BusinessException("Only MANAGER can approve step 1");
        }

        // Step 2 → FINANCE only
        if (currentStep == 2 && approver.getRole() != Role.FINANCE) {
            throw new BusinessException("Only FINANCE can approve step 2");
        }

        ApprovalAction approvalAction=ApprovalAction.builder()
                .request(request)
                .approvedBy(approver)
                .action("APPROVE")
                .comment(comment)
                .localDateTime(LocalDateTime.now())
                .build();

        approvalActionRepository.save(approvalAction);

        if(request.getCurrentStep()< workflowDefinition.getTotalSteps())
        {
            request.setCurrentStep(request.getCurrentStep()+1);

        }
        else{
                request.setStatus(RequestStatus.APPROVED);
        }

        requestRepository.save(request);
    }

    public void rejectRequest(Request request , User approver, String comment)
    {

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new BusinessException(
                    "Only PENDING requests can be rejected. Current status: " + request.getStatus()
            );
        }
        ApprovalAction approvalAction = ApprovalAction.builder()
                .request(request)
                .approvedBy(approver)
                .action("REJECT")
                .comment(comment)
                .localDateTime(LocalDateTime.now())
                .build();

        approvalActionRepository.save(approvalAction);
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);
    }
}
