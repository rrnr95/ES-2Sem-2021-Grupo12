package extractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedResource {
	private List<MethodStats> sharedResource;
	
	public SharedResource() {
		this.sharedResource = new ArrayList<>();
	}
	
	public synchronized void addMetodo (MethodStats meth) {
		sharedResource.add(meth);
	}
	
	public List<MethodStats> getMethodStats() {
		return sharedResource;
	}
	
	public Map<String, Integer> statsSummary() {
		Map<String, Integer> map = new HashMap<>();
		
		Set<String> packs = new HashSet<>();
		Set<String> cls = new HashSet<>();
		
		int totalMethods = sharedResource.size();
		int linesOfCode = getLinesOfCode();
		for (MethodStats ms : sharedResource) {
			String pck = ms.getPack();
			packs.add(pck);
			String cl = ms.getCls();
			cls.add(cl);
		}
		
		map.put("NumPacks", packs.size());
		map.put("NumClasses", cls.size());
		map.put("NumMethods", totalMethods);
		map.put("NumLinesOfCode", linesOfCode);
		
		return map;
	}

	private int getLinesOfCode() {
		int linesOfCode = 0;
		for (MethodStats ms : sharedResource) {
			int lines = ms.getLOC_method();
			linesOfCode += lines;
		}
		return linesOfCode;
	}

}
