import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.github.javaparser.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class CycloMethod {



	
	public static void main(String[] args) throws FileNotFoundException {
 
		List<String> methodCode;
		List<String> methodName;
		String path = ("C:\\Users\\Diogo\\git\\ES-2Sem-2021-Grupo12\\src\\main\\java\\NumberOfClassesPerFile.java");
		methodCode = new MethodUtils(path).getMethodCode();
		methodName = new MethodUtils(path).getMethodName();
		List<Integer> methodsCycloValue = allMethodsCycloValue(methodCode);
		 
		for(String s : methodCode) {
			System.out.println(methodName.get(methodCode.indexOf(s)) +": " +  methodsCycloValue.get(methodCode.indexOf(s)));
		}
		 
		System.out.println("Class wmc value: " + wmcCalculator(methodsCycloValue));			 
	}
	
	
	
	private static boolean isEmptyOrLineCommentary(String line) {		
		line.trim();
		// verificar se existe comentário de uma só linha ou se linha está vazia
		return line.startsWith("//") || "".equals(line);		
	}
	
	
	/**
	 * 
	 * @param line
	 * @return String representing the given line without any comments or text strings. 
	 * 			If there's no code in the given line returns an empty String
	 */
	
	private static String sourceCodeExtrator(String line) {
		
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
	 * 
	 * @param line of code. Should be filtered previously with sourceCodeExtrator(String line) function.
	 * @return returns the number of keywords (if|while|for|case) present in the given line.
	 */
	
	
	private static int lineCycloCounter(String line) {
		
		Pattern cyclofinder = Pattern.compile("\\b(if|while|for|case)\\b");
		Matcher countMatchs = cyclofinder.matcher(line);
		
		int count=0;
		while(countMatchs.find())
			count++;		
		return count;		
	}
	
	private static int cycloMethodValue(String[] method) {
		int res = 1;	
		for(String s : method)
			res+=lineCycloCounter(s);
		return res;
	}
	
	
	public static List<Integer> allMethodsCycloValue(List<String> methods){
		
		List<Integer> result = new ArrayList<Integer>();
		
		for(String method : methods) {
			result.add(cycloMethodValue(method.split("\n"))); 
		}
		
		return result;
	}
	
	
	public static int wmcCalculator(List<Integer> methodsCycloValue) {
		int res = 0;
		for(Integer value : methodsCycloValue)
			res+=value;
		return res;	
	}
		
		
		
	
	
	
	
}
