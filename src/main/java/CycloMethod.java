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

	
	/**
	 * Verificar:
	 * 
	 * 	- Existencia de If, for, while, case, (...)
	 * 	
	 * 	1 Checkar linha a linha x
	 * 	2 Verificar se é código ou comentário comentário 
	 * 	3 Verificar se existe uma string na linha "string"
	 * 	4 Verificar se existe mais do que uma keyword.
	 * @throws FileNotFoundException 
	 * 
	 */
	public static List<String> methodCode;
	public static List<String> methodName;

	
	public static void main(String[] args) throws FileNotFoundException {
//		ArrayList<String> tests = new ArrayList<String>();
//		 tests.add("private int a = 3;");
//		 tests.add("/* comentario */");
//		 tests.add("/* comentario */ if manel do isto");
//		 tests.add("/* comentario */ if manelito do isto entao /* outro if{} comentario*/");
//		 tests.add("/* comentario */ if{} do isto //coment de linha");
//		 tests.add("algum codigo e /* um comentario */");
//		 tests.add("algum String = \"aroz doce\" codigo while() /* um comentario */ e mais algum codigo if() \"emais uma string\" mais codigo2");
//		 
//		 for(String str : tests)
//			 System.out.println(sourceCodeExtrator(str) + " -> complexidade da linha: " + lineCycloCounter(sourceCodeExtrator(str)));
//		 
		 String path = ("C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\src\\main\\java\\imported_project_test\\test.java");
		 methodCode = new MethodUtils(path).getMethodCode();
		 methodName = new MethodUtils(path).getMethodName();
		 
		 for(String s : methodCode) {
			System.out.println(methodName.get(methodCode.indexOf(s)) +": " +  cycloMethodValue(s.split("\n")));
		 }
		
		 
		 
		 
	}
	
	public static boolean isEmptyOrLineCommentary(String line) {
		
		line.trim();
		// verificar se existe comentário de uma só linha ou se linha está vazia
		return line.startsWith("//") || "".equals(line);		
		
	}
	
	
	/**
	 * 
	 * @param line
	 * @return String representing the given line without any comments or text strings. 
	 * 			If there's no code in the given line the method returns an empty String
	 */
	
	public static String sourceCodeExtrator(String line) {
		
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
	
	public static int lineCycloCounter(String line) {
		
		Pattern cyclofinder = Pattern.compile("\\b(if|while|for|case)\\b");
		Matcher countMatchs = cyclofinder.matcher(line);
		
		int count=0;
		while(countMatchs.find())
			count++;		
		return count;		
	}
	
	
	
//	public static void extractMethodSourceCode(String path) throws FileNotFoundException{
//		 
//	
//		
//		
//		new VoidVisitorAdapter<Object>() {
//            @Override
//            public void visit(MethodDeclaration n, Object arg) {
//                super.visit(n, arg);
//             //   System.out.println(" * " + n);
//                methodCode.add(n.toString());
//                methodName.add(n.getName().toString());
//                
//                
//            }
//        }.visit(StaticJavaParser.parse(new File(path)), null);
//	
//        
//	}
		
	public static int cycloMethodValue(String[] method) {
		
		int res = 1;
		
		for(String s : method) {
			res+=lineCycloCounter(s);
		}
		
		return res;
		
	}
		
		
	
	
	
	
}
