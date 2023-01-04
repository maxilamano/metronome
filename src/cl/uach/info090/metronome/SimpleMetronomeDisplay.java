package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class SimpleMetronomeDisplay extends JPanel implements MetronomeDisplay{
	
	// texto bpm
    private JLabel BPMText;
    private JSlider BPMSlider;
    
    private int minBPM = 60;
    private int maxBPM = 240;
    private int initialBPM; //valor inicial de bpm

	public SimpleMetronomeDisplay() {
		minBPM = 60;
	    maxBPM = 240;
	    initialBPM = (maxBPM - minBPM) / 2 + minBPM;
		this.BPMSlider = new JSlider(minBPM, maxBPM, initialBPM); //Slider para cambiar BPM
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
        ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS)); //crear layout
        BPMSlider.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar slider
        BPMText.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar texto BPM
        
        
        BPMText.setText(String.valueOf(initialBPM)); //definir texto como valor inicial BPM
        
        // Añadir slider con indicacion de bpm
        ui.add(BPMSlider);
        ui.add(BPMText);
        // color de fondo
        ui.setBackground(Color.white);
        
        
        BPMSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		// Actualiza el valor de la etiqueta con el nuevo valor del BPMSlider
        		int BPMValue = BPMSlider.getValue();
        		BPMText.setText(String.valueOf(BPMValue));
            }
        });
        
        //beatIndicator
        ui.add(beatIndicator());
        
        //agregar elementos a la ventana
        window.add(ui);
	}
	
	public JPanel beatIndicator(){
		JPanel beatIndicator = new JPanel(); //panel que muestra visualmente el pulso
		beatIndicator.setLayout(new BoxLayout(beatIndicator, BoxLayout.X_AXIS));
		beatIndicator.setBackground(Color.white);
		
		//crear cuatro circulos (beats)
		JLabel beat1 = new JLabel();
		beat1.setOpaque(true);
		beat1.setBackground(Color.red);

		JLabel beat2 = new JLabel();
		beat2.setOpaque(true);
		beat2.setBackground(Color.gray);

		JLabel beat3 = new JLabel();
		beat3.setOpaque(true);
		beat3.setBackground(Color.gray);

		JLabel beat4 = new JLabel();
		beat4.setOpaque(true);
		beat4.setBackground(Color.gray);
		
		// Crea un borde redondeado
		Border border = new LineBorder(Color.black, 1, true);

		// Establece el borde redondeado en cada JLabel
		beat1.setBorder(border);
		beat2.setBorder(border);
		beat3.setBorder(border);
		beat4.setBorder(border);

		// Establece el tamaño de cada JLabel
		beat1.setPreferredSize(new Dimension(30, 30));
		beat2.setPreferredSize(new Dimension(30, 30));
		beat3.setPreferredSize(new Dimension(30, 30));
		beat4.setPreferredSize(new Dimension(30, 30));
		
		//agregarlos al panel
		beatIndicator.add(beat1);
		beatIndicator.add(beat2);
		beatIndicator.add(beat3);
		beatIndicator.add(beat4);
		
		//por ultimo retornar el panel
		return beatIndicator;
	}
}
