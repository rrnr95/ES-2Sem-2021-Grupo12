package backend;

/**
 * Class that groups the main attributes of a method
 * @author ES-2Sem-2021-Grupo12
 *
 */

public class Method{
	
	String name;
	String excelName;
	String code;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param code
	 */
	
	Method(String name, String code){
		this.name = name;
		this.code = code;
	}
	
	/**
	 * Constructor
	 * Creates a method with an empty name and code.
	 */
	public Method() {
		this.name = "";
		this.code = "";
	}
	
	/**
	 * Returns the Method name.
	 * @return String with Method name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the excel Method name.
	 * @return String with excel Method name.
	 */
	public String getExcelName() {
		return this.excelName;
	}

	/**
	 * Sets the name of the Method with the given string.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the excel name of the Method with the given string.
	 * @param n
	 */
	public void setExcelName(String n) {
		this.excelName = n;
	}
	
	/**
	 * Returns the Method code.
	 * @return String with method code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the Method Code with the given string.
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
}