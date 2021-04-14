import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeSmells {
	private String rootPath;
	private RecursoPartilhado metodos;
	private List<Thread> threads;
		
	public CodeSmells (String path) {
		this.rootPath = path;
		this.metodos = new RecursoPartilhado();
		this.threads = new ArrayList<>();
	}
	
	public void init() throws InterruptedException {
		analyse();
		createExcellDoc();
	}
	
	private void analyse() {
		//par nome-path dos packages
		HashMap<String, String> packs = new FindPackages(rootPath).getPackages();
		
		//iterar cada package
		for (String pck : packs.keySet()) {
			//encontrar o path para todos os ficheiros
			List<String> pathToFiles = getPathToJavaFiles(packs.get(pck));

			//iterar cada ficheiro, e lançar uma thread para analisar
			for (String file : pathToFiles) {
				AnalyseFile af = new AnalyseFile(pck, file, metodos);
				threads.add(af);
				//af.start();
				af.run();
			}
		}	
	}
	
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
	
	private void createExcellDoc() throws InterruptedException {
		for (Thread thread : threads) {
			thread.join();
		}
		
		//System.out.println("Path de escrita: " + rootPath);
		WriteToXLSX excell = new WriteToXLSX(rootPath + "\\smells.xlsx", metodos);
		excell.init();
		//System.out.println("ESCRITO!!!");
	}
	
	public static void main (String[] args) {
//		CodeSmells cs = new CodeSmells("C:\\Users\\mrfur\\git\\BattleshipCodeCoverage");
		CodeSmells cs = new CodeSmells("C:\\Users\\mrfur\\git\\ES-2Sem-2021-Grupo12");
//		CodeSmells cs = new CodeSmells("C:\\Users\\mrfur\\eclipse-workspace\\Matrix");

		try {
			cs.init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}