package com.week8.activitytrackerappws.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private String title;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String completedAt;
}
