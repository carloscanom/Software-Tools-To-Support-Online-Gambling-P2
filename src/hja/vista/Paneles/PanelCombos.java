package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import hja.modelo.game.Carta;
import hja.modelo.game.Mano;
import hja.vista.GUI;

public class PanelCombos extends JPanel {

	/**
	 * 20 posibles jugadas
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JButton play;
	
	private PanelJugadas panelJugadas;
	
	private JScrollPane scroll;
	
	private JTextField combos;
	
	public PanelCombos(GUI g, JPanel panelLateral) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Combos"));
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		
		this.play = new JButton("Calcula combos");
		this.play.setAlignmentX(0);
		this.play.setToolTipText("Calcula los combos posibles");
		this.play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ArrayList<String> casillas = g.getCasillasMarcadas();
				ArrayList<Carta> board = g.getCartasBoard();
				
				if(board.size() > 2) {
					panelJugadas.limpia();
					updateUI();
					int c = calculaCombos(casillas, board);
					
					combos.setText(Integer.toString(c));
					Double porciento = c * 100.0 / 1326;
					
					g.escribePorcentaje(porciento);
					g.actualizaSlider();
				}
				else {
					JOptionPane.showMessageDialog(null,
											"No hay suficientes cartas en el board",
											"No se puede calcular",
											JOptionPane.WARNING_MESSAGE);
				}
				
			}});
		this.add(play);
		
		
		this.panelJugadas = new PanelJugadas();
		this.scroll = new JScrollPane(this.panelJugadas);
		this.add(scroll);
		
		
		JPanel panelCombos = new JPanel();
		panelCombos.setLayout(new BoxLayout(panelCombos, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("Combos: ");
		panelCombos.add(label);
		
		this.combos = new JTextField();
		combos.setEditable(false);
		panelCombos.add(this.combos);
		
		JScrollPane scroll2 = new JScrollPane(panelCombos);
		scroll2.setPreferredSize(new Dimension(300, 60));
		scroll2.setMaximumSize(new Dimension(300, 60));
		scroll2.setBorder(BorderFactory.createEtchedBorder());
		
		this.add(scroll2);
		
		this.setPreferredSize(new Dimension(270, 50));
		
		panelLateral.add(this, BorderLayout.EAST);
	}
	
	
	// FUNCION QUE CALCULA LOS COMBOS TOTALES CON LAS CARTAS DEL BOARD Y LAS CASILLAS DE LA TABLA
	/*********************************************************************************************
	
	boolean esSwitter(String combo) {
		boolean ok;
		if(combo.charAt(1) == combo.charAt(3)) {
			ok = true;
		}
		else {
			ok = false;
		}
		return ok;
	}
	
	/*
	 * MIRA SI EL COMBO ES VALIDO, ES DECIR
	 * COMPRUEBA SI EL COMBO POSEE ALGUNA CARTA DEL BOARD
	 *
	
	boolean esValidoElCombo(ArrayList<Carta> board, String combo) {
		boolean ok = true;
		int j = 0;
		Carta primeraCarta = new Carta(combo.charAt(0), combo.charAt(1));
		Carta segundaCarta = new Carta(combo.charAt(2), combo.charAt(3));
		while(j < board.size() && ok == true) {
			if(board.get(j).getFigura() == primeraCarta.getFigura() &&
					board.get(j).getColor() == primeraCarta.getColor() ||
					board.get(j).getFigura() == segundaCarta.getFigura() &&
					board.get(j).getColor() == segundaCarta.getColor()) {
					ok = false;	
			}
			else {
				ok = true;
			}
			j++;
		}		
		return ok;	
	}
	
	/*
	 * CAMBIA EL ORDEN DE LAS CARTAS DEL COMBO
	 * PARA VER SI SE ENCUENTRA
	 * EN EL ARRAYLIST PERO EN DISTINTO ORDEN.
	 *
	
	boolean quitarDuplicado(ArrayList<String> combosPosibles, String combo) {
		boolean ok = true;
		Carta primeraCarta = new Carta(combo.charAt(2), combo.charAt(3));
		Carta segundaCarta = new Carta(combo.charAt(0), combo.charAt(1));
		String comboAlReves = String.valueOf(primeraCarta.toString() + segundaCarta.toString());
		if(combosPosibles.isEmpty()) {
			ok = true;
		}
		else if(!combosPosibles.isEmpty()) {
			if(combosPosibles.contains(comboAlReves)) {
				ok = false;
			}
		}
		return ok;
	}
	
	/*
	 * VUELTA ATRÁS
	 * EJEMPLO:AA
	 * METE LA PRIMERA FIGURA EN EL STRING COMBO "A"
	 * METE SEGUN EL INDICE DEL FOR LOS PALOS "Ah"
	 * METE LA SEGUNDA FIGURA "AhA"
	 * METE EL SEGUNDO PALO SEGUN EL INDICE DEL FOR "AhAh"
	 * COMPRUEBA QUE SI SON PAREJA NO PUEDEN TENER EL MISMO PALO, SI SON DIFERENTE FIGURA SI
	 * AL NO SER VALIDO QUITA EL ULTIMO CARACTER "AhA"
	 * VUELVE AL FOR Y METE SIGUIENTE PALO "AhAs"
	 * AL SER VALIDO COMPRUEBA 1º SI ES VALIDO EL COMBO DADAS UNAS CARTAS EN EL BOARD 
	 * Y 2º COMPRUEBA QUE ESE COMBO NO SE ENCUENTRE YA EN EL ARRAYLIST PERO EN ORDEN DISTINTO ES DECIR: "AsAh"
	 * SI TODO ES CORRECTO AÑADE ESE COMBO AL ARRAYLIST "combosPosibles" 
	 * BORRA EL ULTIMO CARACTER "AhA"
	 * VUELVE A METER OTRO PALO EN ESTE CASO SE QUEDA "AhAd"
	 * COMPRUEBA QUE ESTE TODO CORRECTO Y LO INTRODUCE, EN EL CASO DE QUE SEA CORRECTO PERO NO VALIDO
	 * BORRA EL ULTIMO PALO E INTRODUCE OTRO HASTA LLEGAR AL LIMITE DEL FOR Y VOLVER AL FOR ANTERIOR
	 * EN ESE CASO BORRA LOS 3 ULTIMOS CARACTERES E INTRODUCE EL SIGUIENTE PALO "As"
	 * Y VOLVERIA A EMPEZAR, "AsA"..."AsAh", AL ESTAR DUPLICADO NO VALDRIA..."AsAs", NO VALDRIA..."AsAd", VALDRIA...
	 *
	
	@SuppressWarnings("unused")
	private ArrayList<String> listaCombosPosible(ArrayList<String> manos, ArrayList<Carta> board) {
		ArrayList<String> combosPosibles = new ArrayList<String>();
		String combo = String.valueOf("");
		int tam = 0;
		while(tam < manos.size()) {
			combo += String.valueOf(manos.get(tam).charAt(0));
			for(int i = 0; i < 4; i++) {
				if(i == 0) {
					combo += "h";
				}
				else if(i == 1) {
					combo += "s";
				}
				else if(i == 2) {
					combo += "d";
				}
				else {
					combo += "c";
				}
				combo += String.valueOf(manos.get(tam).charAt(1));
				for(int j = 0; j < 4; j++) {
					if(j == 0) {
						combo += "h";
					}
					else if(j == 1) {
						combo += "s";
					}
					else if(j == 2) {
						combo += "d";
					}
					else {
						combo += "c";
					}
					if(combo.charAt(0) == combo.charAt(2) && combo.charAt(1) != combo.charAt(3)) {//Si es pareja
						if(esValidoElCombo(board, combo) && quitarDuplicado(combosPosibles, combo) == true) {
							combosPosibles.add(combo);		
							String cadenaNueva = combo.substring(0, combo.length()-1); 
							combo = cadenaNueva;
						}
						else {
							String cadenaNueva = combo.substring(0, combo.length()-1); 
							combo = cadenaNueva;
						}
					}
					else if(combo.charAt(0) != combo.charAt(2)){
						if(manos.get(tam).charAt(2) == 's' && esValidoElCombo(board, combo) && quitarDuplicado(combosPosibles, combo) == true && esSwitter(combo)) {//Si es switter
							combosPosibles.add(combo);		
							String cadenaNueva = combo.substring(0, combo.length()-1); 
							combo = cadenaNueva;
						}
						else if(manos.get(tam).charAt(2) == 'o' && esValidoElCombo(board, combo) && quitarDuplicado(combosPosibles, combo) == true && !esSwitter(combo)) {//Si es off switter
							combosPosibles.add(combo);		
							String cadenaNueva = combo.substring(0, combo.length()-1); 
							combo = cadenaNueva;
						}
						else {
							String cadenaNueva = combo.substring(0, combo.length()-1); 
							combo = cadenaNueva;
						}
					}
					//Si no es valido 
					//Borro la ultima letra(PALO)
					else {
						String cadenaNueva = combo.substring(0, combo.length()-1); 
						combo = cadenaNueva;
					}
					//Si posee el combo dos palos iguales
					//Borro la ultima letra(PALO)
				}
				//Acaba el primer palo 
				//Borro hasta la priemra figuara
				//Cuidado aqui porq ya tiene un caracter borrado
				String cadenaNueva = combo.substring(0, 1); 
				combo = cadenaNueva;
			}
			combo = String.valueOf("");
			tam++;
		}
		System.out.println("	COMBOS POSIBLES DEL ARRAYLIST");
		System.out.println("******************************************");
		for(int i = 0; i < combosPosibles.size(); i++) {
			System.out.println(combosPosibles.get(i));
		}
		System.out.println("******************************************");
		
		return combosPosibles;
	}
	
	/***********************************************************************************************/
	
	private ArrayList<ArrayList<Carta>> creaCombinaciones(String mano){
		ArrayList<ArrayList<Carta>> result = new ArrayList<ArrayList<Carta>>();
		ArrayList<Carta> aux = new ArrayList<Carta>();
		if(mano.length() == 2) { // parejas - 6 combis
			for(int i = 0; i < 3; i++) {
				aux.add(new Carta(mano.charAt(0), PanelBoard.palos[i]));
				for(int j = i + 1; j < 4; j++) {
					aux.add(new Carta(mano.charAt(0), PanelBoard.palos[j]));
					ArrayList<Carta> aux2 = new ArrayList<Carta>();
					aux2.addAll(aux);
					result.add(aux2);
					aux.remove(aux.size() - 1);
				}
				aux.remove(aux.size() - 1);
			}
		}
		else if(mano.length() == 3) {
			if(mano.charAt(2) == 'o') { // manos off - 12 combis
				for(int i = 0; i < 4; i++) {
					aux.add(new Carta(mano.charAt(0), PanelBoard.palos[i]));
					for(int j = 0; j < 4; j++) {
						if(aux.get(aux.size() - 1).getColor() != PanelBoard.palos[j]) {
							aux.add(new Carta(mano.charAt(1), PanelBoard.palos[j]));
							ArrayList<Carta> aux2 = new ArrayList<Carta>();
							aux2.addAll(aux);
							result.add(aux2);
							aux.remove(aux.size() - 1);
						}
					}
					aux.remove(aux.size() - 1);
				}
			}
			else { // manos suited - 4 combis
				for(int i = 0; i < 4; i++) {
					aux.add(new Carta(mano.charAt(0), PanelBoard.palos[i]));
					aux.add(new Carta(mano.charAt(1), PanelBoard.palos[i]));
					ArrayList<Carta> aux2 = new ArrayList<Carta>();
					aux2.addAll(aux);
					result.add(aux2);
					aux.remove(aux.size() - 1);
					aux.remove(aux.size() - 1);
				}
			}
		}
		return result;
	}
	
	
	
	
	// funcion que devuelve si una carta esta en un array de cartas -- YA ESTA COMPLETA
	private boolean contiene(ArrayList<Carta> v, Carta c) {
		boolean esta = false;
		int pos = 0;
		while(pos < v.size() && !esta) {
			if(v.get(pos).getFigura() == c.getFigura() && v.get(pos).getColor() == c.getColor())
				esta = true;
			pos++;
		}
		return esta;
	}
	
	// funcion que devuelve el numero de combos -- YA ESTA COMPLETA, SOLO FALTA AÑADIR
	// PARA QUE AÑADA EN EL PANEL DE LOS GRAFICOS
	private int calculaCombos(ArrayList<String> manos, ArrayList<Carta> board) {
		int combosTotal = 0;
		
		ArrayList<String> botones = new ArrayList<String>();
		ArrayList<ArrayList<Mano>> jugadas = new ArrayList<ArrayList<Mano>>();
		
		for(int i = 0; i < manos.size(); i++) {
			int combosAct = 0;
			String manoBt = manos.get(i);
			ArrayList<ArrayList<Carta>> combis = this.creaCombinaciones(manoBt);
			for(int w = 0 ; w < combis.size(); w++) {
				ArrayList<Carta> mano = combis.get(w);
				boolean hayRepes = false;
				int pos = 0;
				while(pos < mano.size() && !hayRepes) {
					if(contiene(board, mano.get(pos))) hayRepes = true;
					pos++;
				}
				if(!hayRepes) {
					combosAct++;
					ArrayList<Carta> a = new ArrayList<Carta>(mano);
					a.addAll(board);
					int esta = botones.indexOf(manoBt);
					if(esta == -1) {
						botones.add(manoBt);
						ArrayList<Mano> aux = new ArrayList<Mano>();
						aux.add(new Mano(a));
						jugadas.add(aux);
					}
					else {
						jugadas.get(esta).add(new Mano(a));
					}
				}
				
			}
			combosTotal += combosAct;
		}
		
		panelJugadas.pinta(botones,  jugadas);
		
		return combosTotal;
	}
	

	/*********************************************************************************************/
	
	
	

}
