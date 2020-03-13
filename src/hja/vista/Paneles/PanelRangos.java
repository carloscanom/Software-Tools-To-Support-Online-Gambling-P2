package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import hja.modelo.game.Carta;
import hja.vista.GUI;
import hja.vista.Botones.BotonMano;

public class PanelRangos extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// tabla de botones
	private BotonMano[][] rangos;

	// dimension de la tabla de botones
	private static final int dim = 13;

	private static class Casilla {
		private int i;
		private int j;
		private Double p;

		public Casilla(Double prio, int i, int j) {
			p = prio;
			this.i = i;
			this.j = j;
		}

		public Double getPrio() {
			return p;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}

		public static int compare(Casilla a, Casilla b) {
			int r;
			if (a.getPrio() > b.getPrio())
				r = -1;
			else
				r = 1;
			return r;
		}

	}

	private ArrayList<Casilla> ranking;

	private static final double[][] chubukov = {
			/* A */{ Double.MAX_VALUE, 277, 137, 91.6, 69.5, 52, 44.9, 39.5, 35.3, 36.1, 33.3, 31.1, 29 },
			/* K */{ 166, 477, 43.3, 36.3, 31.4, 23.9, 19.9, 18.6, 17.4, 16.1, 15, 14.1, 13.3 },
			/* Q */{ 96, 29.3, 239, 24.7, 21.9, 16.2, 13.3, 11.3, 10.9, 10.1, 9.4, 8.8, 8.3 },
			/* J */{ 68.1, 25.4, 16.4, 159, 18, 12.8, 10.3, 8.5, 7.3, 7, 6.4, 6, 5.5 },
			/* T */{ 53.1, 22.4, 14.8, 11.5, 120, 11.2, 8.7, 7, 5.9, 4.9, 4.6, 4.2, 3.7 },
			/* 9 */{ 40.8, 17.8, 11.7, 8.8, 7.4, 95.7, 7.6, 6.1, 5, 4.1, 3.2, 3, 2.6 },
			/* 8 */{ 35.4, 15.2, 9.9, 7.4, 6, 5.1, 79.6, 5.5, 4.5, 3.6, 2.8, 2.2, 2 },
			/* 7 */{ 31.3, 14.2, 8.5, 6.3, 5.1, 4.2, 3.7, 67.4, 4.1, 3.2, 2.5, 2, 1.6 },
			/* 6 */{ 28, 13.3, 8.1, 5.3, 4.2, 3.5, 3, 2.7, 57.6, 3.1, 2.3, 1.8, 1.5 },
			/* 5 */{ 28.2, 12.3, 7.5, 5, 3.4, 2.8, 2.4, 2.1, 1.9, 49.3, 2.4, 1.9, 1.5 },
			/* 4 */{ 25.9, 11.4, 6.8, 4.4, 3.1, 2.1, 1.8, 1.6, 1.5, 1.6, 40.9, 1.7, 1.4 },
			/* 3 */{ 24.2, 10.6, 6.2, 3.9, 2.7, 2, 1.5, 1.3, 1.3, 1.3, 1.1, 32.7, 1.2 },
			/* 2 */{ 22.5, 10, 5.6, 3.44, 2.4, 1.8, 1.4, 1.1, 1, 1, 0.9, 0.9, 24 } };

	public PanelRangos(GUI ventanaPrincipal) {
		this.setLayout(new GridLayout());

		ranking = cargaRanking();
		ranking.sort(new Comparator<Casilla>() {
			@Override
			public int compare(Casilla o1, Casilla o2) {
				return Casilla.compare(o1, o2);
			}
		});

		this.rangos = new BotonMano[dim][dim];
		JPanel panelMatriz = new JPanel(new GridLayout(dim, dim));
		for (int i = dim - 1; i >= 0; i--) {
			for (int j = dim - 1; j >= 0; j--) {
				String name = this.nombreBoton(i, j);
				String color = this.colorBoton(name);
				boolean suited = this.esSuited(color);

				BotonMano bt = new BotonMano(name, color, suited);
				bt.setPreferredSize(new Dimension(20, 50));
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (bt.getColorAct().equals(bt.getColorDefault())) {
							bt.marcaBoton(GUI.colorAmarillo);
							ventanaPrincipal.leeRango();
							ventanaPrincipal.actualizaPorcentaje();
						} else {
							bt.setDefaultColor();
							ventanaPrincipal.leeRango();
							ventanaPrincipal.actualizaPorcentaje();
						}
					}
				});

				rangos[i][j] = bt;
				panelMatriz.add(rangos[i][j]);
			}
		}
		panelMatriz.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Representacion grafica del rango"));
		panelMatriz.setMaximumSize(new Dimension(500, 650));
		panelMatriz.setPreferredSize(new Dimension(500, 650));
		this.add(panelMatriz);
		
		this.setMaximumSize(new Dimension(400, 650));
		this.setPreferredSize(new Dimension(400, 650));
		
		ventanaPrincipal.add(this, BorderLayout.CENTER);
	}

	private ArrayList<Casilla> cargaRanking() {
		ArrayList<Casilla> r = new ArrayList<Casilla>();
		int w = 0, t;
		for (int i = dim - 1; i >= 0; i--) {
			t = 0;
			for (int j = dim - 1; j >= 0; j--) {
				Casilla aux = new Casilla(chubukov[w][t], i, j);
				r.add(aux);
				t++;
			}
			w++;
		}
		return r;
	}

	private String nombreBoton(int i, int j) {
		String nombre;
		Carta a = new Carta(i, ' ');
		Carta b = new Carta(j, ' ');
		if (i > j) { // cartas suited
			nombre = Character.toString(a.getFigura()) + Character.toString(b.getFigura()) + "s";
		} else if (i < j) { // cartas off
			nombre = Character.toString(b.getFigura()) + Character.toString(a.getFigura()) + "o";
		} else { // parejas
			nombre = Character.toString(a.getFigura()) + Character.toString(b.getFigura());
		}
		return nombre;
	}

	private String colorBoton(String name) {
		String color;
		if (name.length() == 3) {
			if (name.charAt(2) == 'o') { // cartas suited
				color = "#cccccc";
			} else { // cartas off-suited
				color = "#ff4d4d";
			}
		} else { // pareja de cartas
			color = "#4dff88";
		}
		return color;
	}

	private boolean esSuited(String color) {
		return color == "#ff4d4d";
	}

	public void desmarca() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				rangos[i][j].setDefaultColor();
			}
		}
	}

	public int marcaMano(String mano) {
		int tam = mano.length();
		int combos = 0;
		if (tam == 2) { // parejas
			Carta a = new Carta(mano.charAt(0), ' ');
			int v = a.getValor();
			if (!this.rangos[v][v].estaMarcado()) {
				this.rangos[v][v].marcaBoton(GUI.colorAmarillo);
				combos += 6;
			}
		} else if (tam == 3) {
			Carta a = new Carta(mano.charAt(0), ' ');
			Carta b = new Carta(mano.charAt(1), ' ');
			int v = a.getValor();
			int v2 = b.getValor();
			if (mano.charAt(2) == 's') { // AKs
				if (!this.rangos[v][v2].estaMarcado()) {
					this.rangos[v][v2].marcaBoton(GUI.colorAmarillo);
					combos += 4;
				}
			} else if (mano.charAt(2) == 'o') { // AKo
				if (!this.rangos[v2][v].estaMarcado()) {
					this.rangos[v2][v].marcaBoton(GUI.colorAmarillo);
					combos += 12;
				}
			} else { // AA+
				for (int i = v; i < dim; i++) {
					if (!this.rangos[i][i].estaMarcado()) {
						this.rangos[i][i].marcaBoton(GUI.colorAmarillo);
						combos += 6;
					}
				}
			}
		} else if (tam == 4) {
			Carta a = new Carta(mano.charAt(0), ' ');
			Carta b = new Carta(mano.charAt(1), ' ');
			int v = a.getValor();
			int v2 = b.getValor();
			if (mano.charAt(2) == 's') { // AKs+
				for (int i = v2; i < v; i++) {
					if (!this.rangos[v][i].estaMarcado()) {
						this.rangos[v][i].marcaBoton(GUI.colorAmarillo);
						combos += 4;
					}
				}
			} else if (mano.charAt(2) == 'o') { // AKo+
				for (int i = v2; i < v; i++) {
					if (!this.rangos[i][v].estaMarcado()) {
						this.rangos[i][v].marcaBoton(GUI.colorAmarillo);
						combos += 12;
					}
				}
			}
		}
		return combos;
	}

	public int marcaIntervaloManos(String mano, String manoAux) {
		int combos = 0;
		int t = mano.length();
		int t2 = manoAux.length();
		if (t == 2 && t2 == 2) { // 88-22
			int a = new Carta(mano.charAt(0), ' ').getValor();
			int b = new Carta(manoAux.charAt(0), ' ').getValor();
			for (int i = b; i < a; i++) {
				if (!this.rangos[i][i].estaMarcado()) {
					this.rangos[i][i].marcaBoton(GUI.colorAmarillo);
					combos += 6;
				}
			}
		} else if (t2 == 3) { // suited
			char c = manoAux.charAt(2);
			int a = new Carta(mano.charAt(1), ' ').getValor();
			int b = new Carta(manoAux.charAt(1), ' ').getValor();
			if (c == 's') {
				int fila = new Carta(mano.charAt(0), ' ').getValor();
				for (int i = b; i < a; i++) {
					if (!this.rangos[fila][i].estaMarcado()) {
						this.rangos[fila][i].marcaBoton(GUI.colorAmarillo);
						combos += 4;
					}
				}
			} else { // off suited
				int columna = new Carta(mano.charAt(0), ' ').getValor();
				for (int i = b; i < a; i++) {
					if (!this.rangos[i][columna].estaMarcado()) {
						this.rangos[i][columna].marcaBoton(GUI.colorAmarillo);
						combos += 12;
					}
				}
			}

		}
		return combos;
	}

	public String dimeRango() {
		String r = "";
		boolean[][] marcados = new boolean[dim][dim];
		for (int i = 0; i < dim; i++) {

			if (rangos[i][i].estaMarcado()) { // PAREJAS
				if (!marcados[i][i]) {
					marcados[i][i] = true;
					int j = i + 1;
					while (j < dim && rangos[j][j].estaMarcado()) {
						marcados[j][j] = true;
						j++;
					}
					if (j == dim) {
						if (r.length() != 0)
							r += ",";
						r += rangos[i][i];
						if (rangos[i][i].getCarta1() != 'A')
							r += "+";
					} else {
						if (j == i + 1) {
							if (r.length() != 0)
								r += ",";
							r += rangos[i][i];
						} else {
							if (r.length() != 0)
								r += ",";
							r += rangos[j - 1][j - 1] + "-" + rangos[i][i];
						}
					}
				}
			}

			for (int w = i + 1; w < dim; w++) { // ZONA SUITED Y OFF-SUITED

				if (rangos[w][i].estaMarcado()) {
					if (!marcados[w][i]) {
						marcados[w][i] = true;
						int j = i + 1;
						while (rangos[w][j].estaMarcado() && !rangos[w][j].esPareja()) {
							marcados[w][j] = true;
							j++;
						}
						if (j == i + 1) {
							if (r.length() != 0)
								r += ",";
							r += rangos[w][i];
						} else {
							if (rangos[w][j].esPareja()) {
								if (r.length() != 0)
									r += ",";
								r += rangos[w][i] + "+";
							} else {
								if (r.length() != 0)
									r += ",";
								r += rangos[w][j - 1] + "-" + rangos[w][i];
							}
						}
					}
				}

				if (rangos[i][w].estaMarcado()) {
					if (!marcados[i][w]) {
						marcados[i][w] = true;
						int j = i + 1;
						while (rangos[j][w].estaMarcado() && !rangos[j][w].esPareja()) {
							marcados[j][w] = true;
							j++;
						}
						if (j == i + 1) {
							if (r.length() != 0)
								r += ",";
							r += rangos[i][w];
						} else {
							if (rangos[w][j].esPareja()) {
								if (r.length() != 0)
									r += ",";
								r += rangos[i][w] + "+";
							} else {
								if (r.length() != 0)
									r += ",";
								r += rangos[j - 1][w] + "-" + rangos[i][w];
							}
						}
					}
				}

			}

		}
		return r;
	}

	public void marcaRango(Double rango) {
		int combos = (int) (rango * 1326) / 100;
		int aux = 0;
		int pos = 0;
		boolean parar = false;
		if (combos == 0)
			parar = true;
		while (pos < ranking.size() && !parar) {
			int i = ranking.get(pos).getI();
			int j = ranking.get(pos).getJ();
			this.rangos[i][j].marcaBoton(GUI.colorMorado);
			if (this.rangos[i][j].esS()) {
				if (aux + 4 < combos)
					aux += 4;
				else
					parar = true;
			} else if (this.rangos[i][j].esPareja()) {
				if (aux + 6 < combos)
					aux += 6;
				else
					parar = true;
			} else {
				if (aux + 12 < combos)
					aux += 12;
				else
					parar = true;
			}
			pos++;
		}
	}
	
	
	public ArrayList<String> getCasillasMarcadas(){
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				if(this.rangos[i][j].estaMarcado()) {
					result.add(this.rangos[i][j].toString());
				}
			}
		}
		
		return result;
	}
	
	
	
	

}
