public class AnalyseFile extends Thread {
	private String parentPackage;
	private String pathToFile;
	private RecursoPartilhado metodos;
	
	public AnalyseFile(String parent, String path, RecursoPartilhado metodos) {
		this.parentPackage = parent;
		this.pathToFile = path;
		this.metodos = metodos;
	}
	
	public void run() {
		//1 - encontrar cada classe dentro do ficheiro .java;		
		
		//2 - para cada classe, levantamento dos metodos;
		//for (metodo m : class.getMetodos()) {
		
		//		3 - para cada metodo, criar um Objeto MethodStats e colocar no RecursoPartilhado
		//}
		
		
		//exemplo:
		MethodStats ms = new MethodStats(0, parentPackage, pathToFile, null, "metodo", 0, 0, 0, 10, 10);
		metodos.addMetodo(ms);
	
	}

}