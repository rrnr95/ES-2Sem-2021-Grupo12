import java.util.ArrayList;
import java.util.List;

/**
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class ClassMethods {
	

	
	
	private List<Method> methods;

	public ClassMethods(String name) {
		this.methods = new ArrayList<Method>();
	}

	private class Method{
		String name;
		List<String> methodCode;
		
		private Method(String name) {
			this.name = name;
			methodCode = new ArrayList<String>();
		}
		
		public String getMethodName() {
			return this.name;
		}

		public void setMethodName(String methodName) {
			this.name = methodName;
		}

		public List<String> getMethodCode() {
			return methodCode;
		}	

		public void setMethodCode(List<String> methodCode) {
			this.methodCode = methodCode;
		}
		
//		public void addMethodCodeLine(String methodLine) {
//			this.methodCode.add(methodLine);
//		}
	}
}
