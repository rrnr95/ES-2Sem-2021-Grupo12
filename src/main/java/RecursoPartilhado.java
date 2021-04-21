import java.util.ArrayList;
import java.util.List;
//TODO MethodStatList
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

}
