# OOP---Exam-Generator

*Overview*
This project was developed as part of an Object-Oriented Programming (OOP) course during my first year of a Bachelor's degree in Computer Science. The main objective was to create an automated system for generating multiple-choice tests.

*Features*
1. Question and Answer Management
Display all questions in the system along with their respective answers, clearly indicating whether each answer is correct.
Add new answers to existing questions or add entirely new questions to the repository.
Update existing questions and answers to ensure content remains current and accurate.
Delete specific answers or entire questions with all associated answers, providing flexibility in maintaining the repository.
2. Test Creation
Manual Test Creation:
Specify the number of questions for the test and select questions from the repository.
Choose which answers to include, and the system generates a text file with the selected questions and answers.
The test always includes the default options "None of the above" and "More than one of the above."
A corresponding answer key file is also generated.
Automated Test Creation:
The system randomly selects questions and answers from the repository, ensuring a balanced mix of correct and incorrect answers.
Supports the inclusion of open-ended questions.
3. File Management
Tests and answer keys are saved as text files with timestamps, facilitating easy organization and retrieval.
The question repository is saved and loaded from binary files, eliminating the need for re-entering data with each session.
4. Question Metadata
Each question is assigned a unique serial number automatically.
Questions can be categorized by difficulty level, including easy, medium, and hard.
Supports both multiple-choice and open-ended questions, adding depth to the test creation process.
5. Error Handling
Custom exceptions are thrown for specific errors, such as attempting to generate a test with too many questions or choosing a multiple-choice question with insufficient answers.
Ensures a smooth and error-free user experience.
Conclusion
This project demonstrates the application of Object-Oriented Programming principles in creating a robust and user-friendly exam generation system. It highlights the importance of modularity, error handling, and data management in software development.
