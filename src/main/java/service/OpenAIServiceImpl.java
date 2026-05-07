package service;

import dto.CandidateApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import com.openai.models.completions.CompletionCreateParams;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    private final OpenAiChatModel openAiChatModel;

    @Override
    public String reviewCandidate(CandidateApplication candidateApplication) {

        if (candidateApplication == null) {
            throw new IllegalArgumentException("Candidate application is null");
        }

        SystemMessage systemMessage = SystemMessage.builder()
                .text("""
                        You are an experienced HR recruiter evaluating candidates
                        for a Fullstack development course.

                        Your responsibilities:
                        - Review the candidate application carefully
                        - Decide whether the candidate is Accepted or Rejected
                        - Give short reasoning
                        - Generate a professional personalized email

                        Rules:
                        - Be fair and professional
                        - Focus on background, experience, motivation, and technical skills
                        - Email must be 100 to 120 words
                        - Use polite and clear English
                        """)
                .build();

        String userInput = String.format("""
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
        );

        UserMessage userMessage = UserMessage.builder()
                .text(userInput)
                .build();

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .chatOptions(
                        ChatOptions.builder()
                                .model("gpt-4o")
                                .temperature(0.7)
                                .maxTokens(1500)
                                .build()
                )
                .build();

        return openAiChatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }
}