package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Member;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.PriorityEnum;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchCriteria {
    private String title;
    private StatusEnum status;
    private PriorityEnum priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Member creator;
    private Member assignee;
}
