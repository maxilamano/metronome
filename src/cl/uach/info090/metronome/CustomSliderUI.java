package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {
	
	//cargar thumbs para slider
    BufferedImage thumb1Load = null;
    BufferedImage thumb2Load = null;
    BufferedImage thumb3Load = null;
    
    JSlider slider;
	

	public CustomSliderUI(JSlider slider) {
		super(slider);
		try {
	    	thumb1Load = ImageIO.read(new File("assets/button/sliderIcon1.png"));
	    	thumb2Load = ImageIO.read(new File("assets/button/sliderIcon2.png"));
	    	thumb3Load = ImageIO.read(new File("assets/button/sliderIcon3.png"));
	    } catch (IOException e) {
	      // maneja la excepción en caso de que la imagen no pueda ser leída
	    	System.out.println(e);
	    }
	    
	    
	    this.slider = slider;
	}
	
	@Override
    public void installUI(JComponent c) {
        super.installUI(c);
        // deshabilitar el cambio de valor al hacer clic en el thumb
        ((JSlider) c).setSnapToTicks(true);
    }
    @Override
    public void paintFocus(Graphics g) {
        // No hacer nada para evitar el borde de selección
    }
    @Override
    public void paintTrack(Graphics g) {
        // dibuja el relleno del slider
        Graphics2D g2 = (Graphics2D) g; // convierte g a Graphics2D para poder usar RoundRectangle2D
        g2.setColor(Color.BLACK);
        RoundRectangle2D track = new RoundRectangle2D.Double(trackRect.x, (int) (((trackRect.y)+trackRect.height/2)-2.5), trackRect.width, 5, 5, 5); // crea un RoundRectangle2D con bordes redondeados de radio 5
        g2.fill(track); // dibuja el RoundRectangle2D
    }
    @Override
    public void paintThumb(Graphics g) {
        // Obtener el valor actual del slider
    	int value = slider.getValue();

        // Mostrar la imagen correspondiente según el valor del thumb
    	final Image thumb1 = thumb1Load;
	    final Image thumb2 = thumb2Load;
	    final Image thumb3 = thumb3Load;
	    
        Rectangle thumb = thumbRect;
        if (value < 100) {
            g.drawImage(thumb1, thumb.x, thumb.y, null);
        } else if (value < 200) {
            g.drawImage(thumb2, thumb.x, thumb.y, null);
        } else {
            g.drawImage(thumb3, thumb.x, thumb.y, null);
        }
        // Repintar el componente completo para evitar dejar estela al deslizar el thumb
        slider.repaint();
    }

}
