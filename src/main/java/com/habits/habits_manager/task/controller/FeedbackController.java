package com.habits.habits_manager.task.controller;

import com.habits.habits_manager.task.dtos.FeedbackResponseDTO;
import com.habits.habits_manager.task.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FeedbackResponseDTO generateFeedback() {

        return this.feedbackService.generateFeedback();
    }
}
