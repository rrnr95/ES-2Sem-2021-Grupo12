import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodUtils {
	
	
	private static List<String> methodCode;
	private static List<String> methodName;
	
	public MethodUtils(String path) {
		this.methodCode = new ArrayList<String>();
		this.methodName = new ArrayList<String>();
		try {
			extractMethodSourceCode(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
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


	public List<String> getMethodCode() {
		return methodCode;
	}
	
	
	public List<String> getMethodName() {
		return methodName;
	}

}