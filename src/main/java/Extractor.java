import java.io.File;
import java.util.ArrayList;
import java.util.List;




public final class Extractor {
	private static ArrayList<String> fjava_path_list = new ArrayList<String>();
	private static int total_number_classes;
	private static int total_number_methods;
	private static int total_number_lines;
	
	//Alterar para o caminho do package "imported_project_test"
	//private final static File EXTRACTION_FOLDER = new File("C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\src\\main\\java\\imported_project_test");
	//TODO: ARRANJAR MANEIRA DE FORNECER O CAMINHO PARA O PACKAGE DE MANEIRA GENÉRICA
		
	//Se for passado o caminho como argumento, o TODO acima fica resolvido, vejam lá se faz sentido
	public static void main(String[] args) {
		final File EXTRACTION_FOLDER = new File(args[0]);
		
		fjava_path_list = getAllJavaFiles(EXTRACTION_FOLDER, fjava_path_list);
		total_number_classes = fjava_path_list.size();
		total_number_methods = 0;
		total_number_lines = 0;
		
		for(String path: fjava_path_list) {

			try {
			
				LineCounter ln = new LineCounter(path);
				final int num_lines = ln.getLinesCount();
				final List<String> method_lines_list = ln.getMethodList();
			
				final int num_methods = NOM_class.get_NOM(path);
				
				System.out.println("---------------//--------------");
				System.out.println("number of lines: " + num_lines);
				System.out.println("number of methods: " + num_methods + "\n");
				
				total_number_methods += num_methods;
				total_number_lines += num_lines;
				
				for (String string : method_lines_list) {
					System.out.println(string);
				}
				
//				for (Method m : NOM_class.getMethods(path))
//					System.out.println("METHODDD:" + m.toString());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\nTotal number of classes: " + total_number_classes);
		System.out.println("Total number of lines: " + total_number_lines);
		System.out.println("Total number of methods: " + total_number_methods);
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

				if(isJavaFile(file_path)) {
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

	private static boolean isJavaFile(String path) {
		return path.endsWith(".java");
	}
}
