package hja.vista.Paneles;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import hja.modelo.game.Carta;
import hja.modelo.game.Jugada;
import hja.modelo.game.Mano;
import hja.vista.Paneles.PanelesTipo.PanelTipo;

public class PanelJugadas extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<PanelTipo> paneles;

	private PanelTipo escaleraReal;
	private PanelTipo escaleraColor;
	private PanelTipo poker;
	private PanelTipo full;
	private PanelTipo color;
	private PanelTipo escalera;
	private PanelTipo trio;
	private PanelTipo doblePareja;

	private PanelTipo overPair;
	private PanelTipo pocketPairBelowTop;
	private PanelTipo middlePair;
	private PanelTipo weekPair;
	private PanelTipo topPair;

	private PanelTipo cartaAlta;

	private PanelTipo drawEsc;
	private PanelTipo drawEsc2;
	private PanelTipo drawCol;

	public PanelJugadas() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.paneles = new ArrayList<PanelTipo>();

		escaleraReal = new PanelTipo("EscReal", this);
		paneles.add(escaleraReal);

		escaleraColor = new PanelTipo("EscCol", this);
		paneles.add(escaleraColor);

		poker = new PanelTipo("Poker", this);
		paneles.add(poker);

		full = new PanelTipo("Fulls", this);
		paneles.add(full);

		color = new PanelTipo("Color", this);
		paneles.add(color);

		escalera = new PanelTipo("Escaleras", this);
		paneles.add(escalera);

		trio = new PanelTipo("Trios", this);
		paneles.add(trio);

		doblePareja = new PanelTipo("Dobles", this);
		paneles.add(doblePareja);

		overPair = new PanelTipo("OverPair", this);
		paneles.add(overPair);

		topPair = new PanelTipo("TopPair", this);
		paneles.add(topPair);

		pocketPairBelowTop = new PanelTipo("PPBelow", this);
		paneles.add(pocketPairBelowTop);

		middlePair = new PanelTipo("MiddlePair", this);
		paneles.add(middlePair);

		weekPair = new PanelTipo("WeekPair", this);
		paneles.add(weekPair);

		cartaAlta = new PanelTipo("No hand", this);
		paneles.add(cartaAlta);

		drawCol = new PanelTipo("DrawCol(*)", this);
		paneles.add(drawCol);

		drawEsc = new PanelTipo("Gutshot(*)", this);
		paneles.add(drawEsc);

		drawEsc2 = new PanelTipo("OpEnded(*)", this);
		paneles.add(drawEsc2);

	}

	public void pintaJugada(Jugada j, String nombre) {
		int cc = 1;
		if (nombre.length() > 4) {
			if (nombre.length() == 5) {
				cc = 6;
			} else if (nombre.charAt(2) == 's') {
				cc = 4;
			} else {
				cc = 12;
			}
		}
		switch (j.getNombre()) {
		case "Poker": {
			this.poker.anyade(nombre);
			this.poker.sumaCombos(cc);
		}
			break;
		case "Escalera real": {
			this.escaleraReal.anyade(nombre);
			this.escaleraReal.sumaCombos(cc);
		}
			break;
		case "Escalera de color": {
			this.escaleraColor.anyade(nombre);
			this.escaleraColor.sumaCombos(cc);
		}
			break;
		case "Full": {
			this.full.anyade(nombre);
			this.full.sumaCombos(cc);
		}
			break;
		case "Color": {
			this.color.anyade(nombre);
			this.color.sumaCombos(cc);
		}
			break;
		case "Escalera": {
			this.escalera.anyade(nombre);
			this.escalera.sumaCombos(cc);
		}
			break;
		case "Trio": {
			this.trio.anyade(nombre);
			this.trio.sumaCombos(cc);
		}
			break;
		case "Dobles parejas": {
			this.doblePareja.anyade(nombre);
			this.doblePareja.sumaCombos(cc);
		}
			break;
		case "Overpair": {
			this.overPair.anyade(nombre);
			this.overPair.sumaCombos(cc);
		}
			break;
		case "Toppair": {
			this.topPair.anyade(nombre);
			this.topPair.sumaCombos(cc);
		}
			break;
		case "Pocket": {
			this.pocketPairBelowTop.anyade(nombre);
			this.pocketPairBelowTop.sumaCombos(cc);
		}
			break;
		case "Middlepair": {
			this.middlePair.anyade(nombre);
			this.middlePair.sumaCombos(cc);
		}
			break;
		case "Weekpair": {
			this.weekPair.anyade(nombre);
			this.weekPair.sumaCombos(cc);
		}
			break;
		case "Carta Alta": {
			this.cartaAlta.anyade(nombre);
			this.cartaAlta.sumaCombos(cc);
		}
			break;
		case "DrawC": {
			this.drawCol.anyade(nombre);
			this.drawCol.sumaCombos(cc);
		}
			break;
		case "Gutshot": {
			this.drawEsc.anyade(nombre);
			this.drawEsc.sumaCombos(cc);
		}
			break;
		case "OpEnd": {
			this.drawEsc2.anyade(nombre);
			this.drawEsc2.sumaCombos(cc);
		}
			break;
		}
		this.actualizaUI();
	}

	private void actualizaUI() {
		for (PanelTipo p : paneles)
			p.updateUI();
	}

	public void limpia() {
		for (PanelTipo p : paneles) {
			p.limpia();
		}
	}

	/*****************************************************************************************************************/
	public void pinta(ArrayList<String> botones, ArrayList<ArrayList<Mano>> jugadas) {
		ArrayList<Jugada> lasJugadas = new ArrayList<Jugada>();
		for (int i = 0; i < botones.size(); i++) {
			boolean mismaJugada = true;
			ArrayList<Mano> aux = jugadas.get(i);
			int pos = 0;
			for (Mano m : aux) {
				Jugada jug = null;
				ArrayList<Carta> j;
				//////////////////////////////////////////////////////////////////////////////////////////////////
				ArrayList<Carta> auxEsc = m.escalera();
				if (auxEsc != null && auxEsc.size() == 5) { // tenemos escalera
					if (Mano.esReal(auxEsc)) { // escalera real
						jug = new Jugada("Escalera real", ' ', ' ', m.dimeMano());
						lasJugadas.add(jug);
						if (pos != 0) {
							if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
								mismaJugada = false;
						}
						pos++;
						continue;
					} else if (Mano.esColor(auxEsc)) { // escalera de color
						jug = new Jugada("Escalera de color", ' ', ' ', m.dimeMano());
						lasJugadas.add(jug);
						if (pos != 0) {
							if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
								mismaJugada = false;
						}
						pos++;
						continue;
					}
				}
				if (auxEsc != null && auxEsc.size() == 4) {
					if (Mano.openEnded(auxEsc)) {
						Jugada jE = new Jugada("OpEnd", ' ', ' ', m.dimeMano());
						this.pintaJugada(jE, jugadas.get(i).get(pos).getCartasTablero());
					} else {
						Jugada jE = new Jugada("Gutshot", ' ', ' ', m.dimeMano());
						this.pintaJugada(jE, jugadas.get(i).get(pos).getCartasTablero());
					}
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				ArrayList<Carta> auxCol = m.color();
				if (auxCol != null && auxCol.size() == 4) {
					Jugada jC = new Jugada("DrawC", ' ', ' ', m.dimeMano());
					this.pintaJugada(jC, jugadas.get(i).get(pos).getCartasTablero());
				}

				//////////////////////////////////////////////////////////////////////////////////////////////////
				j = m.poker(); // compruebo si tengo poker
				if (j != null) {
					jug = new Jugada("Poker", j.get(0).getFigura(), ' ', m.dimeMano());
					lasJugadas.add(jug);
					if (pos != 0) {
						if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
							mismaJugada = false;
					}
					pos++;
					continue;
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				j = m.full(); // compruebo si tengo full
				if (j != null) {
					jug = new Jugada("Full", j.get(0).getFigura(), j.get(j.size() - 1).getFigura(), m.dimeMano());
					lasJugadas.add(jug);
					if (pos != 0) {
						if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
							mismaJugada = false;
					}
					pos++;
					continue;
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				if (auxCol != null) { // hemos encontrado color //
					if (auxCol.size() == 5) {
						jug = new Jugada("Color", auxCol.get(0).getColor(), ' ', m.dimeMano());
						lasJugadas.add(jug);
						if (pos != 0) {
							if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
								mismaJugada = false;
						}
						pos++;
						continue;
					}
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				if (auxEsc != null && auxEsc.size() == 5) { // tenemos escalera
					jug = new Jugada("Escalera", ' ', ' ', m.dimeMano());
					lasJugadas.add(jug);
					if (pos != 0) {
						if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
							mismaJugada = false;
					}
					pos++;
					continue;
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				j = m.trioMesa(); // trio
				if (j != null) {
					jug = new Jugada("Trio", j.get(0).getFigura(), ' ', m.dimeMano());
					lasJugadas.add(jug);
					if (pos != 0) {
						if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
							mismaJugada = false;
					}
					pos++;
					continue;
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				j = m.doblesParejas(0); // pareja o dobles parejas
				if (j != null) {
					if (j.size() == 2) {
						// jug = new Jugada("Pareja", j.get(0).getFigura(), ' ', m.dimeMano());

						if (Mano.esOverPair(j, m.cartaMasAltaBoard())) {
							jug = new Jugada("Overpair", j.get(0).getFigura(), ' ', m.dimeMano());
						} else if (Mano.esTopPair(j, m.cartaMasAltaBoard())) {
							jug = new Jugada("Toppair", j.get(0).getFigura(), ' ', m.dimeMano());
						} else if (Mano.esMiddlePair(j, m.segundaCartaMasAltaBoard())) {
							jug = new Jugada("Middlepair", j.get(0).getFigura(), ' ', m.dimeMano());
						} else if (Mano.esPocket(j, m.getCartasTablero())) {
							jug = new Jugada("Pocket", j.get(0).getFigura(), ' ', m.dimeMano());
						} else {
							jug = new Jugada("Weekpair", j.get(0).getFigura(), ' ', m.dimeMano());
						}
						lasJugadas.add(jug);
						if (pos != 0) {
							if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
								mismaJugada = false;
						}
						pos++;
						continue;
					} else {
						jug = new Jugada("Dobles parejas", j.get(0).getFigura(), j.get(j.size() - 1).getFigura(),
								m.dimeMano());
						lasJugadas.add(jug);
						if (pos != 0) {
							if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
								mismaJugada = false;
						}
						pos++;
						continue;
					}
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////
				j = new ArrayList<Carta>();
				j.add(m.dimeCarta(0)); // solo llevo la carta alta
				jug = new Jugada("Carta Alta", ' ', ' ', m.dimeMano());
				lasJugadas.add(jug);
				if (pos != 0) {
					if (!lasJugadas.get(0).getNombre().equals(jug.getNombre()))
						mismaJugada = false;
				}
				pos++;
				//////////////////////////////////////////////////////////////////////////////////////////////////
			} // fin for 2
			if (mismaJugada) {
				String boton = botones.get(i);
				if (boton.length() == 3) { // suites u off
					if (boton.charAt(2) == 'o') { // off
						if (jugadas.get(i).size() == 12) {
							Jugada jj = lasJugadas.get(0);
							this.pintaJugada(jj, boton + "(" + jugadas.get(i).size() + ")");
						} else {
							for (int t = 0; t < lasJugadas.size(); t++) {
								this.pintaJugada(lasJugadas.get(t), jugadas.get(i).get(t).getCartasTablero());
							}
						}
					} else { // suited
						if (jugadas.get(i).size() == 4) {
							Jugada jj = lasJugadas.get(0);
							this.pintaJugada(jj, boton + "(" + jugadas.get(i).size() + ")");
						} else {
							for (int t = 0; t < lasJugadas.size(); t++) {
								this.pintaJugada(lasJugadas.get(t), jugadas.get(i).get(t).getCartasTablero());
							}
						}
					}
				} else { // parejas
					if (jugadas.get(i).size() == 6) {
						Jugada jj = lasJugadas.get(0);
						this.pintaJugada(jj, boton + "(" + jugadas.get(i).size() + ")");
					} else {
						for (int t = 0; t < lasJugadas.size(); t++) {
							this.pintaJugada(lasJugadas.get(t), jugadas.get(i).get(t).getCartasTablero());
						}
					}
				}

			} else {
				for (int t = 0; t < lasJugadas.size(); t++) {
					this.pintaJugada(lasJugadas.get(t), jugadas.get(i).get(t).getCartasTablero());
				}
			}
			lasJugadas.clear();
		} // fin for 1

	}
	/*****************************************************************************************************************/
}
