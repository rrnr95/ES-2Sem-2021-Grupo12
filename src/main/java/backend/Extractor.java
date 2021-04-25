//package backend;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//
//
//public final class Extractor {
//	private static ArrayList<String> fjava_path_list = new ArrayList<String>();
//	private static int total_number_classes;
//	private static int total_number_methods;
//	private static int total_number_lines;
//	
//	//Alterar para o caminho do package "imported_project_test"
//	//private final static File EXTRACTION_FOLDER = new File("C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\src\\main\\java\\imported_project_test");
//	//TODO: ARRANJAR MANEIRA DE FORNECER O CAMINHO PARA O PACKAGE DE MANEIRA GENÉRICA
//		
//	//Se for passado o caminho como argumento, o TODO acima fica resolvido, vejam lá se faz sentido
//	public static void main(String[] args) {
//		final File EXTRACTION_FOLDER = new File(args[0]);
//		
//		fjava_path_list = getAllJavaFiles(EXTRACTION_FOLDER, fjava_path_list);
//		total_number_classes = fjava_path_list.size();
//		total_number_methods = 0;
//		total_number_lines = 0;
//		
//		for(String path: fjava_path_list) {
//				
////				MethodUtils mu = new MethodUtils(path);
////				final int num_methods = mu.getMethodName().size();
//				MethodUtils.getMethodsFromFile(path);
//				final int num_methods = MethodUtils.getNumberOfMethods();
//				
////				LineCounter ln = new LineCounter(path);
//				LineCounter.countLines(path);
//				final int num_lines = LineCounter.getTotalLinesCount();
//				final Map<String, Integer> method_lines_list = LineCounter.getMethodNameLines();
//				
//				System.out.println("---------------//--------------");
//				System.out.println("Linhas do ficheiro: " + num_lines);
//				System.out.println("number of methods: " + num_methods + "\n");
//				
//				
//				
//				total_number_methods += num_methods;
//				total_number_lines += num_lines;
//				
//				for (Map.Entry<String, Integer> m : method_lines_list.entrySet()) {
//					System.out.println(m.getKey() + ": " + m.getValue());
//				}
//				
////				for (Method m : NOM_class.getMethods(path))
////					System.out.println("METHODDD:" + m.toString());
//		}
//		
//		System.out.println("\nTotal number of classes: " + total_number_classes);
//		System.out.println("Total number of lines: " + total_number_lines);
//		System.out.println("Total number of methods: " + total_number_methods);
//	}
//
//	//Vai buscar todos os ficheiros ".java" dentro de uma pasta,
//	//caso encontre subpastas, chama o método recursivamente nessas subpastas.
//	public static ArrayList<String> getAllJavaFiles(File root, ArrayList<String> target_list) {
//
//		File[] file_list = root.listFiles();
//		if(file_list == null)
//			throw new IllegalStateException("No Files to grab"); 
//
//		for (File file : file_list) {
//
//			if (file.isDirectory())
//				getAllJavaFiles(file, target_list); //chamada recursiva
//
//			else {
//				final String file_path = file.getAbsolutePath();
//
//				
//				if(isJavaFile(file_path)) {
//					
//					
//					target_list.add(file_path);
//					System.out.println("Added ->  " + file_path);
//					int numberOfClasses = NumberOfClassesPerFile.getClassesFromFile(file_path).size();
//					System.out.println( "Filename: " + NumberOfClassesPerFile.getFileName(file_path) );
//					System.out.println( "Number of classes: " + numberOfClasses);
//				}
//				else
//					System.out.println("Not added ->  " + file_path);
//			}
//		}
//
//		if(target_list.isEmpty()) 
//			throw new IllegalStateException("File list empty");
//
//		return target_list;
//	}
//
//	private static boolean isJavaFile(String path) {
//		return path.endsWith(".java");
//	}
//}