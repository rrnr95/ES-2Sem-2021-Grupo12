package extractor;

import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 		Class used to create and write to a .xlsx file 
 * 		@author ES-2Sem-2021-Grupo12
 */

public class WriteToXLSX {
	
	static String[] columns = {	"Method Id", 
								"package",
								"class", 
								"inner classes", 
								"method", 
								"NOM_class", 
								"LOC_class", 
								"WMC_class", 
								"is_God_class", 
								"LOC_method", 
								"CYCLO_method", 
								"is_long_method"};
	static int numOfCol = columns.length;

	
	/**
	 * 			Constructor
	 * @param 	path
	 * 			Location(String) where the file is stored
	 * @param 	rec
	 * 		 	Metrics list
	 */
	static public void exportToExcel(String path, RecursoPartilhado rec) {
		Workbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet("code_smells");
		createHeader(wb,sh);
		populate(rec,sh);
		resizeCell(sh);
		write(path,wb);
	}
	
	
	/**
	 * 			Creates an excel header
	 * 	@param 	wb
	 * 			Workbook that contains the sheet 
	 * 	@param	sh
	 * 			Sheet where the header is to be created
	 */
	static private void createHeader( Workbook wb, Sheet sh ) {
		
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short)12);
		
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(headerFont);
		
		Row headerRow = sh.createRow(0);
		for (int i = 0; i < numOfCol; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerStyle);
		}
	}
	
	/**
	 * 			Populates the excel file
	 * 			It uses a MethodStats as a list for each row, iterates over it and writes each value to a cell
	 * 	@param	rec
	 * 			Shared Resource with the MethodStats list
	 * 	@param 	sh
	 * 			Sheet where the list information is to be dumped
	 */
	static private void populate(RecursoPartilhado rec, Sheet sh) {
		int index = 1;
		int l = 1;
		for(MethodStats stat : rec.getMethodStats()) {
			Row row = sh.createRow(l++);
			int c = 0;
			for (String s : stat.getMethodAsList()) {
				Cell cell = row.createCell(c++);
				if (cell.getColumnIndex() == 0) {
					cell.setCellValue(String.valueOf(index));
					index++;
				}
				else {
					cell.setCellValue(s);
				}
			}
		}
	}
	
	
	/**
	 * 			Resizes all cells
	 * 	@param	sh
	 * 			Sheet in which the resizing is to be applied
	 */
	static private void resizeCell(Sheet sh) {
		for (int i = 0; i < numOfCol; i++) {
			sh.autoSizeColumn(i);
		}
	}
	
	/**
	 * 			Writes the XSSFWorkbook to file
	 * 	@param	path
	 *			Location for the file
	 * 	@param	wb
	 * 			Workbook to be copied
	 */
	static private void write(String path, Workbook wb) {
		try {			
			FileOutputStream out = new FileOutputStream(path);
			wb.write(out);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}