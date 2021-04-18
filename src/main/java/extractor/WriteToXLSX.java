package extractor;
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

}