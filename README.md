# OOP---Exam-Generator

**Overview**
This project was developed as part of an Object-Oriented Programming (OOP) course during my first year of a Bachelor's degree in Computer Science. The main objective was to create an automated system for generating multiple-choice tests.

**Features**

1. **Question and Answer Management**
   - View all questions along with their answers.
   - Add answers to existing questions or add entirely new questions.
   - Update questions and answers to keep content accurate and relevant.
   - Delete specific answers or entire questions along with all associated answers.

2. **File Management**
   - Save tests and answer keys as timestamped text files for easy organization.
   - Store questions in binary files to avoid re-entering data for each session.

3. **Question Metadata**
   - Each question is automatically assigned a unique serial number.
   - Questions can be categorized by difficulty level (easy, medium, or hard).
   - Supports both multiple-choice and open-ended question formats.

4. **Test Creation**
   - **Manual Test Creation**: Define the number of questions for the test and select questions from the stock (with at least 3 answers in it). The system generates a text file with the selected questions, always including the options "There is no correct answer" and "More than one of the above." A corresponding answer key is also created.
   - **Automated Test Creation**: The system randomly selects questions from the stock. Open-ended questions can also be included.
