package backend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Class used to count lines of file and methods
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class LineCounter {
	
	/**
	 * 	Total number of lines
	 */
	private static int totalLinesCount;
	/**
	 *	List of methods names
	 */
	//private static List<String> methodsNames;

	private static Map<String, Integer> methodNameLines = new HashMap<String, Integer>();


	/**
	 * 			Count lines of a java file located at a given path
	 * @param 	path
	 * 			the name of the file to read
	 */
	public static void countLines(String path, List<Method> metodos) {
		try {
			FileReader freader = new FileReader(path);
			BufferedReader bfreader = new BufferedReader(freader);

			counter(bfreader , metodos);
			
			bfreader.close();
			freader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 			Counts total of lines of file passed line by line as a BufferedReader, and populates methodCountList 
	 * 			with: " MethodName: Number_of_Lines"
	 * @param 	bReader
	 * @throws 	IOException
	 */
	private static void counter(BufferedReader bReader, List<Method> methods) throws IOException {
		boolean commentBegan = false;
		String line = null;
		
		boolean methodBegan = false;
		int methodLinesCount = 0;
		int methodCount = 0;
		Method m = new Method();
		m.setExcelName("");
		
		if (methods.size() != 0)
			m = methods.get(methodCount);	// method's name (string)
	
		Stack<String> curlyBracketStack = new Stack<String>();

		while ((line = bReader.readLine()) != null) {
			line = line.trim();
			if ("".equals(line) || line.startsWith("//")) {
				continue;		
			}
			
			if (commentBegan) {
				if (commentEnded(line)) {
					line = line.substring(line.indexOf("*/") + 2).trim();
					commentBegan = false;
					if ("".equals(line) || line.startsWith("//")) {
						continue;
					}
				} else
					continue;
			}
			
			methodCount = methodCount(methodCount, m, line);
			if (isSourceCodeLine(line)) {
				totalLinesCount++;
				
				// find start of method
				if (!"".equals(m.getName())) {
					if ((line.contains( m.getName() ) && !line.contains(";")) || (line.contains( m.getName() ) && line.matches(".+(public|private|protected|static|abstract).+"))) {
						methodBegan = true;
					}
				}
				
				// increase method's line count
				if (methodBegan) {
					methodLinesCount++;
				
					// if there is a '{' then push to stack
					if (line.contains("{")) 
						curlyBracketStack.push(line);
					
					// if there is a '}' the pop from stack
					if (line.contains("}")) 
						curlyBracketStack.pop();						

					// if stack is empty then method is over
					if (curlyBracketStack.isEmpty()) {
						methodBegan = false;
						methodNameLines.put(m.getExcelName(), methodLinesCount); 	// add method's name and line count to list 
						methodLinesCount = 0;
						
						if (methods.size() > methodCount)
							m = methods.get(methodCount);
					}
				}
			}
			if (commentBegan(line)) {
				commentBegan = true;
			}
		}
	}
	
	/**
	 * 			Checks if there are any method declaration on a given line, and increments if it does.
	 * @param 	methodCount 
	 * 			actual number of methods
	 * @param 	m
	 * 			method to get name
	 * @param 	line
	 * 			line to analyse
	 * @return	
	 */
	private static int methodCount(int methodCount, Method m, String line) {
		if (isSourceCodeLine(line)) {
			if (!"".equals(m.getName())) {
				if ((line.contains(m.getName()) && !line.contains(";")) || (line.contains(m.getName())
						&& line.matches(".+(public|private|protected|static|abstract).+"))) {
					methodCount++;
				}
			}
		}
		return methodCount;
	}
	
	
	/**
	 * 			Checks if a given line has a comment start
	 * @param 	line
	 * @return 	true or false
	 */
	protected static boolean commentBegan(String line) {
		// If line = /* */, this method will return false
		// If line = /* */ /*, this method will return true
		int index = line.indexOf("/*");
		if (index < 0) {
			return false;
		}
		int quoteStartIndex = line.indexOf("\"");
		if (quoteStartIndex != -1 && quoteStartIndex < index) {
			while (quoteStartIndex > -1) {
				line = line.substring(quoteStartIndex + 1);
				int quoteEndIndex = line.indexOf("\"");
				line = line.substring(quoteEndIndex + 1);
				quoteStartIndex = line.indexOf("\"");
			}
			return commentBegan(line);
		}
		return !commentEnded(line.substring(index + 2));
	}

	/**
	 * 			Checks if in the given line a comment has ended and no new comment has begun
	 * @param 	line 
	 * 			string that represents a line of code
	 * @return 	true of false
	 */
	protected static boolean commentEnded(String line) {
		// If line = */ /* , this method will return false
		// If line = */ /* */, this method will return true
		int index = line.indexOf("*/");
		if (index < 0) {
			return false;
		} else {
			String subString = line.substring(index + 2).trim();
			if ("".equals(subString) || subString.startsWith("//")) {
				return true;
			}
			return !commentBegan(subString);
		}
	}

	/**
	 * 			This method returns true if there is any valid source code in the given input line. 
	 * 			It does not worry if comment has begun or not.
	 * 			This method will work only if we are sure that comment has not already begun previously. 
	 * 			Hence, this method should be called only after {@link #commentBegan(String)} is called
	 * @param 	line 
	 * 			string that represents a line of code
	 * @return 	true or false
	 */
	protected static boolean isSourceCodeLine(String line) {
		boolean isSourceCodeLine = false;
		line = line.trim();
		if ("".equals(line) || line.startsWith("//")) {
			return isSourceCodeLine;
		}
		if (line.length() == 1) {
			return true;
		}
		int index = line.indexOf("/*");
		if (index != 0) {
			return true;
		} else {
			while (line.length() > 0) {
				line = line.substring(index + 2);
				int endCommentPosition = line.indexOf("*/");
				if (endCommentPosition < 0) {
					return false;
				}
				if (endCommentPosition == line.length() - 2) {
					return false;
				} else {
					String subString = line.substring(endCommentPosition + 2)
							.trim();
					if ("".equals(subString) || subString.indexOf("//") == 0) {
						return false;
					} else {
						if (subString.startsWith("/*")) {
							line = subString;
							continue;
						}
						return true;
					}
				}
			}
		}
		return isSourceCodeLine;
	}

	
	/**
	 * 			Getter of Map with method's name and number of lines of method
	 * @return 	Map with method's name and number of lines of method
	 */
	public static Map<String, Integer> getMethodNameLines() {
		return methodNameLines;
	}
	

	/**
	 * 			Returns the total lines number, and resets count
	 * @return 	total of lines
	 */
	public static int getTotalLinesCount() {
		int aux = totalLinesCount;
		totalLinesCount = 0;
		return aux;
	}


}

