package avielZamanRanBarak;

import java.io.Serializable;

public class Answer implements Serializable{
	// member variables
	private boolean indication;
	private String answerString;

	// methods
	// constructors

	public Answer(String answerString, boolean indication) {
		setAnswerString(answerString);
		setIndicate(indication);
	}

	// copy constructor
	public Answer(Answer other) {
		indication = other.indication;
		answerString = other.answerString;
	}

	// GETTERS
	public String getStringAnswer() { // gets the answer's string
		return answerString;
	}

	public boolean getIndicate() { // gets the answer's indication
		return indication;
	}

	// SETTERS

	public void setIndicate(boolean indication) {
		this.indication = indication;
	}

	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

	// other methods

	public String toString() {
		return answerString + " , " + indication;
	}
}
