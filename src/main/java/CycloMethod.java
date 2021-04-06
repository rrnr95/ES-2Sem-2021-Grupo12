import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CycloMethod {

	
	/**
	 * Verificar:
	 * 
	 * 	- Existencia de If, for, while, case, (...)
	 * 	
	 * 	1 Checkar linha a linha x
	 * 	2 Verificar se � c�digo ou coment�rio coment�rio 
	 * 	3 Verificar se existe uma string na linha "string"
	 * 	4 Verificar se existe mais do que uma keyword.
	 * 
	 */
	

	
	public static void main(String[] args) {
		ArrayList<String> tests = new ArrayList<String>();
		 tests.add("private int a = 3;");
		 tests.add("/* comentario */");
		 tests.add("/* comentario */ if manel do isto");
		 tests.add("/* comentario */ if manelito do isto entao /* outro if{} comentario*/");
		 tests.add("/* comentario */ if{} do isto //coment de linha");
		 tests.add("algum codigo e /* um comentario */");
		 tests.add("algum String = \"aroz doce\" codigo while() /* um comentario */ e mais algum codigo if() \"emais uma string\" mais codigo2");
		 
		 for(String str : tests)
			 System.out.println(sourceCodeExtrator(str) + " -> complexidade da linha: " + lineCycloCounter(sourceCodeExtrator(str)));
	}
	
	public static boolean isEmptyOrLineCommentary(String line) {
		
		line.trim();
		// verificar se existe coment�rio de uma s� linha ou se linha est� vazia
		return line.startsWith("//") || "".equals(line);		
		
	}
	
	
	/**
	 * 
	 * @param line
	 * @return String representing the given line without any comments or text strings. 
	 * 			If there's no code in the given line the method returns an empty String
	 */
	
	public static String sourceCodeExtrator(String line) {
		
			// come�a por descartar linhas comentadas ou vazias
			if(!isEmptyOrLineCommentary(line)) {
					
			
				// Verifica a existencia de coment�rios na linha
				
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
						
				//n�o verifica strings que tenham sido iniciadas em linhas anteriores! -> pode levar a falhas.
				
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
	
	public static int lineCycloCounter(String line) {
		
		Pattern cyclofinder = Pattern.compile("\\b(if|while|for|case)\\b");
		Matcher countMatchs = cyclofinder.matcher(line);
		
		int count=0;
		while(countMatchs.find())
			count++;		
		return count;		
	}
	
	
	
}