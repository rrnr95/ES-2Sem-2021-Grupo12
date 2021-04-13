package backend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class LineCounter {

	private int linesCount;
	private List<String> methodsNames;
	private List<String> methodCountList = new ArrayList<String>();

	public LineCounter(String path) {
		try {
			FileReader freader = new FileReader(path);
			BufferedReader bfreader = new BufferedReader(freader);
			
			methodsNames = new MethodUtils(path).getMethodName();

			counter(bfreader);
			
			bfreader.close();
			freader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public void counter(BufferedReader bReader) throws IOException {
		//int count = 0;
		boolean commentBegan = false;
		String line = null;
		
		boolean methodBegan = false;
		int methodLinesCount = 0;
		int methodCount = 0;
		String m = "";
		
		if (methodsNames.size() != 0)
			m = methodsNames.get(methodCount);	// method's name (string)
	
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
			
			if (isSourceCodeLine(line)) {
				linesCount++;
				
				// find start of method
				if (!"".equals(m)) {
					if (line.matches(".*\\b" + m  + "\\b.*") && !line.contains(";")) {
						methodCount++;
						methodBegan = true;
					}
				}
				
				// increase method's line count
				if (methodBegan) {
					methodLinesCount++;
				
					// if there is a '{' then push to stack
					if (line.contains("{")) 
						curlyBracketStack.push(line);
					
					else {
						// if there is a '}' the pop from stack
						if (line.contains("}")) 
							curlyBracketStack.pop();						
					}

					// if stack is empty then method is over
					if (curlyBracketStack.isEmpty()) {
						methodBegan = false;
						methodCountList.add(m + ": " + methodLinesCount); 	// add method's name and line count to list 
						methodLinesCount = 0;
						
						if (methodsNames.size() > methodCount)
							m = methodsNames.get(methodCount);
					}
				}
			}
			if (commentBegan(line)) {
				commentBegan = true;
			}
		}
	}
	
	
	  /**
		 * @param line
		 * @return This method checks if in the given line a comment has begun and has not ended
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
		 * @param line
		 * @return This method checks if in the given line a comment has ended and no new comment has not begun
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
		 * @param line
		 * @return This method returns true if there is any valid source code in the given input line. It does not worry if comment has begun or not.
		 * This method will work only if we are sure that comment has not already begun previously. Hence, this method should be called only after {@link #commentBegan(String)} is called
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


		public int getLinesCount() {
			return linesCount;
		}

		public List<String> getMethodList() {
			return methodCountList;
		}
	}