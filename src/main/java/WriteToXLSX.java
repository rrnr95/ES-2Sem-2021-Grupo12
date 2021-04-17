import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToXLSX {
	private Sheet sh;
	private Workbook wb;
	private String path;
	private RecursoPartilhado rec;
	private int numOfCol;
	
	/**
	 * 			Constructor
	 * @param 	path
	 * 			
	 * @param 	rec
	 */
	public WriteToXLSX(String path, RecursoPartilhado rec) {
		this.path = path;
		this.wb = new XSSFWorkbook();
		this.sh = wb.createSheet("code_smells");
		this.rec = rec;
	}
	
	public void init() {
		createHeader();
		populate();
		resizeCell();
		write();
	}
	
	/**
	 * 	Create header excel header
	 */
	private void createHeader() {
		String[] columns = {"Method Id", "package", "class", "inner classes", "method", "NOM_class", "LOC_class", "WMC_class", "is_God_class", "LOC_method", "CYCLO_method", "is_long_method"};
		numOfCol = columns.length;
		
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
	 * Populate the excel file
	 * It uses a MethodStats as a list for each row, iterates over it and write each value to a cell
	 */
	private void populate() {
		int l = 1;
		for(MethodStats stat : rec.getMethodStats()) {
			Row row = sh.createRow(l++);
			int c = 0;
			for (String s : stat.getMethodAsList()) {
				Cell cell = row.createCell(c++);
				cell.setCellValue(s);
			}
		}
	}
	
	/**
	 * Resize all cells
	 */
	private void resizeCell() {
		for (int i = 0; i < numOfCol; i++) {
			sh.autoSizeColumn(i);
		}
	}
	
	/**
	 * Write the XSSFWorkbook to file
	 */
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

}