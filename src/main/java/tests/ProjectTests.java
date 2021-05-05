package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import backend.ConfusionMatrix;
import backend.CycloMethod;
import backend.FindPackages;
import backend.LineCounter;
import backend.Method;
import backend.MethodUtils;
import backend.NumberOfClassesPerFile;
import backend.RulesManager;
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
	private SharedResource SR;
	private SharedResource expected_SR;
	
	//CycloMethod test
	
	private final String FILE_1 = System.getProperty("user.dir")+"\\imported_project\\not_source\\src\\pckg\\TestForCycloMethod.java";
	private List<Method> methods;
	private List<Integer> methodsCycloValue;
	private List<Integer> expected_methodsCycloValue;
	private int valueWMC;
	
	//FindPackages test
	
	private static final String PATH1 = "C:\\Users\\Diogo\\git\\BattleshipCodeCoverage";
	private HashMap<String, String> packs;
	private HashMap<String, String> expected_packs;
	
	//LineCounter test
		private final String FILE_3 = System.getProperty("user.dir")+"\\imported_project\\not_source\\src\\pckg\\TestForLineCounter.java";
		private List<Method> methods_testLineCounter;
		private LineCounter testerLineCounter;
		private int testerTotalLineCount;
	
	//MethodComparer test
	
	private MetricComparer mc_test;
	private List<MethodComparisson> testList;
	private HashMap<String, String> godClassDetection_test;
	private HashMap<String, String> longClassDetection_test;
	
	//NumberOfClassesPerFile test
	
	private final String FILE_2 = System.getProperty("user.dir")+"\\imported_project\\not_source\\src\\pckg2\\HelloWorld.java";	
	private List<String> classes;		
	private List<String> expected_classes;
	
	//ConfusionMatrix test
	private ConfusionMatrix testConfMatrix;
	
	//RulesManager test
	private final String RM_TEST_PATH = System.getProperty("user.dir")+"\\imported_project";
	private RulesManager test_rm;
	private Rule test_rule;
	private List<Rule> expected_rules_list;
	
	
	
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
		
		//CycloMethod test
		
		methods = MethodUtils.getMethodsFromFile(FILE_1);
		methodsCycloValue = CycloMethod.allMethodsCycloValue(methods);
		
		expected_methodsCycloValue = new ArrayList<Integer>();
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(3);
		expected_methodsCycloValue.add(1);
		
		valueWMC = CycloMethod.wmcCalculator(methods);
		
		
		
		
		//FindPackages test
		
		packs = FindPackages.getPackages(PATH1);
		expected_packs = new HashMap<String, String>();
		expected_packs.put("test", "C:\\Users\\Diogo\\git\\BattleshipCodeCoverage\\Battleship\\src\\test");
		expected_packs.put("battleship", "C:\\Users\\Diogo\\git\\BattleshipCodeCoverage\\Battleship\\src\\battleship");
		
		
		
		
		//LineCounter test
		methods_testLineCounter = MethodUtils.getMethodsFromFile(FILE_3);
		testerLineCounter = new LineCounter();
		testerLineCounter.countLines(FILE_3, methods_testLineCounter);
		testerTotalLineCount = testerLineCounter.getTotalLinesCount();
		
		
		
		
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
		
		
		godClassDetection_test = new HashMap<String, String>();		
		godClassDetection_test.put(a.getCls(), "VP");
		godClassDetection_test.put(b.getCls(), "VP");
		godClassDetection_test.put(c.getCls(), "FP");
		
		longClassDetection_test = new HashMap<String, String>();	
		longClassDetection_test.put(a.getCls()+"."+a.getMeth(), "VN");
		longClassDetection_test.put(b.getCls()+"."+b.getMeth(), "FP");
		longClassDetection_test.put(c.getCls()+"."+c.getMeth(), "VN");
		
		
		
		//NumberOfClassesPerFile test
		
		classes = NumberOfClassesPerFile.getClassesFromFile(FILE_2);	
		
		expected_classes = new ArrayList<String>();		
		expected_classes.add("HelloWorld");
		expected_classes.add("Wtv");
		
		
		//ConfusionMatrix test
		
		testConfMatrix = new ConfusionMatrix();
		testConfMatrix.setFN(2);
		testConfMatrix.setFP(1);
		testConfMatrix.setVN(2);
		testConfMatrix.setVP(1);
		
		//RulesManager test
		/**
		 * Delete, if exists, .bin located in imported_project Dir before starting the test
		 */
		test_rm = new RulesManager(RM_TEST_PATH);
		test_rule = new Rule("test rule",0, 10, 0, 10, 0, 10, false, 0, 10, 0, 10, false);
		test_rm.addRuleToFile(test_rule);
		expected_rules_list = new ArrayList();
		expected_rules_list.add(RulesManager.DEFAULT_RULE);
		expected_rules_list.add(test_rule);
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
		
		
		//CycloMethod test
		assertEquals(expected_methodsCycloValue, methodsCycloValue);
		assertEquals(7, valueWMC);
		
		
		//FindPackage test
		
		assertEquals(expected_packs, packs);
		
		//LineCounter test
		assertEquals(16, testerTotalLineCount);
		
		
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
		
		
		
		//ConfusionMatrix test
		
		assertEquals(2, testConfMatrix.getFN());
		assertEquals(1, testConfMatrix.getFP());
		assertEquals(2, testConfMatrix.getVN());
		assertEquals(1, testConfMatrix.getVP());
		
		//RulesManager test
		
		try {
			
			List<Rule> aux_actual_rule_list = test_rm.readObjectsFromFile(); 
			
			for(int i=0; i<2; i++) {
				assertEquals(expected_rules_list.get(i).getCycloMethodMin(), aux_actual_rule_list.get(i).getCycloMethodMin());
				assertEquals(expected_rules_list.get(i).getCycloMethodMax(), aux_actual_rule_list.get(i).getCycloMethodMax());			
				assertEquals(expected_rules_list.get(i).getLocClassMax(), aux_actual_rule_list.get(i).getLocClassMax());
				assertEquals(expected_rules_list.get(i).getLocClassMin(), aux_actual_rule_list.get(i).getLocClassMin());				
				assertEquals(expected_rules_list.get(i).getLocMethodMax(), aux_actual_rule_list.get(i).getLocMethodMax());
				assertEquals(expected_rules_list.get(i).getLocMethodMin(), aux_actual_rule_list.get(i).getLocMethodMin());				
				assertEquals(expected_rules_list.get(i).getName(), aux_actual_rule_list.get(i).getName());
				assertEquals(expected_rules_list.get(i).getLocMethodMin(), aux_actual_rule_list.get(i).getLocMethodMin());				
				assertEquals(expected_rules_list.get(i).getLocMethodMax(), aux_actual_rule_list.get(i).getLocMethodMax());
				assertEquals(expected_rules_list.get(i).getNomClassMax(), aux_actual_rule_list.get(i).getNomClassMax());
				assertEquals(expected_rules_list.get(i).getNomClassMin(), aux_actual_rule_list.get(i).getNomClassMin());
				assertEquals(expected_rules_list.get(i).getWmcClassMax(), aux_actual_rule_list.get(i).getWmcClassMax());				
				assertEquals(expected_rules_list.get(i).getWmcClassMin(), aux_actual_rule_list.get(i).getWmcClassMin());
				assertEquals(expected_rules_list.get(i).isClassRulesConjunction(), aux_actual_rule_list.get(i).isClassRulesConjunction());				
				assertEquals(expected_rules_list.get(i).isMethodRulesConjunction(), aux_actual_rule_list.get(i).isMethodRulesConjunction());
			}
			
			
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
