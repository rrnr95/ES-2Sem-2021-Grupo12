package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.NumberOfClassesPerFile;

public class NumberOfClassesPerFileTest {
	
	private final String FILE = "C:\\Users\\renat\\Documents\\GitHub_repository\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg2\\HelloWorld.java";
	private final NumberOfClassesPerFile noc= new NumberOfClassesPerFile(FILE);
	
	@Test
	public void testNumberOfClassesPerFile() {
		assertEquals(NumberOfClassesPerFile.class , noc.getClass());
	}
	
	@Test
	public void testGetClasses(){
		
		List<String> classes = noc.getClasses();		
		List<String> expected_classes = new ArrayList<String>();
		
		expected_classes.add("HelloWorld");
		expected_classes.add("Wtv");
		
		assertEquals(expected_classes, classes);
	}

}
