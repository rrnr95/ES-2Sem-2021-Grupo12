package backend;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 			Class used to extract the number of classes from a file
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class NumberOfClassesPerFile {
	/**
	 * 	Filename
	 */
	private String fileName;
	/**
	 * 	List of classes
	 */
	private List<String> classes;
	
	/**
	 * 			Constructor
	 * @param 	file
	 * 			filename
	 */
	public NumberOfClassesPerFile(String file) {
		this.fileName = file;
		this.classes = new ArrayList<String>();
	}
	
	/**
	 * 			Reads the file line by line, and extract the classes found to a list.
	 * @return	list of classes found
	 */
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
				Pattern classfinder = Pattern.compile("\\b(class)\\b");
				Matcher matcher = classfinder.matcher(currentLine);
				
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
	
	/**
	 * 			Get the name of the class in a given line, which was previously identified as a line with class declaration.
	 * @param 	line
	 * @return	name of the class
	 */
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