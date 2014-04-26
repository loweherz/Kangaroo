package it.mancin;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RightPanel extends JPanel {

	Thread data;
	Thread check = new Thread(new CheckDataRaw());
	
	public static JLabel fileNumber = new JLabel();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5227734420671691308L;

	/**
	 * Create the panel.
	 */
	public RightPanel() {
		setBorder(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0};
		gridBagLayout.rowHeights = new int[]{40, 57, 40, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		
		JButton btnCNC = new JButton("CNC's");
		
		btnCNC.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	//Disabilito ricezione e chiudo la seriale nel caso ci fossero cambiamenti
//				            	logTextArea.setLogText("Action", "Go to machines");
							//Open new frame
							CNCFrame cf = new CNCFrame();
							cf.pack();
							cf.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			        		cf.setVisible(true);			        		
							//sf.gui_SettingsFrame();																								
			            }
			        });						
				}
			}				
		);
		
		panel.add(btnCNC);
		btnCNC.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JButton btnRaw = new JButton("Raw file");
		
		btnRaw.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					data = new Thread(new DataRawFrame());					
					data.start();
				}
			}				
		);
		
		//thread for check data in raw folder
		if(!check.isAlive())
			check.start(); 
		
		panel_1.add(btnRaw);
		btnRaw.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel_1.add(fileNumber);
		fileNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		add(panel_2, gbc_panel_2);
		
		JButton btnSettings = new JButton("Settings");
		
		btnSettings.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	SettingsFrame sf = new SettingsFrame();
							sf.pack();
			        		sf.setVisible(true);
							
			        		//restore main GUI
							MainClass.gui.refreshGUI();
			            }
			        });						
				}
			}				
		);
	
		panel_2.add(btnSettings);
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		add(panel_3, gbc_panel_3);
		
		JButton btnRefreshUi = new JButton("Refresh UI");
		btnRefreshUi.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					MainClass.gui.refreshGUI();
				}
			}				
		);
		panel_3.add(btnRefreshUi);
		btnRefreshUi.setAlignmentX(0.5f);

	}
	

}
