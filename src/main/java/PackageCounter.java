import java.io.File;
import java.util.Stack;

public class PackageCounter {
	private Stack<File> pilha;
	private String root;
	
	public PackageCounter (String path) {
		this.pilha = new Stack<File>();
		this.root = path;
	}
	
	public int packagesCount() throws NullPointerException {
		int cnt = 0;
		
		//se a root nao tem o src
		if (! containsSrc(new File(root))) {
			root = newRoot(root);
		}
		
		//começar o teste desde o src (evitar procuras indevidas)
		pilha.push(getFile(root + "\\src"));

		while (!pilha.empty()) {
			File f = pilha.pop();
			
			//caso contenha um ficheiro java, é um package
			if(containsJavaFiles(f)) {
				cnt++;
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
	
	public static void main (String[] args) {
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\git\\ES-2Sem-2021-Grupo12");
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\eclipse-workspace\\DBSample");
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\eclipse-workspace\\coisas");
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\eclipse-workspace\\Matrix");
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\eclipse-workspace\\teste");
//		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\eclipse-workspace\\twitter4j-core");
		PackageCounter pc = new PackageCounter("C:\\Users\\mrfur\\git\\BattleshipCodeCoverage");
//		PackageCounter pc = new PackageCounter("Caminho\\Invalido");
		

		try {
			System.out.println(pc.packagesCount());
		}
		catch (NullPointerException e) {
			System.err.println("ERRO!!! Nao estamos na root do projeto");
		}
	}

}
