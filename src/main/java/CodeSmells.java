import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import backend.FindPackages;

public class CodeSmells {
//	private String rootPath;
//	private static RecursoPartilhado metodos;
//	static private List<Thread> threads;
		
//	public CodeSmells (String path) {
//		this.rootPath = path;
//		this.metodos = new RecursoPartilhado();
////		this.threads = new ArrayList<>();
//	}
	
	public static void init(String path) /*throws InterruptedException*/ {
		RecursoPartilhado metodos = new RecursoPartilhado();
		analyse(path, metodos);
		WriteToXLSX.exportToExcel(path + "\\smells.xlsx", metodos);
	}
	
	/**
	 *	Analyzes the file on the specified path and creates the arraylist
	 * 	
	 */
	private static void analyse(String path , RecursoPartilhado metodos ) {
		//par nome-path dos packages
//		HashMap<String, String> packs = new FindPackages(rootPath).getPackages();
		HashMap<String, String> packs = FindPackages.getPackages(path);
		//iterar cada package
		for (String pck : packs.keySet()) {
			//encontrar o path para todos os ficheiros
			List<String> pathToFiles = getPathToJavaFiles(packs.get(pck));

			//iterar cada ficheiro, e lançar uma thread para analisar
			for (String file : pathToFiles) {
				AnalyseFile af = new AnalyseFile(pck, file, metodos);
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
	

	public static void main (String[] args) {
//		CodeSmells cs = new CodeSmells("D:\\Git\\ES\\ES-2Sem-2021-Grupo12\\imported_project");
		init("D:\\Git\\ES\\ES-2Sem-2021-Grupo12\\imported_project");
//		try {
//			cs.init();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}