package comparer;

/**
 * Class used to count lines of file and methods
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class MethodComparisson {
	private String methodID;
	private String pck;
	private String cls;
	private String meth;
	private int NOM_class_smell;
	private int NOM_class_baseline;
	private int LOC_class_smell;
	private int LOC_class_baseline;
	private int WMC_class_smell;
	private int WMC_class_baseline;
	private boolean is_God_Class_smell;
	private boolean is_God_Class_baseline;
	private int LOC_method_smell;
	private int LOC_method_baseline;
	private int CYCLO_method_smell;
	private int CYCLO_method_baseline;
	private boolean is_Long_Method_smell;
	private boolean is_Long_Method_baseline;
	
	/**
	 * Class constructor
	 */
	public MethodComparisson(String id, String pck, String cls, String meth, int nom_class, int LOC_class, int WMC_class, 
			boolean is_god, int LOC_meth, int CYCLO_meth, boolean is_long) {
		this.methodID = id;
		this.pck = pck;
		this.cls = cls;
		this.meth = meth;
		this.NOM_class_smell = nom_class;
		this.LOC_class_smell = LOC_class;
		this.WMC_class_smell = WMC_class;
		this.is_God_Class_smell = is_god;
		this.LOC_method_smell = LOC_meth;
		this.CYCLO_method_smell = CYCLO_meth;
		this.is_Long_Method_smell = is_long;
	}
	
	/**
	 * @return	returns the baseline Number Of Methods per class
	 */
	public int getNOM_class_baseline() {
		return NOM_class_baseline;
	}

	/**
	 * sets the baseline Number Of Methods per class
	 * @param	baseline Number Of Methods per class
	 */
	public void setNOM_class_baseline(int nOM_class_baseline) {
		NOM_class_baseline = nOM_class_baseline;
	}

	/**
	 * @return	baseline Lines Of Code per class
	 */
	public int getLOC_class_baseline() {
		return LOC_class_baseline;
	}

	/**
	 * sets the baseline Lines Of Code per class
	 * @param	baseline Lines Of Code per class
	 */
	public void setLOC_class_baseline(int lOC_class_baseline) {
		LOC_class_baseline = lOC_class_baseline;
	}

	/**
	 * @return	baseline Weighted Method Count per Class
	 */
	public int getWMC_class_baseline() {
		return WMC_class_baseline;
	}

	/**
	 * sets the baseline Weighted Method Count per Class
	 * @param	baseline Weighted Method Count per Class
	 */
	public void setWMC_class_baseline(int wMC_class_baseline) {
		WMC_class_baseline = wMC_class_baseline;
	}

	/**
	 * @return	true or false
	 */
	public boolean isIs_God_Class_baseline() {
		return is_God_Class_baseline;
	}

	/**
	 * sets the baseline is God Class
	 * @param	is God Class
	 * 			boolean
	 */
	public void setIs_God_Class_baseline(boolean is_God_Class_baseline) {
		this.is_God_Class_baseline = is_God_Class_baseline;
	}

	/**
	 * @return	baseline Lines Of Code of a method
	 */
	public int getLOC_method_baseline() {
		return LOC_method_baseline;
	}

	/**
	 * sets the baseline Lines Of Code of a method
	 * @param	baseline Lines of Code of a method
	 */
	public void setLOC_method_baseline(int lOC_method_baseline) {
		LOC_method_baseline = lOC_method_baseline;
	}

	/**
	 * @return	baseline cyclomatic complexity of a method
	 */
	public int getCYCLO_method_baseline() {
		return CYCLO_method_baseline;
	}

	/**
	 * sets the baseline cyclomatic complexity of a method
	 * @param	baseline cyclomatic complexity of a method
	 */
	public void setCYCLO_method_baseline(int cYCLO_method_baseline) {
		CYCLO_method_baseline = cYCLO_method_baseline;
	}

	/**
	 * @return	baseline is Long Method
	 * 			true or false
	 */
	public boolean isIs_Long_Method_baseline() {
		return is_Long_Method_baseline;
	}

	/**
	 * sets the baseline is Long Method
	 * @param	baseline is Long Method
	 */
	public void setIs_Long_Method_baseline(boolean is_Long_Method_baseline) {
		this.is_Long_Method_baseline = is_Long_Method_baseline;
	}

	/**
	 * @return	method ID
	 */
	public String getMethodID() {
		return methodID;
	}

	/**
	 * @return	package name
	 */
	public String getPck() {
		return pck;
	}

	/**
	 * @return	class name
	 */
	public String getCls() {
		return cls;
	}

	/**
	 * @return	method name
	 */
	public String getMeth() {
		return meth;
	}

	/**
	 * @return	Number of Methods of the compared class
	 */
	public int getNOM_class_smell() {
		return NOM_class_smell;
	}

	/**
	 * @return	Lines of Code of the compared class
	 */
	public int getLOC_class_smell() {
		return LOC_class_smell;
	}

	/**
	 * @return	Weighted Method Count per Class of the compared class
	 */
	public int getWMC_class_smell() {
		return WMC_class_smell;
	}

	/**
	 * @return	is God Class of the compared class
	 */
	public boolean isIs_God_Class_smell() {
		return is_God_Class_smell;
	}

	/**
	 * @return	Lines Of Code of the compared method
	 */
	public int getLOC_method_smell() {
		return LOC_method_smell;
	}

	/**
	 * @return	Cyclomatic Complexity of the compared method
	 */
	public int getCYCLO_method_smell() {
		return CYCLO_method_smell;
	}

	/**
	 * @return	is long method of the compared method
	 * 			true or false
	 */
	public boolean isIs_Long_Method_smell() {
		return is_Long_Method_smell;
	}

}
