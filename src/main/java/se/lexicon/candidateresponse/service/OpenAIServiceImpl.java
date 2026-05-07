package se.lexicon.candidateresponse.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import se.lexicon.candidateresponse.dto.CandidateApplication;
import se.lexicon.candidateresponse.dto.CandidateReviewResponse;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String processSimpleChatQuery(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    @Override
    public String reviewCandidate(CandidateApplication candidateApplication) {

        if (candidateApplication == null) {
            throw new IllegalArgumentException("Candidate application is null");
        }

        return chatClient.prompt()
                .system("""
                        You are an experienced HR recruiter evaluating candidates
                        for a Fullstack development course.

                        Review carefully and decide Accepted or Rejected.
                        Give short reasoning and generate a professional email.
                        """)
                .user(String.format("""
                                Candidate Name: %s
                                Background: %s
                                Experience: %s
                                Motivation: %s
                                Skills: %s

                                Output format:
                                Name:
                                Decision:
                                Reasoning:
                                Email:
                                """,
                        candidateApplication.name(),
                        candidateApplication.background(),
                        candidateApplication.experience(),
                        candidateApplication.motivation(),
                        candidateApplication.skills()
                ))

                .call()
                .content();
    }

    @Override
    public CandidateReviewResponse reviewCandidateJson(CandidateApplication candidateApplication) {

        if (candidateApplication == null) {
            throw new IllegalArgumentException("Candidate application is null");
        }

        BeanOutputConverter<CandidateReviewResponse> converter =
                new BeanOutputConverter<>(CandidateReviewResponse.class);

        String format = converter.getFormat();

        String content = chatClient.prompt()
                .system("""
                        You are an experienced HR recruiter evaluating candidates
                        for a Fullstack development course.

                        Decide Accepted or Rejected.
                        Include short reasoning and professional email.

                        Return only JSON using this format:
                        %s
                        """.formatted(format))
                .user(String.format("""
                        Candidate Name: %s
                        Background: %s
                        Experience: %s
                        Motivation: %s
                        Skills: %s
                        """,
                        candidateApplication.name(),
                        candidateApplication.background(),
                        candidateApplication.experience(),
                        candidateApplication.motivation(),
                        candidateApplication.skills()
                ))
                .call()
                .content();

        if (content == null) {
            throw new IllegalStateException("OpenAI response was empty");
        }

        return converter.convert(content);
    }
}