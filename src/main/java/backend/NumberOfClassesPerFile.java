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
		searchClasses();
	}
	
	/**
	 * 			Reads the file line by line, and extract the classes found to a list.
	 * @return	list of classes found
	 */
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