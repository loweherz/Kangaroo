package it.mancin;

public class MainClass {

	public static GUIController gui = new GUIController();
	
	/* *
	 * MAIN PROGRAM
	 * */	
	public static void main(String[] args){		
		//SERVE PER CARICARE LE IMPOSTAZIONI		
		@SuppressWarnings("unused")
		SettingsFrame sf = new SettingsFrame();
				
		gui.Frame();
		
		System.out.println("KANGAROO");
		
	}//MAIN
	
		
}//END CLASS
