package backend;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import gui.Rule;

public class RulesManager {

	
	
	private File stored_rules;
	private static Rule DEFAULT_RULE = new Rule("Default", 0,10,0,10,0,10,false,0,10,0,10,false);
	//TODO Criar default ruleFile.bin com default rule para garantir a presen�a de um file sempre com uma regra.
	//TODO Criar metodos para adicionar e remover regra ao file
	
	public RulesManager(String path) {
		path+= "\\stored_rules.bin";
		stored_rules = new File(path);
		if(!stored_rules.exists())
			initFile();
		
	}
	
	
	private void initFile() {
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			
			List<Rule> rules = new ArrayList<>();
			rules.add(DEFAULT_RULE);
			// Write object to file
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Rule> readObjectsFromFile() throws IOException, ClassNotFoundException {

		List<Rule> rules = new ArrayList<Rule>();
		FileInputStream fi = new FileInputStream(stored_rules);
		ObjectInputStream oi = new ObjectInputStream(fi);

		rules = (List<Rule>) oi.readObject();

		oi.close();
		fi.close();
		return rules;
	}
	
	public void addRuleToFile(Rule rule) throws ClassNotFoundException, IOException {
		 
		List<Rule> rules= this.readObjectsFromFile();
		rules.add(rule);
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			// Write object to file
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRuleFromFile (Rule rule) throws ClassNotFoundException, IOException {
		int index = index(rule);
		List<Rule> rules = readObjectsFromFile();
		rules.remove(index);
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			// Write object to file
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private int index(Rule rule) throws IOException, ClassNotFoundException {
		List<Rule> rules = readObjectsFromFile();
		int index = 0;
		for (Rule r : rules) {
			if (r.toString().equals(rule.toString())) {
				break;
			}
			index++;
		}
		return index;
	}
	
	public void updateAllFileRules(List<Rule> rules) {
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			// Write object to file
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
