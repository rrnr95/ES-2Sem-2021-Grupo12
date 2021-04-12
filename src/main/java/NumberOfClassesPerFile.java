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
		searchClasses();
	}
	
	public void searchClasses() {
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
					
					classes.add( trimClass(currentLine));
				}
				
				currentLine = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String trimClass(String line) {
		return	 line.trim()
					 .split("class ")[1]
					 .replace("{", "")
					 .replace("}", "")
					 .trim();
	}
	
	public List<String> getClasses() {
		return classes;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public static void main (String[] args) {
		NumberOfClassesPerFile n = new NumberOfClassesPerFile("D:\\Git\\ES\\ES-2Sem-2021-Grupo12\\imported_project\\src\\pckg\\HelloWorld.java");
		System.out.println(n.getClasses().size());
		
		System.out.println(n.getClasses());
		System.out.println(n.getFileName());
//		for (String s : n.getClasses()) {
//			System.out.println(s);
//		}
	}
	
}