package hja.modelo.game;

import java.util.ArrayList;

/**
 * <h2 style="color:DodgerBlue;">Clase Jugada</h2>
 * 
 * @author Cristo Fernando Lopez Cabañas, Sergio Manzanaro Caraballo y Ernesto
 *         Vivar Laviña
 *
 */
public class Jugada {
	
	// ATRIBUTOS
	
	// Enumerado con las distintas jugadas
	private enum Jugadas{CARTA, PAREJA, DOBLEPAREJA, TRIO, ESCALERA,
		 				 COLOR, FULL, POKER, ESCALERACOLOR, ESCALERAREAL};
	
	// Nombre de la jugada
	private String nombre;
	
	// Array de cartas
	private ArrayList<Carta> cartas;
	
	// String con la carta uno
	private String cartaUno;
	
	// String con la carta dos
	private String cartaDos;
	
	// Valor de la carta
	private int valor;
	
	/**
	 * Constructor
	 * @param nombre: Nombre de la jugada
	 * @param elemA: Carta 1
	 * @param elemB: Carta 2
	 * @param c: Array de las cartas que forman la jugada
	 */
	public Jugada(String nombre, char elemA, char elemB, ArrayList<Carta> c) {
		this.nombre = nombre;
		this.cartas = c;
		this.valor = this.calculaValor();
		if(elemA == ' ') {
			cartaUno = null;
		}
		else cartaUno = this.aPlural(elemA);
		if(elemB == ' ') {
			cartaDos = null;
		}
		else cartaDos = this.aPlural(elemB);
	}
	
	/**
	 * Calcula el valor de la jugada en funcion de su nombre
	 * @return r
	 */
	private int calculaValor() {
		int r = 0;
		switch (nombre) {
		case "Carta alta": r = Jugadas.CARTA.ordinal(); break;
		case "Dobre pareja": r = Jugadas.DOBLEPAREJA.ordinal(); break;
		case "Trio": r = Jugadas.TRIO.ordinal(); break;
		case "Escalera": r = Jugadas.ESCALERA.ordinal(); break;
		case "Color": r = Jugadas.COLOR.ordinal(); break;
		case "Full": r = Jugadas.FULL.ordinal(); break;
		case "Poker": r = Jugadas.POKER.ordinal(); break;
		case "Escalera de color": r = Jugadas.ESCALERACOLOR.ordinal(); break;
		case "Escalera real": r = Jugadas.ESCALERAREAL.ordinal(); break;
		}
		return r;
	}
	
	/**
	 * Devuelve el plurar de las cartas
	 * @param e: Carta
	 * @return result
	 */
	private String aPlural(char e) {
		String result = "";
		switch (e) {
		case 'A': result = "ases"; break;
		case '2': result = "doses"; break;
		case '3': result = "treses"; break;
		case '4': result = "cuatros"; break;
		case '5': result = "cincos"; break;
		case '6': result = "seises"; break;
		case '7': result = "sietes"; break;
		case '8': result = "ochos"; break;
		case '9': result = "nueves"; break;
		case 'T': result = "dieces"; break;
		case 'J': result = "sotas"; break;
		case 'Q': result = "reinas"; break;
		case 'K': result = "reyes"; break;
		case 'd': result = "diamantes"; break;
		case 'h': result = "corazones"; break;
		case 'c': result = "treboles"; break;
		case 's': result = "picas"; break;
		}
		return result;
	}
	
	/**
	 * Si la jugada es un full escribe la salida correspondiente
	 * @return result
	 *
	public String algoSiMeLoTienesQueDecir() {
		String result = "";
		if(cartaUno != null)
			result += " de " + cartaUno;
		if(cartaDos != null)
			result += " y " + cartaDos;
		return result;
	}*/
	
	/**
	 * Getter del valor de la jugada
	 * @return valor
	 */
	public int getValor() {
		return valor;
	}
	
	@Override
	public String toString() {
		String result = nombre + ' ';
		if(cartaUno != null)
			result += "de " + cartaUno + ' ';
		if(cartaDos != null)
			result += "y " + cartaDos + ' ';
		if(cartas != null) {
			result += "(";
			for(Carta c : cartas) {
				result += c;
			}
			result += ")";
		}
		return result;
	}
	
	/**
	 * Getter de la mano de la jugada
	 * @return cartas
	 */
	public ArrayList<Carta> getMano(){
		return cartas;
	}
	
	/**
	 * Transforma en String un array de cartas
	 * @return r
	 *
	public String manoAString() {
		String r = "";
		for(Carta c : getMano()) {
			char f = c.getFigura();
			char col = c.getColor();
			r += Character.toString(f) + Character.toString(col);
		}
		return r;
	}*/
	
	/**
	 * Getter del nombre de la jugada
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Comparador de jugadas
	 * @param a: Jugada A a comparar
	 * @param b: Jugada B a comparar
	 * @return result
	 *
	public static int compare(Jugada a, Jugada b) {
		int result;
		if(a.getValor() > b.getValor()) result = -1;
		else if(a.getValor() == b.getValor()) {
			result = 0;
			int pos = 0;
			while(result == 0 && pos < a.getMano().size()) {
				result = Carta.compare(a.getMano().get(pos), b.getMano().get(pos));
				pos++;
			}
			
		}
		else result = 1;
		return result;
	}*/
	
	/**
	 * Comprueba que jugada es mejor
	 * @param a: Jugada A a comparar
	 * @param b: Jugada B a comparar
	 * @return esMejor
	 *
	public static boolean esMejorJugada(Jugada a, Jugada b) {
		boolean esMejor = false;
		if(b == null)
			esMejor = true;
		else {
			int aux = compare(a, b);
			if(aux == -1) esMejor = true;
		}
		return esMejor;
	}*/
	
}
