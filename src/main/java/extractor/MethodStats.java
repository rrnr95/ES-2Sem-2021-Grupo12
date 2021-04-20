package extractor;
import java.util.ArrayList;
import java.util.List;

/**
 * 			Object that represents each row of excel file
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class MethodStats {
	private int methodId;
	private String pack;
	private String cls;
	private List<String> innerClasses;
	private String meth;
	private int LOC_method;
	private int CYCLO_method;
	
	//referente à CLASSE: tenho duvidas aqui???
	private int NOM_class;
	private int LOC_class;
	private int WMC_class;
	
	/**
	 * 			Constructor
	 * @param 	methodId
	 * 			MethodID
	 * @param 	pack
	 * 			Package
	 * @param 	cls
	 * 			Class
	 * @param 	innerClasses
	 * 			List of inner classes
	 * @param 	meth
	 * 			Method
	 * @param 	lOC_method
	 * 			Lines of code
	 * @param 	cYCLO_method
	 * 			Number of cycles
	 * @param 	nOM_class
	 * 			Number of methods
	 * @param 	lOC_class
	 * 			Lines of code
	 * @param 	wMC_class
	 * 			
	 */
	public MethodStats(int methodId, String pack, String cls, List<String> innerClasses, String meth, int lOC_method, int cYCLO_method, int nOM_class, int lOC_class, int wMC_class) {
		this.methodId = methodId;
		this.pack = pack;
		this.cls = cls;
		this.innerClasses = innerClasses;
		this.meth = meth;
		LOC_method = lOC_method;
		CYCLO_method = cYCLO_method;
		NOM_class = nOM_class;
		LOC_class = lOC_class;
		WMC_class = wMC_class;
	}
	
	public MethodStats() {
		innerClasses = new ArrayList<>();
	}
	
	public int getMethodId() {
		return methodId;
	}
	
	
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}
	
	
	public String getPack() {
		return pack;
	}
	
	
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	public List<String> getInnerClasses() {
		return innerClasses;
	}
	
	
	public void setInnerClasses(List<String> inner) {
		this.innerClasses = inner;
	}
	
	
	public String getCls() {
		return cls;
	}
	
	
	public void setCls(String cls) {
		this.cls = cls;
	}
	
	
	public String getMeth() {
		return meth;
	}
	
	
	public void setMeth(String meth) {
		this.meth = meth;
	}
	
	
	public int getLOC_method() {
		return LOC_method;
	}
	
	
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}
	
	
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	
	
	public void setCYCLO_method(int cYCLO_method) {
		CYCLO_method = cYCLO_method;
	}
	
	
	public int getNOM_class() {
		return NOM_class;
	}
	
	
	public void setNOM_class(int nOM_class) {
		NOM_class = nOM_class;
	}
	
	
	public int getLOC_class() {
		return LOC_class;
	}
	
	
	public void setLOC_class(int lOC_class) {
		LOC_class = lOC_class;
	}
	
	
	public int getWMC_class() {
		return WMC_class;
	}
	
	
	public void setWMC_class(int wMC_class) {
		WMC_class = wMC_class;
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	private String listAsString(List<String> list) {
		if (list == null || list.size() < 2) {
			return "";
		}
		else {
			return list.subList(1, list.size()).toString();
		}
	}
	
	/**
	 * 			Gets a filename from a given fullpath
	 * @param 	p
	 * 			fullpath from a file
	 * @return	filename from a given fullpath
	 */
	private String fileName(String p) {
		String fileName = p.replace("\\", "/");
		String[] splitted = fileName.split("/");
		return splitted[splitted.length - 1];
	}
	
	/**
	 * 			Creates a list of the field values of MethodStats object, used to iterate over, and fill each cell in a row
	 * @return	a String list with all MethodStats's field 
	 */
	public List<String> getMethodAsList() {
		List<String> list = new ArrayList<>();
		
		list.add(String.valueOf(methodId));
		list.add(pack);
		list.add(fileName(cls));
		list.add(listAsString(innerClasses));
		list.add(meth);
		
		list.add(String.valueOf(NOM_class));
		list.add(String.valueOf(LOC_class));
		list.add(String.valueOf(WMC_class));
		
		list.add("is_God_class");
		
		list.add(String.valueOf(LOC_method));
		list.add(String.valueOf(CYCLO_method));
		
		list.add("is_long_method");
		
		return list;
	}
	
}
