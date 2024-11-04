package avielZamanRanBarak;

import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable {
	// member variables
	private String schoolSolution;

	// constructor
	public OpenQuestion(String questionString, Answer schoolSolution, Difficulity kind) {
		super(questionString, kind);
		setSolution(schoolSolution.getStringAnswer());
	}

	// setter
	public void setSolution(String schoolSolution) {
		this.schoolSolution = schoolSolution;
	}

	// getter
	public String getSolution() {
		return schoolSolution;
	}

	public String toString() {
		return super.toString() + this.getSolution() + "\n";
	}

	public String toStringNoSolution() {
		return super.toString() + "\n";
	}
}
