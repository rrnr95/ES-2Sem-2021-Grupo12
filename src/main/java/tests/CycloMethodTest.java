package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.MethodUtils;
import backend.CycloMethod;

public class CycloMethodTest {
	
	private final String FILE = "C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg\\TestForCycloMethod.java";
	//TODO refactor a MethodUtils
//	private final List<String> methodCode = MethodUtils.getMethods();
//	private List<Integer> methodsCycloValue = CycloMethod.allMethodsCycloValue(methodCode);
	
	@Test
	public void testAllMethodsCycloValue() {	
		
		List<Integer> expected_methodsCycloValue = new ArrayList<Integer>();
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(1);
		
//		assertEquals(expected_methodsCycloValue, methodsCycloValue);
	}
	
	@Test
	public void testWmcCalculator() {
//		int valueWMC = CycloMethod.wmcCalculator(methodsCycloValue);
//		assertEquals(7, valueWMC);
	}

}