package extractor;
import java.util.ArrayList;
import java.util.List;

import backend.NumberOfClassesPerFile;

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
	
	private int NOM_class;
	private int LOC_class;
	private int WMC_class;
	
	private String isGodClass;
	private String isLongMethod;
	
	/**
	 * 			Constructor
	 * 
	 * @param 	methodId
	 * 			Method's ID
	 * @param 	pack
	 * 			Package's name
	 * @param 	cls
	 * 			Class's name
	 * @param 	innerClasses
	 * 			List of inner classes
	 * @param 	meth
	 * 			Method
	 * @param 	lOC_method
	 * 			Lines of code
	 * @param 	cYCLO_method
	 * 			Cyclometic complexity count (of the method)
	 * @param 	nOM_class
	 * 			Number of methods
	 * @param 	lOC_class
	 * 			Lines of code
	 * @param 	wMC_class
	 * 			Weighted method count (of the class)
	 * @param	isGodClass
	 * 			string that represents if is god class
	 * @param 	isLongMethod
	 * 			string that represents if is long method
	 * 			
	 */
	public MethodStats(int methodId, 
						String pack, 
						String cls, 
						List<String> innerClasses, 
						String meth, 
						int lOC_method, 
						int cYCLO_method, 
						int nOM_class, 
						int lOC_class, 
						int wMC_class, 
						String isGodClass, 
						String isLongMethod) {
		
		this.methodId = methodId;
		this.pack = pack;
		this.cls = cls;
		this.innerClasses = innerClasses;
		LOC_method = lOC_method;
		CYCLO_method = cYCLO_method;
		NOM_class = nOM_class;
		LOC_class = lOC_class;
		WMC_class = wMC_class;
		this.isLongMethod = isLongMethod;
		this.isGodClass = isGodClass;
	}
	
	
	/**
	 * Constructor
	 */
	public MethodStats() {
		innerClasses = new ArrayList<>();
	}
	
	
	/**
	 * Getter of 'methodId'
	 * 
	 * @return
	 * Method's ID
	 */
	public int getMethodId() {
		return methodId;
	}
	
	/**
	 * Setter of 'methodId'
	 * 
	 * @param methodId
	 * Method's ID
	 */
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}
	
	/**
	 * Getter of 'pack'
	 * 
	 * @return
	 * Pack's name
	 */
	public String getPack() {
		return pack;
	}
	
	/**
	 * Setter of 'pack'
	 * 
	 * @param pack
	 * Package's name
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	/**
	 * Getter of 'innerClasses'
	 * 
	 * @return
	 * List of inner classes' names
	 */
	public List<String> getInnerClasses() {
		return innerClasses;
	}
	
	/**
	 * Setter of 'innerClasses'
	 * @param inner
	 * List of inner classes' names
	 */
	public void setInnerClasses(List<String> inner) {
		this.innerClasses = inner;
	}
	
	/**
	 * Getter of 'cls'
	 * @return
	 * Class's name
	 */
	public String getCls() {
		return cls;
	}
	
	/**
	 * Setter of 'cls'
	 * 
	 * @param cls
	 * Class's name
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}
	
	/**
	 * Getter of 'meth'
	 * 
	 * @return
	 * Method's name
	 */
	public String getMeth() {
		return meth;
	}
	
	/**
	 * Setter of 'meth'
	 * 
	 * @param meth
	 * Method's name
	 */
	public void setMeth(String meth) {
		this.meth = meth;
	}
	
	/**
	 * Getter of 'LOC_method'
	 * 
	 * @return
	 * Method's number of lines of code
	 */
	public int getLOC_method() {
		return LOC_method;
	}
	
	/**
	 * Setter of 'LOC_method'
	 * 
	 * @param lOC_method
	 * Method's number of lines of code
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}
	
	/**
	 * Getter of 'CYCLO_method'
	 * 
	 * @return
	 * Cyclometic complexity count (of the method)
	 */
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	
	/**
	 * Setter of 'CYCLO_method'
	 * 
	 * @param cYCLO_method
	 * Cyclometic complexity count (of the method)
	 */
	public void setCYCLO_method(int cYCLO_method) {
		CYCLO_method = cYCLO_method;
	}
	
	/**
	 * Getter of 'NOM_class'
	 * 
	 * @return
	 * Number of methods
	 */
	public int getNOM_class() {
		return NOM_class;
	}
	
	/**
	 * Setter of 'NOM_class'
	 * 
	 * @param nOM_class
	 * Number of methods
	 */
	public void setNOM_class(int nOM_class) {
		NOM_class = nOM_class;
	}
	
	/**
	 * Getter of 'LOC_class'
	 * 
	 * @return
	 * Classes' lines of code
	 */
	public int getLOC_class() {
		return LOC_class;
	}
	
	/**
	 * Setter of 'LOC_class
	 * 
	 * @param lOC_class
	 * Class's number of lines of code
	 */
	public void setLOC_class(int lOC_class) {
		LOC_class = lOC_class;
	}
	
	/**
	 * Gettter of 'WMC_class'
	 * 
	 * @return
	 * Weighted method count (of the class)
	 */
	public int getWMC_class() {
		return WMC_class;
	}
	
	/**
	 * Setter of 'WMC_class'
	 * 
	 * @param wMC_class
	 * Weighted method count (of the class)
	 */
	public void setWMC_class(int wMC_class) {
		WMC_class = wMC_class;
	}
	
	/**
	 * Getter of 'isLongMethod'
	 * 
	 * @return
	 * String that represents if is long method
	 */
	public String getIsLongMethod() {
		return isLongMethod;
	}
	
	/**
	 * Setter of 'isLongMethod'
	 * 
	 * @param isLongMethod
	 * String that represents if is long method
	 */
	public void setIsLongMethod(String isLongMethod) {
		this.isLongMethod = isLongMethod;
	}
	
	/**
	 * Getter of 'isGodClass'
	 * 
	 * @return
	 * String that represents if is god class
	 */
	public String getIsGodClass() {
		return isGodClass;
	}
	
	/**
	 * Setter of 'isGoodClass'
	 * 
	 * @param isGodClass
	 * String that represents if is long method
	 */
	public void setIsGodClass(String isGodClass) {
		this.isGodClass = isGodClass;
	}
	
	/**
	 * Creates a String that represents the given list of strings, from index 1 to the end of the list
	 * 
	 * @param list
	 * List of Strings
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
	 * 			Creates a list of the field values of MethodStats object, used to iterate over, and fill each cell in a row
	 * @return	
	 * String list with all MethodStats's field 
	 */
	public List<String> getMethodAsList() {
		List<String> list = new ArrayList<>();
		
		list.add(String.valueOf(methodId));
		list.add(pack);
		list.add(NumberOfClassesPerFile.getFileName(cls));
		list.add(listAsString(innerClasses));
		list.add(meth);
		
		list.add(String.valueOf(NOM_class));
		list.add(String.valueOf(LOC_class));
		list.add(String.valueOf(WMC_class));
		
		list.add(isGodClass);
		
		list.add(String.valueOf(LOC_method));
		list.add(String.valueOf(CYCLO_method));
		
		list.add(isLongMethod);
		
		return list;
	}
	
}
