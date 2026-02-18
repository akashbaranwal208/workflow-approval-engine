package com.workflow.approval.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="approval_actions")
public class ApprovalAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="request_id", nullable = false)
    private Request request;

    @ManyToOne
    @JoinColumn(name="approved_by",nullable = false)
    private User approvedBy;

    @Column(nullable = false)
    private String action;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime localDateTime;
}
