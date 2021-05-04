
package extractor;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import backend.CycloMethod;
import backend.LineCounter;
import backend.Method;
import backend.MethodUtils;
import backend.NumberOfClassesPerFile;
import gui.Rule;

/**
 * 			Class used to analyse a single java file
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class AnalyseFile extends Thread {
	
	private String parentPackage;
	private String pathToFile;

	private SharedResource metodos;
	private Rule rule;

	
	/**
	 * Constructor
	 * 
	 * @param parent
	 * Parent package's name
	 * @param path
	 * Path to java file
	 * @param metodos
	 * Methods
	 * @param rule
	 * Rule
	 */
	public AnalyseFile(String parent, String path, SharedResource metodos, Rule rule) {
		this.parentPackage = parent;
		this.pathToFile = path;

		this.metodos = metodos;
		this.rule = rule;

	}
	
	/**
	 * Initialize file analysis: calculation of metrics and populates 'metodos'
	 */
	public void run() {

		List<String> innerClasses = NumberOfClassesPerFile.getClassesFromFile(pathToFile);

		List<Method> methodList = MethodUtils.getMethodsFromFile(pathToFile);
		

		LineCounter.countLines(pathToFile, methodList);
		int nom = methodList.size();
		int loc = LineCounter.getTotalLinesCount();		

		int wmc = CycloMethod.wmcCalculator(methodList);
		
		Map<String, Integer> loc_method_hash = LineCounter.getMethodNameLines();
		
		for (Method method : methodList) {
			
			int cyc = CycloMethod.cycloMethodValue(method);
			
			MethodStats meth = createRow(innerClasses, method , nom, loc, wmc, cyc, loc_method_hash);
			
			metodos.addMetodo(meth);
			
		}
		
		if (methodList.isEmpty()) {
			Method m = new Method();
			m.setExcelName("");
			int cyc = CycloMethod.cycloMethodValue(m);
			MethodStats meth = createRow(innerClasses, m , nom, loc, 1, cyc, loc_method_hash);
			
			metodos.addMetodo(meth);
		}	
		
	}
	
	/**
	 * Creates and populates a MethodStats object 
	 * 
	 * @param innerClasses
	 * List of inner classes' names
	 * @param method
	 * Method
	 * @param nom
	 * Number of methods
	 * @param loc
	 * Number of lines of code
	 * @param wmc
	 * Weighted method count (of the class)
	 * @param cyc
	 * Cyclometic complexity count (of the method)
	 * @param loc_method_hash
	 * Map of all methods' names and corresponding number of lines of code
	 * @return 
	 * MethodStats object to insert in excel file
	 */
	private MethodStats createRow(	List<String> innerClasses, 
									Method method,
									int nom, 
									int loc, 
									int wmc, 
									int cyc,
									Map<String, Integer> loc_method_hash ) {
		
		MethodStats meth = new MethodStats();
		meth.setPack(parentPackage);
		meth.setCls(pathToFile.replace(".java", ""));
		meth.setInnerClasses(innerClasses);
		
		meth.setMeth(method.getExcelName());
		for(String k : loc_method_hash.keySet()) {
			if (k.equals(method.getExcelName())) {
				meth.setLOC_method(loc_method_hash.get(k));
			}
		}

		meth.setCYCLO_method(cyc);
		
		meth.setNOM_class(nom);
		meth.setLOC_class(loc);
		meth.setWMC_class(wmc);
		
		meth.setIsGodClass(String.valueOf(rule.isGodClass(meth)));
		meth.setIsLongMethod(String.valueOf(rule.isLongMethod(meth)));
		
		return meth;
	}
}