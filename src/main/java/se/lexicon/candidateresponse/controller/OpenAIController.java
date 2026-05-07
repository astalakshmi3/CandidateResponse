package se.lexicon.candidateresponse.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.candidateresponse.dto.CandidateApplication;
import lombok.RequiredArgsConstructor;
import se.lexicon.candidateresponse.dto.CandidateReviewResponse;
import se.lexicon.candidateresponse.service.OpenAIService;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Validated
public class OpenAIController {

    private final OpenAIService openAIService;

    @GetMapping("/chat")
    public String chat(
            @RequestParam @NotNull(message = "Question should not be null") String question
    ) {
        return openAIService.processSimpleChatQuery(question);
    }

    @PostMapping("/review")
    public String reviewCandidate(
            @RequestBody @Valid CandidateApplication candidateApplication
    ) {
        return openAIService.reviewCandidate(candidateApplication);
    }


    @PostMapping("/review/json")
    public CandidateReviewResponse reviewCandidateJson(
            @RequestBody @Valid CandidateApplication candidateApplication
    ) {
        return openAIService.reviewCandidateJson(candidateApplication);
    }
}
