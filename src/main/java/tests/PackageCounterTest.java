package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.PackageCounter;

public class PackageCounterTest {
	
	private static final String PATH = "C:\\Users\\renat\\Documents\\GitHub_repository\\ES-2Sem-2021-Grupo12\\imported_project";
	private static final String PATH_2 = "C:\\Users\\renat\\Documents\\GitHub_repository\\ES-2Sem-2021-Grupo12\\imported_project\\not_source";
	private static final PackageCounter pc = new PackageCounter(PATH);
	private static final PackageCounter pc_2 = new PackageCounter(PATH_2);
	
	@Test
	public void test() {
		
		assertEquals(PackageCounter.class, pc.getClass());
		
		//IMPORTED_PROJECT#1
		int num_packages = pc.packagesCount();	
		assertEquals(3, num_packages);
		
		List<String> pack_list = pc.getPackages();
		List<String> expected_pck_list = new ArrayList<String>();
		
		expected_pck_list.add("pckg2");
		expected_pck_list.add("pckg2.2");
		expected_pck_list.add("pckg");
		
		assertEquals(expected_pck_list, pack_list);
		
		int num_packages_2 = pc_2.packagesCount();	
		assertEquals(3, num_packages_2);
		
	}

}
