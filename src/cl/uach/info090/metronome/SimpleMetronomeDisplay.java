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
    private int currentBeat;
    
    //indicador de pulso (unicamente 3 ya que los tres se activan a la vez en el primer pulso)
    private PulseLabel beat1;
    private PulseLabel beat2;
    private PulseLabel beat3;
    
    //sonidos para reproducir (se guardan en variables aparte para permitir la reproduccion simultanea)
    Clip fxBeat1; //sonido del primer pulso
    Clip fxBeat2; //sonido del segundo pulso
    Clip fxBeat3; //sonido del tercer pulso
    Clip fxBeat4; //sonido del cuarto pulso

	public SimpleMetronomeDisplay() {
		this.minBPM = 30;
	    this.maxBPM = 350;
	    this.initialBPM = (maxBPM - minBPM) / 2 + minBPM;
		this.BPMSlider = new JSlider(minBPM, maxBPM, initialBPM); //Slider para cambiar BPM
	    this.BPMText = new JLabel(); //Texto que indica los BPM actuales
	    this.currentBeat = 1; //definir pulso actual (siempre empieza en 1)
	    
	    //indicadores de pulso
	    this.beat1 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png",200,130,3,8,2,BPMSlider.getValue(),2);
	    this.beat2 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png",200,130,3,8,2,BPMSlider.getValue(),3);
	    this.beat3 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png",200,130,3,8,2,BPMSlider.getValue(),4);
	    
	    //cargar sonidos
	    this.fxBeat1 = loadSound("assets/sounds/fxPop.wav");
	    this.fxBeat2 = loadSound("assets/sounds/fxSqueeze.wav");
	    this.fxBeat3 = loadSound("assets/sounds/fxSqueeze.wav");
	    this.fxBeat4 = loadSound("assets/sounds/fxSqueeze.wav");
	}
	
	

	public JLabel getBPMText() {
		return BPMText;
	}



	public JSlider getBPMSlider() {
		return BPMSlider;
	}
	
	private Clip loadSound(String filePath) {
	    try{
	        File soundPath = new File(filePath); //archivo de audio
	        if(soundPath.exists()){
	            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
	            Clip clip = AudioSystem.getClip(); //reproductor
	            clip.open(audioInput);//abrir clip
	            return clip;
	        }else{
	            System.out.println("Sonido faltante! ("+filePath+").");
	        }
	    }
	    catch(Exception e)
	    {
	        System.out.println(e);
	    }
	    return null;
	}
	
	public void playSound(Clip clip) {
	    if (clip != null) {
	        clip.setFramePosition(0);
	        clip.start();
	    }
	}

	public void tick() {
		while (true) {
			switch(currentBeat) {
				case 1:
					playSound(fxBeat1); // reproducir sonido
					currentBeat++; //aumentar beat actual
					break;
				case 2:
					playSound(fxBeat2); // reproducir sonido
					currentBeat++; //aumentar beat actual
					break;
				case 3:
					playSound(fxBeat3); // reproducir sonido
					currentBeat++; //aumentar beat actual
					break;
				case 4:
					playSound(fxBeat4); // reproducir sonido
					currentBeat = 1; //regresar beat a 1
					break;
			}
	        
	        //reproducir animacion
	        /*
	        beat1.setSprite(2);
	        beat2.setSprite(2);
	        beat3.setSprite(1);
	        beat4.setSprite(0);
	        
	        beat1.setSprite(0);
	        beat2.setSprite(0);
	        beat3.setSprite(0);
	        beat4.setSprite(1);
	        */
	        
	        beat1.updateBeat(BPMSlider.getValue());
	        beat2.updateBeat(BPMSlider.getValue());
	        beat3.updateBeat(BPMSlider.getValue());
	        
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
        /*
        beat1.setSprite(0);
        beat2.setSprite(0);
        beat3.setSprite(0);
        beat4.setSprite(0);
        */
        
        //añadir beats a beatIndicator
        beatIndicator.add(beat1);
        beatIndicator.add(beat2);
        beatIndicator.add(beat3);
        //beatIndicator.add(beat4);
		
		//por ultimo retornar el panel
		return beatIndicator;
	}
}
