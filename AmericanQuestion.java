package avielZamanRanBarak;

import java.io.Serializable;

public class AmericanQuestion extends Question implements Serializable {
	// finals
	private static final int MAX_ANSWERS = 8;

	// member variables
	private Answer[] answers;

	public AmericanQuestion(String questionString, Answer[] answersOfUser, Difficulity kind) {
		super(questionString, kind);
		answers = new Answer[answersOfUser.length];
		for (int i = 0; i < answersOfUser.length; i++) {
			answers[i] = new Answer(answersOfUser[i]); // creates objects to each reference
		}
		serial++; // the unity digit will be "1"- which means it's an American Q
	}

	// getter
	public Answer[] getAnswersArray() {
		return this.answers;
	}

	// setter
	public void setAnswers(Answer[] answers) {
		this.answers = answers;
	}

	// other methods

	public int findInCorrectAnswers() {	// return how much answers with indication false are exist 
		int countIncorrectAns= 0;
		for (int i = 0; i < this.getAnswersArray().length; i++) {
			if (!this.getAnswersArray()[i].getIndicate())
				countIncorrectAns++;
		}
		return countIncorrectAns;
	}

	public void removeAnswer(int location) {
		Answer[] tempAnswers = new Answer[answers.length - 1];
		answers[location - 1] = answers[answers.length - 1]; // copy the last value of the array
		for (int i = 0; i < tempAnswers.length; i++)
			tempAnswers[i] = answers[i];
		this.answers = tempAnswers;
	}

	public boolean addAnswer(AmericanQuestion question, String answerString, boolean indication) {
		// check if the total of answers less than the max answers
		if (question.getAnswersArray().length < MAX_ANSWERS) {
			Answer[] tempAnswers = new Answer[question.getAnswersArray().length + 1];
			tempAnswers[tempAnswers.length - 1] = new Answer(answerString, indication);
			for (int i = 0; i < tempAnswers.length - 1; i++)
				tempAnswers[i] = question.getAnswersArray()[i];
			this.answers = tempAnswers;
			return true;
		}
		return false; // if the total of answers is equal to max answers (or greater) , return false
	}

	public String toStringNoIndication() {
		String st = "";
		for (int i = 0; i < this.getAnswersArray().length; i++)
			st += (char) (i + 97) + ". " + this.getAnswersArray()[i].getStringAnswer() + "\n";
		return super.toString() + st;
	}

	public String toString() { // shows the question with the answers
		String st = "";
		for (int i = 0; i < this.getAnswersArray().length; i++) {
			if (this.getAnswersArray()[i] == null)
				continue;
			st += (char) (i + 97) + ". " + this.getAnswersArray()[i].toString() + "\n";
		}
		return super.toString() + st;
	}

}
