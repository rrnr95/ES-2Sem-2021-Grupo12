package backend;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				Pattern cyclofinder = Pattern.compile("\\b(class)\\b");
				Matcher matcher = cyclofinder.matcher(currentLine);
				
				if(isValid && !currentLine.contains("//") && matcher.find() && !currentLine.contains("(")){
					classes.add(trimClass(currentLine));
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
	
	private static String trimClass(String line) {
		String str_temp;
		str_temp = line.trim()
				 .split("class ")[1]
				 .replace("{", "")
				 .replace("}", "")
				 .trim();
		
		return str_temp;
		
	}
	
}