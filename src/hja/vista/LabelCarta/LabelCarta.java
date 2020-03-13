package hja.vista.LabelCarta;

import javax.swing.JLabel;

import hja.modelo.game.Carta;

public class LabelCarta extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Carta carta;
	
	public LabelCarta(Carta c) {
		super();
		carta = c;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

}
