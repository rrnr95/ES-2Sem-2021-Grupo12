package backend;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.math3.exception.NullArgumentException;

public class PackageCounter {
	private Stack<File> pilha;
	private List<String> packages;
	private String root;
	
	public PackageCounter (String path) {
		this.pilha = new Stack<File>();
		this.root = path;
		this.packages = new ArrayList<String>();
	}
	
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
	
	public List<String> getPackages() {
		return packages;
	}
	
	private String newRoot() {
		for (File f : new File(root).listFiles()) {
			if (f.isDirectory() && !f.isHidden()) {
				return f.getAbsolutePath();
			}
		}
		throw new NullPointerException("Root not found");
	}
	
	private boolean containsJavaFiles(File file) {
		for (File f : file.listFiles()) {
			if (f.getName().contains(".java")) {
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
