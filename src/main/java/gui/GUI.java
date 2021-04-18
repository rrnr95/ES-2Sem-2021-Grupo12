package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;


public class GUI {

	private static JFrame frmExtractMetrics;
	private static JTextField txtf_path;
	private JTable table;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExtractMetrics = new JFrame();
		frmExtractMetrics.setBackground(Color.GRAY);
		frmExtractMetrics.setTitle("Extract metrics");
		frmExtractMetrics.setResizable(false);
		frmExtractMetrics.setBounds(100, 100, 928, 620);
		frmExtractMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmExtractMetrics.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		txtf_path = new JTextField();
		txtf_path.setEditable(false);
		txtf_path.setBounds(10, 541, 232, 20);
		panel.add(txtf_path);
		txtf_path.setColumns(10);

		JButton btn_folder = new JButton("Folder");
		addChooseListener(btn_folder);

		btn_folder.setBounds(250, 540, 74, 23);
		panel.add(btn_folder);
		
		JButton btnCalculateMetrics = new JButton("Calculate Metrics");
		btnCalculateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateMetricsPressed();
			}
		});
		btnCalculateMetrics.setBounds(772, 540, 140, 23);
		panel.add(btnCalculateMetrics);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 902, 519);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
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
		
		// TODO thread join  
		
		File project = new File(txtf_path.getText());
		if (project.exists()) {
			
			CodeSmells cs = new CodeSmells(project.getAbsolutePath());
			try {
				cs.init();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			try {
				Thread.sleep(3000);	// temp
				printMetricsGUI(project);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			
			JOptionPane.showMessageDialog(null,"Diretoria Desconhecida");
		}
		
//		printMetricsGUI(new File("C:\\Users\\Utilizador\\eclipse-workspace\\BattleshipCodeCoverage-master\\Battleship"));
	}
	
	
	private void printMetricsGUI(File project) {
		
		FileInputStream excelFIS = null;
		BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        DefaultTableModel model = new DefaultTableModel();
        
		try {
            File excelFile = new File(project.getAbsolutePath() + "\\smells.xlsx");
            
            excelFIS = new FileInputStream(excelFile);
            excelBIS = new BufferedInputStream(excelFIS);
            excelImportToJTable = new XSSFWorkbook(excelBIS);
            XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);

            for (int row = 0; row < excelSheet.getLastRowNum(); row++) {
                XSSFRow excelRow = excelSheet.getRow(row);

                XSSFCell excelMethod_id = excelRow.getCell(0);
                System.out.println(excelMethod_id);
                XSSFCell excelPackage = excelRow.getCell(1);
                XSSFCell excelClass = excelRow.getCell(2);
                XSSFCell excelInner_classes = excelRow.getCell(3);
                XSSFCell excelMethod = excelRow.getCell(4);
                XSSFCell excelNOM_class = excelRow.getCell(5);
                XSSFCell excelLOC_class = excelRow.getCell(6);
                XSSFCell excelWMC_class = excelRow.getCell(7);
                XSSFCell excelIs_God_class = excelRow.getCell(8);
                XSSFCell excelLOC_method = excelRow.getCell(9);
                XSSFCell excelCYCLO_method = excelRow.getCell(10);
                XSSFCell excelIs_long_method = excelRow.getCell(11);

                model.addRow(new Object[]{excelMethod_id, excelPackage, excelClass, excelInner_classes, excelMethod, excelNOM_class, excelLOC_class, excelWMC_class, excelIs_God_class, excelLOC_method, excelCYCLO_method, excelIs_long_method});
            }
            table.setModel(model);  // isto nao esta a funcionar fsr!!!!!!!!!!!
            
           // JOptionPane.showMessageDialog(null, "Imported Successfully!");
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
		// refresh table
		table.revalidate();
        table.repaint();
	}
}
