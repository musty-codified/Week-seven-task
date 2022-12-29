package com.week8.activitytrackerappws.dto;


import com.week8.activitytrackerappws.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TaskDto {
    private String title;
    private String description;
    private Status status = Status.PENDING;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
