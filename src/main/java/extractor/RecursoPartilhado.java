package extractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecursoPartilhado {
	private List<MethodStats> recursoPartilhado;
	
	public RecursoPartilhado() {
		this.recursoPartilhado = new ArrayList<>();
	}
	
	public synchronized void addMetodo (MethodStats meth) {
		recursoPartilhado.add(meth);
	}
	
	public List<MethodStats> getMethodStats() {
		return recursoPartilhado;
	}
	
	public Map<String, Integer> statsSummary() {
		Map<String, Integer> map = new HashMap<>();
		
		Set<String> packs = new HashSet<>();
		Set<String> cls = new HashSet<>();
		
		int totalMethods = recursoPartilhado.size();
		int linesOfCode = 0;
		
		for (MethodStats ms : recursoPartilhado) {
			String pck = ms.getPack();
			packs.add(pck);
			String cl = ms.getCls();
			cls.add(cl);
			int lines = ms.getLOC_method();
			linesOfCode += lines;
		}
		
		map.put("NumPacks", packs.size());
		map.put("NumClasses", cls.size());
		map.put("NumMethods", totalMethods);
		map.put("NumLinesOfCode", linesOfCode);
		
		return map;
	}

}
