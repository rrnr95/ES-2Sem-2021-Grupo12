package gui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import extractor.MethodStats;

public class Rule implements Serializable {
	
	private String name;
	private int nomClassMin, nomClassMax, locClassMin, locClassMax, wmcClassMin, wmcClassMax;
	private boolean classRulesConjunction;
	private int locMethodMin, locMethodMax, cycloMethodMin, cycloMethodMax;
	private boolean methodRulesConjunction;
	
	

	

	
	public Rule(String name, int nomClassMin, int nomClassMax, int locClassMin, int locClassMax, int wmcClassMin,
			int wmcClassMax, boolean classRulesConjunction, int locMethodMin, int locMethodMax, int cycloMethodMin,
			int cycloMethodMax, boolean methodRulesConjunction) {
		this.name = name;
		this.nomClassMin = nomClassMin;
		this.nomClassMax = nomClassMax;
		this.locClassMin = locClassMin;
		this.locClassMax = locClassMax;
		this.wmcClassMin = wmcClassMin;
		this.wmcClassMax = wmcClassMax;
		this.classRulesConjunction = classRulesConjunction;
		this.locMethodMin = locMethodMin;
		this.locMethodMax = locMethodMax;
		this.cycloMethodMin = cycloMethodMin;
		this.cycloMethodMax = cycloMethodMax;
		this.methodRulesConjunction = methodRulesConjunction;
	}


//	@Override
//	public String toString() {
//		String res = "Rule: " + name + "\nNom_Class   min: " + nomClassMin + " max: " + nomClassMax + "\n";		
//		res+= "Loc_Class   min: " + locClassMin + " max: " + locClassMax + "\n";
//		res+= "Wmc_Class   min: " + wmcClassMin + " max: " + wmcClassMax + "\n";
//		res+= "Conjuction? " + classRulesConjunction + "\n\n";
//		res+= "Loc_Method   min: " + locMethodMin + " max: " + locMethodMax + "\n";
//		res+= "Cyclo_Method   min: " + cycloMethodMin + " max: " + cycloMethodMax + "\n";
//		res+= "Conjuction? " + methodRulesConjunction + "\n\n";
//		return res;		
//	}
	
	
	
//	Extrair NOM_class
//	Extrair LOC_class
//	Extrair WMC_class
//	Extrair LOC_method
//	Extrair CYCLO_method
	
	
	
	public String printPrettyCondition() {
		String andOrGodClass = 	(classRulesConjunction ? "AND" :  "OR");
		String andOrLongMethod = (methodRulesConjunction ? "AND" :  "OR");
				
		String godClassCond = "isGodClass if: ";
		if(isValidParam(this.nomClassMin)) {
			godClassCond+= this.nomClassMin + " < NOM_class < " + this.nomClassMax;
		}
		
		
		if (isValidParam(this.locClassMin)) {
			if (!godClassCond.equals("isGodClass if: ")) {
				godClassCond += " " + andOrGodClass + " ";
			}
			
			godClassCond+= this.locClassMin + " < LOC_class < " + this.locClassMax;
		}
		
		if (isValidParam(this.wmcClassMin)) {
			if (!godClassCond.equals("isGodClass if: ")) {
				godClassCond += " " + andOrGodClass + " ";
			}
			
			godClassCond+= this.wmcClassMin + " < WMC_class < " + this.wmcClassMax;
		}	
		
		
		
		String longMethodCond = "isLongMethod if: ";
		if(isValidParam(this.locMethodMin)) {
			longMethodCond+= this.locMethodMin + " < LOC_method < " + this.locMethodMax;
		}
		
		
		if (isValidParam(this.cycloMethodMin)) {
			if (!longMethodCond.equals("isLongMethod if: ")) {
				longMethodCond += " " + andOrLongMethod + " ";
			}
			
			longMethodCond+= this.cycloMethodMin + " < CYCLO_method < " + this.cycloMethodMax;
		}
		return godClassCond + "\n" + longMethodCond;
	}
	

	@Override
	public String toString() {
		return this.name;
	}
	
	public boolean isGodClass(MethodStats method) {
		
		List<Boolean> results = new ArrayList<Boolean>();
		
		//checks if method results are inside of thresholds, if outside the threshold adds 'true' value to results list.
		if(isValidParam(this.nomClassMin)) {
			boolean nomClassRes = ((method.getNOM_class() >= this.nomClassMin) && (method.getNOM_class() <= this.nomClassMax));
			results.add(!nomClassRes);
		}
		
		if(isValidParam(this.locClassMin)) {
			boolean locClassRes = ((method.getLOC_class() >= this.locClassMin) && (method.getLOC_class() <= this.locClassMax));
			results.add(!locClassRes);
		}
		
		
		if(isValidParam(this.wmcClassMin)) {
			boolean wmcClassRes = ((method.getWMC_class() >= this.wmcClassMin) && (method.getWMC_class() <= this.wmcClassMax));
			results.add(!wmcClassRes);
		}
		
		
		// checks and executes AND or OR with the results
		if(this.classRulesConjunction) {
			boolean res = true;
			for(boolean val : results) {
				res = res && val;
			}
			
			return res;
		
		}else {
			boolean res = false;
			for(boolean val : results) {
				res = res || val;
			}
			
			return res;
		}				
	}
	
	public boolean isLongMethod(MethodStats method) {


		List<Boolean> results = new ArrayList<Boolean>();

		// checks if method results are inside of thresholds, if outside the threshold
		// adds 'true' value to results list.
		if (isValidParam(this.locMethodMin)) {
			boolean locMethodRes = ((method.getLOC_method() >= this.locMethodMin) && (method.getLOC_method() <= this.locMethodMax));
			results.add(!locMethodRes);
		}
		
		
		if (isValidParam(this.cycloMethodMin)) {
			boolean cycloMethodRes = ((method.getCYCLO_method() >= this.cycloMethodMin) && (method.getCYCLO_method() <= this.cycloMethodMax));
			results.add(!cycloMethodRes);
		}
		
		// checks and executes AND or OR with the results
				if(this.methodRulesConjunction) {
					boolean res = true;
					for(boolean val : results) {
						res = res && val;
					}
					
					return res;
				
				}else {
					boolean res = false;
					for(boolean val : results) {
						res = res || val;
					}
					
					return res;
				}		
		
	}
	
	public String getRuleName() {
		return this.name;
	}
	
	private boolean isValidParam(int param) {
		return !(param < 0);
	}
	
	
	
	//~~~~~~~~~~DAQUI PARA BAIXO~~~~~~~~~~
	//SO PARA TESTAGEM
	
	
	
	
	
	public void saveObjectToFile(String path) {
		 
		try {
			FileOutputStream f = new FileOutputStream(new File(path));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			
			List<Rule> rules = new ArrayList<>();
			rules.add(this);
			// Write object to file
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static List<Rule> readObjectsFromFile(String path) throws IOException, ClassNotFoundException{
		
		List<Rule> rules = new ArrayList<Rule>();
		FileInputStream fi = new FileInputStream(new File(path));
        ObjectInputStream oi = new ObjectInputStream(fi);	
      
        rules = (List<Rule>) oi.readObject();
      
        
        oi.close();
        fi.close();
        return rules;        
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Rule r = new Rule("rule1", 0,10,0,10,0,10,true,5,10,5,10,true);
		ArrayList<String> innerteste = new ArrayList<String>();
		MethodStats ms = new MethodStats(1,"default","Main",innerteste,"get()",6,11,11,11,11,"","");
		System.out.println(r.printPrettyCondition());

	
		System.out.println("\n");
	
		System.out.println("Nom_class:"+ms.getNOM_class()+ " Loc_class:"+ms.getLOC_class()+" Wmc_class:"+ms.getWMC_class());
		
		
		System.out.println("\n\n");
		System.out.println(r.isGodClass(ms));
		System.out.println(r.isLongMethod(ms));
		
	}
	
	
	
}