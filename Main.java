package avielZamanRanBarak;

import java.util.Arrays;
import java.util.Scanner;

import avielZamanRanBarak.Question.Difficulity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
	// finals
	public static final int MAX_ANSWERS = 8;
	public static final int MIN_ANSWERS_TO_CREATE = 2; // minimum of answers when creating an American question
	public static final int MIN_ANSWERS_FOR_TEST = 4; // minimum of answers in question when choosing for test
	public static final int LIMIT_OF_QS = 3;

	// Scanner
	public static Scanner s = new Scanner(System.in);

	public static boolean indication;

	public static Answer[] answersOfQuestion(String strOfQuestion) { // build an array of answers to Q
		System.out.println(strOfQuestion);
		System.out.println("Enter the number of answers you want up to 8 answers ( minimum " + MIN_ANSWERS_TO_CREATE
				+ " answers):");
		int numOfAnswers = s.nextInt();
		while (numOfAnswers < MIN_ANSWERS_TO_CREATE || numOfAnswers > MAX_ANSWERS) {
			System.out.println("Can't initialize, insert another number. ");
			numOfAnswers = s.nextInt();
		}
		Answer[] answers = new Answer[numOfAnswers];
		insertUserAnswers(answers);
		return answers;
	}

	public static Answer[] addDefaultAnswers(Answer[] answers) {
		int countRightAnswers = 0, countFalseAnswers = 0;
		Answer[] tempAnswers = new Answer[answers.length + 2];
		for (int i = 0; i < answers.length; i++) {
			tempAnswers[i] = answers[i];
			if (answers[i].getIndicate())
				countRightAnswers++; // count how many right answers
			else
				countFalseAnswers++; // count how many false answers
		}
		if (countRightAnswers == answers.length) {
			tempAnswers[tempAnswers.length - 2] = new Answer("All the answers are correct", true);// if all the answers
																									// are true
			tempAnswers[tempAnswers.length - 1] = new Answer("There is no correct answer", false);
			for (int i = 0; i < tempAnswers.length - 2; i++) {
				tempAnswers[i].setIndicate(false);

			}
		} else if (countFalseAnswers == answers.length) {
			tempAnswers[tempAnswers.length - 2] = new Answer("All the answers are correct", false);
			tempAnswers[tempAnswers.length - 1] = new Answer("There is no correct answer", true);
		} else {
			tempAnswers[tempAnswers.length - 2] = new Answer("All the answers are correct", false);
			tempAnswers[tempAnswers.length - 1] = new Answer("There is no correct answer", false);
		}
		return tempAnswers;
	}

	public static void insertUserAnswers(Answer[] answers) {
		String str;
		for (int i = 0; i < answers.length; i++) {
			System.out.println("Enter the string of answer number " + (i + 1) + " : (use - for space)");
			s.nextLine();
			str = s.nextLine();
			System.out.println("Is that the right answer ? (true / false)");
			indication = s.nextBoolean();
			answers[i] = new Answer(str, indication);
		}
	}

	public static void testBuilder(int numOfQuestions, Stock copyOfStock, Stock originalStock, Stock test)
			throws NumOfQsOverLimit, AmericanUnderMin {
		if (numOfQuestions < 1)
			System.out.println("Invalid input, try again.");

		else if (numOfQuestions <= LIMIT_OF_QS) { // check if the input is valid
			int chosenQuestion;
			int originalLengthOfAs;
			for (int i = 0; i < numOfQuestions; i++) {

				System.out.println("What question's number do you want ?");
				chosenQuestion = s.nextInt();
				chosenQuestion = checkIfQBetweenRange(chosenQuestion, copyOfStock);
				if (checkIfAmericanQ(chosenQuestion, copyOfStock)) {
					originalLengthOfAs = ((AmericanQuestion) originalStock.getStock()[chosenQuestion - 1])
							.getAnswersArray().length;
					if (((AmericanQuestion) copyOfStock.getStock()[chosenQuestion - 1])
							.getAnswersArray().length < MIN_ANSWERS_FOR_TEST) // check if the chosen American Question
																				// under min
						// of A's
						throw new AmericanUnderMin(" The chosen American Question under minimum of Answers");
					else if (originalLengthOfAs == ((AmericanQuestion) copyOfStock.getStock()[chosenQuestion - 1])
							.getAnswersArray().length) {
						// we do it because we didn't want to duplicate the default answers in the test.
						Answer[] tempAnswers = new Answer[((AmericanQuestion) copyOfStock.getStock()[chosenQuestion
								- 1]).getAnswersArray().length + 2];
						tempAnswers = addDefaultAnswers(
								((AmericanQuestion) copyOfStock.getStock()[chosenQuestion - 1]).getAnswersArray());
						((AmericanQuestion) copyOfStock.getStock()[chosenQuestion - 1]).setAnswers(tempAnswers);
					}

				}
				copyOfStock.chooseAQuestion(chosenQuestion, test);
			}
		} else
			throw new NumOfQsOverLimit("Sorry, the number of Questions is over the limit.");
	}

	public static void testToFiles(Stock test, String typeExam, String nameProfession) throws FileNotFoundException {
		LocalDateTime lDt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		File examFile = new File(nameProfession +" " + typeExam+ " Exam_" + lDt.format(dtf) + ".txt");
		File solutionFile = new File("Solution_" + lDt.format(dtf) + ".txt");
		PrintWriter pWExam = new PrintWriter(examFile);
		PrintWriter pWSolution = new PrintWriter(solutionFile);
		for (int i = 0; i < test.getStock().length; i++) {
			if (checkIfAmericanQ(i + 1, test)) {
				pWExam.println((i + 1) + ") " + ((AmericanQuestion) test.getStock()[i]).toStringNoIndication());
				pWSolution.println((i + 1) + ") " + ((AmericanQuestion) test.getStock()[i]).toString());
			} else {
				pWExam.println((i + 1) + ") " + ((OpenQuestion) test.getStock()[i]).toStringNoSolution());
				pWSolution.println((i + 1) + ") " + ((OpenQuestion) test.getStock()[i]).toString());
			}
		}
		pWExam.close();
		pWSolution.close();
	}

	public static boolean checkIfAmericanQ(int numOfQuestion, Stock stock) {
		if (stock.getStock()[numOfQuestion - 1].serial % 2 != 0)
			return true; // it is an American q
		else
			return false; // it is an open q
	}

	public static void addAnswerToQ(int numOfQuestion, Stock stock) {
		numOfQuestion = checkIfQBetweenRange(numOfQuestion, stock); // check if the num of Q is between the range of
																	// 1-amount of Qs
		while (!checkIfAmericanQ(numOfQuestion, stock)) {
			System.out.println(
					"It is an open question. you can't add answer for it, only 'replace' its answer. do it in OPTION 5.");
			System.out.println("If you want to procceed, type true , else false :");
			if (!s.nextBoolean())
				return; // if the user type false, return to menu
			System.out.println("Enter the number of question :");
			numOfQuestion = s.nextInt();
			numOfQuestion = checkIfQBetweenRange(numOfQuestion, stock);
		}
		if (((AmericanQuestion) stock.getStock()[numOfQuestion - 1]).getAnswersArray().length >= MAX_ANSWERS)
			System.out.println("There's no place for another answer");
		else {
			System.out.println("Enter the answer :");
			s.nextLine();
			String stg = s.nextLine();
			System.out.println("Enter new answer's indication : (true/false)");
			indication = s.nextBoolean();
			stock.insertQuestionNum(numOfQuestion, stg, indication);
//			copyOfStock.insertQuestionNum(numOfQuestion, stg, indication);
		}
	}

	public static void removeAnswerOfQ(Stock stock) {
		System.out.println("Which question do you want to delete an answer to ?");
		int numOfQ = s.nextInt();
		numOfQ = checkIfQBetweenRange(numOfQ, stock); // check if the num of Q is between the range of 1-amount of Qs
		if (stock.getStock()[numOfQ - 1].serial % 2 == 0) { // this is an open question
			System.out.println(stock.getStock()[numOfQ - 1].toString());
			System.out.println("Please type the new answer for the question :");
			s.nextLine();
			((OpenQuestion) stock.getStock()[numOfQ - 1]).setSolution(s.nextLine());
			// ((OpenQuestion) copyOfStock.getStock()[numOfQ -
			// 1]).setSolution(s.nextLine());
		} else { // this is an American question
			System.out.println(stock.getStock()[numOfQ - 1].toString());
			System.out.println("Which answer do you want to delete ?");
			int numOfA = s.nextInt();
			while (numOfA <= 0
					|| (numOfA - 1) >= ((AmericanQuestion) stock.getStock()[numOfQ - 1]).getAnswersArray().length) {
				System.out.println("Invalid number of answer, insert another number :");
				numOfA = s.nextInt();
			}
			if (((AmericanQuestion) stock.getStock()[numOfQ - 1]).getAnswersArray().length <= MIN_ANSWERS_FOR_TEST)
				System.out.println("Sorry, the amount of reminded answers is under the minimum.");
			else {
				((AmericanQuestion) stock.getStock()[numOfQ - 1]).removeAnswer(numOfA);
				// ((AmericanQuestion) copyOfStock.getStock()[numOfQ - 1]).removeAnswer(numOfA);
			}
		}
	}

/*	public static Stock stockBuilder(Stock stock) {
		String str = " ";
		Difficulity easy = Difficulity.Easy;
		Difficulity medium = Difficulity.Medium;
		Difficulity hard = Difficulity.Hard;
		Answer[] answersAQ1 = { new Answer("25", true), new Answer("55", false), new Answer("125", false) };
		Answer[] answersAQ2 = { new Answer("3", false), new Answer("3.1", false), new Answer("4", true),
				new Answer("2.9", false) };
		Answer[] answersAQ3 = { new Answer("45°", false), new Answer("90°", true), new Answer("27°", false),
				new Answer("100°", false), new Answer("180°", false), new Answer("10°", false),
				new Answer("47°", false), new Answer("63°", false) };
		Answer[] answersAQ4 = { new Answer("0", false), new Answer("10", false), new Answer("1", false),
				new Answer("1.000001", false) };
		Answer oQ1 = new Answer("17.25", true);
		Answer oQ2 = new Answer("180°", true);
		str = "5! = ?";
		AmericanQuestion q1 = new AmericanQuestion(str, answersAQ1, easy);
		str = "Which number is greater than π ?";
		AmericanQuestion q2 = new AmericanQuestion(str, answersAQ2, medium);
		str = "How much is π/2 in degrees ?";
		AmericanQuestion q3 = new AmericanQuestion(str, answersAQ3, hard);
		str = "10/0 = ? ?";
		AmericanQuestion q4 = new AmericanQuestion(str, answersAQ4, hard);
		str = "Calculate 276/16 (approximately) ";
		OpenQuestion q5 = new OpenQuestion(str, oQ1, easy);
		str = "How many degrees is a flat angle ? ";
		OpenQuestion q6 = new OpenQuestion(str, oQ2, medium);
		
		stock.addAmericanQuestion(q1);
		stock.addAmericanQuestion(q2);
		stock.addAmericanQuestion(q3);
		stock.addAmericanQuestion(q4);
		stock.addOpenQuestion(q5);
		stock.addOpenQuestion(q6);
		return stock;
	}
*/
	public static int checkIfQBetweenRange(int numToRemove, Stock stock) {
		while (numToRemove <= 0 || numToRemove > stock.getStock().length) { // check if the requested Q is within the
																			// range of array
			System.out.println("Invalid location, insert another number.");
			numToRemove = s.nextInt();
		}
		return numToRemove;
	}

	public static void addQuestionToStock(Stock stock) {
		String questionString = " ";
		String schoolSolution = " ";
		System.out.print("What level is the question ? (Easy,Medium ,Hard)   ");
		Difficulity level = Difficulity.valueOf(s.next());
		System.out.println("Enter the string of the question :");
		s.nextLine();
		questionString = s.nextLine();
		System.out.println("to add open question press 0, else press 1");
		int choice = s.nextInt();
		if (choice == 0) { // chose open Q
			System.out.println("Enter the answer :");
			s.nextLine();
			schoolSolution = s.nextLine();
			Answer answerOfOpenQ = new Answer(schoolSolution, true); // indication of school solution is always true
			OpenQuestion oQ = new OpenQuestion(questionString, answerOfOpenQ, level);
			stock.addOpenQuestion(oQ);
		} else { // American question
			AmericanQuestion aQ = new AmericanQuestion(questionString, answersOfQuestion(questionString), level);
			stock.addAmericanQuestion(aQ);
		}
	}

	public static void copyStockNoRef(Stock copyOfStock, Stock stock) { // copy the stock to another one without
																		// references
		Question[] copyOfQuestions = new Question[stock.getStock().length];
		for (int i = 0; i < stock.getStock().length; i++) {
			if (checkIfAmericanQ(i + 1, stock)) {
				copyOfQuestions[i] = new AmericanQuestion(stock.getStock()[i].getQuestion(),
						((AmericanQuestion) stock.getStock()[i]).getAnswersArray(), stock.getStock()[i].kind);
				copyOfQuestions[i].setSerial(stock.getStock()[i].serial);
			} else {
				copyOfQuestions[i] = new OpenQuestion(stock.getStock()[i].getQuestion(),
						(new Answer(((OpenQuestion) stock.getStock()[i]).getSolution(), true)),
						stock.getStock()[i].kind);
				copyOfQuestions[i].setSerial(stock.getStock()[i].serial);
			}
		}
		copyOfStock.setQuestions(copyOfQuestions);
	}

	public static void generateStockBinaryFile(Stock stock, String st) {
		WritingBinaryFile wb = new WritingBinaryFile();
		wb.openFile(st+".ser");
		wb.writeData(stock);
		wb.closeFile();
	}
	
	public static void generateStringBinaryFile(String [] names) {
		WritingBinaryFile wb = new WritingBinaryFile();
		wb.openFile("namesOfProfessions.ser");
		wb.writeData(names);
		wb.closeFile();
	}
	
	public static void generateArrayStockBinaryFile(Stock [] arrOfStocks) {
		WritingBinaryFile wb = new WritingBinaryFile();
		wb.openFile("arrayOfProfessions.ser");
		wb.writeData(arrOfStocks);
		wb.closeFile();
	}
	
	public static void createNewProfession (Stock stock) {
		System.out.println("How many questions do you want in the profession ?");
		int numQuestions = s.nextInt();
		for (int i = 0; i < numQuestions; i++) {
			addQuestionToStock(stock);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// finals
		final int EXIT = 0;
		final int SHOW_Q_AND_A = 1;
		final int ADD_ANSWER = 2;
		final int ADD_QUESTION = 3;
		final int REMOVE_QUESTION = 4;
		final int REMOVE_ANSWER = 5;
		final int GENERATE_A_TEST = 6;

		// Build stock
		Stock stock = new Stock();

		Stock copyOfStock = new Stock();
		Stock [] arrayOfProfessions = new Stock [0];		// array of stocks
		String [] arrayNamesOfProfessions = new String [0];		// array of names of professions
			

		ReadingBinaryFile rb = new ReadingBinaryFile();
		rb.openFile("namesOfProfessions.ser");				// get the array of string from file
		arrayNamesOfProfessions= rb.readData(arrayNamesOfProfessions);
		rb.closeFile();
		
		rb.openFile("arrayOfProfessions.ser");				// get the array of stocks (professions) from file
		arrayOfProfessions= rb.readData(arrayOfProfessions);
		rb.closeFile();
		
		for (int i = 1; i < arrayNamesOfProfessions.length; i++) {
			System.out.println("For profession " + arrayNamesOfProfessions[i] + ", press " + i);
		}
		System.out.println("To add a new profession, press 0");
		int numOfProfession= s.nextInt();
		while (numOfProfession < 0 || numOfProfession >arrayNamesOfProfessions.length-1) {
			System.out.println("Invalid input, insert another number :");
			numOfProfession = s.nextInt();			// get the index of requested profession
		}
		stock = arrayOfProfessions [numOfProfession];	// specific profession (stock)
		String nameOfProfession = arrayNamesOfProfessions[numOfProfession];		// string of specific profession(stock)
		if (numOfProfession ==0) {
			stock = new Stock ();
			System.out.println("Insert the name of the new profession :");
			s.nextLine();
			String anotherProfession = s.nextLine();
			createNewProfession(stock);
			nameOfProfession = anotherProfession;
			arrayNamesOfProfessions = Arrays.copyOf(arrayNamesOfProfessions, arrayNamesOfProfessions.length+1);
			arrayNamesOfProfessions[arrayNamesOfProfessions.length-1] = anotherProfession;
			arrayOfProfessions = Arrays.copyOf(arrayOfProfessions, arrayOfProfessions.length+1);	// increase the length in 1
			arrayOfProfessions[arrayOfProfessions.length-1] = stock;
		}
		
		int choice;
		System.out.println(); // space between the stock to the menu
		do {
			System.out.println("To show Q&A, press 1");
			System.out.println("To add answer for an american question, press 2");
			System.out.println("To add question, press 3");
			System.out.println("To remove a question, press 4");
			System.out.println("To remove an answer for a question, press 5");
			System.out.println("To generate a test, press 6");
			System.out.println("To exit press 0");
			choice = s.nextInt();
			switch (choice) {
			case SHOW_Q_AND_A:
				System.out.println(stock.toString());
				break;
			case ADD_ANSWER:
				System.out.println("Enter the number of the question for which you want to add an answer : ");
				int numOfQuestion = s.nextInt();
				addAnswerToQ(numOfQuestion, stock);
				break;
			case ADD_QUESTION:
				addQuestionToStock(stock);
				break;
			case REMOVE_QUESTION:
				System.out.println("Enter the number of Q you want to remove :");
				int numToRemove = s.nextInt();
				stock.removeQuestion(checkIfQBetweenRange(numToRemove, stock));
				break;
			case REMOVE_ANSWER:
				removeAnswerOfQ(stock);
				break;
			case GENERATE_A_TEST:
				Stock test = new Stock();
				copyStockNoRef(copyOfStock, stock);
				boolean check = false;
				int questionsForTest;
				System.out.println("For manual exam press 1, for automatic exam press 2");
				int choose = s.nextInt();
				if (choose ==1) {
					Exam ex;
					ex= new ManualExam();
				while (!check) {
					try {
						System.out.println(
								"How many questions do you want in the test ? (the limit is : " + LIMIT_OF_QS + ")");
						questionsForTest = s.nextInt();
						testBuilder(questionsForTest, copyOfStock, stock, test);
						check = true;
					} catch (NumOfQsOverLimit e) { // limit of Q
						System.out.println(e.getMessage());
						s.nextLine(); // cleans buffer
						test.emptyStock(); // Due to exception, we have to empty the test and send to the function an
											// empty test again
						copyStockNoRef(copyOfStock, stock);
					} catch (AmericanUnderMin e) { // The chosen American Question under min of A's
						System.out.println(e.getMessage());
						s.nextLine(); // cleans buffer
						test.emptyStock(); // Due to exception, we have to empty the test and send to the function an
											// empty test again
						copyStockNoRef(copyOfStock, stock);
					}
				}
				ex.createExam(test);
				testToFiles(test, "manual", nameOfProfession);
				}
				else if (choose ==2) { 		// the user chose automatic exam
					Exam ex;
					ex= new AutomaticExam();
					System.out.println(
							"How many questions do you want in the test ? (the limit is : " + LIMIT_OF_QS + ")");
						// we assume that the input is valid.
					((AutomaticExam)ex).setNumOfQuestions(s.nextInt());
					ex.createExam(copyOfStock);
					test = ex.questionsForExam;
					testToFiles(test, "auto", nameOfProfession);
					copyStockNoRef(copyOfStock, stock);
				}
				else {		// wrong input
					System.out.println("Invalid input");
					break;
				}		
				// Please change the viewing direction of files to: from left to right!
				break;
			case EXIT:
				generateStockBinaryFile(stock, nameOfProfession);	// update the file with the new stock (if changed)
				if (numOfProfession == 0)
					arrayOfProfessions[arrayOfProfessions.length-1] = stock;	// update the stock in the specific location
				else
					arrayOfProfessions[numOfProfession] = stock;
				generateArrayStockBinaryFile(arrayOfProfessions);
				generateStringBinaryFile(arrayNamesOfProfessions);
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
			System.out.println();
		} while (choice != EXIT);
		System.out.println("Have a nice day !");
		s.close();

	}

}
