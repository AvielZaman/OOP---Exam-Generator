package avielZamanRanBarak;

public abstract class Exam  implements Examable {
	protected Stock questionsForExam;
	
	
	
	public abstract void createExam(Stock stock);
}
