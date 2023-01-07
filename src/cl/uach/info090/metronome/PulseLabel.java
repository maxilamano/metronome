package cl.uach.info090.metronome;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Thread;

/**
 * Clase PulseLabel extiende JLabel.
 * se usa como indicador para los pulsos de BPM.
 * admite 3 animaciones en forma de SPRITE SHEETS (Primer pulso, Otros pulsos e "Idle").
 * Las animaciones se reproducen una vez cada vez que se active, no en bucle.
 * Ademas admite una imagen extra que se usa como imagen "por defecto" segun se indique.
 * @author Maximiliano Medina Murillo
 *
 */
public class PulseLabel extends JLabel {
	
	/**
	 * SERIALUID
	 */
	private static final long serialVersionUID = 1L;
	
	//los 3 sprites correspondientes (sprite sheet)
	/**
	 * Ruta del SpriteSheet de la animacion Idle
	 */
	private BufferedImage spriteSheetIdle;
	/**
	 * Ruta del SpriteSheet de la animacion del primer pulso
	 */
	private BufferedImage spriteSheetFirstBeat;
	/**
	 * Ruta del SpriteSheet de la animacion de otros pulsos
	 */
	private BufferedImage spriteSheetOtherBeat;
	/**
	 * Ruta de la imagen por defecto (OFF)
	 */
	private BufferedImage spriteSheetOff;
	
	//alto y ancho del sprite (no del sprite sheet completo) (se asume que los 3 sprite sheets tienen sprites del mismo tamaño)
	/**
	 * Ancho del sprite (No de la imagen)
	 */
    private int spriteWidth;
    /**
     * Alto del sprite (No de la imagen)
     */
    private int spriteHeight;
    
    //total de fotogramas de cada sprite
    /**
     * Total de fotogramas de Idle
     */
    private int totalFramesIdle;
    /**
     * Total de fotogramas de primer pulso
     */
    private int totalFramesFirstBeat;
    /**
     * Total de fotogramas de otro pulso
     */
    private int totalFramesOtherBeat;
    /**
     * frame actual (universal entre sprites, debe volver a 0 al cambiar de sprite)
     */
    private int currentFrame; //frame actual (universal entre sprites, debe volver a 0 al cambiar de sprite)
    
    //variables de pulso para cambiar animacion
    /**
     * pulso que representa la clase (1 a 4)
     */
    private int beat; //pulso que representa la clase (1 a 4)
    /**
     * pulsto actual (inicializado en 1)
     */
    private int currentBeat; //pulsto actual (inicializado en 1)
    /**
     * velocidad de animacion (segun bpm), indica el tiempo entre cada fotograma
     */
    private int frameDelay; //velocidad de animacion (segun bpm), indica el tiempo entre cada fotograma
    
    /**
     * Constructor PulseLabel.
     * @param idleSpriteSheetPath Ruta del SpriteSheet de la animacion Idle
     * @param firstBeatSpriteSheetPath Ruta del SpriteSheet de la animacion del primer pulso
     * @param otherBeatSpriteSheetPath Ruta del SpriteSheet de la animacion de otros pulsos
     * @param offSpriteSheetPath Ruta de la imagen por defecto (OFF)
     * @param spriteWidth Ancho del sprite (No de la imagen)
     * @param spriteHeight Alto del sprite (No de la imagen)
     * @param totalFramesIdle Total de fotogramas de Idle
     * @param totalFramesFirstBeat Total de fotogramas de primer pulso
     * @param totalFramesOtherBeat Total de fotogramas de otro pulso
     * @param bpm Pulsos por minuto
     * @param beat Pulso que representa la clase, se usa para activar la animacion de "otro pulso"
     */
    public PulseLabel(String idleSpriteSheetPath, String firstBeatSpriteSheetPath, String otherBeatSpriteSheetPath,String offSpriteSheetPath, int spriteWidth, int spriteHeight, int totalFramesIdle, int totalFramesFirstBeat, int totalFramesOtherBeat, int bpm, int beat) {
        // Cargar el sprite sheets desde el archivo
        try {
            spriteSheetIdle = ImageIO.read(new File(idleSpriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            spriteSheetFirstBeat = ImageIO.read(new File(firstBeatSpriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            spriteSheetOtherBeat = ImageIO.read(new File(otherBeatSpriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            spriteSheetOff = ImageIO.read(new File(offSpriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //alto y ancho del sprite
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        
        //definir total de fotogramas
        this.totalFramesIdle = totalFramesIdle;
        this.totalFramesFirstBeat = totalFramesFirstBeat;
        this.totalFramesOtherBeat = totalFramesOtherBeat;
        this.currentFrame = 0;
        
        this.beat = beat;
        this.currentBeat = 0;
        
        setFrameDelay(bpm);
        updateSprite();
        animate();
        
    }
    
    /**
     * Funcion setFrameDelay() calcula la velocidad de la animacion en el momento.
     * define el tiempo que debe haber entre cada fotograma de la animacion.
     * @param bpm Pulsos por minuto, se usa para calcular la velocidad de animacion.
     */
    private void setFrameDelay(int bpm){
    	if(bpm < 130){
    		frameDelay = 40;
    	}else {
    		if (40 -(bpm-130) > 20) {
    			frameDelay = 40 - (bpm-130);
    		}else {
    			frameDelay = 20;
    		}
    	}
    }
    
    /**
     * Funcion updateBeat() cambia el pulso actual cuando se llama
     * Cambia el valor entre 1 a 4 segun corresponda
     * el valor se utiliza para saber que animación reproducir
     * @param bpm BPM, utilizados para llamar a setFrameDelay().
     */
    public void updateBeat(int bpm) {
    	if (currentBeat >= 4) {
    		currentBeat = 1;
    	}
    	else {
    		currentBeat++;
    	}
    	setFrameDelay(bpm);
    	this.currentFrame = 0;
    	if(currentBeat<=beat) {
    		animate();
    	}
    }
    
    /**
     * Funcion resetBeat() deja en 0 el pulso actual.
     * Usado para reiniciar las animaciones al pausar los indicadores.
     */
    public void resetBeat() {
    	this.currentBeat = 0;
    	updateSprite(spriteSheetOff);
    }
    
    /**
     * Funcion updateSprite() usada para actualizar al siguiente fotograma correspondiente segun corresponda.
     */
    private void updateSprite() {
    	//colocar sprite correspondiente
    	BufferedImage currentSprite;
    	switch(currentBeat) {
    		case 1:
    			currentSprite = spriteSheetFirstBeat;
    			break;
    		default:
    			if(currentBeat == beat) {
    				currentSprite = spriteSheetOtherBeat;
    			}else {
    				currentSprite = spriteSheetIdle;
    			}
    	}
        // Cortar el sprite del sprite sheet y establecerlo como el icono del JLabel
    	
        BufferedImage sprite = currentSprite.getSubimage(currentFrame * spriteWidth, 0, spriteWidth, spriteHeight);
        setIcon(new ImageIcon(sprite));
    }
    
    /**
     * Funcion sobrecargada updateSprite() usada para actualizar al primer fotograma dentro del spriteSheet indicado.
     * @param currentSprite SpriteSheet a utilizar para cambiar el fotograma.
     */
    private void updateSprite(BufferedImage currentSprite) {
    	currentFrame = 0;
        BufferedImage sprite = currentSprite.getSubimage(currentFrame * spriteWidth, 0, spriteWidth, spriteHeight);
        setIcon(new ImageIcon(sprite));
    }

    /**
     * Funcion animate() usada para reproducir la animacion correspondiente del indicador.
     */
    public void animate() {
        Thread animacionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Iniciar una animación que dure el número de fotogramas especificado y tenga un retraso entre fotogramas determinado
                int totalFrames;
                // Colocar los frames correspondientes
                switch (currentBeat) {
                    case 1:
                        totalFrames = totalFramesFirstBeat;
                        break;
                    default:
                        if (currentBeat == beat) {
                            totalFrames = totalFramesOtherBeat;
                        } else {
                            totalFrames = totalFramesIdle;
                        }
                        break;
                }
                
                currentFrame = 0;
                // Colocar el sprite inicial
                updateSprite();
                
                // Ejecutar la tarea un número determinado de veces
                for (int i = 0; i < totalFrames - 1; i++) {
                    try {
                        // Retrasar la ejecución de la tarea por el tiempo especificado
                        Thread.sleep(frameDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // Avanzar al siguiente frame y actualizar el sprite
                    currentFrame++;
                    updateSprite();
                }
            }
        });
        animacionThread.start(); // Iniciar el hilo
    }


}
