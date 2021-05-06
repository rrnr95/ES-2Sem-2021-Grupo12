package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import backend.RulesManager;
import comparer.MetricComparer;
import extractor.CodeSmells;
import extractor.SharedResource;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

//TODO rp to sr refactor 

public class GUI {

	private static JFrame frmExtractMetrics;
	private static JTextField txtf_path;
	private static JTextField code_smells_path;
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
		
	private RulesManager ruleManager;
	private Rule selectedRule;
	private Rule DEFAULT_RULE;
	private JTextArea ruleDescriptionField;
	private JLabel currentRuleLabel;

	private JPanel panelAddRules;
	private JTextField txtNOMmin;
	private JTextField txtLOC_class_min;
	private JTextField txtWMCmin;
	private JTextField txtNOMmax;
	private JTextField txtLOC_class_max;
	private JTextField txtWMCmax;
	private JSeparator separator;
	private JCheckBox chckbxLOC_method;
	private JCheckBox chckbxCYCLO_method;
	
	private JCheckBox chckbxNOM_class;
	private JCheckBox chckbxLOC_class;
	private JCheckBox chckbxWMC_class;
	
	private JTextField txtLOC_method_min;
	private JTextField txtLOC_method_max;
	private JTextField txtCYCLOmin;
	private JTextField txtCYCLOmax;
	private JRadioButton rdbtnAND_GOD_CLASS;
	private JRadioButton rdbtnOR_GOD_CLASS;
	private ButtonGroup G1;
	private JRadioButton rdbtnAND_LONG_METHOD;
	private JRadioButton rdbtnOR_LONG_METHOD;
	private ButtonGroup G2;
	private JSeparator separator_1;
	private JSeparator separator_horizontal;
	private JButton btnCancel;
	private JButton btnSaveRule;
	private JTextField txtRuleName;
	
	private JButton btnClassQuality;
	private JPanel panel_matrix;
	private JTable table_matrix1;
	private JTable table_matrix2;
	private JLabel lblGodClass1_1;
	private JLabel lblLongMethod1_1;
	private JLabel lblTopTrue1_1;
	private JLabel lblTopFalse1_1;
	private JLabel lblSideTrue1_1;
	private JLabel lblSideFalse1_1;
	private JLabel lblPredicted1_1;
	private JLabel lblActual1_1;
	private JLabel lblPredicted2_1;
	private JLabel lblTopTrue2_1;
	private JLabel lblTopFalse2_1;
	private JLabel lblSideTrue2_1;
	private JLabel lblActual2_1;
	private JLabel lblSideFalse2_1;
	private JButton closeBtn_matrix;
	
	
	


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

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		btnClassQuality();
		frmExtractMetrics.getContentPane().add(panel, BorderLayout.CENTER);
		addChooseListener(btn_folder, txtf_path);
		panel_matrix = new JPanel();
		
		panelAddRules = new JPanel();	
		
	}

	private void btnClassQuality() throws HeadlessException {
		btnRules();
		btnClassQuality.setBounds(710, 512, 200, 23);
	}

	private void btnRules() throws HeadlessException {
		btnFetchXLSX();
		btnRules.setBounds(470, 540, 140, 23);
	}

	private void btnFetchXLSX() throws HeadlessException {
		btnCalculateMetrics();
		btnFetchXLSX.setBounds(620, 540, 140, 23);
	}

	private void btnCalculateMetrics() throws HeadlessException {
		try {
			selectedRule = ruleManager.readObjectsFromFile().get(0);
			DEFAULT_RULE = selectedRule;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		frmExtractMetrics = new JFrame();
		frmExtractMetrics.setBackground(Color.GRAY);
		frmExtractMetrics.setTitle("Extract metrics");
		frmExtractMetrics.setResizable(false);
		frmExtractMetrics.setBounds(100, 100, 928, 620);
		frmExtractMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setVisible(true);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		txtf_path();
		panel.add(txtf_path);
		buildBtn_folder();
		panel.add(btn_folder);
		btnCalculateMetrics = new JButton("Calculate Metrics");
		btnCalculateMetrics.setBounds(770, 540, 140, 23);
		btnCalculateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmExtractMetrics.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				calculateMetricsPressed();
			}
		});
		panel.add(btnCalculateMetrics);
		btnFetchXLSX = new JButton("Fetch XLSX File");
		btnFetchXLSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showXLSXPressed();
			}
		});
		panel.add(btnFetchXLSX);
		btnRules = new JButton("Rules");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRulesPressed();
			}
		});
		panel.add(btnRules);
		currentRuleLabel = new JLabel("Current Rule: " + selectedRule.getRuleName());
		currentRuleLabel.setBounds(10, 516, 140, 23);
		panel.add(currentRuleLabel);
		btnClassQuality = new JButton("Classification Quality");
		btnClassQuality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//showClassificationQualityPressed();
				createDialog();
			}
		});
		panel.add(btnClassQuality);
	}

	private void buildBtn_folder() {
		btn_folder = new JButton("Folder");
		btn_folder.setBounds(250, 540, 74, 23);
	}

	private void txtf_path() {
		txtf_path = new JTextField();
		txtf_path.setBounds(10, 541, 232, 20);
		txtf_path.setEditable(false);
		txtf_path.setColumns(10);
	}

	
	private static void addChooseListener(JButton btn, final JTextField tf) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = fileChooser.showOpenDialog(frmExtractMetrics);

				if(option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					
					if(f.exists())
						tf.setText(f.getPath());
					else
						tf.setText("Invalid");
				}		
			}
		});
	}
	
	private static void addChooseListenerXLSX(JButton btn, final JTextField tf) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = fileChooser.showOpenDialog(frmExtractMetrics);

				if(option == JFileChooser.APPROVE_OPTION) {
					File f = fileChooser.getSelectedFile();
					
					if(f.exists())
						tf.setText(f.getPath());
					else
						tf.setText("Invalid");
				}		
			}
		});
	}
	
	
	private void calculateMetricsPressed() {
		
		File project = new File(txtf_path.getText());
		if (project.exists()) {

			SharedResource rp;
				rp = CodeSmells.init(project.getAbsolutePath(), selectedRule);

			cleanFrame();
			printCalculatedMetrics(rp);
			
		} else {
			
			JOptionPane.showMessageDialog(null,"Diretoria Desconhecida");
		}
		
		frmExtractMetrics.setCursor(Cursor.getDefaultCursor());
	}
	
	
	private void printCalculatedMetrics(SharedResource rp) {
		scrollPane(rp);
		panel.add(scrollPane);
		
	}

	private void scrollPane(SharedResource rp) {
		table(rp);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 902, 500);
	}

	private void table(SharedResource rp) {
		Object[] columnNames = { "Nº total packages", "Nº total de classes", "Nº total de metodos",
				"Nº total de linhas de codigo" };
		HashMap<String, Integer> summary = (HashMap<String, Integer>) rp.statsSummary();
		Object[][] info = { { summary.get("NumPacks"), summary.get("NumClasses"), summary.get("NumMethods"),
				summary.get("NumLinesOfCode") } };
		table = new JTable(info, columnNames);
		table.setEnabled(false);
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
	    		
	            addTableScrollPane(header, data);		
	    		
	    		
	    		
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

	private void addTableScrollPane(Object[] header, Object[][] data) {
		tableToScrollPane(header, data);
		panel.add(scrollPane);
	}

	private void tableToScrollPane(Object[] header, Object[][] data) {
		buildTable(header, data);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 902, 500);
	}

	private void buildTable(Object[] header, Object[][] data) {
		table = new JTable(data, header);
		table.setEnabled(false);
	}
	
	
	private void showRulesPressed() {
		cleanFrame();
		panelAddRules.setVisible(false);
		panel_matrix.setVisible(false);
		panel.setVisible(true);
		

		guiRuleList = new JList();
		
		try {
			List<Rule> rules = ruleManager.readObjectsFromFile();
			guiRuleList=new JList(rules.toArray());
			
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		
		
		ruleDescriptionField = new JTextArea();
		
		ruleDescriptionField.setBounds(10, 358, 902, 50);
		panel.add(ruleDescriptionField);

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
		
		btnConfirmRule = new JButton("Delete Rule");
		btnConfirmRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRulePressed();
			}
		});
		btnConfirmRule.setBounds(720, 450, 140, 23);
		panel.add(btnConfirmRule);
		
		addListListner();
		
		panel.revalidate();
		panel.repaint();
	}
	
	
	private void addRulePressed() {
		cleanFrame();
		clearAddRules();
		panel.setVisible(false);
		
		panelAddRules.setVisible(true);
		frmExtractMetrics.getContentPane().add(panelAddRules, BorderLayout.CENTER);
		panelAddRules.setLayout(null);
		
		JLabel lblSelectMetrics = lblSelectMetrics();
		panelAddRules.add(lblSelectMetrics);
		
		JLabel lblGodClass = lblGodClass();
		panelAddRules.add(lblGodClass);
		
		chckbxNOM_class = new JCheckBox("NOM_class:");
		chckbxNOM_class.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxNOM_class.setBounds(123, 115, 97, 23);
		panelAddRules.add(chckbxNOM_class);
		
		chckbxLOC_class = new JCheckBox("LOC_class:");
		chckbxLOC_class.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxLOC_class.setBounds(123, 153, 97, 23);
		panelAddRules.add(chckbxLOC_class);
		
		chckbxWMC_class = new JCheckBox("WMC_class:");
		chckbxWMC_class.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxWMC_class.setBounds(123, 190, 97, 23);
		panelAddRules.add(chckbxWMC_class);
		
		txtNOMmin = new JTextField();
		txtNOMmin.setToolTipText("");
		txtNOMmin.setBounds(248, 117, 86, 20);
		panelAddRules.add(txtNOMmin);
		txtNOMmin.setColumns(10);
		
		txtLOC_class_min = new JTextField();
		txtLOC_class_min.setBounds(248, 155, 86, 20);
		panelAddRules.add(txtLOC_class_min);
		txtLOC_class_min.setColumns(10);
		
		txtWMCmin = new JTextField();
		txtWMCmin.setBounds(248, 192, 86, 20);
		panelAddRules.add(txtWMCmin);
		txtWMCmin.setColumns(10);
		
		rdbtnAND_GOD_CLASS = new JRadioButton("AND", true);
		rdbtnAND_GOD_CLASS.setActionCommand("AND");
		rdbtnAND_GOD_CLASS.setBounds(600, 116, 60, 23);
		panelAddRules.add(rdbtnAND_GOD_CLASS);
		
		rdbtnOR_GOD_CLASS = new JRadioButton("OR");
		rdbtnOR_GOD_CLASS.setActionCommand("OR");
		rdbtnOR_GOD_CLASS.setBounds(600, 154, 60, 23);
		panelAddRules.add(rdbtnOR_GOD_CLASS);
		
		G1 = new ButtonGroup();
		G1.add(rdbtnAND_GOD_CLASS);
		G1.add(rdbtnOR_GOD_CLASS);
		
		JLabel lblLogicOperators = lblLogicOperators();
		panelAddRules.add(lblLogicOperators);
		
		JLabel lbMin = lbMin();
		panelAddRules.add(lbMin);
		
		txtNOMmax = new JTextField();
		txtNOMmax.setToolTipText("");
		txtNOMmax.setColumns(10);
		txtNOMmax.setBounds(344, 117, 86, 20);
		panelAddRules.add(txtNOMmax);
		
		txtLOC_class_max = new JTextField();
		txtLOC_class_max.setToolTipText("");
		txtLOC_class_max.setColumns(10);
		txtLOC_class_max.setBounds(344, 155, 86, 20);
		panelAddRules.add(txtLOC_class_max);
		
		txtWMCmax = new JTextField();
		txtWMCmax.setToolTipText("");
		txtWMCmax.setColumns(10);
		txtWMCmax.setBounds(344, 192, 86, 20);
		panelAddRules.add(txtWMCmax);
		
		JLabel lblMax = lblMax();
		panelAddRules.add(lblMax);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(504, 77, 12, 136);
		panelAddRules.add(separator);
		
		JLabel lblLongMethod = lblLongMethod();
		panelAddRules.add(lblLongMethod);
		
		JLabel lblSelectMetrics_1 = lblSelectMetrics_1();
		panelAddRules.add(lblSelectMetrics_1);
		
		chckbxLOC_method = new JCheckBox("LOC_method:");
		chckbxLOC_method.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxLOC_method.setBounds(123, 352, 119, 23);
		panelAddRules.add(chckbxLOC_method);
		
		chckbxCYCLO_method = new JCheckBox("CYCLO_method:");
		chckbxCYCLO_method.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxCYCLO_method.setBounds(123, 388, 119, 23);
		panelAddRules.add(chckbxCYCLO_method);
		
		txtLOC_method_min = new JTextField();
		txtLOC_method_min.setToolTipText("");
		txtLOC_method_min.setColumns(10);
		txtLOC_method_min.setBounds(248, 354, 86, 20);
		panelAddRules.add(txtLOC_method_min);
		
		txtLOC_method_max = new JTextField();
		txtLOC_method_max.setToolTipText("");
		txtLOC_method_max.setColumns(10);
		txtLOC_method_max.setBounds(344, 354, 86, 20);
		panelAddRules.add(txtLOC_method_max);
		
		txtCYCLOmin = new JTextField();
		txtCYCLOmin.setToolTipText("");
		txtCYCLOmin.setColumns(10);
		txtCYCLOmin.setBounds(248, 390, 86, 20);
		panelAddRules.add(txtCYCLOmin);
		
		txtCYCLOmax = new JTextField();
		txtCYCLOmax.setToolTipText("");
		txtCYCLOmax.setColumns(10);
		txtCYCLOmax.setBounds(344, 390, 86, 20);
		panelAddRules.add(txtCYCLOmax);
		
		JLabel lblMin_1 = lblMin_1();
		panelAddRules.add(lblMin_1);
		
		JLabel lblMax_1 = lblMax_1();
		panelAddRules.add(lblMax_1);
		
		JLabel lblLogicOperators_1 = lblLogicOperators_1();
		panelAddRules.add(lblLogicOperators_1);
		
		rdbtnAND_LONG_METHOD = new JRadioButton("AND", true);
		rdbtnAND_LONG_METHOD.setActionCommand("AND");
		rdbtnAND_LONG_METHOD.setBounds(600, 353, 60, 23);
		panelAddRules.add(rdbtnAND_LONG_METHOD);
		
		rdbtnOR_LONG_METHOD = new JRadioButton("OR");
		rdbtnOR_LONG_METHOD.setActionCommand("OR");
		rdbtnOR_LONG_METHOD.setBounds(600, 389, 60, 23);
		panelAddRules.add(rdbtnOR_LONG_METHOD);
		
		G2 = new ButtonGroup();
		G2.add(rdbtnAND_LONG_METHOD);
		G2.add(rdbtnOR_LONG_METHOD);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(504, 309, 12, 114);
		panelAddRules.add(separator_1);
		
		separator_horizontal = new JSeparator();
		separator_horizontal.setBounds(47, 242, 812, 10);
		panelAddRules.add(separator_horizontal);
		
		btnSaveRule = new JButton("Save Rule");
		btnSaveRule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRulePressed();
			}
		});
		btnSaveRule.setBounds(745, 528, 114, 23);
		panelAddRules.add(btnSaveRule);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRulesPressed();
			}
		});
		btnCancel.setBounds(54, 528, 104, 23);
		panelAddRules.add(btnCancel);
		
		JSeparator separator_horizontal_1 = new JSeparator();
		separator_horizontal_1.setBounds(47, 444, 812, 10);
		panelAddRules.add(separator_horizontal_1);
		
		JLabel lblRuleName = lblRuleName();
		panelAddRules.add(lblRuleName);
		
		txtRuleName = new JTextField();
		txtRuleName.setToolTipText("");
		txtRuleName.setColumns(10);
		txtRuleName.setBounds(165, 474, 180, 20);
		panelAddRules.add(txtRuleName);
		
	}

	private JLabel lblSelectMetrics_1() {
		JLabel lblSelectMetrics_1 = new JLabel("Select the desired metrics:");
		lblSelectMetrics_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectMetrics_1.setBounds(47, 313, 173, 16);
		return lblSelectMetrics_1;
	}

	private JLabel lblSelectMetrics() {
		JLabel lblSelectMetrics = new JLabel("Select the desired metrics:");
		lblSelectMetrics.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectMetrics.setBounds(47, 77, 173, 16);
		return lblSelectMetrics;
	}

	private JLabel lblRuleName() {
		JLabel lblRuleName = new JLabel("Name of the Rule:");
		lblRuleName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRuleName.setBounds(47, 471, 152, 23);
		return lblRuleName;
	}

	private JLabel lblMin_1() {
		JLabel lblMin_1 = new JLabel("Minimum");
		lblMin_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMin_1.setBounds(266, 327, 54, 16);
		return lblMin_1;
	}

	private JLabel lblMax_1() {
		JLabel lblMax_1 = new JLabel("Maximum");
		lblMax_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMax_1.setBounds(359, 327, 54, 16);
		return lblMax_1;
	}

	private JLabel lblMax() {
		JLabel lblMax = new JLabel("Maximum");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMax.setBounds(359, 90, 54, 16);
		return lblMax;
	}

	private JLabel lblLongMethod() {
		JLabel lblLongMethod = new JLabel("LONG_METHOD");
		lblLongMethod.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLongMethod.setBounds(47, 274, 122, 16);
		return lblLongMethod;
	}

	private JLabel lblLogicOperators_1() {
		JLabel lblLogicOperators_1 = new JLabel("Select the desired logic operator:");
		lblLogicOperators_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogicOperators_1.setBounds(548, 315, 202, 16);
		return lblLogicOperators_1;
	}

	private JLabel lblLogicOperators() {
		JLabel lblLogicOperators = new JLabel("Select the desired logic operator:");
		lblLogicOperators.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogicOperators.setBounds(548, 77, 202, 16);
		return lblLogicOperators;
	}

	private JLabel lblGodClass() {
		JLabel lblGodClass = new JLabel("GOD_CLASS");
		lblGodClass.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGodClass.setBounds(47, 35, 94, 16);
		return lblGodClass;
	}

	private JLabel lbMin() {
		JLabel lbMin = new JLabel("Minimum");
		lbMin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbMin.setBounds(266, 90, 54, 16);
		return lbMin;
	}
	
	private void saveRulePressed() {
		
		if (! validName(txtRuleName)) {
			JOptionPane.showMessageDialog(null,"Choose a valid name for the rule");
		}else if(!validRuleSelection()) {
			JOptionPane.showMessageDialog(null,"Select at least one parameter");
		}
		
		
		else {
			
			String name = txtRuleName.getText();
			
			int NOMmin = -1; //DEFAULT_RULE.getNomClassMin();
			int NOMmax = -1; //DEFAULT_RULE.getNomClassMax();
			
			int LOCclassmin = -1; //DEFAULT_RULE.getLocClassMin();
			int LOCclassmax = -1; //DEFAULT_RULE.getLocClassMax();
			
			int WMCmin = -1; //DEFAULT_RULE.getWmcClassMin();
			int WMCmax = -1; //DEFAULT_RULE.getWmcClassMax();
			
			boolean classConjunction = DEFAULT_RULE.isClassRulesConjunction();
			
			int LOCmin = -1; //DEFAULT_RULE.getLocMethodMin();
			int LOCmax = -1; //DEFAULT_RULE.getLocMethodMax();
			
			int CYCLOmin = -1; //DEFAULT_RULE.getCycloMethodMin();
			int CYCLOmax = -1; //DEFAULT_RULE.getCycloMethodMax();
			
			boolean methodConjunction = DEFAULT_RULE.isMethodRulesConjunction();
			Object error = false;
			
			try {
				if (chckbxNOM_class.isSelected()) {
					NOMmin = Integer.parseInt(txtNOMmin.getText());
					NOMmax = Integer.parseInt(txtNOMmax.getText());
					error = validInput(NOMmin, NOMmax);
				}
				if (chckbxLOC_class.isSelected()) {
					LOCclassmin = Integer.parseInt(txtLOC_class_min.getText());
					LOCclassmax = Integer.parseInt(txtLOC_class_max.getText());
					error = validInput(LOCclassmin, LOCclassmax);
				}
				if(chckbxWMC_class.isSelected()) {
					WMCmin = Integer.parseInt(txtWMCmin.getText());
					WMCmax = Integer.parseInt(txtWMCmax.getText());
					error = validInput(WMCmin, WMCmax);
				}		
				if (G1.getSelection().getActionCommand().equals("AND")) {
					classConjunction = true;
				}
				if(chckbxLOC_method.isSelected()) {
					LOCmin = Integer.parseInt(txtLOC_method_min.getText());
					LOCmax = Integer.parseInt(txtLOC_method_max.getText());
					error = validInput(LOCmin, LOCmax);
				}
				if(chckbxCYCLO_method.isSelected()) {
					CYCLOmin = Integer.parseInt(txtCYCLOmin.getText());
					CYCLOmax = Integer.parseInt( txtCYCLOmax.getText());
					error = validInput(CYCLOmin, CYCLOmax);
				}
				Rule rule = buildRule(name, NOMmin, NOMmax, LOCclassmin, LOCclassmax, WMCmin, WMCmax, classConjunction,
						LOCmin, LOCmax, CYCLOmin, CYCLOmax, methodConjunction);
				try {
					ruleManager.addRuleToFile(rule);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Apenas é possivel escolher valores numéricos \n O valor maximo deverá ser maior que o minimo");
			}

		}
		showRulesPressed();
	}

	private Rule buildRule(String name, int NOMmin, int NOMmax, int LOCclassmin, int LOCclassmax, int WMCmin,
			int WMCmax, boolean classConjunction, int LOCmin, int LOCmax, int CYCLOmin, int CYCLOmax,
			boolean methodConjunction) {
		methodConjunction = isMethodConjunction(methodConjunction);
		Rule rule = new Rule(name, NOMmin, NOMmax, LOCclassmin, LOCclassmax, WMCmin, WMCmax, classConjunction, LOCmin,
				LOCmax, CYCLOmin, CYCLOmax, methodConjunction);
		return rule;
	}

	private boolean isMethodConjunction(boolean methodConjunction) {
		if (G2.getSelection().getActionCommand().equals("AND")) {
			methodConjunction = true;
		}
		showRulesPressed();
		return methodConjunction;
	}
	
	private void deleteRulePressed() {
		if (selectedRule.toString().equals("Default")) {
			JOptionPane.showMessageDialog(null, "Não é possivel apagar esta regra");
		}
		else {
			try {
				ruleManager.deleteRuleFromFile(selectedRule);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			showRulesPressed();
		}
	}
	
	private void addListListner() {
		guiRuleList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	

	        	selectedRule = (Rule)guiRuleList.getSelectedValue();
	        	currentRuleLabel.setText("Current Rule: " + selectedRule.getRuleName());
	        	ruleDescriptionField.setText(selectedRule.printPrettyCondition());

	        }
	    });
	}
	
	private void createDialog(){
		final JDialog modelDialog = new JDialog(frmExtractMetrics, "Code Smells Classification Quality", 
		         true);
		      modelDialog.setBounds(130, 130, 300, 200);
		      Container dialogContainer = modelDialog.getContentPane();
		      dialogContainer.setLayout(new BorderLayout());
		      dialogContainer.add(new JLabel("      Please indicate your Code Smells directory.")
		      , BorderLayout.CENTER);
		      JPanel panel1 = new JPanel();
		      panel1.setLayout(new FlowLayout());
		      JButton folderButton = new JButton("xlsx file");
		      addChooseListenerXLSX(folderButton, code_smells_path = new JTextField());
		      
		      JButton okButton = new JButton("ok");
		      okButton.addActionListener(new ActionListener() {
		         @Override
		         public void actionPerformed(ActionEvent e) {
		        	 
		        	if(code_smells_path.getText().equals("Invalid") || code_smells_path.getText().equals("")) 
		         		 JOptionPane.showMessageDialog(null, "Please choose a valid directory.");
		        	else {
		        		modelDialog.setVisible(false);
		        		frmExtractMetrics.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		        		showClassificationQualityPressed();
		        	}
		         }
		      });

		      panel1.add(folderButton);
		      panel1.add(okButton);
		      dialogContainer.add(panel1, BorderLayout.SOUTH);
		      modelDialog.setVisible(true);
		      modelDialog.setVisible(false);

	   }
	
	protected void showClassificationQualityPressed() {
		
		/*
		 * aplicar metricas com a nossa rule ao jasml
		 * 
		 * 
		 */
		
		SharedResource rp = buildSR();
		MetricComparer mc = buildMetricComparer();

		mc.formPairs();
		
		cleanFrame();		
		panel.setVisible(false);	
		
		panel_matrix.setBackground(Color.LIGHT_GRAY);
		frmExtractMetrics.getContentPane().add(panel_matrix, BorderLayout.CENTER);
		panel_matrix.setLayout(null);
		panel_matrix.setVisible(true);
		
		int gc_vp = mc.getGodClassConfMatrixValues().getVP();
		int gc_vn = mc.getGodClassConfMatrixValues().getVN();
		int gc_fp = mc.getGodClassConfMatrixValues().getFP();
		int gc_fn = mc.getGodClassConfMatrixValues().getFN();
		
		Object[][] dataMatrix1 = {
									{gc_vp, gc_fn},
									{gc_fp, gc_vn}
		};
		String[] colum = {""," "};
		
		class CenterTableCellRenderer extends DefaultTableCellRenderer { 
			  protected  CenterTableCellRenderer() {
			    setHorizontalAlignment(JLabel.CENTER);
			  } 
			   
		} 
		
		table_matrix1 = new JTable(dataMatrix1, colum);
		table_matrix1.setRowHeight(150);
		table_matrix1.getColumn("").setCellRenderer(new CenterTableCellRenderer());
		table_matrix1.getColumn(" ").setCellRenderer(new CenterTableCellRenderer());
	
		
		table_matrix1.setBounds(76, 156, 300, 300);
		panel_matrix.add(table_matrix1);
		
		int lm_vp = mc.getLongMethodConfMatrixValues().getVP();
		int lm_vn = mc.getLongMethodConfMatrixValues().getVN();
		int lm_fp = mc.getLongMethodConfMatrixValues().getFP();
		int lm_fn = mc.getLongMethodConfMatrixValues().getFN();
		
		Object[][] dataMatrix2 = {
									{lm_vp, lm_fn},
									{lm_fp, lm_vn}
		};
		
		int correctValues = gc_vp + gc_vn + lm_vp + lm_vn;
		int errors = gc_fp + gc_fn + lm_fp + lm_fn;
		
		table_matrix2 = new JTable(dataMatrix2,colum);
		table_matrix2.setRowHeight(150);
		table_matrix2.getColumn("").setCellRenderer(new CenterTableCellRenderer());
		table_matrix2.getColumn(" ").setCellRenderer(new CenterTableCellRenderer());
		table_matrix2.setBounds(535, 156, 300, 300);
		panel_matrix.add(table_matrix2);
		
		lblGodClass1_1 = new JLabel("GodClass");
		lblGodClass1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblGodClass1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGodClass1_1.setBounds(76, 67, 300, 19);
		panel_matrix.add(lblGodClass1_1);
		
		lblLongMethod1_1 = new JLabel("LongMethod");
		lblLongMethod1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLongMethod1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLongMethod1_1.setBounds(535, 69, 300, 19);
		panel_matrix.add(lblLongMethod1_1);
		
		lblTopTrue1_1 = new JLabel("True");
		lblTopTrue1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopTrue1_1.setBounds(76, 131, 150, 14);
		panel_matrix.add(lblTopTrue1_1);
		
		lblTopFalse1_1 = new JLabel("False");
		lblTopFalse1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopFalse1_1.setBounds(226, 131, 150, 14);
		panel_matrix.add(lblTopFalse1_1);
		
		lblSideTrue1_1 = new JLabel("True");
		lblSideTrue1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideTrue1_1.setBounds(44, 156, 32, 150);
		panel_matrix.add(lblSideTrue1_1);
		
		lblSideFalse1_1 = new JLabel("False");
		lblSideFalse1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideFalse1_1.setBounds(44, 306, 32, 150);
		panel_matrix.add(lblSideFalse1_1);
		
		lblPredicted1_1 = new JLabel("PREDICTED");
		lblPredicted1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPredicted1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPredicted1_1.setBounds(76, 97, 300, 14);
		panel_matrix.add(lblPredicted1_1);
		
		lblActual1_1 = new JLabel("<html>A<br>C<br>T<br>U<br>A<br> L</html>");
		lblActual1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblActual1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblActual1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblActual1_1.setBounds(0, 156, 46, 300);
		panel_matrix.add(lblActual1_1);
		
		lblPredicted2_1 = new JLabel("PREDICTED");
		lblPredicted2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPredicted2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPredicted2_1.setBounds(535, 97, 300, 14);
		panel_matrix.add(lblPredicted2_1);
		
		lblTopTrue2_1 = new JLabel("True");
		lblTopTrue2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopTrue2_1.setBounds(535, 131, 150, 14);
		panel_matrix.add(lblTopTrue2_1);
		
		lblTopFalse2_1 = new JLabel("False");
		lblTopFalse2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopFalse2_1.setBounds(685, 131, 150, 14);
		panel_matrix.add(lblTopFalse2_1);
		
		lblSideTrue2_1 = new JLabel("True");
		lblSideTrue2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideTrue2_1.setBounds(503, 156, 32, 150);
		panel_matrix.add(lblSideTrue2_1);
		
		lblActual2_1 = new JLabel("<html>A<br>C<br>T<br>U<br>A<br> L</html>");
		lblActual2_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblActual2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblActual2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblActual2_1.setBounds(459, 156, 46, 300);
		panel_matrix.add(lblActual2_1);
		
		lblSideFalse2_1 = new JLabel("False");
		lblSideFalse2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSideFalse2_1.setBounds(503, 306, 32, 150);
		panel_matrix.add(lblSideFalse2_1);
		
		JLabel lblCorrectness = new JLabel("Number of correct predictions = " + correctValues);
		lblCorrectness.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblCorrectness.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCorrectness.setBounds(535, 500, 300, 25);
		panel_matrix.add(lblCorrectness);
		
		JLabel lblErrors = new JLabel("Number of wrong predictions = " + errors);
		lblErrors.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblErrors.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblErrors.setBounds(535, 520, 300, 25);
		panel_matrix.add(lblErrors);
		
		closeBtn_matrix = new JButton("Close");
		closeBtn_matrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_matrix.setVisible(false);
				panel.setVisible(true);
			}
		});
		closeBtn_matrix.setBounds(54, 528, 104, 23);
		panel_matrix.add(closeBtn_matrix);
		
		frmExtractMetrics.setCursor(Cursor.getDefaultCursor());

	}

	private MetricComparer buildMetricComparer() {
		String CodeSmellsBaseline = code_smells_path.getText();
		String CodeSmellsCalculated = txtf_path.getText()  + "\\smells.xlsx";
		MetricComparer mc = new MetricComparer(CodeSmellsCalculated, CodeSmellsBaseline);
		return mc;
	}

	private SharedResource buildSR() {
		SharedResource rp;
		rp = CodeSmells.init(txtf_path.getText(), selectedRule);
		return rp;
	}
	
	
	private boolean validInput (int min, int max) {
		if (max >= min) return false;
		else throw new NumberFormatException();
	}
	
	private boolean validName (JTextField name) {
		if (name.getText().equals("")) {
			return false;
		}
		for (int i = 0; i < guiRuleList.getModel().getSize(); i++) {
			Object item = guiRuleList.getModel().getElementAt(i);
			String itemName = item.toString();
			if (itemName.equals(name.getText())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean validRuleSelection() {
		return chckbxNOM_class.isSelected() 
				|| chckbxLOC_class.isSelected() 
				|| chckbxWMC_class.isSelected() 
				|| chckbxLOC_method.isSelected() 
				|| chckbxCYCLO_method.isSelected();
	}
	
	private void cleanFrame() {
		frmExtractMetrics();
	}

	private void frmExtractMetrics() {
		frmExtMetricsTable();
		if (table != null) {
			frmExtractMetrics.remove(table);
		}
		frmExtMetricsScrollPane();
		if (scrollPane != null) {
			frmExtractMetrics.remove(scrollPane);
		}
		btnAddRule();
		if (btnAddRule != null) {
			frmExtractMetrics.remove(btnAddRule);
		}
		btnConfirmRule();
		if (btnConfirmRule != null) {
			frmExtractMetrics.remove(btnConfirmRule);
		}
		guiRuleList();
		if (guiRuleList != null) {
			frmExtractMetrics.remove(btnAddRule);
		}
		ruleDescriptionField();
		if (ruleDescriptionField != null) {
			frmExtractMetrics.remove(ruleDescriptionField);
		}
		table_matrix1();
		if (table_matrix1 != null) {
			frmExtractMetrics.remove(table_matrix1);
		}
		table_matrix2();
		if (table_matrix2 != null) {
			frmExtractMetrics.remove(table_matrix2);
		}
		lblGodClass1_1();
		if (lblGodClass1_1 != null) {
			frmExtractMetrics.remove(lblGodClass1_1);
		}
		lblLongMethod1_1();
		if (lblLongMethod1_1 != null) {
			frmExtractMetrics.remove(lblLongMethod1_1);
		}
		lblTopTrue1_1();
		if (lblTopTrue1_1 != null) {
			frmExtractMetrics.remove(lblTopTrue1_1);
		}
		lblTopFalse1_1();
		if (lblTopFalse1_1 != null) {
			frmExtractMetrics.remove(lblTopFalse1_1);
		}
		lblSideTrue1_1();
		if (lblSideTrue1_1 != null) {
			frmExtractMetrics.remove(lblSideTrue1_1);
		}
		lblSideFalse1_1();
		if (lblSideFalse1_1 != null) {
			frmExtractMetrics.remove(lblSideFalse1_1);
		}
		lblPredicted1_1();
		if (lblPredicted1_1 != null) {
			frmExtractMetrics.remove(lblPredicted1_1);
		}
		lblActual1_1();
		if (lblActual1_1 != null) {
			frmExtractMetrics.remove(lblActual1_1);
		}
		lblPredicted2_1();
		if (lblPredicted2_1 != null) {
			frmExtractMetrics.remove(lblPredicted2_1);
		}
		lblTopTrue2_1();
		if (lblTopTrue2_1 != null) {
			frmExtractMetrics.remove(lblTopTrue2_1);
		}
		lblTopFalse2_1();
		if (lblTopFalse2_1 != null) {
			frmExtractMetrics.remove(lblTopFalse2_1);
		}
		lblSideTrue2_1();
		if (lblSideTrue2_1 != null) {
			frmExtractMetrics.remove(lblSideTrue2_1);
		}
		lblActual2_1();
		if (lblActual2_1 != null) {
			frmExtractMetrics.remove(lblActual2_1);
		}
		lblSideFalse2_1();
		if (lblSideFalse2_1 != null) {
			frmExtractMetrics.remove(lblSideFalse2_1);
		}
		closeBtn_matrix();
		if (closeBtn_matrix != null) {
			frmExtractMetrics.remove(closeBtn_matrix);
		}
	}

	private void table_matrix2() {
		if (table_matrix2 != null) {
			table_matrix2.setVisible(false);
		}
	}

	private void table_matrix1() {
		if (table_matrix1 != null) {
			table_matrix1.setVisible(false);
		}
	}

	private void frmExtMetricsTable() {
		if (table != null) {
			table.setVisible(false);
		}
	}

	private void frmExtMetricsScrollPane() {
		if (scrollPane != null) {
			scrollPane.setVisible(false);
		}
	}

	private void ruleDescriptionField() {
		if (ruleDescriptionField != null) {
			ruleDescriptionField.setVisible(false);
		}
	}

	private void lblTopTrue2_1() {
		if (lblTopTrue2_1 != null) {
			lblTopTrue2_1.setVisible(false);
		}
	}

	private void lblTopTrue1_1() {
		if (lblTopTrue1_1 != null) {
			lblTopTrue1_1.setVisible(false);
		}
	}

	private void lblTopFalse2_1() {
		if (lblTopFalse2_1 != null) {
			lblTopFalse2_1.setVisible(false);
		}
	}

	private void lblTopFalse1_1() {
		if (lblTopFalse1_1 != null) {
			lblTopFalse1_1.setVisible(false);
		}
	}

	private void lblSideTrue2_1() {
		if (lblSideTrue2_1 != null) {
			lblSideTrue2_1.setVisible(false);
		}
	}

	private void lblSideTrue1_1() {
		if (lblSideTrue1_1 != null) {
			lblSideTrue1_1.setVisible(false);
		}
	}

	private void lblSideFalse2_1() {
		if (lblSideFalse2_1 != null) {
			lblSideFalse2_1.setVisible(false);
		}
	}

	private void lblActual1_1() {
		if (lblActual1_1 != null) {
			lblActual1_1.setVisible(false);
		}
	}

	private void guiRuleList() {
		if (guiRuleList != null) {
			guiRuleList.setVisible(false);
		}
	}

	private void closeBtn_matrix() {
		if (closeBtn_matrix != null) {
			closeBtn_matrix.setVisible(false);
		}
	}

	private void btnAddRule() {
		if (btnAddRule != null) {
			btnAddRule.setVisible(false);
		}
	}

	private void btnConfirmRule() {
		if (btnConfirmRule != null) {
			btnConfirmRule.setVisible(false);
		}
	}

	private void lblActual2_1() {
		if (lblActual2_1 != null) {
			lblActual2_1.setVisible(false);
		}
	}

	private void lblGodClass1_1() {
		if (lblGodClass1_1 != null) {
			lblGodClass1_1.setVisible(false);
		}
	}

	private void lblLongMethod1_1() {
		if (lblLongMethod1_1 != null) {
			lblLongMethod1_1.setVisible(false);
		}
	}

	private void lblPredicted1_1() {
		if (lblPredicted1_1 != null) {
			lblPredicted1_1.setVisible(false);
		}
	}

	private void lblPredicted2_1() {
		if (lblPredicted2_1 != null) {
			lblPredicted2_1.setVisible(false);
		}
	}

	private void lblSideFalse1_1() {
		if (lblSideFalse1_1 != null) {
			lblSideFalse1_1.setVisible(false);
		}
	}
	
	
	private void clearAddRules() {
		frmClearExtractMetrics();
		if (G1 != null) {
			G1 = null;
		}
		if (G2 != null) {
			G2 = null;
		}
	}

	private void frmClearExtractMetrics() {
		txtNOMmin();
		if (txtNOMmin != null) {
			frmExtractMetrics.remove(txtNOMmin);
		}
		txtLOC_class_min();
		if (txtLOC_class_min != null) {
			frmExtractMetrics.remove(txtLOC_class_min);
		}
		txtWMCmin();
		if (txtWMCmin != null) {
			frmExtractMetrics.remove(txtWMCmin);
		}
		txtNOMmax();
		if (txtNOMmax != null) {
			frmExtractMetrics.remove(txtNOMmax);
		}
		txtLOC_class_max();
		if (txtLOC_class_max != null) {
			frmExtractMetrics.remove(txtLOC_class_max);
		}
		txtWMCmax();
		if (txtWMCmax != null) {
			frmExtractMetrics.remove(txtWMCmax);
		}
		chckbxLOC_method();
		if (chckbxLOC_method != null) {
			frmExtractMetrics.remove(chckbxLOC_method);
		}
		chckbxCYCLO_method();
		if (chckbxCYCLO_method != null) {
			frmExtractMetrics.remove(chckbxCYCLO_method);
		}
		txtLOC_method_min();
		if (txtLOC_method_min != null) {
			frmExtractMetrics.remove(txtLOC_method_min);
		}
		txtLOC_method_max();
		if (txtLOC_method_max != null) {
			frmExtractMetrics.remove(txtLOC_method_max);
		}
		txtCYCLOmin();
		if (txtCYCLOmin != null) {
			frmExtractMetrics.remove(txtCYCLOmin);
		}
		txtCYCLOmax();
		if (txtCYCLOmax != null) {
			frmExtractMetrics.remove(txtCYCLOmax);
		}
		rdbtnAND_GOD_CLASS();
		if (rdbtnAND_GOD_CLASS != null) {
			frmExtractMetrics.remove(rdbtnAND_GOD_CLASS);
		}
		rdbtnOR_GOD_CLASS();
		if (rdbtnOR_GOD_CLASS != null) {
			frmExtractMetrics.remove(rdbtnOR_GOD_CLASS);
		}
		rdbtnAND_LONG_METHOD();
		if (rdbtnAND_LONG_METHOD != null) {
			frmExtractMetrics.remove(rdbtnAND_LONG_METHOD);
		}
		rdbtnOR_LONG_METHOD();
		if (rdbtnOR_LONG_METHOD != null) {
			frmExtractMetrics.remove(rdbtnOR_LONG_METHOD);
		}
		chckbxNOM_class();
		if (chckbxNOM_class != null) {
			frmExtractMetrics.remove(chckbxNOM_class);
		}
		chckbxLOC_class();
		if (chckbxLOC_class != null) {
			frmExtractMetrics.remove(chckbxLOC_class);
		}
		chckbxWMC_class();
		if (chckbxWMC_class != null) {
			frmExtractMetrics.remove(chckbxWMC_class);
		}
		txtRuleName();
		if (txtRuleName != null) {
			frmExtractMetrics.remove(txtRuleName);
		}
	}

	private void txtWMCmin() {
		if (txtWMCmin != null) {
			txtWMCmin.setVisible(false);
		}
	}

	private void txtWMCmax() {
		if (txtWMCmax != null) {
			txtWMCmax.setVisible(false);
		}
	}

	private void txtRuleName() {
		if (txtRuleName != null) {
			txtRuleName.setVisible(false);
		}
	}

	private void txtNOMmin() {
		if (txtNOMmin != null) {
			txtNOMmin.setVisible(false);
		}
	}

	private void txtNOMmax() {
		if (txtNOMmax != null) {
			txtNOMmax.setVisible(false);
		}
	}

	private void txtLOC_method_min() {
		if (txtLOC_method_min != null) {
			txtLOC_method_min.setVisible(false);
		}
	}

	private void txtLOC_method_max() {
		if (txtLOC_method_max != null) {
			txtLOC_method_max.setVisible(false);
		}
	}

	private void txtLOC_class_min() {
		if (txtLOC_class_min != null) {
			txtLOC_class_min.setVisible(false);
		}
	}

	private void txtLOC_class_max() {
		if (txtLOC_class_max != null) {
			txtLOC_class_max.setVisible(false);
		}
	}

	private void txtCYCLOmin() {
		if (txtCYCLOmin != null) {
			txtCYCLOmin.setVisible(false);
		}
	}

	private void txtCYCLOmax() {
		if (txtCYCLOmax != null) {
			txtCYCLOmax.setVisible(false);
		}
	}

	private void rdbtnOR_LONG_METHOD() {
		if (rdbtnOR_LONG_METHOD != null) {
			rdbtnOR_LONG_METHOD.setVisible(false);
		}
	}

	private void rdbtnOR_GOD_CLASS() {
		if (rdbtnOR_GOD_CLASS != null) {
			rdbtnOR_GOD_CLASS.setVisible(false);
		}
	}

	private void rdbtnAND_LONG_METHOD() {
		if (rdbtnAND_LONG_METHOD != null) {
			rdbtnAND_LONG_METHOD.setVisible(false);
		}
	}

	private void rdbtnAND_GOD_CLASS() {
		if (rdbtnAND_GOD_CLASS != null) {
			rdbtnAND_GOD_CLASS.setVisible(false);
		}
	}

	private void chckbxWMC_class() {
		if (chckbxWMC_class != null) {
			chckbxWMC_class.setVisible(false);
		}
	}

	private void chckbxNOM_class() {
		if (chckbxNOM_class != null) {
			chckbxNOM_class.setVisible(false);
		}
	}

	private void chckbxLOC_method() {
		if (chckbxLOC_method != null) {
			chckbxLOC_method.setVisible(false);
		}
	}

	private void chckbxLOC_class() {
		if (chckbxLOC_class != null) {
			chckbxLOC_class.setVisible(false);
		}
	}

	private void chckbxCYCLO_method() {
		if (chckbxCYCLO_method != null) {
			chckbxCYCLO_method.setVisible(false);
		}
	}
}
