package cl.uach.info090.metronome;

public class MetronomeGUI {
	private static MetronomeGUI metronome = new MetronomeGUI();

	private MetronomeGUI() {
		super();
	}
	
	public static MetronomeGUI getInstance() {
		if (metronome == null) {
			metronome = new MetronomeGUI();
		}
		return metronome;
	}
	//---------------------------main---------------------------//
	public static void main(String[] args) {
		MetronomeGUI metronome = MetronomeGUI.getInstance();
		
	}
	
}