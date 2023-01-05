package cl.uach.info090.metronome;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Octopus extends JLabel{
	private ImageIcon idleImage;
    private ImageIcon firstBeatImage;
    private ImageIcon otherBeatsImage;

    public Octopus(String idleImagePath, String firstBeatImagePath, String otherBeatsImagePath) {
        // Cargar las imágenes del gif desde archivos
        idleImage = new ImageIcon(idleImagePath);
        firstBeatImage = new ImageIcon(firstBeatImagePath);
        otherBeatsImage = new ImageIcon(otherBeatsImagePath);
        
     // Imprimir el tamaño de las imágenes para verificar que se cargaron correctamente
        System.out.println("Tamaño de la imagen idle: " + idleImage.getIconWidth() + "x" + idleImage.getIconHeight());
        System.out.println("Tamaño de la imagen first beat: " + firstBeatImage.getIconWidth() + "x" + firstBeatImage.getIconHeight());
        System.out.println("Tamaño de la imagen other beats: " + otherBeatsImage.getIconWidth() + "x" + otherBeatsImage.getIconHeight());
    }

    public void setSprite(int sprite) {
        switch(sprite) {
            case 1:
                // Establecer el sprite para el primer pulso
                setIcon(firstBeatImage);
                break;
            case 2:
                // Establecer el sprite para los siguientes tres pulsos
                setIcon(otherBeatsImage);
                break;
            default:
                // Establecer el sprite para el idle
                setIcon(idleImage);
                break;
        }
    }

};
