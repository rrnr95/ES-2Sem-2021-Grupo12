package backend;

public class Method{
	
	String name;

	String code;

	Method(String name, String code){
		this.name = name;
		this.code = code;
	}
	
	public Method() {
		this.name = "";
		this.code = "";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}