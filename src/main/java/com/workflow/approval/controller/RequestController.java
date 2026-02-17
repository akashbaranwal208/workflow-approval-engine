package com.workflow.approval.controller;


import com.workflow.approval.entity.Request;
import com.workflow.approval.entity.RequestStatus;
import com.workflow.approval.entity.RequestType;
import com.workflow.approval.entity.User;
import com.workflow.approval.exception.ResourceNotFoundException;
import com.workflow.approval.service.RequestService;
import com.workflow.approval.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;

    public RequestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestParam RequestType requestType,
                                                 @RequestParam String userEmail)
    {

        User user=userService.getUserByEmail(userEmail)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found: " + userEmail));

        return ResponseEntity.ok(requestService.createRequest(requestType,user));

    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Request>> getRequestByUser(@PathVariable String email)
    {
        User user= userService.getUserByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User Not found: "+ email));

        return ResponseEntity.ok(requestService.getRequestsByUser(user));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable RequestStatus status)
    {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }
}
