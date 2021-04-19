package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RulesGUI {
	private static JFrame frame;
	private JPanel panel;
	private JButton btnOk;
	
	public RulesGUI() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.GRAY);
		frame.setTitle("Rules");
		frame.setResizable(false);
		frame.setBounds(100, 100, 928, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OKPressed();
			}
		});
		btnOk.setBounds(470, 540, 140, 23);
		panel.add(btnOk);
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	private void OKPressed() {
		frame.setVisible(false);
		frame.dispose();
	}
	

}
