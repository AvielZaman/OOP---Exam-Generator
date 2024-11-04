package avielZamanRanBarak;

public class AutomaticExam extends Exam {
	// finals
	public static final int MAX_RANDOM_ANSWERS_FOR_QUESTION = 4; // maximum of answers in question when getting random answers
	public static final int MIN_ANSWERS_FALSE = 3;
	public static final int MIN_GENERAL_ANSWERS_OF_QUESTION = 4; // minimum of answers in question when choosing for test

	// member variables
	private int numOfQuestions;

	// setter
	public void setNumOfQuestions(int numQs) {
		numOfQuestions = numQs;
	}

	public void createExam(Stock stock) {
		int randomQ, randomA, incorrectAnswers;
		int countTAnswers; // indicator if we already choose a true answer
		Stock autoExam = new Stock();
		for (int i = 0; i < this.numOfQuestions; i++) {
			randomQ = randomQuestion(stock);
			if (stock.getStock()[randomQ].serial % 2 != 0) { // this is an americanQ
				Answer [] renewdAnswers= new Answer [5];
				countTAnswers=0;
				incorrectAnswers = ((AmericanQuestion) (stock.getStock()[randomQ])).findInCorrectAnswers();
				if (incorrectAnswers < MIN_ANSWERS_FALSE || ((AmericanQuestion) (stock.getStock()[randomQ])).getAnswersArray().length < MIN_GENERAL_ANSWERS_OF_QUESTION) { 
				// check if the question has minimum 3 false answers (because if it has 2 or less false answers, the rest answers is true and we can
				// only add 1 true answer, so there will be 3 answers in the new array of answers, and we need 4 (without the default), or check if
				// the question has less than 4 answers in total
					stock.removeQuestion(randomQ+1); // we remove that question because it doesn't meet the condition of
													// minimum false answers
					i--;
					continue;
				}
				else {
				for (int j = 0; j < MAX_RANDOM_ANSWERS_FOR_QUESTION; j++) {
					randomA = randomAnswer(((AmericanQuestion) (stock.getStock()[randomQ])));
					if (((AmericanQuestion) (stock.getStock()[randomQ])).getAnswersArray()[randomA].getIndicate()) {
						if (countTAnswers ==0) {	// check if it's the first answer with indication true
							countTAnswers++;
							renewdAnswers[j]= ((AmericanQuestion) (stock.getStock()[randomQ])).getAnswersArray()[randomA];
							continue;
					}
						((AmericanQuestion) (stock.getStock()[randomQ])).removeAnswer(randomA+1); // remove other answers with "true"
						j--;
						continue;
					}
					else {	// indication false
						renewdAnswers[j]= ((AmericanQuestion) (stock.getStock()[randomQ])).getAnswersArray()[randomA];
						((AmericanQuestion) (stock.getStock()[randomQ])).removeAnswer(randomA+1);
				}
				}	// finished fill the new answers array
				((AmericanQuestion) (stock.getStock()[randomQ])).setAnswers(renewdAnswers);
				// no answer with "true" added, which means "all the answers are incorrect" is true
				((AmericanQuestion) (stock.getStock()[randomQ])).getAnswersArray()[4]= new Answer ("There is no correct answer", countTAnswers==0);
				}
			}
			stock.chooseAQuestion(randomQ + 1, autoExam);
			stock.removeQuestion(randomQ+1);
		}		// finished fill the exam
		this.questionsForExam= autoExam;
	}

	public int randomQuestion(Stock stock) {
		return (int) (Math.random() * stock.getStock().length);
	}

	public int randomAnswer(AmericanQuestion americanQ) {
		return (int) (Math.random() * americanQ.getAnswersArray().length);
	}
	
}
