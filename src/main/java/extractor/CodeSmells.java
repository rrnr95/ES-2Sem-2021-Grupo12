package extractor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import backend.FindPackages;
import gui.Rule;

/**
 * 			Application entry point. Project analyser 
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class CodeSmells {
	
	/**
	 * Initialization of project analysis: creates a SharedResource and exports the data to excel file
	 * 
	 * @param path
	 * Path to project's directory
	 * @param rule
	 * Rule
	 * @return
	 * SharedResource
	 */
	public static SharedResource init(String path,Rule rule)  {
		SharedResource metodos = new SharedResource();
		analyse(path, metodos, rule);
		WriteToXLSX.exportToExcel(path + "\\smells.xlsx", metodos);
		return metodos;
	}

	
	/**
	 * Analyses the file on the specified path and creates the arraylist
	 * 
	 * @param path
	 * Path to java file
	 * @param metodos
	 * SharedResource
	 * @param rule
	 * Rule
	 */
	private static void analyse(String path , SharedResource metodos, Rule rule ) {
		HashMap<String, String> packs = FindPackages.getPackages(path);
		
		//iterar cada package
		for (String pck : packs.keySet()) {
			//encontrar o path para todos os ficheiros
			List<String> pathToFiles = getPathToJavaFiles(packs.get(pck));

			//iterar cada ficheiro, e lançar uma thread para analisar
			for (String file : pathToFiles) {

				AnalyseFile af = new AnalyseFile(pck, file, metodos, rule);
				af.run();
			}
		}
	}
	
	/**
	 * 			Creates a list of absolutepath of the java files found on a given directory
	 * 
	 * @param 	directoryPath
	 * 	path to get files from
	 * @return	
	 * list of absolutepath of java files on a given directory 
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