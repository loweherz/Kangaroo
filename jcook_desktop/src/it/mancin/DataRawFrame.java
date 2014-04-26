package it.mancin;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class DataRawFrame extends JFrame implements Runnable{
	
	public DataRawFrame() {
	}

    private static final long serialVersionUID = -2690739309203957804L;
    
    static Options options = Options.getInstance();
    private String FOLDER = options.getRawFolder();

	public void run() {

        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new DataRawFrame().create();
            }
        });
    }

	void create() {
        File userDir = new File(FOLDER);//System.getProperty("user.dir"));
        File[] files = userDir.listFiles();

        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
        for (File f : files) {
            if (f.isFile() && !f.isHidden()) {
                RecentFile rf = new RecentFile(f);
                toolBar.add(rf.getAction());
            }
        }
        JMenuBar menuBar = new JMenuBar();
        
        JScrollPane scrollPane = new JScrollPane (toolBar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JFrame f = new JFrame("RAW");
        f.setJMenuBar(menuBar);
        f.getContentPane().add(scrollPane, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(200,400);
        f.setVisible(true);
    }
}

class RecentFile extends AbstractAction {

    /**
	 * 
	 */
	static Options options = Options.getInstance();
	private static final long serialVersionUID = -8155828465107533843L;
	private final File file;
	private String FOLDER = options.getRawFolder() + "\\";//"program\\LRM\\_RAW\\";	
    
    public RecentFile(final File file) {
        this.file = file;        
        this.putValue(Action.NAME, file.getName());
        this.putValue(Action.SHORT_DESCRIPTION, file.getAbsolutePath());
        
    }

    public void actionPerformed(ActionEvent e) {
    	System.out.println(FOLDER + file.getName());
    	try {
    		File f = new File(FOLDER + file.getName());
    		
    		if (Desktop.isDesktopSupported()) {
    		    Desktop.getDesktop().open(f);
    		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public Action getAction() {
        return this;
    }
}
