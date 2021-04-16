package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class GUI {

	private static JFrame frmExtractMetrics;
	private static JTextField txtf_path;

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
		frmExtractMetrics.setBounds(100, 100, 395, 355);
		frmExtractMetrics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmExtractMetrics.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		txtf_path = new JTextField();
		txtf_path.setEditable(false);
		txtf_path.setBounds(40, 40, 232, 20);
		panel.add(txtf_path);
		txtf_path.setColumns(10);

		JButton btn_folder = new JButton("Folder");
		addChooseListener(btn_folder);

		btn_folder.setBounds(282, 39, 74, 23);
		panel.add(btn_folder);
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
	
}
