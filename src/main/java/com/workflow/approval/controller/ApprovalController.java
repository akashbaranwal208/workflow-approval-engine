package com.workflow.approval.controller;

import com.workflow.approval.entity.Request;
import com.workflow.approval.entity.User;
import com.workflow.approval.exception.ResourceNotFoundException;
import com.workflow.approval.security.SecurityUtil;
import com.workflow.approval.service.ApprovalService;
import com.workflow.approval.service.RequestService;
import com.workflow.approval.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;
    private final RequestService requestService;
    private final UserService userService;

    public ApprovalController(ApprovalService approvalService, RequestService requestService, UserService userService) {
        this.approvalService = approvalService;
        this.requestService = requestService;
        this.userService = userService;
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveRequest(@RequestParam Long requestId,
                                                 @RequestParam(required = false) String comment)
    {
        String approverEmail= SecurityUtil.getCurrentUserEmail();
        Request request=requestService.getRequestById(requestId);
        User approver=userService.getUserByEmail(approverEmail).
                orElseThrow(()->new ResourceNotFoundException("Approver not found with email:" + approverEmail));

        approvalService.approveRequest(request,approver,comment);
        return ResponseEntity.ok("Request Approved Successfully");

    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectRequest(
            @RequestParam Long requestId,
            @RequestParam(required = false) String comment) {

        String approverEmail=SecurityUtil.getCurrentUserEmail();
        Request request = requestService.getRequestById(requestId);
        User approver = userService.getUserByEmail(approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Approver not found with email: " + approverEmail
                ));

        approvalService.rejectRequest(request, approver, comment);
        return ResponseEntity.ok("Request rejected successfully");
    }
}
