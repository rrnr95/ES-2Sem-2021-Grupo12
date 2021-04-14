package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.LineCounter;

public class LineCounterTest {

	private static final String FILE = "C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg\\TestForLineCounter.java";
	private LineCounter ln = new LineCounter(FILE);
	
	@Test
	public void test() {
		assertEquals(LineCounter.class , ln.getClass());
		
		int lines = ln.getLinesCount();
		assertEquals(16, lines);
		
		//List<String> method_list = ln.getMethodList();
		List<String> expected_method_list = new ArrayList<String>();
		
		expected_method_list.add("method1: 5");
		expected_method_list.add("method2: 6");
		expected_method_list.add("main: 3");
		
		//assertEquals(expected_method_list, method_list);
		
		
	}

}
