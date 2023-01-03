package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


import javax.swing.*;

public class SimpleMetronomeDisplay extends JPanel implements MetronomeDisplay{
	
	// texto bpm
    private JLabel BPMText;
    private JSlider BPMSlider;

	public SimpleMetronomeDisplay() {
		this.BPMSlider = new JSlider(60,500,300); //Slider para cambiar BPM
	    this.BPMText = new JLabel(); //Texto que indica los BPM actuales
	}
	
	

	public JLabel getBPMText() {
		return BPMText;
	}



	public JSlider getBPMSlider() {
		return BPMSlider;
	}



	public SimpleMetronomeDisplay(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public SimpleMetronomeDisplay(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public SimpleMetronomeDisplay(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	public void UI(JFrame window) {
  
  
        // Crear panel
        JPanel ui = new JPanel();
  
        // Añadir slider con indicacion de bpm
        ui.add(BPMSlider);
        ui.add(BPMText);
  
        // color de fondo
        ui.setBackground(Color.white);
        
        //agregar elementos a la ventana
        window.add(ui);
        
        
        BPMSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		// Actualiza el valor de la etiqueta con el nuevo valor del BPMSlider
        		int BPMValue = BPMSlider.getValue();
        		BPMText.setText(String.valueOf(BPMValue));
            }
        });
		
	}

}
