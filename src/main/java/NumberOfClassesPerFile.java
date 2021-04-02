import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class NumberOfClassesPerFile {
	private String fileName;
	
	public NumberOfClassesPerFile(String file) {
		this.fileName = file;
	}
	
	public int getNumClasses() {
		String currentLine;
		int count = 0;
		int comment = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			
			while (currentLine != null) {
				if (currentLine.contains("/*")) {
					comment++;
				}
				if (currentLine.contains("*/")) {
					if (comment != 0)
						comment--;
				}
				if(comment == 0 && !currentLine.contains("//") && currentLine.contains("class") && !currentLine.contains("(")) {
					count++;
				}
				
				currentLine = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static void main (String[] args) {
		NumberOfClassesPerFile n = new NumberOfClassesPerFile("C:\\Users\\mrfur\\git\\ES-2Sem-2021-Grupo12\\src\\main\\java\\NumberOfClassesPerFile.java");
		System.out.println(n.getNumClasses());

	}
}