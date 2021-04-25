package extractor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import backend.FindPackages;
import gui.Rule;

public class CodeSmells {
	private String rootPath;
	private RecursoPartilhado metodos;
	private List<Thread> threads;
	private Rule rule;
		
	public CodeSmells (String path, Rule rule) {
		this.rootPath = path;
		this.metodos = new RecursoPartilhado();
		this.threads = new ArrayList<>();
		this.rule = rule;
	}
	
	public void init() throws InterruptedException {
		analyse();
		createExcellDoc();
	}
	

	public RecursoPartilhado getRecursoPartilhado() {
		return metodos;
	}
	

/**
 *	Analyzes the file on the specified path and creates the arraylist
 * 	
 */
	private void analyse() {
		//par nome-path dos packages
		HashMap<String, String> packs = new FindPackages(rootPath).getPackages();
		
		//iterar cada package
		for (String pck : packs.keySet()) {
			//encontrar o path para todos os ficheiros
			List<String> pathToFiles = getPathToJavaFiles(packs.get(pck));

			//iterar cada ficheiro, e lançar uma thread para analisar
			for (String file : pathToFiles) {
				AnalyseFile af = new AnalyseFile(pck, file, metodos, rule);
				threads.add(af);
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
	private List<String> getPathToJavaFiles (String directoryPath) {
		List<String> ficheiros = new ArrayList<>();
		 
		File dir = new File(directoryPath);
		for(File f : dir.listFiles()) {
			if(f.isFile() && f.getName().endsWith(".java")) {
				ficheiros.add(f.getAbsolutePath());
			}
		}
		
		return ficheiros;
	}
	
	/**
	 * 	Creates the excel file
	 * 	@throws InterruptedException
	 */
	private void createExcellDoc() throws InterruptedException {
		for (Thread thread : threads) {
			thread.join();
		}
		
		//System.out.println("Path de escrita: " + rootPath);
		WriteToXLSX excell = new WriteToXLSX(rootPath + "\\smells.xlsx", metodos);
		excell.init();
		//System.out.println("ESCRITO!!!");
	}
	

/*	public static void main (String[] args) {
		CodeSmells cs = new CodeSmells("C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship");
		try {
			cs.init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/	
}