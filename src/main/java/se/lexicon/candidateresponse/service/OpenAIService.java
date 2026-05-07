package se.lexicon.candidateresponse.service;

import se.lexicon.candidateresponse.dto.CandidateApplication;
import se.lexicon.candidateresponse.dto.CandidateReviewResponse;

public interface OpenAIService {
    String processSimpleChatQuery (String query);
    String reviewCandidate (CandidateApplication candidateApplication);
    CandidateReviewResponse reviewCandidateJson (CandidateApplication candidateApplication);
}
