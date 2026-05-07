package service;

import dto.CandidateApplication;

public interface OpenAIService {
    String reviewCandidate (CandidateApplication candidateApplication);
}
