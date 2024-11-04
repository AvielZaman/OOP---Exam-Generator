package avielZamanRanBarak;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class WritingBinaryFile {
	private ObjectOutputStream output;

	public void openFile(String fName) {
		try {
			output = new ObjectOutputStream(new FileOutputStream(fName));
		} catch (FileNotFoundException e) {
			System.out.println("Can't open file " + fName);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Can't open file " + fName);
			e.printStackTrace();
		}
	}
	public void closeFile() {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				System.out.println("Can't close file");
				e.printStackTrace();
			}
		}

	}

	public void writeData(Stock stock) {
		if (output != null) {
			try {
				for (int i = 0; i < stock.getStock().length; i++) {
					output.writeObject(stock.getStock()[i]);
				}
			} catch (IOException e) {
				System.out.println("The object is not serializable");
				e.printStackTrace();
			}
		}
	}
	public void writeData(String [] names) {
		if (output != null) {
			try {
					output.writeObject(names);		
			}
			catch (IOException e) {
				System.out.println("The object is not serializable");
				e.printStackTrace();
			}
		
	}
	
}
	
	public void writeData(Stock [] arrayOfProfessions) {
		if (output != null) {
			try {
					output.writeObject(arrayOfProfessions);		
			}
			catch (IOException e) {
				System.out.println("The object is not serializable");
				e.printStackTrace();
			}
		
	}
	
}

}