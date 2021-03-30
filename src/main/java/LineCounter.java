import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class LineCounter {

	private int methodCount;
	private int linesCount;
	//	private List<Method> methodList = new ArrayList<Method>();

	public LineCounter(String path) {
		try {
			FileReader freader = new FileReader(path);
			BufferedReader bfreader = new BufferedReader(freader);

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
		boolean methodBegan = false;
		String line = null;
		int methodLinesCount = 0;
		Method m = null;

		while ((line = bReader.readLine()) != null) {
			line = line.trim();
			if ("".equals(line) || line.startsWith("//")) {
				continue;		
			}
			// find start of method
			if (line.matches(".*(public|private|protected|static).*") && line.matches(".+[\\(\\)].+")) {
				methodCount++;
				//				if (methodBegan) {
				//					m.setLines(methodLinesCount);
				//					methodList.add(m);
				//					methodLinesCount = 0;
				//				}
				//				methodBegan = true;
				//				m = new Method(line);
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
				//count++;
				linesCount++;
				//				if (methodBegan)
				//					methodLinesCount++;
			}
			if (commentBegan(line)) {
				commentBegan = true;
			}
		}
		//return count;
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

	public int getMethodCount() {
		return methodCount;
	}

	public int getLinesCount() {
		return linesCount;
	}

	//	public List<Method> getMethodList() {
	//		return methodList;
	//	}
}
