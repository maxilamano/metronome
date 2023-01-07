package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * Clase SimpleMetronomeDisplay.
 * Extiende a JPanel.
 * Implementa MetronomeDisplay (tick()).
 * se encarga de colocar los elementos correspondientes a la ventana.
 * @author Maximiliano Medina Murillo
 *
 */
public class SimpleMetronomeDisplay extends JPanel implements MetronomeDisplay, Serializable{
	
	/**
	 * SERIALUID
	 */
	private static final long serialVersionUID = 1L;
	// texto bpm
	/**
	 * Texto que indica los BPM actuales.
	 */
    private JLabel BPMText;
    /**
     * Slider que controla los BPM.
     */
    private JSlider BPMSlider;
    
    /**
     * BPM minimos admitidos por el slider.
     */
    private int minBPM = 60; //BPM Minimo
    /**
     * BPM maximos admitidos por el slider.
     */
    private int maxBPM = 240; //BPM Maximo
    /**
     * Valor inicial de BPM
     */
    private int initialBPM; //valor inicial de bpm
    /**
     * Pulso actual. Itera entre 1 a 4.
     */
    private int currentBeat;
    
    //indicador de pulso (unicamente 3 ya que los tres se activan a la vez en el primer pulso)
    /**
     * Primer indicador de pulso.
     */
    private PulseLabel beat1;
    /**
     * Segundo indicador de pulso.
     */
    private PulseLabel beat2;
    /**
     * Tercer indicador de pulso.
     */
    private PulseLabel beat3;
    
    //sonidos para reproducir (se guardan en variables aparte para permitir la reproduccion simultanea)
    /**
     * Sonido del primer pulso.
     */
    Clip fxBeat1; //sonido del primer pulso
    /**
     * Sonido del segundo pulso.
     */
    Clip fxBeat2; //sonido del segundo pulso
    /**
     * Sonido del tercer pulso.
     */
    Clip fxBeat3; //sonido del tercer pulso
    /**
     * Sonido del cuarto pulso.
     */
    Clip fxBeat4; //sonido del cuarto pulso
    
    /**
     * StartButton utilizado para controlar a los PulseLabel.
     */
    private StartButton startButton;
    /**
     * Indica si se esta ejecutando el metronomo (controlando a la funcion tick()).
     */
    AtomicBoolean running; //indica si esta funcionando los pulsos (se usa para llamar tick() )
    
    /**
     * Constructor SimpleMetronomeDisplay
     */
	public SimpleMetronomeDisplay() {
		this.minBPM = 30;
	    this.maxBPM = 350;
	    this.initialBPM = (maxBPM - minBPM) / 2 + minBPM;
		this.BPMSlider = new JSlider(minBPM, maxBPM, initialBPM); //Slider para cambiar BPM
	    this.BPMText = new JLabel(); //Texto que indica los BPM actuales
	    this.currentBeat = 1; //definir pulso actual (siempre empieza en 1)
	    running = new AtomicBoolean(false);
	    
	    //indicadores de pulso
	    this.beat1 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png","assets/octopus/octopusHappy_Sheet.png",234,107,3,8,2,BPMSlider.getValue(),2);
	    this.beat2 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png","assets/octopus/octopusHappy_Sheet.png",234,107,3,8,2,BPMSlider.getValue(),3);
	    this.beat3 = new PulseLabel("assets/octopus/octopusIdle_Sheet.png","assets/octopus/octopusShoot_Sheet.png","assets/octopus/octopusReady_Sheet.png","assets/octopus/octopusHappy_Sheet.png",234,107,3,8,2,BPMSlider.getValue(),4);
	    
	    //cargar sonidos
	    this.fxBeat1 = loadSound("assets/sounds/fxPop.wav");
	    this.fxBeat2 = loadSound("assets/sounds/fxSqueeze.wav");
	    this.fxBeat3 = loadSound("assets/sounds/fxSqueeze.wav");
	    this.fxBeat4 = loadSound("assets/sounds/fxSqueeze.wav");
	    
	    //crear boton start
	    this.startButton = new StartButton("assets/button/startButton.png","assets/button/stopButton.png");
	    // Añade un action listener al botón para activar o desactivar tick()
        startButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                running.set(!running.get());
            }
        });
	}
	
	
	/**
	 * Getter para BPMText.
	 * @return Retorna Un JLabel BPMText, el cual contiene los BPM actuales.
	 */
	public JLabel getBPMText() {
		return BPMText;
	}

	/**
	 * Getter para BPMSlider.
	 * @return Retorna un JSlider BPMSlider, el cual controla los BPM.
	 */
	public JSlider getBPMSlider() {
		return BPMSlider;
	}
	
	/**
	 * Funcion loadSound, encargada de cargar sonidos. Compatible con archivos PCM de cualquier formato.
	 * @param filePath Ruta del sonido
	 * @return retorna el audio Clip
	 */
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
	
	/**
	 * Reproduce un audio previamente cargado
	 * @param clip Clip de audio a reproducir
	 */
	public void playSound(Clip clip) {
	    if (clip != null) {
	        clip.setFramePosition(0);
	        clip.start();
	    }
	}
	
	/**
	 * Funcion tick() encargada de controlar los pulsos y sus indicaciones tanto visuales como auditivas segun BPM, actualiza su ritmo cada vez que pase un pulso (NO ADMITE BPM CON VALORES INFERIORES O IGUALES A 0)
	 */
	public void tick() {
		while (true) {
			if(running.get()) {
				
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
		    }else {
		    	currentBeat = 1;
		    	beat1.resetBeat();
		    	beat2.resetBeat();
		    	beat3.resetBeat();
		    }
			
		}
		
	}
	
	/**
	 * Funcion UI que coloca todos los elementos correspondientes en la ventana usando paneles (JPanel), haciendo uso de las funciones beatIndicator() y sliderPanel().
	 * @param window Ventana en cual colocar los elementos, debe ser JFrame.
	 */
	public void UI(JFrame window) {
		
		//definir colores
		Color borderColor = Color.decode("#DF9A00");
		Color backgroundColor = Color.decode("#FFDE00");
		
		int borderThickness = 30; //grosor borde
  
		JPanel ui = new JPanel(); //crear panel de UI
		ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS)); //definir layout vertical
		ui.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
        ui.setBackground(backgroundColor);
		
		ui.add(sliderPanel());
		ui.add(beatIndicator());
		
        
        window.add(ui); //agregar elementos a la ventana
        
        window.setSize(400, 600); // definir tamaño de la ventana
  
        window.setVisible(true); //hacer visible el contenido
	}
	
	/**
	 * Funcion sliderPanel() usada por UI() para crear un JPanel con el slider que controla los BPM y su label indicando los BPM actuales.
	 * @return Retorna el JPanel con los elementos agregados
	 */
	private JPanel sliderPanel() {
		// Crear panel
        JPanel sliderPanel = new JPanel();
        
  
        // Añadir slider con indicacion de bpm
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS)); //crear layout
        BPMSlider.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar slider
        BPMText.setAlignmentX(Component.CENTER_ALIGNMENT); //centrar texto BPM
        
        //cambiar fuente texto
        String fontFile = "assets/pixeled.ttf";
        Font ttfFont = null;
		try {
			ttfFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ttfFont = ttfFont.deriveFont(10f);
        BPMText.setFont(ttfFont);
        
        BPMText.setText(String.valueOf(initialBPM)); //definir texto como valor inicial BPM
        
        // Añadir slider con indicacion de bpm
        sliderPanel.add(BPMSlider);
        sliderPanel.add(BPMText);
        // color de fondo
        Color backgroundColor = Color.decode("#FFDE00");
        sliderPanel.setBackground(backgroundColor);
        
        
        BPMSlider.addChangeListener(new ChangeListener() { //funcion al interactuar
        	public void stateChanged(ChangeEvent e) {
        		// Actualiza el valor de la etiqueta con el nuevo valor del BPMSlider
        		int BPMValue = BPMSlider.getValue();
        		BPMText.setText(String.valueOf(BPMValue));
        		startButton.requestFocusInWindow(); //mantiene focus en el boton start
            }
        });
        BPMSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.requestFocusInWindow(); //mantiene el focus en el boton start
            }
        });
        
        
        BPMSlider.setUI(new CustomSliderUI(BPMSlider));
        
        BPMSlider.setBackground(backgroundColor);
        return sliderPanel;
	}
	
	/**
	 * Funcion BeatIndicator() usada por UI() para crear un JPanel con los indicadores visuales PulseLabel, ademas de un boton StartButton que activa y desactiva los indicadores
	 * @return Retorna un JPanel con los elementos agregados
	 */
	private JPanel beatIndicator(){
		
		//definir colores
		//Color borderColor = Color.decode("#DF9A00"); //TEST ONLY
		Color backgroundColor = Color.decode("#FFDE00");
		
		//int borderThickness = 2; //grosor borde //TEST ONLY
		
		//añadir indicadores
        JPanel beatIndicator = new JPanel();
        
        //propiedades del panel
        beatIndicator.setLayout(new BoxLayout(beatIndicator, BoxLayout.Y_AXIS)); //crear layout
        //beatIndicator.setBorder(BorderFactory.createLineBorder(borderColor, borderThickness)); //TEST ONLY
        beatIndicator.setBackground(backgroundColor);
        
        //centrar indicadores
        beat1.setAlignmentX(Component.CENTER_ALIGNMENT);
        beat2.setAlignmentX(Component.CENTER_ALIGNMENT);
        beat3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //boton start
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        //añadir beats a beatIndicator
        beatIndicator.add(beat1);
        beatIndicator.add(beat2);
        beatIndicator.add(beat3);
        
        //añadir boton que controla los indicadores
        beatIndicator.add(new Box.Filler(new Dimension(10, 20), new Dimension(10, 20), new Dimension(10, 20)));
        beatIndicator.add(startButton);
		
		//por ultimo retornar el panel
		return beatIndicator;
	}
}
