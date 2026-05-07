package se.lexicon.candidateresponse.service;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import se.lexicon.candidateresponse.dto.CandidateApplication;
import se.lexicon.candidateresponse.dto.CandidateReviewResponse;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final OpenAiChatModel openAiChatModel;

    public OpenAIServiceImpl(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @Override
    public String processSimpleChatQuery(String query) {
        return openAiChatModel.call(query);
    }

    @Override
    public String reviewCandidate(CandidateApplication candidateApplication) {

        SystemMessage systemMessage = new SystemMessage("""
                You are an experienced HR recruiter evaluating candidates
                for a Fullstack development course.

                Decide if the candidate is Accepted or Rejected.
                Give short reasoning and generate a professional email.
                """);

        UserMessage userMessage = new UserMessage(String.format("""
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
        ));

        Prompt prompt = new Prompt(systemMessage, userMessage);

        return openAiChatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

    @Override
    public CandidateReviewResponse reviewCandidateJson(CandidateApplication candidateApplication) {

        BeanOutputConverter<CandidateReviewResponse> converter =
                new BeanOutputConverter<>(CandidateReviewResponse.class);

        String format = converter.getFormat();

        SystemMessage systemMessage = new SystemMessage("""
                You are an experienced HR recruiter evaluating candidates
                for a Fullstack development course.

                Decide if the candidate is Accepted or Rejected.
                Give short reasoning and generate a professional email.

                Return only JSON using this format:
                %s
                """.formatted(format));

        UserMessage userMessage = new UserMessage(String.format("""
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
        ));

        Prompt prompt = new Prompt(systemMessage, userMessage);

        String response = openAiChatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        if (response == null) {
            throw new IllegalStateException("OpenAI response was empty");
        }

        return converter.convert(response);
    }
}