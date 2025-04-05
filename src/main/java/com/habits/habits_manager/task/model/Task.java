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
@NoArgsConstructor
@EqualsAndHashCode
public class Task implements Serializable {
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
    private String duration;
    private IconType icon;
    private String color;
    private boolean completed;
    private TaskType type;
}
