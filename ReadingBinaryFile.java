package avielZamanRanBarak;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class ReadingBinaryFile {
	private ObjectInputStream input;

	public void openFile(String fName) {
		try {
			input = new ObjectInputStream(new FileInputStream(fName));
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file +" + fName);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't open file " + fName);
			e.printStackTrace();
		}
	}

	public void closeFile() {
		if (input != null) {
			try {
				input.close(); // FileInputStream will be closed automatically
			} catch (IOException e) {
				System.out.println("Can't close file");
				e.printStackTrace();
			}
		}

	}

	public void readData(Stock stock) {
		// in case we don't know how many questions are in file
		Question aQuestion = null;
		Question[] questions = null;
		int numQuestions = 0;

		try {
			while (true) {
				aQuestion = (Question) input.readObject();
				if (numQuestions == 0) {
					questions = new Question[1];
				}
				else {
					questions = Arrays.copyOf(questions, numQuestions+1);
				}
				questions[numQuestions] = aQuestion;
				numQuestions++;
			}
		} catch (EOFException eof) { // we have reached the end of file
			stock.setQuestions(questions);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		public String [] readData(String [] names) {
			try {
				
					return ( (String[]) input.readObject());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

	}
		
		public Stock [] readData(Stock [] arrayOfProfessions) {
			try {
				
					return ( (Stock[]) input.readObject());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

	}
}
