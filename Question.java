package avielZamanRanBarak;

import java.io.Serializable;

public abstract class Question implements Serializable{

	public enum Difficulity {
		Easy, Medium, Hard
	}

	// static variable
	protected static int SerialGenerator = 1000;

	// member variables
	protected int serial;
	protected String questionString;
	protected Difficulity kind;

	// methods
	// constructors

	public Question(String questionString, Difficulity kind) {
		setQuestionString(questionString);
		serial = SerialGenerator; // we add 10 because we want to classify American Q's as "0" in the unity digit,
		// and open Q's as "1" in the unity digit
		SerialGenerator += 10;
		this.kind = kind;
	}

	// GETTERS
	public String getQuestion() { // gets the question's string
		return questionString;
	}
	
	public void setSerial (int serial) { 	// 	we will use it when we use the function "CopyStock" because to want
											// to keep each question's serial
		this.serial= serial;
	}

	// SETTER
	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}

	public String toString() {
		String st = "Question : " + this.getQuestion() + "     Difficulity : " + kind.name() + "              "
				+ "(Serial Number :" + serial + ")" + "\n";
		return st;
	}
}