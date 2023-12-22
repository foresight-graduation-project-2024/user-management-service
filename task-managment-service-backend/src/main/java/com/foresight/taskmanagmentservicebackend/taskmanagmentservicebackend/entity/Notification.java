package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
        private String notificationId;
        private String content;
        private LocalDateTime issuedDate;
        private Member receiver;
}
