import java.io.File;
import java.util.ArrayList;



public final class Extractor {

	//TODO: ARRANJAR MANEIRA DE FORNECER O CAMINHO PARA O PACKAGE DE MANEIRA GENÉRICA
	
	//Alterar para o caminho do package "imported_project_test"
	private final static File EXTRACTION_FOLDER = new File("C:\\Users\\renat\\Documents\\GitHub_repository\\ES-2Sem-2021-Grupo12\\src\\main\\java\\imported_project_test");

	public static void main(String[] args) {

		ArrayList<String> fjava_path_list = new ArrayList<String>();
		fjava_path_list = getAllJavaFiles(EXTRACTION_FOLDER, fjava_path_list);

		for(String path: fjava_path_list) {

			try {
			
				LineCounter ln = new LineCounter(path);
				final int num_lines = ln.getLinesCount();
				
				final int num_methods = NOM_class.get_NOM(path);
				
				System.out.println("----------------------------");
				System.out.println("number of lines: " + num_lines);
				System.out.println("number of methods: " + num_methods);


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	//Vai buscar todos os ficheiros ".java" dentro de uma pasta,
	//caso encontre subpastas, chama o método recursivamente nessas subpastas.
	public static ArrayList<String> getAllJavaFiles(File root, ArrayList<String> target_list) {

		File[] file_list = root.listFiles();
		if(file_list == null)
			throw new IllegalStateException("No Files to grab"); 

		for (File file : file_list) {

			if (file.isDirectory())
				getAllJavaFiles(file, target_list); //chamada recursiva

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
			throw new IllegalStateException("File list empty");

		return target_list;
	}

	private static boolean isClass(String path) {
		return path.endsWith(".java");
	}
}
