package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import extractor.CodeSmells;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


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
	private JButton btnAddRule;
	private JButton btnConfirmRule;
	
	private JPanel panelAddRules;
	private String rule;
	private JTextField txtNOMmin;
	private JTextField txtLOC_class_min;
	private JTextField txtWMCmin;
	private JTextField txtNOMmax;
	private JTextField txtLOC_class_max;
	private JTextField txtWMCmax;
	private JSeparator separator;
	private JCheckBox chckbxLOC_method;
	private JCheckBox chckbxCYCLO_method;
	private JTextField txtLOC_method_min;
	private JTextField txtLOC_method_max;
	private JTextField txtCYCLOmin;
	private JTextField txtCYCLOmax;
	private JRadioButton rdbtnAND_LONG_METHOD;
	private JRadioButton rdbtnOR_LONG_METHOD;
	private JSeparator separator_1;
	private JSeparator separator_horizontal;
	private JButton btnCancel;
	private JButton btnSaveRule;


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
		rule = "default";
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExtractMetrics = new JFrame();
		//frmExtractMetrics.setVisible(true);
		frmExtractMetrics.setBackground(Color.GRAY);
		frmExtractMetrics.setTitle("Extract metrics");
		frmExtractMetrics.setResizable(false);
		frmExtractMetrics.setBounds(100, 100, 928, 620);
		frmExtractMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setVisible(true);
		panel.setBackground(Color.LIGHT_GRAY);
		frmExtractMetrics.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		txtf_path = new JTextField();
		txtf_path.setBounds(10, 541, 232, 20);
		txtf_path.setEditable(false);
		panel.add(txtf_path);
		txtf_path.setColumns(10);

		btn_folder = new JButton("Folder");
		btn_folder.setBounds(250, 540, 74, 23);
		addChooseListener(btn_folder);
		panel.add(btn_folder);
		
		btnCalculateMetrics = new JButton("Calculate Metrics");
		btnCalculateMetrics.setBounds(770, 540, 140, 23);
		btnCalculateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateMetricsPressed();
			}
		});
		panel.add(btnCalculateMetrics);

		btnFetchXLSX = new JButton("Fetch XLSX File");
		btnFetchXLSX.setBounds(620, 540, 140, 23);
		btnFetchXLSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showXLSXPressed();
			}
		});
		panel.add(btnFetchXLSX);
		
		btnRules = new JButton("Rules");
		btnRules.setBounds(470, 540, 140, 23);
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRulesPressed();
			}
		});
		panel.add(btnRules);
		
		panelAddRules = new JPanel();

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
		scrollPane.setBounds(10, 11, 902, 519);
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
	    		scrollPane.setBounds(10, 11, 902, 519);
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
		panelAddRules.setVisible(false);
		panel.setVisible(true);
		
		//AQUI TERÁ DE SER UM PROCESSO IO!!!
		Object[] columnNames = {"header1"};
		Object[][] info = { {"conteudo1"}, {"conteudo2"}, {"conteudo3"} };

		table = new JTable(info, columnNames);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 902, 419);
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
		
		addTableListner();
		
		panel.revalidate();
		panel.repaint();
	}
	
	
	private void addRulePressed() {
		System.out.println("add rule pressed");
		cleanFrame();
		panel.setVisible(false);
		
		//panelAddRules = new JPanel();
		panelAddRules.setVisible(true);
		frmExtractMetrics.getContentPane().add(panelAddRules, BorderLayout.CENTER);
		panelAddRules.setLayout(null);
		
		JLabel lblSelectMetrics = new JLabel("Select the desired metrics:");
		lblSelectMetrics.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectMetrics.setBounds(47, 77, 173, 16);
		panelAddRules.add(lblSelectMetrics);
		
		JLabel lblGodClass = new JLabel("GOD_CLASS");
		lblGodClass.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGodClass.setBounds(47, 35, 94, 16);
		panelAddRules.add(lblGodClass);
		
		JCheckBox chckbxNOM_class = new JCheckBox("NOM_class:");
		chckbxNOM_class.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxNOM_class.setBounds(123, 115, 97, 23);
		panelAddRules.add(chckbxNOM_class);
		
		JCheckBox chckbxLOC_class = new JCheckBox("LOC_class:");
		chckbxLOC_class.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxLOC_class.setBounds(123, 153, 97, 23);
		panelAddRules.add(chckbxLOC_class);
		
		JCheckBox chckbxWMC_class = new JCheckBox("WMC_class:");
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
		
		JRadioButton rdbtnAND_GOD_CLASS = new JRadioButton("AND");
		rdbtnAND_GOD_CLASS.setBounds(600, 116, 60, 23);
		panelAddRules.add(rdbtnAND_GOD_CLASS);
		
		JRadioButton rdbtnOR_GOD_CLASS = new JRadioButton("OR");
		rdbtnOR_GOD_CLASS.setBounds(600, 154, 60, 23);
		panelAddRules.add(rdbtnOR_GOD_CLASS);
		
		JLabel lblLogicOperators = new JLabel("Select the desired logic operator:");
		lblLogicOperators.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogicOperators.setBounds(548, 77, 202, 16);
		panelAddRules.add(lblLogicOperators);
		
		JLabel lbMin = new JLabel("Minimum");
		lbMin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbMin.setBounds(266, 90, 54, 16);
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
		
		JLabel lblMax = new JLabel("Maximum");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMax.setBounds(359, 90, 54, 16);
		panelAddRules.add(lblMax);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(504, 77, 12, 136);
		panelAddRules.add(separator);
		
		JLabel lblLongMethod = new JLabel("LONG_METHOD");
		lblLongMethod.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLongMethod.setBounds(47, 300, 122, 16);
		panelAddRules.add(lblLongMethod);
		
		JLabel lblSelectMetrics_1 = new JLabel("Select the desired metrics:");
		lblSelectMetrics_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectMetrics_1.setBounds(47, 339, 173, 16);
		panelAddRules.add(lblSelectMetrics_1);
		
		chckbxLOC_method = new JCheckBox("LOC_method:");
		chckbxLOC_method.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxLOC_method.setBounds(123, 378, 119, 23);
		panelAddRules.add(chckbxLOC_method);
		
		chckbxCYCLO_method = new JCheckBox("CYCLO_method:");
		chckbxCYCLO_method.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxCYCLO_method.setBounds(123, 414, 119, 23);
		panelAddRules.add(chckbxCYCLO_method);
		
		txtLOC_method_min = new JTextField();
		txtLOC_method_min.setToolTipText("");
		txtLOC_method_min.setColumns(10);
		txtLOC_method_min.setBounds(248, 380, 86, 20);
		panelAddRules.add(txtLOC_method_min);
		
		txtLOC_method_max = new JTextField();
		txtLOC_method_max.setToolTipText("");
		txtLOC_method_max.setColumns(10);
		txtLOC_method_max.setBounds(344, 380, 86, 20);
		panelAddRules.add(txtLOC_method_max);
		
		txtCYCLOmin = new JTextField();
		txtCYCLOmin.setToolTipText("");
		txtCYCLOmin.setColumns(10);
		txtCYCLOmin.setBounds(248, 416, 86, 20);
		panelAddRules.add(txtCYCLOmin);
		
		txtCYCLOmax = new JTextField();
		txtCYCLOmax.setToolTipText("");
		txtCYCLOmax.setColumns(10);
		txtCYCLOmax.setBounds(344, 416, 86, 20);
		panelAddRules.add(txtCYCLOmax);
		
		JLabel lblMin_1 = new JLabel("Minimum");
		lblMin_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMin_1.setBounds(266, 353, 54, 16);
		panelAddRules.add(lblMin_1);
		
		JLabel lblMax_1 = new JLabel("Maximum");
		lblMax_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMax_1.setBounds(359, 353, 54, 16);
		panelAddRules.add(lblMax_1);
		
		JLabel lblLogicOperators_1 = new JLabel("Select the desired logic operator:");
		lblLogicOperators_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogicOperators_1.setBounds(548, 341, 202, 16);
		panelAddRules.add(lblLogicOperators_1);
		
		rdbtnAND_LONG_METHOD = new JRadioButton("AND");
		rdbtnAND_LONG_METHOD.setBounds(600, 379, 60, 23);
		panelAddRules.add(rdbtnAND_LONG_METHOD);
		
		rdbtnOR_LONG_METHOD = new JRadioButton("OR");
		rdbtnOR_LONG_METHOD.setBounds(600, 415, 60, 23);
		panelAddRules.add(rdbtnOR_LONG_METHOD);
		
		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(504, 335, 12, 130);
		panelAddRules.add(separator_1);
		
		separator_horizontal = new JSeparator();
		separator_horizontal.setBounds(47, 260, 812, 10);
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
		
	}
	
	private void saveRulePressed() {
		System.out.println("save rule pressed");
	}
	
	private void confirmRulePressed() {
		System.out.println("confirm rule pressed");
	}
	
	private void addTableListner() {
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	
	            rule = table.getValueAt(table.getSelectedRow(), 0).toString();
	            System.out.println(rule);
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
		
		System.out.println("LIMPAR");
	}
}
