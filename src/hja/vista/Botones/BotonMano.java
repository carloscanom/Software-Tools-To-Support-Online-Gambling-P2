package hja.vista.Botones;

import java.awt.Color;

import javax.swing.JButton;

public class BotonMano extends JButton {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String colorAct;
	private String colorDefault;
	private String name;
	private boolean suited;
	
	
	public BotonMano(String nombre, String color, boolean s) {
		super(nombre);
		name = nombre;
		colorDefault = colorAct = color;
		suited = s;
		this.setBackground(Color.decode(color));
	}
	
	public char getCarta1() {
		return name.charAt(0);
	}
	
	public char getCarta2() {
		return name.charAt(1);
	}
	
	public boolean esS() {
		return suited;
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
	
	public boolean esPareja() {
		return this.getCarta1() == this.getCarta2();
	}
	
	
	public String toString() {
		String c1 = Character.toString(this.getCarta1());
		String c2 = Character.toString(this.getCarta2());
		String r = c1 + c2;
		if(this.esS()) {
			r += "s";
		}
		else {
			if(!c1.equals(c2))
				r += "o";
		}
		return r;
	}
	
	
	
	
	
}
