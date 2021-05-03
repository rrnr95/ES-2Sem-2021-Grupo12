package backend;

public class ConfusionMatrix {
	
	
	/*           		 Matriz de Confusão
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

	public ConfusionMatrix() {
		matrix = new int [2][2];
		matrix[0][0]=0;
		matrix[0][1]=0;
		matrix[1][0]=0;
		matrix[1][1]=0;
	}
	
	public int getVP() {
		return matrix[0][0];
	}
	
	public void setVP(int value) {
		matrix[0][0] = value;
	}
	
	
	
	public int getFN() {
		return matrix[0][1];
	}
	
	public void setFN(int value) {
		matrix[0][1] = value;
	}
	
	
	
	public int getFP() {
		return matrix[1][0];
	}
	
	public void setFP(int value) {
		matrix[1][0] = value;
	}
	
	
	
	public int getVN() {
		return matrix[1][1];
	}
	
	public void setVN(int value) {
		matrix[1][1]=value;
	}


	
	
	
}
