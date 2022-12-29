package cl.uach.info090.metronome;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.*;

public class SimpleMetronomeDisplay extends JPanel implements MetronomeDisplay{
	
	// texto bpm
    private static JLabel BPMText;
    private static JSlider BPMSlider;
    private static int currentBPM;

	public SimpleMetronomeDisplay() {
		this.BPMSlider = new JSlider(60,500,300);
		this.currentBPM = BPMSlider.getValue();
	    // Creating a label to display text
	    this.BPMText = new JLabel(String.valueOf(currentBPM));
		// TODO Auto-generated constructor stub
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
  
  
        // Creating a panel to add buttons
        JPanel ui = new JPanel();
  
        // Adding buttons and textfield to panel
        // using add() method
        ui.add(BPMSlider);
        ui.add(BPMText);
  
        // setbackground of panel
        ui.setBackground(Color.white);
        window.add(ui);
		
	}
	
	public static void updateBPM() { //retorna nuevo valor de bpm segun slider
		if(currentBPM == BPMSlider.getValue()) {
			return;
		}
		else {
			currentBPM = BPMSlider.getValue();
		    BPMText.setText(String.valueOf(currentBPM));
		}
	}

}
