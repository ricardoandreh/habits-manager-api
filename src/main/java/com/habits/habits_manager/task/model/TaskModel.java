package com.habits.habits_manager.task.model;

import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.task.enums.IconType;
import com.habits.habits_manager.task.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "tb_task")
@Table(name = "tb_task")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TaskModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean completed;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    
    @Column(nullable = false)
    private String color;
    
    @Column(nullable = false)
    private IconType icon;
    
    @Column(nullable = false)
    private TaskType type;

    @Column
    private String location;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                ", type=" + type +
                ", location='" + location + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
