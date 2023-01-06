package cl.uach.info090.metronome;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.lang.Thread;

public class PulseLabel extends JLabel {
	
	private static final long serialVersionUID = 1L;
	//los 3 sprites correspondientes (sprite sheet)
	private BufferedImage spriteSheetIdle;
	private BufferedImage spriteSheetFirstBeat;
	private BufferedImage spriteSheetOtherBeat;
	private BufferedImage spriteSheetOff;
	
	//alto y ancho del sprite (no del sprite sheet completo) (se asume que los 3 sprite sheets tienen sprites del mismo tamaño)
    private int spriteWidth;
    private int spriteHeight;
    
    //total de fotogramas de cada sprite
    private int totalFramesIdle;
    private int totalFramesFirstBeat;
    private int totalFramesOtherBeat;
    
    private int currentFrame; //frame actual (universal entre sprites, debe volver a 0 al cambiar de sprite)
    
    //variables de pulso para cambiar animacion
    private int beat; //pulso que representa la clase (1 a 4)
    private int currentBeat; //pulsto actual (inicializado en 1)
    private int frameDelay; //velocidad de animacion (segun bpm), indica el tiempo entre cada fotograma

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
    
    public void resetBeat() {
    	this.currentBeat = 0;
    	updateSprite(spriteSheetOff);
    }

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
    
    private void updateSprite(BufferedImage currentSprite) {
    	currentFrame = 0;
        BufferedImage sprite = currentSprite.getSubimage(currentFrame * spriteWidth, 0, spriteWidth, spriteHeight);
        setIcon(new ImageIcon(sprite));
    }
    
    public void animate1() {
        
    	// Iniciar una animación que dure el número de fotogramas especificado y tenga un retraso entre fotogramas determinado
        int totalFrames;
    	// Colocar los frames correspondientes
        switch (currentBeat) {
            case 1:
                totalFrames = this.totalFramesFirstBeat;
                break;
            default:
                if (currentBeat == beat) {
                    totalFrames = this.totalFramesOtherBeat;
                } else {
                    totalFrames = this.totalFramesIdle;
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
