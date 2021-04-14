import java.util.List;
import java.util.Map;

import backend.CycloMethod;
import backend.LineCounter;
import backend.MethodUtils;
import backend.NumberOfClassesPerFile;

public class AnalyseFile extends Thread {
	private String parentPackage;
	private String pathToFile;
	private RecursoPartilhado metodos;
	
	public AnalyseFile(String parent, String path, RecursoPartilhado metodos) {
		this.parentPackage = parent;
		this.pathToFile = path;
		this.metodos = metodos;
	}
	
	public void run() {
/*
		//1 - encontrar cada classe dentro do ficheiro .java;	
		NumberOfClassesPerFile noc = new NumberOfClassesPerFile(pathToFile);
		final List<String> classes = noc.getClasses();
		
		LineCounter ln = new LineCounter(pathToFile);
		
		//2 - para cada classe, levantamento dos metodos;
		List<String> methodCode = new MethodUtils(pathToFile).getMethodCode();
		final List<Integer> cyclo_method_list = CycloMethod.allMethodsCycloValue(methodCode);
		
		final Map<String, Integer> loc_method_hash = ln.getMethodNameLines();
		
		//		3 - para cada metodo, criar um Objeto MethodStats e colocar no RecursoPartilhado
		int i = 0;
		for (Map.Entry<String, Integer> m : loc_method_hash.entrySet()) {
			MethodStats ms = new MethodStats(i+1, parentPackage, pathToFile, classes, m.getKey(), m.getValue(), 0, 0, ln.getLinesCount(), 0);
			
			ms.setNOM_class(methodCode.size());
			ms.setCYCLO_method(cyclo_method_list.get(i));
			ms.setWMC_class(CycloMethod.wmcCalculator(cyclo_method_list));
			
			metodos.addMetodo(ms);
			i++;
		}
*/
		
		NumberOfClassesPerFile noc = new NumberOfClassesPerFile(pathToFile);
		List<String> innerClasses = noc.getClasses();
		MethodUtils methods = new MethodUtils(pathToFile);
		
		List<String> methodCodeList = methods.getMethodCode();
		List<String> methodNameList = methods.getMethodName();
		
		LineCounter ln = new LineCounter(pathToFile);
		//int nom = new MethodUtils(pathToFile).getMethodName().size();
		int nom = methodNameList.size();
		int loc = ln.getLinesCount();
		int wmc = CycloMethod.wmcCalculator(CycloMethod.allMethodsCycloValue(methodCodeList));
		
		Map<String, Integer> loc_method_hash = ln.getMethodNameLines();
		
		for (String methodName : methodNameList) {
			
			int lineNum = metodos.getMethodStats().size();
			
			MethodStats meth = new MethodStats();
			meth.setMethodId(lineNum + 1);
			meth.setPack(parentPackage);
			meth.setCls(pathToFile);
			meth.setInnerClasses(innerClasses);
			
			meth.setMeth(methodName);
			for(String k : loc_method_hash.keySet()) {
				if (k.equals(methodName)) {
					meth.setLOC_method(loc_method_hash.get(k));
				}
			}
			List<Integer> cycLst = CycloMethod.allMethodsCycloValue(methodCodeList);
			int ind = methodNameList.indexOf(methodName);
			int cyc = cycLst.get(ind);
			meth.setCYCLO_method(cyc);
			
			meth.setNOM_class(nom);
			meth.setLOC_class(loc);
			meth.setWMC_class(wmc);
			
			metodos.addMetodo(meth);
			
		}
	}
}