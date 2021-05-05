package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import backend.MethodUtils;
import backend.CycloMethod;
import backend.Method;

public class CycloMethodTest {
	
	private final String FILE = System.getProperty("user.dir")+"\\imported_project\\not_source\\src\\pckg\\TestForCycloMethod.java";
	private List<Method> methods;
	private List<Integer> methodsCycloValue;
	List<Integer> expected_methodsCycloValue;
	private int valueWMC;
	
	@Before
	public void setUp() throws Exception {
		methods = MethodUtils.getMethodsFromFile(FILE);
		methodsCycloValue = CycloMethod.allMethodsCycloValue(methods);
		
		expected_methodsCycloValue = new ArrayList<Integer>();
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(1);
		
		valueWMC = CycloMethod.wmcCalculator(methods);
		
	}
	
	@Test
	public void test() {	
		
		assertEquals(expected_methodsCycloValue, methodsCycloValue);
		assertEquals(7, valueWMC);
	}
	
	@Test
	public void testWmcCalculator() {
//		int valueWMC = CycloMethod.wmcCalculator(methodsCycloValue);
//		assertEquals(7, valueWMC);
	}

}