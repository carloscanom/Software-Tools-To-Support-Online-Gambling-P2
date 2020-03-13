package hja.vista.Botones;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import hja.modelo.game.Carta;

public class BotonCarta extends JButton {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String colorAct;
	private String colorDefault;
	
	private Carta carta;
	
	public BotonCarta(Carta c, String color) {
		super(Character.toString(c.getFigura()) + Character.toString(c.getColor()));
		
		colorDefault = colorAct = color;
		carta = c;
		
		this.setMaximumSize(new Dimension(20, 20));
		this.setBackground(Color.decode(color));
	}
	
	
	public void setDefaultColor() {
		colorAct = colorDefault;
		this.setBackground(Color.decode(colorDefault));
	}
	
	public void marcaBoton(String c) {
		colorAct = c;
		this.setBackground(Color.decode(c));
	}
	
	public String getColorAct() {
		return colorAct;
	}
	
	public String getColorDefault() {
		return colorDefault;
	}
	
	public boolean estaMarcado() {
		return colorAct != colorDefault;
	}

	public Carta getCarta() {
		return carta;
	}
	
	
}
