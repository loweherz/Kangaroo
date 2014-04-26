package it.mancin;

import java.io.File; 

public class CheckDataRaw implements Runnable {

	static Options options = Options.getInstance();	
	
	private static int numberFile = 0;
	
	private volatile static boolean execute;
	
	public CheckDataRaw() {
		
	}

	
	public void run() {

		//maialata!!!!
		CheckDataRaw.execute = false;
		if(CheckDataRaw.execute==false){
			CheckDataRaw.execute = true;
		
			numberFile = 0;
			if(!options.getRawFolder().equals("")){
				
				File userDir = new File(options.getRawFolder());//System.getProperty("user.dir"));
		        File[] files = userDir.listFiles();
		    
		        for (File f : files) {
		            if (f.isFile() && !f.isHidden()) {
		                numberFile++;                
		            }
		        }
		        refreshString();
	        }
	        
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public void stopExecuting() {
		CheckDataRaw.execute = false;
	}
	
	public static void refreshString(){		
		RightPanel.fileNumber.setText(numberFile + " elements");
	}
}
