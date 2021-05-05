package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import backend.Method;
import extractor.CodeSmells;
import extractor.MethodStats;
import extractor.SharedResource;
import gui.Rule;

public class CodeSmellsTest {

	private final String PATH = "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject";
	private final Rule rule = new Rule("ruleTest", 1, 20, 1, 20, 1, 20, false, 1, 20, 1, 20, true); 

	@Test
	public void testInit() {
		
		SharedResource SR = CodeSmells.init(PATH, rule);
		
		SharedResource expected_SR = new SharedResource();
		List<String> ic = new ArrayList<>();
		ic.add("ClassInPack3");
		expected_SR.addMethod(new MethodStats(
				0, "pack3", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack3\\ClassInPack3", ic, "main(String[])", 3, 1, 1, 6, 1, "false", "false"
				));
		ic = new ArrayList<>();
		ic.add("ClassInPack2");
		expected_SR.addMethod(new MethodStats(
				0, "pack2", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack2\\ClassInPack2", ic, "getAttribute1()", 3, 1, 2, 13, 2, "false", "false"
				));
		expected_SR.addMethod(new MethodStats(
				0, "pack2", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack2\\ClassInPack2", ic, "setAttribute1(int)", 3, 1, 2, 13, 2, "false", "false"
				));
		ic = new ArrayList<>();
		ic.add("ClassInPack1");
		expected_SR.addMethod(new MethodStats(
				0, "pack1", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack1\\ClassInPack1", ic, "getAttribute1()", 3, 1, 4, 23, 5, "true", "false"
				));
		expected_SR.addMethod(new MethodStats(
				0, "pack1", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack1\\ClassInPack1", ic, "setAttribute1(String)", 5, 2, 4, 23, 5, "true", "false"
				));
		expected_SR.addMethod(new MethodStats(
				0, "pack1", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack1\\ClassInPack1", ic, "getAttribute2()", 3, 1, 4, 23, 5, "true", "false"
				));
		expected_SR.addMethod(new MethodStats(
				0, "pack1", "C:\\Users\\Utilizador\\eclipse-workspace\\TestProject\\src\\pack1\\ClassInPack1", ic, "setAttribute2(String)", 3, 1, 4, 23, 5, "true", "false"
				));

			
		assertEquals(expected_SR.statsSummary(), SR.statsSummary());
		
		assertEquals(expected_SR.getMethodStats().get(0), SR.getMethodStats().get(0));
		assertEquals(expected_SR.getMethodStats().get(1), SR.getMethodStats().get(1));
		assertEquals(expected_SR.getMethodStats().get(2), SR.getMethodStats().get(2));
		assertEquals(expected_SR.getMethodStats().get(3), SR.getMethodStats().get(3));
		assertEquals(expected_SR.getMethodStats().get(4), SR.getMethodStats().get(4));
		assertEquals(expected_SR.getMethodStats().get(5), SR.getMethodStats().get(5));
		assertEquals(expected_SR.getMethodStats().get(6), SR.getMethodStats().get(6));
	}
	
}
