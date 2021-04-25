package extractor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import backend.FindPackages;
import gui.Rule;

/**
 * 			Application entry point. Project analyzer 
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class CodeSmells {
//<<<<<<< HEAD
//	private String rootPath;
//	private static RecursoPartilhado metodos;
//	static private List<Thread> threads;
		
//	public CodeSmells (String path) {
//		this.rootPath = path;
//		this.metodos = new RecursoPartilhado();
////		this.threads = new ArrayList<>();
//	}
//=======
//	private String rootPath;
//	private RecursoPartilhado metodos;
//	private List<Thread> threads;
//	private Rule rule;
//		
//	public CodeSmells (String path, Rule rule) {
//		this.rootPath = path;
//		this.metodos = new RecursoPartilhado();
//		this.threads = new ArrayList<>();
//		this.rule = rule;
//	}
//>>>>>>> 02abe83da03a699ae36cf2e95c9a386f3a8daef3
	
	public static RecursoPartilhado init(String path,Rule rule)  {
		RecursoPartilhado metodos = new RecursoPartilhado();
		analyse(path, metodos, rule);
		WriteToXLSX.exportToExcel(path + "\\smells.xlsx", metodos);
		return metodos;
	}
	
//<<<<<<< HEAD:src/main/java/extractor/CodeSmells.java
//
//	public RecursoPartilhado getRecursoPartilhado() {
//		return metodos;
//	}
//	
//
///**
// *	Analyzes the file on the specified path and creates the arraylist
// * 	
// */
//	private void analyse() {
//=======
	/**
	 *	Analyzes the file on the specified path and creates the arraylist
	 * 	
	 */
	private static void analyse(String path , RecursoPartilhado metodos, Rule rule ) {
//>>>>>>> refactor:src/main/java/CodeSmells.java
		//par nome-path dos packages
//		HashMap<String, String> packs = new FindPackages(rootPath).getPackages();
		HashMap<String, String> packs = FindPackages.getPackages(path);
		//iterar cada package
		for (String pck : packs.keySet()) {
			//encontrar o path para todos os ficheiros
			List<String> pathToFiles = getPathToJavaFiles(packs.get(pck));

			//iterar cada ficheiro, e lançar uma thread para analisar
			for (String file : pathToFiles) {
//<<<<<<< HEAD
//				AnalyseFile af = new AnalyseFile(pck, file, metodos);
////				threads.add(af);
//=======
				AnalyseFile af = new AnalyseFile(pck, file, metodos, rule);
//				threads.add(af);

				//af.start();

				af.run();
			}
		}
	}
	
	/**
	 * 			creates a list of absolutepath of the java files found on a given directory
	 * @param 	directoryPath
	 * 			path to get files from
	 * @return	list of absolutepath of java files on a given directory 
	 */
	private static List<String> getPathToJavaFiles (String directoryPath) {
		List<String> ficheiros = new ArrayList<>();
		 
		File dir = new File(directoryPath);
		for(File f : dir.listFiles()) {
			if(f.isFile() && f.getName().endsWith(".java")) {
				ficheiros.add(f.getAbsolutePath());
			}
		}

		return ficheiros;
	}
	

}