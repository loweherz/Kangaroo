package it.mancin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CNCFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3062967268120545618L;
	private final JPanel contentPanel = new JPanel();

	static Options options = Options.getInstance();
	
	/**
	 * Create the dialog.
	 */
	public CNCFrame() {
		setTitle("CNCs");
		
		setBounds(100, 100, 570, 330);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(1, 0));
			
			String[] columnNames = {"Name",
                    "Type",
                    "IP Address"};

			JSONObject json;
			
			try {
				json = readJsonFromUrl(options.getServer() + "server/api/m2m.php?q=listOfCNC");
			    
			    JSONArray jarray = json.getJSONArray("machine");
			    
			    Object[][] data = new Object[jarray.length()][3];
			    
			    for(int i=0;i<jarray.length();i++){
			    	JSONObject tmp = (JSONObject) jarray.get(i);
			    	data[i][0] = tmp.get("name");
			    	data[i][1] = tmp.get("type");
			    	data[i][2] = tmp.get("ipAddress");
			    }
			    
			
				final JTable table = new JTable(data, columnNames);
				table.setPreferredScrollableViewportSize(new Dimension(500, 150));
				table.setFillsViewportHeight(true);
				
				table.addMouseListener(new MouseAdapter() {
	                public void mouseClicked(MouseEvent e) {
	                	// TODO generic click
	                }
	            });
				
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			        public void valueChanged(ListSelectionEvent event) {
			            // do some actions here, for example
			            // print first column value from selected row
			            System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
			        	// TODO click on a row
			        }
			    });
				
				//Create the scroll pane and add the table to it.
		        JScrollPane scrollPane = new JScrollPane(table);
		 
		        //Add the scroll pane to this panel.
		        panel.add(scrollPane);
	        
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){				
						dispose();	
						// TODO Save CNC settings
					}
				}				
				);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){				
						dispose();				
					}
				}				
				);
			}
		}
	}
	
	
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

}
