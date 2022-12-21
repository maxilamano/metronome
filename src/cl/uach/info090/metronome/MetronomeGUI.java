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
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    

	  
	    // JButton
	    JButton b, b1, b2;
	  
	    // Label to display text
	    JLabel l;
	    
	    
        // Creating a label to display text
        l = new JLabel("panel label");
  
        // Creating a new buttons
        b = new JButton("button1");
        b1 = new JButton("button2");
        b2 = new JButton("button3");
  
        // Creating a panel to add buttons
        JPanel p = new JPanel();
  
        // Adding buttons and textfield to panel
        // using add() method
        p.add(b);
        p.add(b1);
        p.add(b2);
        p.add(l);
  
        // setbackground of panel
        p.setBackground(Color.white);
  
        // Adding panel to frame
        window.add(p);
  
        // Setting the size of frame
        window.setSize(400, 600);
  
        window.setVisible(true);
		
	}
	
}