import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Extractor {

	private final static File EXTRACTION_FOLDER = new File("imported_project");

	public static void main(String[] args) throws FileNotFoundException, IOException {

		ArrayList<String> cls_path_list = new ArrayList<String>();
		cls_path_list = getAllClassNames(EXTRACTION_FOLDER, cls_path_list);

		for(String path: cls_path_list) {
			FileReader freader = new FileReader(path);
			int num_lines = LineCounter.getNumberOfLines(new BufferedReader(freader));
			System.out.println(num_lines);
			
		}

	}

	//Vai buscar todos os ficheiros ".java" dentro de uma pasta,
	//caso encontre subpastas, chama o método recursivamente nessas subpastas.
	public static ArrayList<String> getAllClassNames(File root, ArrayList<String> target_list) {

		File[] file_list = root.listFiles();
		if(file_list == null)
			throw new IllegalStateException("A diretoria não contém ficheiros"); 

		for (File file : file_list) {

			if (file.isDirectory())
				getAllClassNames(file, target_list); //chamada recursiva

			else {
				final String file_path = file.getAbsolutePath();

				if(isClass(file_path)) {
					target_list.add(file_path);
					System.out.println("Added ->  " + file_path);
				}
				else
					System.out.println("Not added ->  " + file_path);
			}
		}

		if(target_list.isEmpty()) 
			throw new IllegalStateException("Lista de classes vazia");

		return target_list;
	}
	
	private static boolean isClass(String path) {
		return path.endsWith(".java");
	}
}
