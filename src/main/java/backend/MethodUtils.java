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

                Method m = new Method(name, n.toString() );
                m.setExcelName(excelName);
                metodos.add(m);

            }
        }.visit(StaticJavaParser.parse(new File(path)), null);
	
        
	}


}