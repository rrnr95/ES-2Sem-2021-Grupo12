
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

	private RecursoPartilhado metodos;
	private Rule rule;

	
	public AnalyseFile(String parent, String path, RecursoPartilhado metodos, Rule rule) {
		this.parentPackage = parent;
		this.pathToFile = path;

		this.metodos = metodos;
		this.rule = rule;

	}
	
	public void run() {

		List<String> innerClasses = NumberOfClassesPerFile.getClassesFromFile(pathToFile);

		List<Method> methodList = MethodUtils.getMethodsFromFile(pathToFile);
		

		LineCounter.countLines(pathToFile, methodList);
		int nom = methodList.size();
		int loc = LineCounter.getTotalLinesCount();		

		int wmc = CycloMethod.wmcCalculator(methodList);
		
		Map<String, Integer> loc_method_hash = LineCounter.getMethodNameLines();
		
		for (Method method : methodList) {
			
			int lineNum = metodos.getMethodStats().size();
			
			MethodStats meth = createRow(innerClasses, method , nom, loc, wmc, loc_method_hash, lineNum);
			
			metodos.addMetodo(meth);
			
		}
		
		
		
		if (methodList.isEmpty()) {
			Method m = new Method();
			m.setExcelName("");
			MethodStats meth = createRow(innerClasses, m , nom, loc, 1, loc_method_hash, 0);
			
			metodos.addMetodo(meth);
		}
		
		
		
	}
	/**
	 * 			CREATE ROW?
	 * @param innerClasses
	 * @param methodCodeList
	 * @param methodNameList
	 * @param nom
	 * @param loc
	 * @param wmc
	 * @param loc_method_hash
	 * @param methodName
	 * @param lineNum
	 * @return
	 */
	private MethodStats createRow(	List<String> innerClasses, 
									Method method,
									int nom, 
									int loc, 
									int wmc, 
									Map<String, Integer> loc_method_hash,  
									int lineNum) {
		MethodStats meth = new MethodStats();
		meth.setMethodId(lineNum + 1);
		meth.setPack(parentPackage);
		meth.setCls(pathToFile.replace(".java", ""));
		meth.setInnerClasses(innerClasses);
		
		meth.setMeth(method.getExcelName());
		for(String k : loc_method_hash.keySet()) {
			if (k.equals(method.getExcelName())) {
				meth.setLOC_method(loc_method_hash.get(k));
			}

		}

		int cyc = CycloMethod.cycloMethodValue(method);
		meth.setCYCLO_method(cyc);
		
		meth.setNOM_class(nom);
		meth.setLOC_class(loc);
		meth.setWMC_class(wmc);
		
		meth.setIsGodClass(String.valueOf(rule.isGodClass(meth)));
		meth.setIsLongMethod(String.valueOf(rule.isLongMethod(meth)));
		
		//metodos.addMetodo(meth);
		
		return meth;
	}
}