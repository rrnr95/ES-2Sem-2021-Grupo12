package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import backend.FindPackages;
import backend.NumberOfClassesPerFile;
import comparer.MethodComparisson;
import comparer.MetricComparer;
import extractor.CodeSmells;
import extractor.MethodStats;
import extractor.SharedResource;
import gui.Rule;


	//TODO Resolver a questao dos paths para correr em todas as máquinas?
	//TODO CycloMethods test não funciona???

public class ProjectTests {

	//CodeSmells test
	private final String PATH = System.getProperty("user.dir")+"\\TestProject";
	private final Rule rule = new Rule("ruleTest", 1, 20, 1, 20, 1, 20, false, 1, 20, 1, 20, true); 
	SharedResource SR;
	SharedResource expected_SR;
	
	//FindPackages test
	
	private static final String PATH1 = "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship";
	HashMap<String, String> packs;
	HashMap<String, String> expected_packs;
	
	//MethodComparer test
	
	MetricComparer mc_test;
	List<MethodComparisson> testList;
	HashMap<String, String> godClassDetection_test;
	HashMap<String, String> longClassDetection_test;
	
	//NumberOfClassesPerFile test
	
	private final String FILE = "C:\\Users\\renat\\Documents\\GitHub_repository\\ES-2Sem-2021-Grupo12\\imported_project\\not_source\\src\\pckg2\\HelloWorld.java";
	
	List<String> classes;		
	List<String> expected_classes;
	
	
	
	
	
	@Before
	public void setUp() throws Exception {
		
		// CodeSmells test

		SR = CodeSmells.init(PATH, rule);

		expected_SR = new SharedResource();
		List<String> ic = new ArrayList<>();
		ic.add("ClassInPack3");
		expected_SR.addMethod(new MethodStats(0, "pack3",
				System.getProperty("user.dir")+ "\\TestProject\\src\\pack3\\ClassInPack3", ic, "main(String[])",
				3, 1, 1, 6, 1, "false", "false"));
		ic = new ArrayList<>();
		ic.add("ClassInPack2");
		expected_SR.addMethod(new MethodStats(0, "pack2",
				System.getProperty("user.dir")+ "\\TestProject\\src\\pack2\\ClassInPack2", ic,
				"getAttribute1()", 3, 1, 2, 13, 2, "false", "false"));
		expected_SR.addMethod(new MethodStats(0, "pack2",
				 System.getProperty("user.dir")+ "\\TestProject\\src\\pack2\\ClassInPack2", ic,
				"setAttribute1(int)", 3, 1, 2, 13, 2, "false", "false"));
		ic = new ArrayList<>();
		ic.add("ClassInPack1");
		expected_SR.addMethod(new MethodStats(0, "pack1",
				System.getProperty("user.dir")+ "\\TestProject\\src\\pack1\\ClassInPack1", ic,
				"getAttribute1()", 3, 1, 4, 23, 5, "true", "false"));
		expected_SR.addMethod(new MethodStats(0, "pack1",
				System.getProperty("user.dir")+ "\\TestProject\\src\\pack1\\ClassInPack1", ic,
				"setAttribute1(String)", 5, 2, 4, 23, 5, "true", "false"));
		expected_SR.addMethod(new MethodStats(0, "pack1",
				 System.getProperty("user.dir")+ "\\TestProject\\src\\pack1\\ClassInPack1", ic,
				"getAttribute2()", 3, 1, 4, 23, 5, "true", "false"));
		expected_SR.addMethod(new MethodStats(0, "pack1",
				System.getProperty("user.dir")+ "\\TestProject\\src\\pack1\\ClassInPack1", ic,
				"setAttribute2(String)", 3, 1, 4, 23, 5, "true", "false"));
		
		
		
		
		//FindPackages test
		
		packs = FindPackages.getPackages(PATH1);
		expected_packs = new HashMap<String, String>();
		expected_packs.put("tests", "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship\\src\\tests");
		expected_packs.put("battleship", "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship\\src\\battleship");
		
		
		
		//MethodComparer test
		
		mc_test = new MetricComparer(
				System.getProperty("user.dir")+"\\test_smells.xlsx",
				System.getProperty("user.dir")+"\\test_Code_Smells.xlsx");
		mc_test.formPairs();

		testList = new ArrayList<MethodComparisson>();
		
		MethodComparisson a = new MethodComparisson("1.0", "compiler", "SourceCodeParser", "parse()", 27, 1227, 295, true, 9, 2, false);
		a.setNOM_class_baseline(29);
		a.setLOC_class_baseline(1371);
		a.setWMC_class_baseline(328);
		a.setIs_God_Class_baseline(true);
		a.setLOC_method_baseline(9);
		a.setCYCLO_method_baseline(2);
		a.setIs_Long_Method_baseline(false);
		MethodComparisson b = new MethodComparisson("2.0", "helper", "Util", "accessFlagToString_Class(short)",24 , 576, 246, true, 15, 5, true);
		b.setIs_God_Class_baseline(true);
		b.setIs_Long_Method_baseline(false);
		MethodComparisson c = new MethodComparisson("3.0", "decompiler", "JavaClassParser", "readMagic()", 21, 467, 90, true, 4, 1, false);
	
		c.setIs_God_Class_baseline(false);
	
		c.setIs_Long_Method_baseline(false);
		testList.add(a);
		testList.add(b);
		testList.add(c);
		
		
		godClassDetection_test = new HashMap();			
		godClassDetection_test.put(a.getCls(), "VP");
		godClassDetection_test.put(b.getCls(), "VP");
		godClassDetection_test.put(c.getCls(), "FP");
		
		longClassDetection_test = new HashMap();
		longClassDetection_test.put(a.getCls()+"."+a.getMeth(), "VN");
		longClassDetection_test.put(b.getCls()+"."+b.getMeth(), "FP");
		longClassDetection_test.put(c.getCls()+"."+c.getMeth(), "VN");
		
		
		
		//NumberOfClassesPerFile test
		
		classes = NumberOfClassesPerFile.getClassesFromFile(FILE);		
		expected_classes = new ArrayList<String>();
		
		expected_classes.add("HelloWorld");
		expected_classes.add("Wtv");
		
		
	}


	@Test
	public void test() {
		
		//CodeSmells test

		assertEquals(expected_SR.statsSummary(), SR.statsSummary());

		assertEquals(expected_SR.getMethodStats().get(0), SR.getMethodStats().get(0));
		assertEquals(expected_SR.getMethodStats().get(1), SR.getMethodStats().get(1));
		assertEquals(expected_SR.getMethodStats().get(2), SR.getMethodStats().get(2));
		assertEquals(expected_SR.getMethodStats().get(3), SR.getMethodStats().get(3));
		assertEquals(expected_SR.getMethodStats().get(4), SR.getMethodStats().get(4));
		assertEquals(expected_SR.getMethodStats().get(5), SR.getMethodStats().get(5));
		assertEquals(expected_SR.getMethodStats().get(6), SR.getMethodStats().get(6));
		
		//FindPackage test
		
		assertEquals(expected_packs, packs);
		
		
		
		
		//MethodComaprer test
		
		for(int i=0; i<3; i++) {
			assertEquals(testList.get(i).getCls(), mc_test.getMethodList().get(i).getCls());
			assertEquals(testList.get(i).getCYCLO_method_baseline(), mc_test.getMethodList().get(i).getCYCLO_method_baseline());
			assertEquals(testList.get(i).getCYCLO_method_smell(), mc_test.getMethodList().get(i).getCYCLO_method_smell());
			assertEquals(testList.get(i).getLOC_class_baseline(), mc_test.getMethodList().get(i).getLOC_class_baseline());
			assertEquals(testList.get(i).getLOC_class_smell(), mc_test.getMethodList().get(i).getLOC_class_smell());
			assertEquals(testList.get(i).getLOC_method_baseline(), mc_test.getMethodList().get(i).getLOC_method_baseline());
			assertEquals(testList.get(i).getLOC_method_smell(), mc_test.getMethodList().get(i).getLOC_method_smell());
			assertEquals(testList.get(i).getMeth(), mc_test.getMethodList().get(i).getMeth());
			assertEquals(testList.get(i).getMethodID(), mc_test.getMethodList().get(i).getMethodID());
			assertEquals(testList.get(i).getNOM_class_baseline(), mc_test.getMethodList().get(i).getNOM_class_baseline());
			assertEquals(testList.get(i).getNOM_class_smell(), mc_test.getMethodList().get(i).getNOM_class_smell());
			assertEquals(testList.get(i).getPck(), mc_test.getMethodList().get(i).getPck());
			assertEquals(testList.get(i).getWMC_class_baseline(), mc_test.getMethodList().get(i).getWMC_class_baseline());
			assertEquals(testList.get(i).getWMC_class_smell(), mc_test.getMethodList().get(i).getWMC_class_smell());
			assertEquals(testList.get(i).isIs_God_Class_baseline(), mc_test.getMethodList().get(i).isIs_God_Class_baseline());
			assertEquals(testList.get(i).isIs_God_Class_smell(), mc_test.getMethodList().get(i).isIs_God_Class_smell());
			assertEquals(testList.get(i).isIs_Long_Method_baseline(), mc_test.getMethodList().get(i).isIs_Long_Method_baseline());
			assertEquals(testList.get(i).isIs_Long_Method_smell(), mc_test.getMethodList().get(i).isIs_Long_Method_smell());
		}
		
		
		assertEquals(godClassDetection_test, mc_test.getGodClassDetectionQuality());
		assertEquals(longClassDetection_test, mc_test.getLongMethodDetectionQuality());
		
		
		//NumberOfClassesPerFile test
		
		assertEquals(expected_classes, classes);
		
		
	}

}
