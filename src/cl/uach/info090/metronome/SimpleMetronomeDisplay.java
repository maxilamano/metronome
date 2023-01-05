package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.lang.Thread;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class SimpleMetronomeDisplay extends JPanel implements MetronomeDisplay{
	
	// texto bpm
    private JLabel BPMText;
    private JSlider BPMSlider;
    
    private int minBPM = 60; //BPM Minimo
    private int maxBPM = 240; //BPM Maximo
    private int initialBPM; //valor inicial de bpm
    
    //indicador de pulso
    private Octopus beat1;
    private Octopus beat2;
    private Octopus beat3;
    private Octopus beat4;

	public SimpleMetronomeDisplay() {
		this.minBPM = 60;
	    this.maxBPM = 240;
	    this.initialBPM = (maxBPM - minBPM) / 2 + minBPM;
		this.BPMSlider = new JSlider(minBPM, maxBPM, initialBPM); //Slider para cambiar BPM
	    this.BPMText = new JLabel(); //Texto que indica los BPM actuales
	    
	    //definir indicadores de pulso
	    this.beat1 = new Octopus("octopusIdle.gif","octopusShoot.gif","octopusReady.gif");
	    this.beat2 = new Octopus("octopusIdle.gif","octopusShoot.gif","octopusReady.gif");
	    this.beat3 = new Octopus("octopusIdle.gif","octopusShoot.gif","octopusReady.gif");
	    this.beat4 = new Octopus("octopusIdle.gif","octopusShoot.gif","octopusReady.gif");
	}
	
	

	public JLabel getBPMText() {
		return BPMText;
	}



	public JSlider getBPMSlider() {
		return BPMSlider;
	}


	/*
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
	*/
	
	private void playSound(String filePath) {
	    try{
	    	File soundPath = new File(filePath); //archivo de audio
	    	if(soundPath.exists()){
	    		AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
	    		Clip clip = AudioSystem.getClip(); //reproductor
	    		clip.open(audioInput);//abrir clip
	    		clip.start();//reproducir clip de audio
	    	}else{
	    		System.out.println("Sonido faltante! ("+filePath+").");
	    	}
	    	
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	}

	public void tick() {
		while (true) {
	        playSound("tickSound.wav"); // reproducir sonido
	        
	        //reproducir animacion
	        beat1.setSprite(2);
	        beat2.setSprite(2);
	        beat3.setSprite(1);
	        beat4.setSprite(0);
	        
	        beat1.setSprite(0);
	        beat2.setSprite(0);
	        beat3.setSprite(0);
	        beat4.setSprite(1);

	        // calcular el intervalo de tiempo en milisegundos entre ejecuciones del metrónomo
	        int interval = 60000 / BPMSlider.getValue();

	        try {
	        	Thread.sleep(interval);
	        } catch (InterruptedException e) {
	        	System.out.println(e);
	            // maneja la excepción en caso de que el hilo sea interrumpido
	        }
	    }
		
	}
	
	public void UI(JFrame window) {
  
  
		JPanel ui = new JPanel(); //crear panel de UI
		ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS)); //definir layout vertical
		
		ui.add(sliderPanel());
		ui.add(beatIndicator());
        
        window.add(ui); //agregar elementos a la ventana
        
        window.setSize(400, 600); // definir tamaño de la ventana
  
        window.setVisible(true); //hacer visible el contenido
	}
	
	private JPanel sliderPanel() {
		// Crear panel
        JPanel sliderPanel = new JPanel();
        
  
        // Añadir slider con indicacion de bpm
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS)); //crear layout
        BPMSlider.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar slider
        BPMText.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar texto BPM
        
        
        BPMText.setText(String.valueOf(initialBPM)); //definir texto como valor inicial BPM
        
        // Añadir slider con indicacion de bpm
        sliderPanel.add(BPMSlider);
        sliderPanel.add(BPMText);
        // color de fondo
        sliderPanel.setBackground(Color.white);
        
        
        BPMSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		// Actualiza el valor de la etiqueta con el nuevo valor del BPMSlider
        		int BPMValue = BPMSlider.getValue();
        		BPMText.setText(String.valueOf(BPMValue));
            }
        });
        
        return sliderPanel;
	}
	
	private JPanel beatIndicator(){
		
		//añadir pulpo
        JPanel beatIndicator = new JPanel();
        beatIndicator.setLayout(new BoxLayout(beatIndicator, BoxLayout.Y_AXIS)); //crear layout
        
        //inicializar sprites por defecto (idle)
        beat1.setSprite(0);
        beat2.setSprite(0);
        beat3.setSprite(0);
        beat4.setSprite(0);
        
        //añadir beats a beatIndicator
        beatIndicator.add(beat1);
        beatIndicator.add(beat2);
        beatIndicator.add(beat3);
        beatIndicator.add(beat4);
		
		//por ultimo retornar el panel
		return beatIndicator;
	}
}
