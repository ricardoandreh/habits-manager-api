package com.habits.habits_manager.task.service;

import com.habits.habits_manager.task.dtos.FeedbackResponseDTO;
import com.habits.habits_manager.task.model.TaskModel;
import com.habits.habits_manager.task.repository.TaskRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Value("classpath:/system-prompt-template.st")
    private Resource systemPromptTemplate;

    @Value("classpath:/user-prompt-template.st")
    private Resource userPromptTemplate;

    private final ChatClient chatClient;

    private final TaskRepository taskRepository;

    public FeedbackService(ChatClient.Builder chatClientBuilder, TaskRepository taskRepository) {
        this.chatClient = chatClientBuilder.build();
        this.taskRepository = taskRepository;
    }

    public FeedbackResponseDTO generateFeedback() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TaskModel> tasks = this.taskRepository.findByUserEmail(email);

        String tasksString = tasks.stream()
            .map(TaskModel::toString)
            .collect(Collectors.joining("\n"));

        String feedback = this.performRequest(tasksString);

        return new FeedbackResponseDTO(feedback);
    }

    public String performRequest(String tasksParam) {

        return this.chatClient.prompt()
            .system(systemSpec -> systemSpec
                    .text(this.systemPromptTemplate))
            .user(userSpec -> userSpec
                    .text(this.userPromptTemplate)
                    .param("tasks", tasksParam))
            .call()
            .content();
    }
}
