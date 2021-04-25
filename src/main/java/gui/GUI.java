package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import backend.Rule;
import backend.RulesManager;
import extractor.CodeSmells;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class GUI {

	private static JFrame frmExtractMetrics;
	private static JTextField txtf_path;
	private JPanel panel;
	private JButton btn_folder;
	private JButton btnCalculateMetrics;
	private JButton btnFetchXLSX;
	private JButton btnRules;
	private JScrollPane scrollPane;
	private JTable table;
	private JList guiRuleList;
	private JButton btnAddRule;
	private JButton btnConfirmRule;
		
	//private String rule;
	private RulesManager ruleManager;
	private Rule selectedRule;
	private JTextArea ruleDescriptionField;
	private JLabel currentRuleLabel;
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmExtractMetrics.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		ruleManager = new RulesManager(System.getProperty("user.dir"));
		
		
		
		
//		rule = "default";
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		try {
			selectedRule = ruleManager.readObjectsFromFile().get(0);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		frmExtractMetrics = new JFrame();
		frmExtractMetrics.setBackground(Color.GRAY);
		frmExtractMetrics.setTitle("Extract metrics");
		frmExtractMetrics.setResizable(false);
		frmExtractMetrics.setBounds(100, 100, 928, 620);
		frmExtractMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmExtractMetrics.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		txtf_path = new JTextField();
		txtf_path.setEditable(false);
		txtf_path.setBounds(10, 541, 232, 20);
		panel.add(txtf_path);
		txtf_path.setColumns(10);

		btn_folder = new JButton("Folder");
		addChooseListener(btn_folder);
		btn_folder.setBounds(250, 540, 74, 23);
		panel.add(btn_folder);
		
		btnCalculateMetrics = new JButton("Calculate Metrics");
		btnCalculateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateMetricsPressed();
			}
		});
		btnCalculateMetrics.setBounds(770, 540, 140, 23);
		panel.add(btnCalculateMetrics);

		btnFetchXLSX = new JButton("Fetch XLSX File");
		btnFetchXLSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showXLSXPressed();
			}
		});
		btnFetchXLSX.setBounds(620, 540, 140, 23);
		panel.add(btnFetchXLSX);
		
		btnRules = new JButton("Rules");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRulesPressed();
			}
		});
		btnRules.setBounds(470, 540, 140, 23);
		panel.add(btnRules);
		
		currentRuleLabel = new JLabel("Current Rule: " + selectedRule.getRuleName());
		currentRuleLabel.setBounds(10, 516, 140, 23);		
		panel.add(currentRuleLabel);
	}

	
	private static void addChooseListener(JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = fileChooser.showOpenDialog(frmExtractMetrics);

				if(option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					
					if(f.exists())
						txtf_path.setText(f.getPath());
					else
						txtf_path.setText("Invalid");
				}		
			}
		});
	}
	
	
	private void calculateMetricsPressed() {
		
		File project = new File(txtf_path.getText());
		if (project.exists()) {
			
			CodeSmells cs = new CodeSmells(project.getAbsolutePath());
			try {
				cs.init();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			cleanFrame();
			printCalculatedMetrics(cs);
			
		} else {
			
			JOptionPane.showMessageDialog(null,"Diretoria Desconhecida");
		}
		
	}
	
	
	private void printCalculatedMetrics(CodeSmells cs) {
		Object[] columnNames = {"Nº total packages", "Nº total de classes", "Nº total de metodos", "Nº total de linhas de codigo"};
		HashMap<String, Integer> summary = (HashMap<String, Integer>) cs.getRecursoPartilhado().statsSummary();
		Object[][] info = {
				{summary.get("NumPacks"), summary.get("NumClasses"), summary.get("NumMethods"), summary.get("NumLinesOfCode")}
		};

		table = new JTable(info, columnNames);
		table.setEnabled(false);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 902, 500);
		
		panel.add(scrollPane);
		
	}
	
	
	private void showXLSXPressed() {
		File project = new File(txtf_path.getText());
		     
        if(project.exists()) {
        	FileInputStream excelFIS = null;
			BufferedInputStream excelBIS = null;
	        XSSFWorkbook excelImportToJTable = null;
        	
			try {
				cleanFrame();
	            File excelFile = new File(project.getAbsolutePath() + "\\smells.xlsx");
	            
	            excelFIS = new FileInputStream(excelFile);
	            excelBIS = new BufferedInputStream(excelFIS);
	            excelImportToJTable = new XSSFWorkbook(excelBIS);
	            XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
	            
	            Object[] header = {"method ID", "package", "class", "inner classes", "method", "NOM_class", "LOC_class", "WMC_class", "is_God_class", "LOC_method", "CYCLO_method", "is_long_method"};
	            Object[][] data = new Object[excelSheet.getLastRowNum()][12];
	
	            for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
	                XSSFRow excelRow = excelSheet.getRow(row);
	
	                XSSFCell excelMethod_id = excelRow.getCell(0);
	                data[row-1][0] = excelMethod_id;
	                XSSFCell excelPackage = excelRow.getCell(1);
	                data[row-1][1] = excelPackage;
	                XSSFCell excelClass = excelRow.getCell(2);
	                data[row-1][2] = excelClass;
	                XSSFCell excelInner_classes = excelRow.getCell(3);
	                data[row-1][3] = excelInner_classes;
	                XSSFCell excelMethod = excelRow.getCell(4);
	                data[row-1][4] = excelMethod;
	                XSSFCell excelNOM_class = excelRow.getCell(5);
	                data[row-1][5] = excelNOM_class;
	                XSSFCell excelLOC_class = excelRow.getCell(6);
	                data[row-1][6] = excelLOC_class;
	                XSSFCell excelWMC_class = excelRow.getCell(7);
	                data[row-1][7] = excelWMC_class;
	                XSSFCell excelIs_God_class = excelRow.getCell(8);
	                data[row-1][8] = excelIs_God_class;
	                XSSFCell excelLOC_method = excelRow.getCell(9);
	                data[row-1][9] = excelLOC_method;
	                XSSFCell excelCYCLO_method = excelRow.getCell(10);
	                data[row-1][10] = excelCYCLO_method;
	                XSSFCell excelIs_long_method = excelRow.getCell(11);
	                data[row-1][11] = excelIs_long_method;
	
	            }
	    		
	            table = new JTable(data, header);
	    		table.setEnabled(false);
	    		scrollPane = new JScrollPane(table);
	    		scrollPane.setBounds(10, 11, 902, 500);
	    		panel.add(scrollPane);		
	    		
	    		
	    		
	        } catch (IOException iOException) {
	            JOptionPane.showMessageDialog(null, iOException.getMessage());
	        } finally {
	            try {
	                if (excelFIS != null) {
	                    excelFIS.close();
	                }
	                if (excelBIS != null) {
	                    excelBIS.close();
	                }
	                if (excelImportToJTable != null) {
	                    excelImportToJTable.close();
	                }
	            } catch (IOException iOException) {
	                JOptionPane.showMessageDialog(null, iOException.getMessage());
	            }
	        }
        }
        else {
        	JOptionPane.showMessageDialog(null,"Diretoria Desconhecida");
        }
	}
	
	private void showRulesPressed() {
		cleanFrame();
		
		//TODO - popular columnNames e info com o ficheiro binario
		//AQUI TERÁ DE SER UM PROCESSO IO!!!
//		Object[] columnNames = {"Regras"};
//		Object[][] info = new Object[20][1];
		guiRuleList = new JList();
		
		try {
			List<Rule> rules = ruleManager.readObjectsFromFile();
			guiRuleList=new JList(rules.toArray());
			
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		ruleDescriptionField = new JTextArea();
		ruleDescriptionField.setBounds(10, 358, 902, 50);
		panel.add(ruleDescriptionField);
		//table = new JTable(info, columnNames);

		scrollPane = new JScrollPane(guiRuleList);
		scrollPane.setBounds(10, 11, 902, 343);
		panel.add(scrollPane);
		
		btnAddRule = new JButton("Add Rule");
		btnAddRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRulePressed();
			}
		});
		btnAddRule.setBounds(550, 450, 140, 23);
		panel.add(btnAddRule);
		
		btnConfirmRule = new JButton("Confirm Rule");
		btnConfirmRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmRulePressed();
			}
		});
		btnConfirmRule.setBounds(720, 450, 140, 23);
		panel.add(btnConfirmRule);
		
		addListListner();
		
		panel.revalidate();
		panel.repaint();
	}
	
	private void addRulePressed() {
		//TODO - logica para adicionar regras ao binario
		System.out.println("add rule pressed");
	}
	
	private void confirmRulePressed() {
		//TODO - confirmar regra selecioonada como atributo
		System.out.println("confirm rule pressed");
	}
	
	private void addListListner() {
		guiRuleList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	
	        	selectedRule = (Rule)guiRuleList.getSelectedValue();
	        	currentRuleLabel.setText("Current Rule: " + selectedRule.getRuleName());
	        	ruleDescriptionField.setText(selectedRule.printPrettyCondition());
	            System.out.println(guiRuleList.getSelectedValue().toString());
	            
	        }
	    });
	}
	
	private void cleanFrame() {
		if (table != null) {
			table.setVisible(false);
			frmExtractMetrics.remove(table);
		}
		if (scrollPane != null) {
			scrollPane.setVisible(false);
			frmExtractMetrics.remove(scrollPane);
		}
		if (btnAddRule != null) {
			btnAddRule.setVisible(false);
			frmExtractMetrics.remove(btnAddRule);
		}
		if (btnConfirmRule != null) {
			btnConfirmRule.setVisible(false);
			frmExtractMetrics.remove(btnConfirmRule);
		}
		if(guiRuleList != null) {
			guiRuleList.setVisible(false);
			frmExtractMetrics.remove(btnAddRule);
		}
		if(ruleDescriptionField != null) {
			ruleDescriptionField.setVisible(false);
			frmExtractMetrics.remove(ruleDescriptionField);
		}
		System.out.println("LIMPAR");
	}
		
}
