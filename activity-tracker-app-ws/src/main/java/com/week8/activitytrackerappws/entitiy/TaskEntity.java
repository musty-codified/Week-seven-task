package com.week8.activitytrackerappws.entitiy;


import com.week8.activitytrackerappws.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String status = Status.PENDING;
    private String createdAt;
    private String updatedAt;
    private String completedAt;
}
