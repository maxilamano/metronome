package cl.uach.info090.metronome;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MetronomeGUI implements java.awt.event.ActionListener{
	private static MetronomeGUI metronome;
	private JFrame window;

	private MetronomeGUI(String windowTitle, String iconPath) {
		super();
		window = new JFrame();
		window.setBounds(0,0,400,600);
	    window.setLocationRelativeTo(null);
	    window.setResizable(false);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setTitle(windowTitle);
	    
	    BufferedImage icon = null;
	    //icono
	    try {
	    	icon = ImageIO.read(new File(iconPath));
	    } catch (IOException e) {
	      // maneja la excepción en caso de que la imagen no pueda ser leída
	    	System.out.println(e);
	    }

	    window.setIconImage(icon);
	}
	
	public static MetronomeGUI getInstance(String windowTitle, String iconPath) {
		if (metronome == null) {
			metronome = new MetronomeGUI(windowTitle, iconPath);
		}
		return metronome;
	}
	
	public void actionPerformed(ActionEvent e) {	
		//nada
	}
	
	public JFrame getWindow() {
		return this.window;
	}
	
	//---------------------------main---------------------------//
	public static void main(String[] args) {
		MetronomeGUI metronome = MetronomeGUI.getInstance("Octopus Metronome", "assets/icon.png");
	    
	    SimpleMetronomeDisplay simpleMetronomeDisplay = new SimpleMetronomeDisplay();
	    
	    // añadir ui a la ventana
        simpleMetronomeDisplay.UI(metronome.getWindow());
        
        
        simpleMetronomeDisplay.tick();

		
	}
	
}