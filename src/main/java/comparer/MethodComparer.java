package comparer;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 		Class used to create a pair of the equivalent methods
 * 		@author ES-2Sem-2021-Grupo12
 */
public class MethodComparer {
	
	/**
	 * 				Given an excel row and an excel sheet, it creates a pair of two equivalent excel rows. 
	 *  @param		excelRow
	 *  			an excel row
	 *  @param		excelSheet
	 *  			the baseline excel sheet
	 *  @return		a MethodComparisson object, with the pair of equivalent excelRows
	 *  
	 */
	public MethodComparisson createMethodComparisson(XSSFRow excelRow, XSSFSheet excelSheet_baseline) {
		String id = excelRow.getCell(0).toString();
		String pck = excelRow.getCell(1).toString();
		String cls = excelRow.getCell(2).toString();
		String meth = excelRow.getCell(4).toString();
		String NOM_class = excelRow.getCell(5).toString();
		int NOM_class_int = Integer.parseInt(NOM_class);
		String LOC_class = excelRow.getCell(6).toString();
		int LOC_class_int = Integer.parseInt(LOC_class);
		String WMC_class = excelRow.getCell(7).toString();
		int WMC_class_int = Integer.parseInt(WMC_class);
		String is_God_class = excelRow.getCell(8).toString();
		boolean is_god = cellConverter(is_God_class);
		String LOC_method = excelRow.getCell(9).toString();
		int LOC_method_int = Integer.parseInt(LOC_method);
		String CYCLO_method = excelRow.getCell(10).toString();
		int CYCLO_method_int = Integer.parseInt(CYCLO_method);
		String is_long_method = excelRow.getCell(11).toString();
		boolean is_long = cellConverter(is_long_method);
		MethodComparisson mc = new MethodComparisson(id, pck, cls, meth, NOM_class_int, LOC_class_int, WMC_class_int,
				is_god, LOC_method_int, CYCLO_method_int, is_long);
		for (int row = 1; row <= excelSheet_baseline.getLastRowNum(); row++) {
			XSSFRow excelRow_baseline = excelSheet_baseline.getRow(row);
			String pck_baseline = excelRow_baseline.getCell(1).toString();
			String cls_baseline = excelRow_baseline.getCell(2).toString();
			String meth_baseline = excelRow_baseline.getCell(3).toString();
			String newPck_baseline = formatPackageName(pck_baseline);
			if (meth_baseline.equals(meth) && cls_baseline.equals(cls) && newPck_baseline.equals(pck)) {
				populateMethodComparisson(mc, excelRow_baseline);
				break;
			}
		}
		return mc;
	}

	/**
	 * 			given a String, it returns the boolean equivalent
	 * @param	String
	 * @return	true or false
	 */
	private boolean cellConverter(String b) {
		boolean res = false;
		if (b.toLowerCase().equals("verdadeiro") || b.toLowerCase().equals("true")) {
			res = true;
		}
		return res;
	}

	/**
	 * 			given a package name, it returns the correct format as a string
	 * @param	pack_baseline
	 * 			a string with the package name, as retrieved from the excelRow
	 * @return	string with the formated package name
	 */
	private String formatPackageName(String pck_baseline) {
		String[] arr = pck_baseline.split("\\.");
		return arr[arr.length - 1];
	}

	/**
	 *			given an excel row, it extracts each entry and populates the MethodComparisson passed
	 *@param	MethodComparisson object
	 *@param	excel row 
	 */
	private void populateMethodComparisson(MethodComparisson mc, XSSFRow excelRow_baseline) {
		if (excelRow_baseline.getCell(4) != null) {
			int nom = (int) Double.parseDouble(excelRow_baseline.getCell(4).toString());
			mc.setNOM_class_baseline(nom);
		}
		if (excelRow_baseline.getCell(5) != null) {
			int loc = (int) Double.parseDouble(excelRow_baseline.getCell(5).toString());
			mc.setLOC_class_baseline(loc);
		}
		if (excelRow_baseline.getCell(6) != null) {
			int wmc = (int) Double.parseDouble(excelRow_baseline.getCell(6).toString());
			mc.setWMC_class_baseline(wmc);
		}
		if (excelRow_baseline.getCell(7) != null) {
			boolean is_god = cellConverter(excelRow_baseline.getCell(7).toString());
			mc.setIs_God_Class_baseline(is_god);
		}
		if (excelRow_baseline.getCell(8) != null) {
			int loc_method = (int) Double.parseDouble(excelRow_baseline.getCell(8).toString());
			mc.setLOC_method_baseline(loc_method);
		}
		if (excelRow_baseline.getCell(9) != null) {
			int cyclo = (int) Double.parseDouble(excelRow_baseline.getCell(9).toString());
			mc.setCYCLO_method_baseline(cyclo);
		}
		if (excelRow_baseline.getCell(10) != null) {
			boolean is_long = cellConverter(excelRow_baseline.getCell(10).toString());
			mc.setIs_Long_Method_baseline(is_long);
		}
	}
}