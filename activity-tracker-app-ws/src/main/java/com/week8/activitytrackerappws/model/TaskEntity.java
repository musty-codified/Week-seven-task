package com.week8.activitytrackerappws.model;
import com.week8.activitytrackerappws.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    public TaskEntity( String title, String description, Status status, UserEntity userEntity){
        this.title = title;
        this.description = description;
        this.status = status;
        this.userEntity = userEntity;

    }

    @CreationTimestamp
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn( name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "task_user_id_fk"))

    private UserEntity userEntity;


}
