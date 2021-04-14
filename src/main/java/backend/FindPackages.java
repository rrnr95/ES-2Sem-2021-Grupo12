package backend;
import java.io.File;
import java.util.HashMap;
import java.util.Stack;


/**
 * 			Class used to count packages
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class FindPackages {

	private Stack<File> pilha;
	private String root;
	

	/**
	 * 			Constructor
	 * @param 	path
	 * 			pathname of project's root
	 */
	public FindPackages (String path) {

		this.pilha = new Stack<File>();
		this.root = path;
	}
	

	/**
	 * 			Counts number of packages
	 * @return	number of packages
	 * @throws 	NullPointerException
	 */
	public HashMap<String, String> getPackages() throws NullPointerException {
		//nome-path
		HashMap<String, String> tuplo = new HashMap<>();

		
		//se a root nao tem o src
		if (! containsSrc(new File(root))) {
			root = newRoot(root);
		}
		
		//começar o teste desde o src (evitar procuras indevidas)
		pilha.push(getFile(root + "\\src"));

		while (!pilha.empty()) {
			File f = pilha.pop();
			
			//caso contenha um ficheiro java, é um package!
			if(containsJavaFiles(f)) {
				String pckname = f.getName();
				String pckpath = f.getAbsolutePath();
				if(containsDirectory(f)) {
					tuplo.put("(default package)", pckpath);
				}
				else {
					tuplo.put(pckname, pckpath);
				}
			}
			
			//procurar mais diretorias
			for (File file : f.listFiles()) {
				if(file.isDirectory()) {
					pilha.push(file);
				}	
			}
		}
		
		return tuplo;
	}
	

	//TODO checkar
	/**
	 * 			Returns the first found filepath of a file that is a directory not hidden, from the given folder
	 * @return	The absolute pathname string 
	 */
	private String newRoot(String path) {

		for (File f : new File(root).listFiles()) {
			if (f.isDirectory() && !f.isHidden()) {
				return f.getAbsolutePath();
			}
		}
		return root;
	}
	
	/**
	 * 			Checks if a given folder has any java files
	 * @param 	file
	 * 			folder file
	 * @return	true or false
	 */
	private boolean containsJavaFiles(File file) {
		for (File f : file.listFiles()) {
			if (f.getName().contains(".java")) {
				return true;
			}
		}
		
		return false;
	}
	

	private boolean containsDirectory(File file) {
		for (File f : file.listFiles()) {
			if(f.isDirectory()) {
				return true;
			}
		}
		return false;
	}
	

	/**
	 * 			Checks if a folder has a subfolder named src
	 * @param 	file
	 * @return	true or false
	 */
	private boolean containsSrc (File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (f.isDirectory() && f.getName().equals("src")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 			Returns a file located on a given path
	 * @param 	path
	 * 			file path
	 * @return	a File located on a given path
	 */
	private File getFile(String path) {
		File f = new File(path);
		return f;
	}
	
}