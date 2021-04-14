package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import backend.LineCounter;

public class LineCounterTest {

	private final String FILE = "C:\\Users\\Utilizador\\eclipse-workspace\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg\\TestForLineCounter.java";
	private LineCounter ln = new LineCounter(FILE);
	
	@Test
	public void testLineCounter() {
		assertEquals(LineCounter.class , ln.getClass());
	}
	
	@Test
	public void testGetLinesCount() {
		int lines = ln.getLinesCount();
		assertEquals(16, lines);
	}	
	
	@Test
	public void testGetMethodNameLines() {
		Map<String, Integer> method_list = ln.getMethodNameLines();
		Map<String, Integer> expected_method_list = new HashMap<String, Integer>();
		
		expected_method_list.put("method1", 5);
		expected_method_list.put("method2", 6);
		expected_method_list.put("main", 3);
		
		assertEquals(expected_method_list, method_list);
	}

}
