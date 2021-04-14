package backend;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.math3.exception.NullArgumentException;

/**
 * 			Class used to count packages
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class PackageCounter {
	private Stack<File> pilha;
	private List<String> packages;
	private String root;
	
	/**
	 * 			Constructor
	 * @param 	path
	 * 			pathname of project's root
	 */
	public PackageCounter (String path) {
		this.pilha = new Stack<File>();
		this.root = path;
		this.packages = new ArrayList<String>();
	}
	
	/**
	 * 			Counts number of packages
	 * @return	number of packages
	 * @throws 	NullPointerException
	 */
	public int packagesCount() throws NullPointerException {
		int cnt = 0;
		
		//se a root nao tem o src
		if (! containsSrc(new File(root))) {
			root = newRoot();
		}
		
		//começar o teste desde o src (evitar procuras indevidas)
		pilha.push(getFile(root + "\\src"));

		while (!pilha.empty()) {
			File f = pilha.pop();
			
			//caso contenha um ficheiro java, é um package!
			if(containsJavaFiles(f)) {
				cnt++;
				packages.add(f.getName());
			}
			
			//procurar mais diretorias
			for (File file : f.listFiles()) {
				if(file.isDirectory()) {
					pilha.push(file);
				}	
			}
		}
		
		return cnt;
	}
	
	/**
	 * 			getter
	 * @return 	list of package names
	 */
	public List<String> getPackages() {
		return packages;
	}
	
	//TODO checkar
	/**
	 * 			Returns the first found filepath of a file that is a directory not hidden, from the given folder
	 * @return	The absolute pathname string 
	 */
	private String newRoot() {
		for (File f : new File(root).listFiles()) {
			if (f.isDirectory() && !f.isHidden()) {
				return f.getAbsolutePath();
			}
		}
		throw new NullPointerException("Root not found");
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
