package backend;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class with methods used to calculate number of cycles
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class CycloMethod {



	
//	public static void main(String[] args) throws FileNotFoundException {
// 
//		List<String> methodCode;
//		List<String> methodName;
//		String path = ("C:\\Users\\Diogo\\git\\ES-2Sem-2021-Grupo12\\src\\main\\java\\NumberOfClassesPerFile.java");
//		methodCode = new MethodUtils(path).getMethodCode();
//		methodName = new MethodUtils(path).getMethodName();
//		List<Integer> methodsCycloValue = allMethodsCycloValue(methodCode);
//		 
//		for(String s : methodCode) {
//			System.out.println(methodName.get(methodCode.indexOf(s)) +": " +  methodsCycloValue.get(methodCode.indexOf(s)));
//		}
//		
//		
//		System.out.println("Class wmc value: " + wmcCalculator(methodsCycloValue));
//		System.out.println("Treated Class wmc value: " + wmcCalculator(methodsCycloValue));
//		
//		
//	}
	
	
	/**
	 * 			Receives a String and checks if it corresponds to an empty line or commentary
	 * @param 	line
	 * 			String to evaluate
	 * @return 	a boolean
	 */
	private static boolean isEmptyOrLineCommentary(String line) {		
		line = line.trim();
		// verificar se existe comentário de uma só linha ou se linha está vazia
		return line.startsWith("//") || "".equals(line);		
	}
	
	
	/**
	 * 			Removes comments from String of code
	 * @param	line
	 * 			String to evaluate
	 * @return 	string representing the given line without any comments or text strings. 
	 * 			If there's no code in the given line returns an empty String
	 */
	private static String sourceCodeExtrator(String line) {
		
			line = line.trim();
			// começa por descartar linhas comentadas ou vazias
			if(!isEmptyOrLineCommentary(line)) {
					
			
				// Verifica a existencia de comentários na linha
				
				if(line.startsWith("*") && line.contains("*/")){ 
				
					int endOfCommentIndex = line.indexOf("*/");
					line = line.substring(endOfCommentIndex+2);
					line = sourceCodeExtrator(line);
				
				}else if (line.startsWith("*") && !line.contains("*/")){
					return "";
					
				}else if (line.startsWith("/*") && line.contains("*/")) {
						int commentEndIndex = line.indexOf("*/");
						line = line.substring(commentEndIndex+2);
						line = sourceCodeExtrator(line);
				}else if(line.contains("/*") && line.contains("*/")) {
					int startOfCommentIndex = line.indexOf("/*");
					int endOfCommentIndex = line.indexOf("*/");
					String comment = line.substring(startOfCommentIndex,endOfCommentIndex+2);
					line = line.replace(comment, "");
					line = sourceCodeExtrator(line);
				}else if(line.contains("//")) {
					int endOfCode = line.indexOf("//");
					line = line.substring(0, endOfCode);
					line = sourceCodeExtrator(line);
				}
				
			
				// se passou todos estes filtros -> 
				//filtrar strings  para evitar a leitura de palavras reservadas existentes nas mesmas, i.e
				//String text = "If something then do something else for a while";
						
				//não verifica strings que tenham sido iniciadas em linhas anteriores! -> pode levar a falhas.
				
				if(line.contains("\"")) {
					int startOfString = line.indexOf("\"");
					String substring = line.substring(startOfString+1);
					int endOfString = startOfString + substring.indexOf("\"")+1;
					String str = line.substring(startOfString, endOfString+1);
					line = line.replace(str, "");
					sourceCodeExtrator(line);
				}
				
				return line;
			}else {
				return "";
			}
	}
	
	/**
	 * Checks the number of keywords (if|while|for|case) present in the given line.
	 * @param 	line 
	 * 			String of code. Should be filtered previously with sourceCodeExtrator(String line) function.
	 * @return 	number of keywords (if|while|for|case) present in the given line.
	 */
	private synchronized  static int lineCycloCounter(String line) {

		
		Pattern cyclofinder = Pattern.compile("\\b(if|while|for|case)\\b");
		Matcher countMatchs = cyclofinder.matcher(line);
		
		int count=0;
		while(countMatchs.find())
			count++;		
		return count;		
	}
	

	/**
	 * 			Counts the number of cycles on a String array representing lines of code on each index. 
	 * 			The array represents a method. 
	 * @param 	method
	 * 			String array representing a method's code
	 * @return	number of cycles found
	 */
	private static int cycloMethodValue(String[] method) {
		int res = 1;	
		for(String s : method)
			res+=lineCycloCounter(sourceCodeExtrator(s));
		return res;
	}
	
	//------------------------
	public static int cycloMethodValue(Method method) {
		int res = 1;
		String[] str = method.getCode().split("\n");
		for(String s : str)
			res+=lineCycloCounter(sourceCodeExtrator(method.getCode()));
		return res;
	}
	//------------------------

	/**
	 * 			Counts the number of cycles on list of Strings. Each String represents a method.
	 * @param 	methods
	 * 			List of Strings. Each String represents a method.
	 * @return	an integers List with the number of cycles found in the 
	 * 			Each index's List, has the number of cycles found in the same list index of the received list 
	 */
	public synchronized static List<Integer> allMethodsCycloValue(List<Method> methods){
		
		String[] methodsArray = new String[methods.size()];
		methods.toArray(methodsArray);

		List<Integer> result = new ArrayList<Integer>();
		
//		for(String method : methods) {
//			result.add(cycloMethodValue(method.split("\n"))); 
//		}
		
		for(int i=0; i < methodsArray.length; i++){ 
			result.add(cycloMethodValue(methodsArray[i].split("\n"))); 
		}
		
		return result;
	}
	

	/**
	 * 			Sum all integers of an Integer List.
	 * 			In this project, this is used for sum all cycles found for a given list.
	 * @param 	methodsCycloValue
	 * 			List containing the number of cycles found in previous calculation.
	 * @return	sum of all integers in a given list. 
	 */
	public  synchronized static int wmcCalculator(List<Method> methods) {
		List<Integer> methodsCycloValue = allMethodsCycloValue(methods);
		
		int res = 0;
		for(Integer value : methodsCycloValue)
			res+=value;
		return res;	
	}	
	
}