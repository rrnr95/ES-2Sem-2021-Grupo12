
public final class NOM_class {
	
	public static int get_NOM(String path) throws ClassNotFoundException {
		
		String cls_name = getClassName(path);	
		Class<?> cls = Class.forName("imported_project_test." + cls_name);
	
		return cls.getDeclaredMethods().length;
	}
	
	
	// #1 Recebe o path "*\imported_project_test\NOME_DA_CLASSE.java" e manipula-o para
	// #2 "\NOME_DA_CLASSE.java", apagando "\" e 
	// #3 retorna "NOME_DA_CLASSE"
	private static String getClassName(String path) {
		String[] tmp_array = path.split("imported_project_test");
		String tmp_str = tmp_array[1].replace("\\", "");
		
		return tmp_str.split("[.]")[0];
	}
	
}
