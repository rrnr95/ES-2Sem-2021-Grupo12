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


/**
 * Class used to compare metrics of a project, using two different assessment tools
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class MetricComparer {
	private MethodComparer methodComparer = new MethodComparer();
	private String smellsDoc;
	private String baselineDoc;
	private List<MethodComparisson> methodList;
	
	
	/**
	 * Class constructor
	 */
	public MetricComparer(String smellsDoc, String baselineDoc) {
		this.smellsDoc = smellsDoc;
		this.baselineDoc = baselineDoc;
		this.methodList = new ArrayList<>();
	}
	
	/**
	 * @return	list with MethodComparisson objects
	 */
	public List<MethodComparisson> getMethodList() {
		return methodList;
	}
	
	/**
	 * a method used to form method pairs, based on both given files
	 * these pairs are inserted in a list
	 */
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
					
					MethodComparisson mc = methodComparer.createMethodComparisson(excelRow, excelSheet_baseline);
					
					methodList.add(mc);
				}
			}
			catch (IOException e) {
				System.err.println("Erro!");
			}
		}
	}
	
	/**
	 * using the list containing the MethodComparison, this method returns a Map containing a pair of isGodClass metrics, obtained using different files
	 * @return	Map with metric pairs (comparisson per class)
	 */
	public HashMap<String, String> getGodClassDetectionQuality(){
		
		if(this.getMethodList().isEmpty())
			throw new IllegalStateException("Devem ser formados os pares antes de chamar a função.");
		
		HashMap<String, String> classResults = new HashMap<String, String>();
		
		for(MethodComparisson mc : getMethodList()) {
			
			if(!classResults.containsKey(mc.getCls())) {
				classResults.put(mc.getCls(), evaluateMetric(mc.isIs_God_Class_smell(), mc.isIs_God_Class_baseline()));
			}
			
		}
		
		return classResults;
	}
	
	/**
	 * using the list containing the MethodComparison, this method returns a Map containing a pair of isLongMethod metrics, obtained using different files
	 * @return	Map with metric pairs (comparisson per method)
	 */
	public HashMap<String, String> getLongMethodDetectionQuality() {

		if (this.getMethodList().isEmpty())
			throw new IllegalStateException("Devem ser formados os pares antes de chamar a função.");

		HashMap<String, String> methodResults = new HashMap<String, String>();

		for (MethodComparisson mc : getMethodList()) {
			if(!mc.getMeth().equals(""))
				methodResults.put(mc.getCls()+"."+mc.getMeth(), evaluateMetric(mc.isIs_Long_Method_smell(), mc.isIs_Long_Method_baseline()));
			
		}

		return methodResults;
	}
	
	/**
	 * A method that returns the confusion matrix's logic
	 * @param	predicted value (from the baseline project)
	 * 			true or false
	 * @param	obtained value
	 * 			true or false
	 * @return	String containing the comparisson result
	 */
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
	
	/*           		 Matriz de Confusão
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
	
	/**
	 * a method that returns a confusion matrix, based on the God Class assessment
	 * @return	confusion matrix with the comparisson result
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
	
	/**
	 * a method that returns a confusion matrix, based on the long method assessment
	 * @return	confusion matrix with the comparisson result
	 */
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
}
