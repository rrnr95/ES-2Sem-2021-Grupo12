package extractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 		Shared resource class which contains all methods' statistics of the project
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class SharedResource {
	
	private List<MethodStats> sharedResource;
	
	/**
	 * Constructor
	 */
	public SharedResource() {
		this.sharedResource = new ArrayList<>();
	}
	
	/**
	 * Adds an object MethodStats to the list 'sharedResource'
	 * 
	 * @param meth
	 * MethodStats
	 */
	public synchronized void addMethod (MethodStats meth) {
		sharedResource.add(meth);
	}
	
	/**
	 * Getter of 'sharedResource'
	 * 
	 * @return
	 * List of MethodStats objects
	 */
	public List<MethodStats> getMethodStats() {
		return sharedResource;
	}
	
	/**
	 * Creates a Map that represents a summary of the project's statistics, containing the number of packages, 
	 * the number of classes, the number of methods and the number os lines of code of the project
	 * 
	 * @return
	 * Map with the name of the statistic and the corresponding value
	 */
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

	/**
	 * Getter of the project's total number of lines of code
	 * 
	 * @return
	 * Total number of lines of code
	 */
	private int getLinesOfCode() {
		int linesOfCode = 0;
		String prev_class = "";

		for (MethodStats ms : sharedResource) {
			String curr_class = ms.getCls();

			if (!curr_class.equals(prev_class)) {
				int lines = ms.getLOC_class();
				linesOfCode += lines;
				prev_class = curr_class;
			}	
		}
		return linesOfCode;
	}

}
