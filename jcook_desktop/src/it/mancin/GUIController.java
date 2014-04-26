package it.mancin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class GUIController extends Object{	
		
	public JButton btnImpostazioni;
	public JButton btnViewCNC;
	
	static Options options = Options.getInstance();
	FileBrowser fb;
 
	static JFrame frame;
	
	public GUIController(){}	

	public void Frame(){		
		frame = new JFrame(options.getAppTitle());
		JPanel panel = new JPanel();
		BorderLayout bord = new BorderLayout();
		panel.setLayout(bord);
		
		//PANNELLO DI DESTRA
		JPanel panelEast = new RightPanel();
		panelEast.setPreferredSize(new Dimension(200, 400));
		
		//PANNELLO CENTRALE
		final JPanel center = new JPanel();
		GridLayout grid = new GridLayout(0, 1, 20, 20);
		center.setLayout(grid);
		
		//Devo far tardare il caricamento del file explorer
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
                    // Significantly improves the look of the output in
                    // terms of the file names returned by FileSystemView!
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception weTried) {
                }
				fb = new FileBrowser();
				center.add(fb.getGui());	
				fb.showRootFile();				
			}
		});	
		
		
		
		//PANNELLO PRINCIPALE
		panel.add(panelEast, BorderLayout.EAST);
		panel.add(center, BorderLayout.CENTER);
		
		//IMPOSTO IL FRAME
		frame.setContentPane(panel);
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame.setSize(xSize-200,ySize-200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
	
	public void refreshGUI(){
		frame.dispose();
		Frame();		
	}

	
}