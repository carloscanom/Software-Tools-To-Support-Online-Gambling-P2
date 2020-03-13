package hja.modelo.game;

import javax.swing.ImageIcon;

/**
 * <h2 style="color:DodgerBlue;">Clase Carta</h2>
 * 
 * @author Cristo Fernando Lopez Cabañas, Sergio Manzanaro Caraballo y Ernesto
 *         Vivar Laviña
 *
 */
public class Carta {

	// ATRIBUTOS

	// Enumerado con las distintas cartas
	private enum cartas {
		DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, DIEZ, JACK, DAMA, REY, AS
	}

	// Valor de la carta
	private int valor;

	// Color de la carta
	private char color;

	// Figura de la carta
	private char figura;

	// Imagen de la carta
	private ImageIcon img;

	/**
	 * Constructor
	 * 
	 * @param f:
	 *            Figura
	 * @param c:
	 *            Color
	 */
	public Carta(char f, char c) {
		color = Character.toLowerCase(c);
		figura = Character.toUpperCase(f);
		valor = calculaValor(figura);
		img = new ImageIcon("src/cartas/" + this.nombreCarta());
	}

	public Carta(int c, char color) {
		valor = c;
		this.color = Character.toLowerCase(color);
		switch (c) {
		case 0:
			figura = '2';
			break;
		case 1:
			figura = '3';
			break;
		case 2:
			figura = '4';
			break;
		case 3:
			figura = '5';
			break;
		case 4:
			figura = '6';
			break;
		case 5:
			figura = '7';
			break;
		case 6:
			figura = '8';
			break;
		case 7:
			figura = '9';
			break;
		case 8:
			figura = 'T';
			break;
		case 9:
			figura = 'J';
			break;
		case 10:
			figura = 'Q';
			break;
		case 11:
			figura = 'K';
			break;
		case 12:
			figura = 'A';
			break;
		}
		img = new ImageIcon("src/cartas/" + this.nombreCarta());
	}
	
	private String nombreCarta() {
		String s = Character.toString(figura) + "_of_";
		String palo = "";
		switch(this.color) {
		case 'h': palo = "hearts"; break;
		case 's': palo = "spades"; break;
		case 'd': palo = "diamonds"; break;
		case 'c': palo = "clubs"; break;
		}
		s += palo;
		s += ".png";
		return s;
	}

	/**
	 * Comprueba si es una carta de un palo valido
	 * 
	 * @return True o False dependiendo de si es un palo valido o no
	 */
	private boolean esColorValido() {
		return color == 's' || color == 'c' || color == 'd' || color == 'h';
	}

	/**
	 * Comprueba si es una carta valida
	 * 
	 * @return True o False dependiendo de si la carta es valida o no
	 */
	private boolean esFiguraValida() {
		return figura == 'A' || figura == 'K' || figura == 'Q' || figura == 'J' || figura == 'T' || figura == '9'
				|| figura == '8' || figura == '7' || figura == '6' || figura == '5' || figura == '4' || figura == '3'
				|| figura == '2';
	}

	/**
	 * Comprueba si el color y la figura son validos
	 */
	public boolean esCartaValida() {
		return this.esColorValido() && this.esFiguraValida();
	}

	/**
	 * Calcula el valor asociado a una figura
	 * 
	 * @param figura:
	 *            Char con la figura
	 * @return Valor de la carta
	 */
	private int calculaValor(char figura) {
		int result = 0;
		switch (figura) {
		case 'A':
			result = cartas.AS.ordinal();
			break;
		case '2':
			result = cartas.DOS.ordinal();
			break;
		case '3':
			result = cartas.TRES.ordinal();
			break;
		case '4':
			result = cartas.CUATRO.ordinal();
			break;
		case '5':
			result = cartas.CINCO.ordinal();
			break;
		case '6':
			result = cartas.SEIS.ordinal();
			break;
		case '7':
			result = cartas.SIETE.ordinal();
			break;
		case '8':
			result = cartas.OCHO.ordinal();
			break;
		case '9':
			result = cartas.NUEVE.ordinal();
			break;
		case 'T':
			result = cartas.DIEZ.ordinal();
			break;
		case 'J':
			result = cartas.JACK.ordinal();
			break;
		case 'Q':
			result = cartas.DAMA.ordinal();
			break;
		case 'K':
			result = cartas.REY.ordinal();
			break;
		}
		return result;
	}

	/**
	 * Comprueba si dos cartas son suited
	 * 
	 * @param other:
	 *            Carta a comparar
	 * @return True o False dependiendo de si las cartas son suited o no
	 */
	public boolean suited(Carta other) {
		return this.color == other.getColor();
	}

	/**
	 * Getter del color de la carta
	 * 
	 * @return color
	 */
	public char getColor() {
		return color;
	}

	/**
	 * Getter de la figura de la carta
	 * 
	 * @return figura
	 */
	public char getFigura() {
		return figura;
	}

	/**
	 * Getter del valor de la carta
	 * 
	 * @return valor
	 */
	public int getValor() {
		return valor;
	}

	@Override
	public String toString() {
		String result = Character.toString(figura) + Character.toString(color);
		return result;
	}

	/**
	 * Getter de la imagen
	 * 
	 * @return img
	 */
	public ImageIcon getImg() {
		return img;
	}

	/**
	 * Comparador de cartas
	 * 
	 * @param a:
	 *            Carta A a comparar
	 * @param b:
	 *            Carta B a comparar
	 * @return result
	 */
	public static int compare(Carta a, Carta b) {
		int result;
		if (a.getValor() > b.getValor())
			result = -1;
		else if (a.getValor() == b.getValor())
			result = 0;
		else
			result = 1;
		return result;
	}

}
