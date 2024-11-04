package avielZamanRanBarak;

import java.io.Serializable;

public class Stock implements Serializable{
	// member variables
	protected Question[] questions;

	// methods
	// constructor

	public Stock(Question[] questions) {
		setQuestions(questions);
	}

	// default constructor
	public Stock() {
		this.questions = new Question[0];
	}

	// getters
	public Question[] getStock() {
		return questions;
	}

	// setter
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}

	// other methods

	public void chooseAQuestion(int numOfUserQuestion, Stock test) {
		if (questions[numOfUserQuestion - 1].serial % 2 == 0) // check if the question is an open Q
			test.addOpenQuestion((OpenQuestion) questions[numOfUserQuestion - 1]);
		else
			test.addAmericanQuestion((AmericanQuestion) questions[numOfUserQuestion - 1]);
	}

	public void addAmericanQuestion(AmericanQuestion anotherQuestion) { // updates the stock with a new question
		Question[] tempQuestions = new Question[this.questions.length + 1];
		for (int i = 0; i < this.questions.length; i++)
			tempQuestions[i] = this.questions[i];
		tempQuestions[tempQuestions.length - 1] = anotherQuestion;
		this.questions = tempQuestions;
	}

	public void addOpenQuestion(OpenQuestion anotherQuestion) {
		Question[] tempQuestions = new Question[this.questions.length + 1];
		for (int i = 0; i < this.questions.length; i++)
			tempQuestions[i] = this.questions[i];
		tempQuestions[tempQuestions.length - 1] = anotherQuestion;
		this.questions = tempQuestions;
	}

	public boolean insertQuestionNum(int location, String str, boolean indication) {
		if (!((AmericanQuestion) this.questions[location - 1])
				.addAnswer((AmericanQuestion) this.questions[location - 1], str, indication))
			return false;
		return true;
	}

	public void removeQuestion(int location) {
		Question[] tempQuestions = new Question[questions.length - 1];
		questions[location - 1] = questions[questions.length - 1]; // copy the last value of the array
		for (int i = 0; i < tempQuestions.length; i++)
			tempQuestions[i] = questions[i];
		this.questions = tempQuestions;
		// * the order of questions has changed
	}

	public void removeDefaultAnswers() { // after generating a test, remove the default answers from the stock
		for (int i = 0; i < this.getStock().length; i++) {
			if (this.getStock()[i].serial % 2 != 0) { // check if it is an American Q
				Answer[] answersAfterRemove = new Answer[(((AmericanQuestion) this.getStock()[i])
						.getAnswersArray().length - 2)];
				for (int j = 0; j < ((AmericanQuestion) this.getStock()[i]).getAnswersArray().length - 2; j++) { 	
					// copy the values from the	 old array to the new one without the default answers
					answersAfterRemove[j] = ((AmericanQuestion) this.getStock()[i]).getAnswersArray()[j];
				}
				((AmericanQuestion) this.getStock()[i]).setAnswers(answersAfterRemove);
			}
		}
	}
	
	public void emptyStock () {
		for (int i = 0; i < this.getStock().length; i++) {
			this.removeQuestion(i+1);
		}
	}

	public String toString() { // prints the stock
		String st = "";
		for (int i = 0; i < questions.length; i++)
			st += Integer.toString(i + 1) + ") " + questions[i].toString() + "\n";
		return st;
	}


	
	

}
