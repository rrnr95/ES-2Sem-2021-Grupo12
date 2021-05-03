package comparer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import backend.ConfusionMatrix;

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
			String pck_baseline = excelRow_baseline.getCell(1).toString();
			String cls_baseline = excelRow_baseline.getCell(2).toString();
			String meth_baseline = excelRow_baseline.getCell(3).toString();
			String newPck_baseline = formatPackageName(pck_baseline);
			//System.out.println("createMethodComparisson: meth_baseline = " + meth_baseline);
			//System.out.println("createMethodComparisson: meth = " + meth);
			
			
			
			if (meth_baseline.equals(meth) && cls_baseline.equals(cls) && newPck_baseline.equals(pck)) {
				//System.out.println("createMethodComparisson: entrou no if");
				System.out.println("METH: " + meth);
				System.out.println("METH_BASELINE: " + meth_baseline);
				System.out.println("--------------------------------------");
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
	
	private String formatPackageName (String pck_baseline) {
		String[] arr = pck_baseline.split("\\.");
		return arr[arr.length - 1];
	}
	
//	private String formatedMethodName(String originalName) {
//		return originalName.split( "\\(" )[0];
//	}
	
	
	
	public HashMap<String, String> getGodClassDetectionQuality(){
		
		if(this.getMethodList().isEmpty())
			throw new IllegalStateException("Devem ser formados os pares antes de chamar a fun��o.");
		
		HashMap<String, String> classResults = new HashMap<String, String>();
		
		for(MethodComparisson mc : getMethodList()) {
			
			if(!classResults.containsKey(mc.getCls())) {
				classResults.put(mc.getCls(), evaluateMetric(mc.isIs_God_Class_smell(), mc.isIs_God_Class_baseline()));
			}
			
		}
		
		return classResults;
	}
	
	
	public HashMap<String, String> getLongMethodDetectionQuality() {

		if (this.getMethodList().isEmpty())
			throw new IllegalStateException("Devem ser formados os pares antes de chamar a fun��o.");

		HashMap<String, String> methodResults = new HashMap<String, String>();

		for (MethodComparisson mc : getMethodList()) {
			if(!mc.getMeth().equals(""))
				methodResults.put(mc.getCls()+"."+mc.getMeth(), evaluateMetric(mc.isIs_Long_Method_smell(), mc.isIs_Long_Method_baseline()));
			
		}

		return methodResults;
	}
	
	private String evaluateMetric(boolean predicted, boolean actual) {
		
		if(predicted) {
			if(actual) {
				return "VP";
			}
			return "FP";
		}else {
			if(actual) {
				return "FN";
			}
			return "VN";
		}
		
	}
	
	/*           		 Matriz de Confus�o
	 * 
	 * 						Predicted
	 * 					  T     |    F	
	 * 				    _________________
	 * 		        T  |  TP    |   FN   |  
	 *     Actual      |________|________|
	 *     			F  |  FP    |  	TN   |
	 *     			   |________|________|           
	 *     
	 */               
	
	public ConfusionMatrix getGodClassConfMatrixValues(){
		
		ConfusionMatrix matrix = new ConfusionMatrix();
		
		
		for(Map.Entry me : getGodClassDetectionQuality().entrySet()) {
			
			if(me.getValue().equals("VP")) {
				matrix.setVP(matrix.getVP()+1);
			}
			if(me.getValue().equals("FN")) {
				matrix.setFN(matrix.getFN()+1);
			}
			if(me.getValue().equals("FP")) {
				matrix.setFP(matrix.getFP()+1);
			}
			if(me.getValue().equals("VN")) {
				matrix.setVN(matrix.getVN()+1);
			}
		}
		
		return matrix;		
	}
	
	
	public ConfusionMatrix getLongMethodConfMatrixValues(){
		
		ConfusionMatrix matrix = new ConfusionMatrix();
		
		
		for(Map.Entry me : getLongMethodDetectionQuality().entrySet()) {
			
			if(me.getValue().equals("VP")) {
				matrix.setVP(matrix.getVP()+1);
			}
			if(me.getValue().equals("FN")) {
				matrix.setFN(matrix.getFN()+1);
			}
			if(me.getValue().equals("FP")) {
				matrix.setFP(matrix.getFP()+1);
			}
			if(me.getValue().equals("VN")) {
				matrix.setVN(matrix.getVN()+1);
			}
		}
		
		return matrix;		
	}
	
	
	public static void main (String[] args) {
		String path1 = "D:\\Programa��o\\EngenhariaSoftware\\jasml_0.10.zip_expanded\\smells.xlsx";
		String path2 = "C:\\Users\\Diogo\\git\\New folder\\ES-2Sem-2021-Grupo12\\Code_Smells.xlsx";
		
		MetricComparer mc = new MetricComparer(path1, path2);
		mc.formPairs();
		//ArrayList<MethodComparisson> arr = (ArrayList<MethodComparisson>) mc.getMethodList();
		
//		for(Map.Entry me : mc.getLongMethodDetectionQuality().entrySet()) {
//			System.out.println(me.getKey()+": "+me.getValue());
//		}
		
		System.out.println(mc.getGodClassConfMatrixValues().getVP()+" | " + mc.getGodClassConfMatrixValues().getFN()+"\n"+
				mc.getGodClassConfMatrixValues().getFP()+" | " + mc.getGodClassConfMatrixValues().getVN());
		System.out.println(mc.getLongMethodConfMatrixValues().getVP()+" | " + mc.getLongMethodConfMatrixValues().getFN()+"\n"+
				mc.getLongMethodConfMatrixValues().getFP()+" | " + mc.getLongMethodConfMatrixValues().getVN());
////		
//		for (MethodComparisson comp : arr) {
//			//if(comp.getMeth().equals("")) {
//			//System.out.println(comp.getMethodID());
//			System.out.println(comp.getPck());
//			System.out.println(comp.getCls());
//			System.out.println(comp.getMeth());
//			
////			System.out.println(comp.getNOM_class_smell());
////			System.out.println(comp.getNOM_class_baseline());
////			System.out.println(comp.getLOC_class_smell());
////			System.out.println(comp.getLOC_class_baseline());
////			System.out.println(comp.getWMC_class_smell());
////			System.out.println(comp.getWMC_class_baseline());
//			System.out.println(comp.isIs_God_Class_smell());
//			System.out.println(comp.isIs_God_Class_baseline());
////			System.out.println(comp.getLOC_method_smell());
////			System.out.println(comp.getLOC_method_baseline());
////			System.out.println(comp.getCYCLO_method_smell());
////			System.out.println(comp.getCYCLO_method_baseline());
//			System.out.println(comp.isIs_Long_Method_smell());
//			System.out.println(comp.isIs_Long_Method_baseline());
//			
//			System.out.println("--------------------------------------------------");
//
//			//}
//			
//			
//		}
////		
////		System.out.println(arr.size());
	}
	
}
