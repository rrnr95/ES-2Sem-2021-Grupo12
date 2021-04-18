package tests;

import static org.junit.Assert.*;

import java.util.HashMap;


import org.junit.Test;

import backend.FindPackages;


public class FindPackagesTest {

	private static final String PATH = "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship";
	private static final FindPackages fp = new FindPackages(PATH);
	
	@Test
	public void testFindPackages() {
		assertEquals(FindPackages.class, fp.getClass());
	}

	@Test
	public void testGetPackages() {
		HashMap<String, String> packs = fp.getPackages();
		HashMap<String, String> expected_packs = new HashMap<String, String>();
		
		expected_packs.put("tests", "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship\\src\\tests");
		expected_packs.put("battleship", "C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship\\src\\battleship");
		
		assertEquals(expected_packs, packs);
	}

}