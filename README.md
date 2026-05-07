# Application Review and Candidate Response
### Scenario:
A company is offering a Fullstack course and has received over 1000 applications.

Each application may include:

name
background
experience
motivation
technical skills
The company wants to:

review applications efficiently

decide if the candidate should be:

Accepted
Rejected
generate a professional email response

include short feedback

Example Input
Name: Erik Johansson
Background: Studied economics.
Experience: Basic knowledge of HTML and CSS.
Motivation: Wants to switch to a career in web development.
Skills: HTML, CSS

---

Name: Anna Svensson
Background: Computer science.
Experience: Built several web applications using React and Node.js.
Motivation: Wants to deepen backend skills.
Skills: JavaScript, React, Node.js, MongoDB
Task
Write a prompt that:

Reviews each application

Decides:

Accepted or Rejected
Generates a personalized email for each candidate

Includes:

clear decision
short reasoning
professional tone
Your Prompt
Write your full prompt below.

You can structure it as:

# SYSTEM:
## Role:
* You are an experience Interviewer, so carefully and make a decision based on the candidate’s background, motivation, and technical skills.
* Your job is to review each application efficiently, decide whether to accept or reject the candidate, and generate a professional email response that includes clear feedback.
# USER:
## Task:
* Decide whether the candidate is Accepted or Rejected
## Context:
* Each application may include: name, background experience, motivation, technical skills.
## Output:
* Write a short reasoning (1–2 lines) explaining the decision Generate a professional email response for each candidate. For each candidate, provide: Name: Decision: Reasoning: Email:
## Constrains:
* Clearly state the decision (Accepted or Rejected) Include brief feedback Keep the email concise (100–120 words)
## Instructions:
* Use a polite and professional tone Personalize the email with the candidate’s name.
  Tip: Think about role, task, structure, and tone when writing your prompt.rienced HR recruiter responsible for evaluating candidates for a Fullstack development course.
