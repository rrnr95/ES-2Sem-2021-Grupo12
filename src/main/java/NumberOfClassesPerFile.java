import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NumberOfClassesPerFile {
	private String fileName;
	private List<String> classes;
	
	public NumberOfClassesPerFile(String file) {
		this.fileName = file;
		this.classes = new ArrayList<String>();
	}
	
	public List<String> getClasses() {
		String currentLine;
		boolean isValid = true;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			
			//enquanto existirem linhas para serem lidas
			while (currentLine != null) {
				
				//inicio de comentario
				if (currentLine.contains("/*")) {
					isValid = false;
				}
				
				//fim de comentario
				if (currentLine.contains("*/")) {
					isValid = true;
				}
				
				//caso a linha nao seja um comentario
				//caso a linha contenha a keyword 'class'
				if(isValid && !currentLine.contains("//") && currentLine.contains(" class ") && !currentLine.contains("(")) {
					classes.add(currentLine);
				}
				
				currentLine = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	
	
	public static void main (String[] args) {
		NumberOfClassesPerFile n = new NumberOfClassesPerFile("C:\\Users\\mrfur\\git\\ES-2Sem-2021-Grupo12\\src\\main\\java\\NumberOfClassesPerFile.java");
		
		for (String s : n.getClasses()) {
			System.out.println(s);
		}
	}
	
}