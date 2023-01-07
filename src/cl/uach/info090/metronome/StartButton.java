package cl.uach.info090.metronome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Clase StartButton
 * extiende a JButton
 * Utilizada para obtener un boton con imagen personalizada que cambia una vez clickeada
 * @author Maximiliano Medina Murillo
 *
 */
public class StartButton extends JButton{
	
	/**
	 * SERIALUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Imagen inicial del botón.
	 */
	private BufferedImage image;
	/**
	 * Imagen al presionar el botón.
	 */
	private BufferedImage imagePressed;
	/**
	 * Estado actual (Start/Stop)
	 */
	private boolean state; //falso para mostrar image, verdadero para imagePressed

	/**
	 * Constructor StartButton
	 * @param imagePath Ruta de imagen por defecto del boton
	 * @param imagePressedPath Ruta de la imagen clickeada del boton
	 */
	public StartButton(String imagePath, String imagePressedPath) {
		state = false; //empieza con la imagen inicial
		
		//quitar boton original
		setBorder(null); // Deshabilitar el borde del botón
		setBorderPainted(false);
		setOpaque(false);
		setFocusPainted(false); //evitar borde de selección
		
		try {
			// Cargar la imagen normal del botón
			image = ImageIO.read(new File(imagePath));
	      	// Cargar la imagen del botón cuando está presionado
	      	imagePressed = ImageIO.read(new File(imagePressedPath));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		this.setIcon(new ImageIcon(image));
		
		// Añade un action listener al botón para cambiar de imagen
        this.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeState();
            }
        });
        
        this.requestFocusInWindow(); //mantener focus
	}
	
	/**
	 * Funcion changeState() usada para cambiar entre activado o desactivado con su imagen core¡respondiente
	 */
	public void changeState() {
		if(state == true) {
			state = false;
			this.setIcon(new ImageIcon(image));
		}else {
			state = true;
			this.setIcon(new ImageIcon(imagePressed));
		}
	}
}
