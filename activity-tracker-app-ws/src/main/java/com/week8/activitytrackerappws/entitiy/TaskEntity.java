package com.week8.activitytrackerappws.entitiy;
import com.week8.activitytrackerappws.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private Status status = Status.PENDING;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime completedAt;


    @ManyToOne
    @JoinColumn( name = "student_id")
    private UserEntity userEntity;
    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void setUpdatedAt(){
        updatedAt= LocalDateTime.now();
    }
}
