package se.lexicon.candidateresponse.dto;

public record CandidateReviewResponse(
        String candidateName,
        String decision,
        String shortFeedback,
        String email
) {
}
