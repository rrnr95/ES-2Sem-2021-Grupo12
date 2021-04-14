package backend;
import java.io.File;
import java.util.HashMap;
import java.util.Stack;

public class FindPackages {
	private Stack<File> pilha;
	private String root;
	
	public FindPackages (String path) {
		this.pilha = new Stack<File>();
		this.root = path;
	}
	
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
	
	private String newRoot(String path) {
		for (File f : new File(root).listFiles()) {
			if (f.isDirectory() && !f.isHidden()) {
				return f.getAbsolutePath();
			}
		}
		return root;
	}
	
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
	
	private File getFile(String path) {
		File f = new File(path);
		return f;
	}
	
}