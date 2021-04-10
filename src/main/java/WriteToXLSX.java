import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToXLSX {
	private Sheet sh;
	private Workbook wb;
	private String path;
	private Object obj;
	private int numOfCol;
	
	public WriteToXLSX(String path, Object obj) {
		this.path = path;
		this.wb = new XSSFWorkbook();
		this.sh = wb.createSheet("code_smells");
	}
	
	public void init() {
		createHeader();
		populate();
		resizeCell();
		write();
	}
	
	private void createHeader() {
		String[] columns = {"Method Id", "package", "method", "NOM_class", "LOC_class", "WMC_class", "is_God_class", "LOC_method", "CYCLO_method", "is_long_method"};
		numOfCol = columns.length;
		
		Font headerFont = wb.createFont();
		headerFont.setBold(true);
		
		Row headerRow = sh.createRow(0);
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
		}
	}
	
	private void populate() {
		
	}
	
	private void resizeCell() {
		for (int i = 0; i < numOfCol; i++) {
			sh.autoSizeColumn(i);
		}
	}
	
	private void write() {
		try {			
			FileOutputStream out = new FileOutputStream(path);
			wb.write(out);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WriteToXLSX w = new WriteToXLSX("C:\\testelol\\teste.xlsx", null);
		w.init();
	}

}
