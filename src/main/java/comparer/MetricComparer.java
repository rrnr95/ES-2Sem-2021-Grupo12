package comparer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MetricComparer {
	private String smellsDoc;
	private String baselineDoc;
	private List<MethodComparisson> methodList;
	
	public MetricComparer(String smellsDoc, String baselineDoc) {
		this.smellsDoc = smellsDoc;
		this.baselineDoc = baselineDoc;
		this.methodList = new ArrayList<>();
	}
	
	public List<MethodComparisson> getMethodList() {
		return methodList;
	}
	
	public void formPairs() {
		File smells = new File(smellsDoc);
		File baseline = new File(baselineDoc);
		if (smells != null && baseline != null) {
			try {
				FileInputStream excelFIS_smells = new FileInputStream(smells);
				BufferedInputStream excelBIS_smells = new BufferedInputStream(excelFIS_smells);
				XSSFWorkbook excelImportToJTable_smells = new XSSFWorkbook(excelBIS_smells);
	            XSSFSheet excelSheet_smells = excelImportToJTable_smells.getSheetAt(0);
	            
	            FileInputStream excelFIS_baseline = new FileInputStream(baseline);
	            BufferedInputStream excelBIS_baseline = new BufferedInputStream(excelFIS_baseline);
	            XSSFWorkbook excelImportToJTable_baseline = new XSSFWorkbook(excelBIS_baseline);
	            XSSFSheet excelSheet_baseline = excelImportToJTable_baseline.getSheetAt(0);
	            
				for (int row = 1; row <= excelSheet_smells.getLastRowNum(); row++) {
					//System.out.println("formPairs: dentro do for");
					XSSFRow excelRow = excelSheet_smells.getRow(row);
					
					MethodComparisson mc = createMethodComparisson(excelRow, excelSheet_baseline);
					
					methodList.add(mc);
				}
			}
			catch (IOException e) {
				System.err.println("Erro!");
			}
		}
	}
	
	private MethodComparisson createMethodComparisson(XSSFRow excelRow, XSSFSheet excelSheet_baseline) {
		//System.out.println("createMethodComparisson: inicio");
		
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
		
		MethodComparisson mc = new MethodComparisson(id, pck, cls, meth, NOM_class_int, LOC_class_int, WMC_class_int, is_god, LOC_method_int, CYCLO_method_int, is_long);
		
		
		for (int row = 1; row <= excelSheet_baseline.getLastRowNum(); row++) {
			//System.out.println("createMethodComparisson: entrou no for");
			
			XSSFRow excelRow_baseline = excelSheet_baseline.getRow(row);
			String meth_baseline = excelRow_baseline.getCell(3).toString();
			
			//System.out.println("createMethodComparisson: meth_baseline = " + meth_baseline);
			//System.out.println("createMethodComparisson: meth = " + meth);
			
			String newMethName = formatedMethodName(meth_baseline);
			
			if (newMethName.equals(meth)) {
				//System.out.println("createMethodComparisson: entrou no if");
				populateMethodComparisson(mc, excelRow_baseline);
				break;
			}
		}
		
		return mc;
	}
	
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
	
	private boolean cellConverter (String b) {
		boolean res = false;
		if (b.toLowerCase().equals("verdadeiro") || b.toLowerCase().equals("true")) {
			res = true;
		}
		return res;
	}
	
	private String formatedMethodName(String originalName) {
		return originalName.split( "\\(" )[0];
	}
	
	public static void main (String[] args) {
		String path1 = "C:\\Users\\mrfur\\eclipse-workspace\\jasml_0.10 (1).zip_expanded\\smells.xlsx";
		String path2 = "D:\\Documents\\LEI\\3º ano\\2º semestre\\Engenharia de Software\\projeto\\Code_Smells.xlsx";
		
		MetricComparer mc = new MetricComparer(path1, path2);
		mc.formPairs();
		ArrayList<MethodComparisson> arr = (ArrayList<MethodComparisson>) mc.getMethodList();
		
		for (MethodComparisson comp : arr) {
			if(comp.getMeth().equals("")) {
			System.out.println(comp.getMethodID());
			System.out.println(comp.getPck());
			System.out.println(comp.getCls());
			System.out.println(comp.getMeth());
			
			System.out.println(comp.getNOM_class_smell());
			System.out.println(comp.getNOM_class_baseline());
			System.out.println(comp.getLOC_class_smell());
			System.out.println(comp.getLOC_class_baseline());
			System.out.println(comp.getWMC_class_smell());
			System.out.println(comp.getWMC_class_baseline());
			System.out.println(comp.isIs_God_Class_smell());
			System.out.println(comp.isIs_God_Class_baseline());
			System.out.println(comp.getLOC_method_smell());
			System.out.println(comp.getLOC_method_baseline());
			System.out.println(comp.getCYCLO_method_smell());
			System.out.println(comp.getCYCLO_method_baseline());
			System.out.println(comp.isIs_Long_Method_smell());
			System.out.println(comp.isIs_Long_Method_baseline());
			
			System.out.println("--------------------------------------------------");

			}
			
			
		}
		
		//System.out.println(arr.size());
	}
	
}
