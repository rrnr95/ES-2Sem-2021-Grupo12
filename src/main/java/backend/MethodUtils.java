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
 * Represents an object with two attributes. Two lists, one with the code, and another with names.  
 * The indexes of each list are correlated. First index of methodName has the corresponding code in methodCode first index.
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class MethodUtils {
	
	/**
	 * List of code for each method
	 */
	private static List<String> methodCode;
	/**
	 * List of method's names
	 */
	private static List<String> methodName;

	/**	
	 * Constructor
	 * @param 	path 
	 * 			a pathname of the java file to parse
	 */
	public MethodUtils(String path) {
		this.methodCode = new ArrayList<String>();
		this.methodName = new ArrayList<String>();
		try {
			extractMethodSourceCode(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 			Parse the java file given on the path given, and update the lists with the 
	 * 			corresponding methods code and name.
	 * @param 	path
	 * 			a pathname string
	 * @throws 	FileNotFoundException
	 */
	public static void extractMethodSourceCode(String path) throws FileNotFoundException{
		 
		new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                super.visit(n, arg);
             //   System.out.println(" * " + n);
                methodCode.add(n.toString());
                methodName.add(n.getName().toString());
                
                
            }
        }.visit(StaticJavaParser.parse(new File(path)), null);
	
        
	}

	/**
	 * 			getter of list of method's code
	 * @return 	String List with all method's code
	 */
	public List<String> getMethodCode() {
		return methodCode;
	}
	
	/**
	 * 			getter of list method's names 
	 * @return 	String List with method's names
	 */
	public List<String> getMethodName() {
		return methodName;
	}

}