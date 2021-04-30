package comparer;

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
	
	
	public int getNOM_class_baseline() {
		return NOM_class_baseline;
	}

	public void setNOM_class_baseline(int nOM_class_baseline) {
		NOM_class_baseline = nOM_class_baseline;
	}

	public int getLOC_class_baseline() {
		return LOC_class_baseline;
	}

	public void setLOC_class_baseline(int lOC_class_baseline) {
		LOC_class_baseline = lOC_class_baseline;
	}

	public int getWMC_class_baseline() {
		return WMC_class_baseline;
	}

	public void setWMC_class_baseline(int wMC_class_baseline) {
		WMC_class_baseline = wMC_class_baseline;
	}

	public boolean isIs_God_Class_baseline() {
		return is_God_Class_baseline;
	}

	public void setIs_God_Class_baseline(boolean is_God_Class_baseline) {
		this.is_God_Class_baseline = is_God_Class_baseline;
	}

	public int getLOC_method_baseline() {
		return LOC_method_baseline;
	}

	public void setLOC_method_baseline(int lOC_method_baseline) {
		LOC_method_baseline = lOC_method_baseline;
	}

	public int getCYCLO_method_baseline() {
		return CYCLO_method_baseline;
	}

	public void setCYCLO_method_baseline(int cYCLO_method_baseline) {
		CYCLO_method_baseline = cYCLO_method_baseline;
	}

	public boolean isIs_Long_Method_baseline() {
		return is_Long_Method_baseline;
	}

	public void setIs_Long_Method_baseline(boolean is_Long_Method_baseline) {
		this.is_Long_Method_baseline = is_Long_Method_baseline;
	}

	public String getMethodID() {
		return methodID;
	}

	public String getPck() {
		return pck;
	}

	public String getCls() {
		return cls;
	}

	public String getMeth() {
		return meth;
	}

	public int getNOM_class_smell() {
		return NOM_class_smell;
	}

	public int getLOC_class_smell() {
		return LOC_class_smell;
	}

	public int getWMC_class_smell() {
		return WMC_class_smell;
	}

	public boolean isIs_God_Class_smell() {
		return is_God_Class_smell;
	}

	public int getLOC_method_smell() {
		return LOC_method_smell;
	}

	public int getCYCLO_method_smell() {
		return CYCLO_method_smell;
	}

	public boolean isIs_Long_Method_smell() {
		return is_Long_Method_smell;
	}

}
