package cl.uach.info090.metronome;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MetronomeGUI implements java.awt.event.ActionListener{
	private static MetronomeGUI metronome = new MetronomeGUI();

	private MetronomeGUI() {
		super();
	}
	
	public static MetronomeGUI getInstance() {
		if (metronome == null) {
			metronome = new MetronomeGUI();
		}
		return metronome;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//---------------------------main---------------------------//
	public static void main(String[] args) {
		MetronomeGUI metronome = MetronomeGUI.getInstance();
		
		//dimension de la pantalla
		Dimension size 		= Toolkit.getDefaultToolkit().getScreenSize(); //resolución de la pantalla
		int screenWidth 	= (int)size.getWidth(); //largo de la pantalla en pixeles
		int screenHeight 	= (int)size.getHeight(); //alto de la pantalla en pixeles
		
		
		
		//ventana principal
		JFrame window = new JFrame();
	    window.setBounds(0,0,400,600);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    SimpleMetronomeDisplay simpleMetronomeDisplay = new SimpleMetronomeDisplay();
	    
  
        // Setting the size of frame
        window.setSize(400, 600);
  
        window.setVisible(true);
       
        // Adding panel to frame
        simpleMetronomeDisplay.UI(window);
		
	}
	
}