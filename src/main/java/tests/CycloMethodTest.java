package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.MethodUtils;
import backend.CycloMethod;

public class CycloMethodTest {
	
	private static final String FILE = "C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg\\TestForCycloMethod.java";
	private static final List<String> methodCode = new MethodUtils(FILE).getMethodCode();
	
	@Test

	public void test() {	
		
		List<Integer> methodsCycloValue = CycloMethod.allMethodsCycloValue(methodCode);
		List<Integer> expected_methodsCycloValue = new ArrayList<Integer>();
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(1);
		
		assertEquals(expected_methodsCycloValue, methodsCycloValue);
		
		int valueWMC = CycloMethod.wmcCalculator(methodsCycloValue);
		assertEquals(7, valueWMC);
		 
	}

}