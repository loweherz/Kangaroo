package it.mancin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

public class SettingsFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2896104245467168751L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldRoot;
	private JTextField textFieldRaw;
	private JTextField textFieldServer;
	
	static Options options = Options.getInstance();
	

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			Settings dialog = new Settings();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog instance.
	 */
	public SettingsFrame(){
		setModal(true);	
		setTitle("Settings");
		setBounds(100, 100, 511, 268);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 10, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JPanel panelRootDirectory = new JPanel();
			contentPanel.add(panelRootDirectory);
			{
				JLabel lblRootDirectory = new JLabel("Root directory");
				panelRootDirectory.add(lblRootDirectory);
				lblRootDirectory.setHorizontalAlignment(SwingConstants.LEFT);
			}
			{
				textFieldRoot = new JTextField();
				textFieldRoot.setText(options.getRootFolder());
				panelRootDirectory.add(textFieldRoot);				
				textFieldRoot.setColumns(40);
			}
			{
				JButton chooserRoot = new JButton("Scegli...");
				chooserRoot.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
					    JFileChooser chooser = new JFileChooser(); 
					    chooser.setCurrentDirectory(new java.io.File("."));
					    chooser.setDialogTitle("Root Folder");
					    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					    //
					    // disable the "All files" option.
					    //
					    chooser.setAcceptAllFileFilterUsed(false);
					    //    
					    if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) { 
					      textFieldRoot.setText(chooser.getSelectedFile().toString());
					    }
					    else {
					      System.out.println("No Selection ");
					      }
					}	
				}				
				);
				panelRootDirectory.add(chooserRoot);
			}
		}
		{
			JPanel panelRawDirectory = new JPanel();
			contentPanel.add(panelRawDirectory);
			{
				JLabel lblRawDirectory = new JLabel("Raw directory");
				lblRawDirectory.setHorizontalAlignment(SwingConstants.LEFT);
				panelRawDirectory.add(lblRawDirectory);
			}
			{
				textFieldRaw = new JTextField();
				textFieldRaw.setColumns(40);
				textFieldRaw.setText(options.getRawFolder());
				panelRawDirectory.add(textFieldRaw);
			}
			{
				JButton chooserRaw = new JButton("Scegli...");
				chooserRaw.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						
					    JFileChooser chooser = new JFileChooser(); 
					    chooser.setCurrentDirectory(new java.io.File("."));
					    chooser.setDialogTitle("Root Folder");
					    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					    //
					    // disable the "All files" option.
					    //
					    chooser.setAcceptAllFileFilterUsed(false);
					    //    
					    if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) { 
					    	textFieldRaw.setText(chooser.getSelectedFile().toString());
					    }
					    else {
					      System.out.println("No Selection ");
					      }
					}	
				}				
				);
				panelRawDirectory.add(chooserRaw);
			}
		}
		
		{
			JPanel panelServer = new JPanel();
			contentPanel.add(panelServer);
			{
				JLabel lblServer = new JLabel("Server address");
				panelServer.add(lblServer);
				lblServer.setHorizontalAlignment(SwingConstants.LEFT);
			}
			{
				textFieldServer = new JTextField();
				textFieldServer.setText(options.getServer());
				panelServer.add(textFieldServer);				
				textFieldServer.setColumns(40);
			}
//			{
//				JButton chooserRoot = new JButton("Scegli...");
//				chooserRoot.addActionListener(new ActionListener(){
//					public void actionPerformed(ActionEvent e){
//						
//					    JFileChooser chooser = new JFileChooser(); 
//					    chooser.setCurrentDirectory(new java.io.File("."));
//					    chooser.setDialogTitle("Root Folder");
//					    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//					    //
//					    // disable the "All files" option.
//					    //
//					    chooser.setAcceptAllFileFilterUsed(false);
//					    //    
//					    if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) { 
//					      textFieldRoot.setText(chooser.getSelectedFile().toString());
//					    }
//					    else {
//					      System.out.println("No Selection ");
//					      }
//					}	
//				}				
//				);
//				panelRootDirectory.add(chooserRoot);
//			}
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.setActionCommand("Save");
				saveButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String rootFolder = textFieldRoot.getText().toString();				
						String rawFolder = textFieldRaw.getText().toString();
						String server = textFieldServer.getText().toString();
						
						options.setRootFolder(rootFolder);
						options.setRawFolder(rawFolder);
						options.setServer(server);
						
						options.saveFile();
						
						dispose();				
					}
				}				
				);
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){				
						dispose();				
					}
				}				
				);
				buttonPane.add(cancelButton);
			}
		}
	}

}
