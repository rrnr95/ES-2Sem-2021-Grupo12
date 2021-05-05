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


/**
 * Class used to manage the storage of Rule objects in a binary file
 * @author ES-2Sem-2021-Grupo12
 *
 */
public class RulesManager {

	
	
	private File stored_rules;
	private static Rule DEFAULT_RULE = new Rule("Default", 0,10,0,10,0,10,false,0,10,0,10,false);

	/**
	 * Contructor
	 * 
	 * @param path
	 * Receives the path where the binary file should be created.
	 * If the file doesn't exist, it is created and initialized by calling initFile().
	 */
	
	public RulesManager(String path) {
		path+= "/stored_rules.bin";
		stored_rules = new File(path);
		if(!stored_rules.exists())
			initFile();
		
	}
	
	/**
	 * Method to initialize the binary file.
	 * The binary file is initialized with a Default Rule. 
	 */
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
	
	/**
	 * Method used to read the Rule objects in the binary file.
	 * @return List of objects Rule, present in the binary file.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<Rule> readObjectsFromFile() throws IOException, ClassNotFoundException {

		List<Rule> rules = new ArrayList<Rule>();
		FileInputStream fi = new FileInputStream(stored_rules);
		ObjectInputStream oi = new ObjectInputStream(fi);

		rules = (List<Rule>) oi.readObject();

		oi.close();
		fi.close();
		return rules;
	}
	
	/**
	 * Method used to write an object Rule in the binary file.
	 * @param Rule object to be written in the file.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Method used to remove a given rule from the binary file.
	 * @param Rule object to be deleted from the file.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
	public void deleteRuleFromFile (Rule rule) throws ClassNotFoundException, IOException {
		int index = index(rule);
		List<Rule> rules = readObjectsFromFile();
		rules.remove(index);
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
		
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Method locates and returns the index of the given Rule object in the file.
	 * @param Rule object to be located.
	 * @return Index number.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
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
	
	/**
	 * Method used to replace the existing rules in the binary file with a new set of rules.
	 * @param Rule objects List.
	 */
	public void updateAllFileRules(List<Rule> rules) {
		
		try {
			FileOutputStream f = new FileOutputStream(stored_rules);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			
			o.writeObject(rules);
			
			o.close();
			f.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
