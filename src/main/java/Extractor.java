import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Extractor {

	private final static File EXTRACTION_FOLDER = new File("extracted_project");

	public static void main(String[] args) throws FileNotFoundException, IOException {

		ArrayList<String> cls_path_list = new ArrayList<String>();
		cls_path_list = getAllClassNames(EXTRACTION_FOLDER, new ArrayList<String>());

		for(String path: cls_path_list) {
			int num_lines = LineCounter.getNumberOfLines(new BufferedReader(new FileReader(path)));
			System.out.println(num_lines);
		}

	}

	//Vai buscar todos os ficheiros ".java" dentro de uma pasta,
	//caso encontre subpastas, chama o método recursivamente nessas subpastas.
	public static ArrayList<String> getAllClassNames(File root, ArrayList<String> list) {

		File[] file_list = root.listFiles();
		if(file_list == null)
			throw new IllegalStateException("A diretoria não contém ficheiros"); 

		for (File file : file_list) {

			if (file.isDirectory())
				getAllClassNames(file, list); //chamada recursiva

			else {
				final String file_path = file.getAbsolutePath();

				if(file_path.endsWith(".java")) {
					list.add(file_path);
					System.out.println("Added ->  " + file_path);
				}
			}
		}

		if(list.isEmpty()) 
			throw new IllegalStateException("Lista de classes vazia");

		return list;
	}
}
