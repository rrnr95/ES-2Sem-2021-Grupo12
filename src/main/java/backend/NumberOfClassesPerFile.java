package backend;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 			Class used to extract the number of classes from a file
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class NumberOfClassesPerFile {

	
	/**
	 * 			Reads the file line by line, and extract the classes found to a list.
	 * @return	list of classes found
	 */
	public static List<String> getClassesFromFile(String fileName) {
		
		List<String> classes = new ArrayList<String>();
		
		String currentLine;
		boolean isValid = true;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			currentLine = br.readLine();
			
			//enquanto existirem linhas para serem lidas
			while (currentLine != null) {
				
				isValid = isValid(isValid, currentLine);
				
				
				//caso a linha nao seja um comentario
				//caso a linha contenha a keyword 'class'

				if(isValid && !currentLine.contains("//") && currentLine.contains(" class ") && !currentLine.contains("(") && !currentLine.contains("+") && !currentLine.contains("=") && !currentLine.contains("=")) {
					
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
		return classes;
	}

	private static boolean isValid(boolean isValid, String currentLine) {
		if (currentLine.contains("/*")) {
			isValid = false;
		}
		if (currentLine.contains("*/")) {
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * 			Get the name of the class in a given line, which was previously identified as a line with class declaration.
	 * @param 	line
	 * @return	name of the class
	 */
	private static String trimClass(String line) {
		return	 line.trim()
					 .split("class ")[1]
					 .replace("{", "")
					 .replace("}", "")
					 .trim();
	}
	

	
	/**
	 * 			Gets a filename from a given fullpath
	 * @param 	p
	 * 			fullpath from a file
	 * @return	filename from a given fullpath
	 */
	public static String getFileName(String p) {
		String fileName = p.replace("\\", "/");
		String[] splitted = fileName.split("/");
		return splitted[splitted.length - 1];
	}
	

	
}