package backend;
/**
 * 			Class that represents a confusion matrix
 * @author 	ES-2Sem-2021-Grupo12
 *
 */
public class ConfusionMatrix {
	
	
	/*           		 Confusion Matrix
	 * 
	 * 						Predicted
	 * 					  T     |    F	
	 * 				    _________________
	 * 		        T  |  VP    |   FN   |  
	 *     Actual      |________|________|
	 *     			F  |  FP    |  	VN   |
	 *     			   |________|________|           
	 *     
	 *     
	 *     
	 */   
	private int[][] matrix;
	
	/**
	 * Constructor
	 */
	public ConfusionMatrix() {
		matrix = new int [2][2];
		matrix[0][0]=0;
		matrix[0][1]=0;
		matrix[1][0]=0;
		matrix[1][1]=0;
	}
	
	/**
	 * 			Getter of True Positive value
	 * @return 	True Positive value
	 */
	public int getVP() {
		return matrix[0][0];
	}
	
	/**
	 * 			Setter of True Positive value
	 * @param 	True Positive value
	 */
	public void setVP(int value) {
		matrix[0][0] = value;
	}
	
	/**
	 * 			Getter of False Negative value
	 * @return	False Negative value
	 */
	public int getFN() {
		return matrix[0][1];
	}
	
	/**
	 * 			Setter of False Negative value
	 * @param 	False Negative value	
	 */
	public void setFN(int value) {
		matrix[0][1] = value;
	}
	
	
	/**
	 * 			Getter of False Positive value
	 * @return	False Positive value
	 */
	public int getFP() {
		return matrix[1][0];
	}
	
	/**
	 * 			Setter of False Positive value
	 * @param 	False Positive value
	 */
	public void setFP(int value) {
		matrix[1][0] = value;
	}
	
	/**
	 * 			Getter of True Negative value
	 * @return	True Negative value
	 */
	public int getVN() {
		return matrix[1][1];
	}
	
	/**
	 * 			Setter of True Negative value
	 * @param 	True Negative value
	 */
	public void setVN(int value) {
		matrix[1][1]=value;
	}


	
	
	
}
