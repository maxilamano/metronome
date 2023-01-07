package cl.uach.info090.metronome;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Clase MetronomeGUI.
 * Implementa ActionListener.
 * Controla la ventana principal del metronomo (SINGLETON).
 * CONTIENE FUNCION MAIN
 * @author Maximiliano Medina Murillo
 *
 */
public class MetronomeGUI implements java.awt.event.ActionListener{
	
	/**
	 * Instancia de MetronomeGUI (SINGLETON)
	 */
	private static MetronomeGUI metronome;
	
	/**
	 * Ventana a utilizar
	 */
	private JFrame window;
	
	/**
	 * Constructor de MetronomeGUI (SINGLETON).
	 * @param windowTitle Titulo de la ventana
	 * @param iconPath Ruta de la imagen para el icono de la ventana
	 */
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
	
	/**
	 * Funcion para obtener instancia (SINGLETON) de MetronomeGUI
	 * @param windowTitle Titulo de la ventana
	 * @param iconPath Ruta de la imagen para el icono de la ventana
	 * @return Retorna la instancia de MetronomeGUI (SINGLETON)
	 */
	public static MetronomeGUI getInstance(String windowTitle, String iconPath) {
		if (metronome == null) {
			metronome = new MetronomeGUI(windowTitle, iconPath);
		}
		return metronome;
	}
	
	/**
	 * Funcion actionPerformed necesaria para la implementacion de ActionListener
	 */
	public void actionPerformed(ActionEvent e) {	
		//nada
	}
	
	/**
	 * Getter de la ventana
	 * @return Retorna la ventana actual
	 */
	public JFrame getWindow() {
		return this.window;
	}
	
	//---------------------------main---------------------------//
	/**
	 * FUNCION MAIN
	 * @param args Argumentos generales (No requerido)
	 */
	public static void main(String[] args) {
		MetronomeGUI metronome = MetronomeGUI.getInstance("Octopus Metronome", "assets/icon.png");
	    
	    SimpleMetronomeDisplay simpleMetronomeDisplay = new SimpleMetronomeDisplay();
	    
	    // añadir ui a la ventana
        simpleMetronomeDisplay.UI(metronome.getWindow());
        
        
        simpleMetronomeDisplay.tick();

		
	}
	
}