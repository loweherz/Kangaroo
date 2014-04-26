package it.mancin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Options {
	private static Options instance;
	
	/** Title of the application */
    private String appTitle = "JCook";	
	private String rootFolder = "";
	private String rawFolder = "";
	private String icon = "";
	private String server = "http://192.168.10.2/";
	
	private Options(){
		try{
			System.out.println("FILE: ");
			File in = new File("C:/Kangaroo/config.xml");
//			InputStream in = this.getClass().getClassLoader().getResourceAsStream("C:/Kangaroo/config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();
			
			//get root node
			Node nNode = doc.getFirstChild();
		 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
				Element eElement = (Element) nNode;
		 
				//System.out.println("Root : " + eElement.getElementsByTagName("root").item(0).getTextContent());					
				rootFolder = eElement.getElementsByTagName("root").item(0).getTextContent();
				rawFolder = eElement.getElementsByTagName("raw").item(0).getTextContent();
				appTitle = eElement.getElementsByTagName("app_title").item(0).getTextContent();
				icon = eElement.getElementsByTagName("icon").item(0).getTextContent();
				server = eElement.getElementsByTagName("server").item(0).getTextContent();
			}		
			
			System.out.println("Root: " + rootFolder);
				
		}catch (IOException e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
				
				File theDir = new File("C:/Kangaroo");

				  // if the directory does not exist, create it
				  if (!theDir.exists()) {
				    System.out.println("creating directory: " + theDir.getPath());
				    boolean result = theDir.mkdir();  

				     if(result) {    
				       System.out.println("DIR created");  
				     }
				  }
				  
				  File file = new File("C:/Kangaroo/config.xml");
				  
			      if(file.exists()){
			    	  System.out.println("Is Execute allow : " + file.canExecute());
					  System.out.println("Is Write allow : " + file.canWrite());
					  System.out.println("Is Read allow : " + file.canRead());
			      }
		 
			      file.setExecutable(false);
			      file.setReadable(false);
			      file.setWritable(false);
		 
			      System.out.println("Is Execute allow : " + file.canExecute());
			      System.out.println("Is Write allow : " + file.canWrite());
			      System.out.println("Is Read allow : " + file.canRead());
		 
			      	try {
						if (file.createNewFile()){
						    System.out.println("File is created!");
						  }else{
						    System.out.println("File already exists.");
						  }
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void saveFile() {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("settings");
			doc.appendChild(rootElement);
	 
			// set attribute to staff element
			//rootElement.setAttribute("app_title", appTitle);
			//rootElement.setAttribute("root", rootFolder);
			//rootElement.setAttribute("icon", icon);

			Element el = doc.createElement("app_title");
			el.appendChild(doc.createTextNode(appTitle));
			rootElement.appendChild(el);
			
			el = doc.createElement("root");
			el.appendChild(doc.createTextNode(rootFolder));
			rootElement.appendChild(el);
			
			el = doc.createElement("raw");
			el.appendChild(doc.createTextNode(rawFolder));
			rootElement.appendChild(el);
			
			el = doc.createElement("icon");
			el.appendChild(doc.createTextNode(icon));
			rootElement.appendChild(el);
			
			el = doc.createElement("server");
			el.appendChild(doc.createTextNode(server));
			rootElement.appendChild(el);
						 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:/Kangaroo/config.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }		
	}
	
	
	public static Options getInstance(){
		if(instance == null)
			instance = new Options();
		return instance;		
	}
	
	public String getIcon(){
		return icon;
	}
	
	public String getAppTitle(){
		return appTitle;
	}
	
	public String getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}
	
	public String getRawFolder() {
		return rawFolder;
	}

	public void setRawFolder(String rawFolder) {
		this.rawFolder = rawFolder;
	}

	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
}
