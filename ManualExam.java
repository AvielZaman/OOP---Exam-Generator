package avielZamanRanBarak;

public class ManualExam extends Exam {
	
	// setter
	public void setManualExam (Stock testFromMain) {
		this.questionsForExam = testFromMain;
	}
	
	public void createExam(Stock testFromMain) {
		setManualExam (testFromMain);
	}
		
	}

