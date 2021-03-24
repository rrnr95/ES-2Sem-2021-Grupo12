import java.io.BufferedReader;
import java.io.IOException;

public class Method  {
	
	private String name;
	private int lines;

	public Method(String name) {
		this.name = name;
	}
	
	public void setLines(int lines) {
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		return ("Method(" + this.name + ", " + this.lines + ")");
	}

}
