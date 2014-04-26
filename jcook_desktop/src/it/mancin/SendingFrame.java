package it.mancin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendingFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2307130566824076137L;
	private final JPanel contentPanel = new JPanel();
	
	File currentFile;
	
	JTable table = null;
	
	static Options options = Options.getInstance();

	/**
	 * Create the dialog.
	 */
	public SendingFrame(File file) {
		setTitle("Sending...");
		
		this.currentFile = file;
		
		setBounds(100, 100, 400, 330);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(1, 0));
			
			String[] columnNames = {"Name",                    
                    "IP Address"};

			JSONObject json;
			
			try {
				json = readJsonFromUrl(options.getServer() + "/server/api/m2m.php?q=listOfCNC");
			    
			    JSONArray jarray = json.getJSONArray("machine");
			    
			    System.out.println(json.toString());
			    
			    Object[][] data = new Object[jarray.length()][3];
			    
			    for(int i=0;i<jarray.length();i++){
			    	JSONObject tmp = (JSONObject) jarray.get(i);
			    	data[i][0] = tmp.get("name");			    	
			    	data[i][1] = tmp.get("ip");
			    }
			    			
				table = new JTable(data, columnNames);
				table.setPreferredScrollableViewportSize(new Dimension(300, 150));
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
			            //System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
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
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//get selected row and ipaddress
						String address = (String) table.getValueAt( table.getSelectedRow() , table.getColumn("IP Address").getModelIndex());
						
						//REMOVE DELETE TODO WARNING ATTENTION
						address = "192.168.10.4";
						
						System.out.println("Sent to " + address);
						try {
							System.out.println("File: " + currentFile.getCanonicalPath().toString());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						//192.168.0.101/server/api/m2m.php?q=sendFileToCNC&address=192.168.30.11&file=O313
						
						String USER_AGENT = "Mozilla/5.0";
						String url = options.getServer() + "/server/api/m2m.php?q=sendFileToCNC&address=" + address + "&file=O313";
						System.out.println(url);
						
						URL obj;
						try {
							
							obj = new URL(url);
							HttpURLConnection con = (HttpURLConnection) obj.openConnection();
							
							con.setRequestMethod("POST");
							con.setRequestProperty("User-Agent", USER_AGENT);
							con.setRequestProperty("Accept-Language", "it-IT,it;q=0.5");
							con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
							con.setRequestProperty("charset", "UTF-8"); 
							con.setRequestProperty("Content-Length", String.valueOf(currentFile.length()));
							con.setUseCaches(false);
							
							String urlParameters = readFile(currentFile.toString(), StandardCharsets.UTF_8);
							
							// Send post request
							con.setDoOutput(true);
							DataOutputStream wr = new DataOutputStream(con.getOutputStream());
							wr.writeBytes(urlParameters);
							wr.flush();
							wr.close();
					 
							int responseCode = con.getResponseCode();
							System.out.println("\nSending 'POST' request to URL : " + url);
							System.out.println("Post parameters : " + urlParameters);
							System.out.println("Response Code : " + responseCode);
					 
							BufferedReader in = new BufferedReader(
							        new InputStreamReader(con.getInputStream()));
							String inputLine;
							StringBuffer response = new StringBuffer();
					 
							while ((inputLine = in.readLine()) != null) {
								response.append(inputLine);
							}
							in.close();
					 
							//print result
							System.out.println(response.toString());
							
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						//send this file to this address
						
						dispose();		
					}
				}				
				);
				getRootPane().setDefaultButton(okButton);
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
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public void sendProgram(File file, String address){
		
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
