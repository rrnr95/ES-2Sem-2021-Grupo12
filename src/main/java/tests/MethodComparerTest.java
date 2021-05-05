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

import comparer.MethodComparisson;
import comparer.MetricComparer;

public class MethodComparerTest {
	
	MetricComparer mc_test;
	List<MethodComparisson> testList;
	HashMap<String, String> godClassDetection_test;
	HashMap<String, String> longClassDetection_test;
	

	
	@Before
	public void setUp() throws Exception {

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
		
	}



	@Test
	public void test() {
		
	
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
		
		
	}

}
