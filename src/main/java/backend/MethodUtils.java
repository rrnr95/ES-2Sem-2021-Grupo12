package backend;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Class used to parse a java file, and extract all method's code and name for a given java file 
 * The indexes of each list are correlated. First index of methodName has the corresponding code in methodCode first index.
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class MethodUtils {
	
//	/**
//	 * List of code for each method
//	 */
//	private static List<String> methods;
//	/**
//	 * List of method's names
//	 */
//	private static List<String> methodNames;
//
	/**	
	 * 			
	 * @param 	path 
	 * 			a pathname of the java file to parse
	 */
	public static List<Method> getMethodsFromFile(String path) {
		List<Method> methods = new ArrayList<Method>();
//		List<String> methods = new ArrayList<String>();
//		List<String> methodNames = new ArrayList<String>();
		
		try {
			extractMethodSourceCode(path, methods);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return methods;
	}
	
	/**
	 * 			Parse the java file given on the path given, and update the lists with the 
	 * 			corresponding methods code and name.
	 * @param 	path
	 * 			a pathname string
	 * @throws 	FileNotFoundException
	 */
	private static void extractMethodSourceCode (String path, final List<Method> metodos ) throws FileNotFoundException{

		new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                super.visit(n, arg);
                String name = n.getName().toString();
                String excelName = n.getDeclarationAsString(false, false, false).split(" ", 2)[1].replace(" ", "");
                //System.out.println(name);


                Method m = new Method(name, n.toString() );
                m.setExcelName(excelName);
                metodos.add(m);
//                methods.add(n.toString());
//                methodNames.add(n.getName().toString());
                
                
            }
        }.visit(StaticJavaParser.parse(new File(path)), null);
	
        
	}
/*	
	public static void main (String[] args) {
//		String path = "C:\\Users\\mrfur\\eclipse-workspace\\jasml_0.10 (1).zip_expanded\\src\\com\\jasml\\decompiler\\JavaClassParser.java";
		String path = "C:\\Users\\mrfur\\git\\ES-2Sem-2021-Grupo12\\src\\main\\java\\backend\\MethodUtils.java";
		ArrayList<Method> methods = new ArrayList<>();
		try {
			extractMethodSourceCode(path, methods);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Method m : methods) {
			//System.out.println(m.getCode());
			//System.out.println(m.getName());
		}
	}
	
	// receives a list of Method objects and returns a list of each method's code as String
	public static List<String> toMethodCodeList(List<Method> methodList) {
		List<String> methodCodeList = new ArrayList<String>();
		
		for (Method m : methodList) {
			methodCodeList.add(m.getCode());
		}
		return methodCodeList;
	}
	*/
	
	// receives a list of Method objects and returns a list of each method's name as String
	public static List<String> toMethodNameList(List<Method> methodList) {
		List<String> methodsNames = new ArrayList<String>();
		
		for (Method m : methodList) {
			methodsNames.add(m.getName());
		}
		return methodsNames;
	}

//	/**
//	 * 			getter of list of method's code
//	 * @return 	String List with all method's code
//	 */
//	public static List<String> getMethods() {
//		
//		return methods;
//	}
//	
//	/**
//	 * 			getter of list method's names 
//	 * @return 	String List with method's names
//	 */
//	public static List<String> getMethodNames() {
//		return methodNames;
//	}
//	
//	public static int getNumberOfMethods() {
//		return methodNames.size();
//	}

}