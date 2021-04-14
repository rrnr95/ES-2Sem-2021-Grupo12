import java.util.HashMap;
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
		
		//exemplo:
//		MethodStats ms = new MethodStats(0, parentPackage, pathToFile, null, "metodo", 0, 0, 0, 10, 10);
//		metodos.addMetodo(ms);
	
	}

}