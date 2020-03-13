package hja.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hja.modelo.game.Carta;
import hja.vista.Paneles.PanelBoard;
import hja.vista.Paneles.PanelCombos;
import hja.vista.Paneles.PanelLeerFichero;
import hja.vista.Paneles.PanelPorcentaje;
import hja.vista.Paneles.PanelRangos;
import hja.vista.Paneles.Toolbar;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//panel donde aparece la tabla de cartas
	private PanelRangos rangos;
	
	// toolbar
	private Toolbar entrada;
	
	// panel de lectura de fichero
	@SuppressWarnings("unused")
	private PanelLeerFichero panelFichero;
	
	// panel de barra de progreso
	private PanelPorcentaje porcentaje;

	// panel del tablero
	@SuppressWarnings("unused")
	private PanelBoard panelBoard;

	// panel de combos
	@SuppressWarnings("unused")
	private PanelCombos combos;
	

	public static final String colorAmarillo = "#ffff66";
	public static final String colorMorado = "#e580ff";
	
	
	
	public GUI(File f) {
		super("Herramientas Informaticas para los Juegos de Azar - Practica 2");
		this.initGUI(f);
	}
	
	
	
	public void initGUI(File f) {
		setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				confirmacionSalida();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		
		this.setLayout(new BorderLayout());
		
		// PANEL DE RANGOS
		this.rangos = new PanelRangos(this);
		rangos.setPreferredSize(new Dimension(800, 650));
		
		// PANEL DE ENTRADA
		this.entrada = new Toolbar(this);
		
		// PANEL LATERAL
		JPanel panelLateral = new JPanel(new BorderLayout());
		this.add(panelLateral, BorderLayout.EAST);
		panelLateral.setMaximumSize(new Dimension(1400, 100));
		panelLateral.setPreferredSize(new Dimension(800, 100));
		panelLateral.setBorder(BorderFactory.createEtchedBorder());
		
		// PANEL DE LECTURA DE FICHERO
		this.panelFichero = new PanelLeerFichero(this, f, panelLateral);
		
		// PANEL BOARD
		this.panelBoard = new PanelBoard(this, panelLateral);
		panelBoard.setPreferredSize(new Dimension(200, 600));
		
		// barra de porcentaje de manos
		this.porcentaje = new PanelPorcentaje(this);

		// panel de los combos que tenemos
		this.combos = new PanelCombos(this, panelLateral);
		combos.setPreferredSize(new Dimension(500, 650));
		
		this.setExtendedState(JFrame.NORMAL);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
	public void confirmacionSalida() {
		int n = JOptionPane.showConfirmDialog(this,
						"¿Desea realmente salir de la aplicacion?", "Salir de la aplicacion",
						JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	
	public void desmarcaPanelRangos() {
		rangos.desmarca();
	}
	
	
	public void marcaRango(Double rango) {
		desmarcaPanelRangos();
		this.rangos.marcaRango(rango);
	}
	
	
	public int marcaRango(String rango) {
		desmarcaPanelRangos();
		int pos = 0;
		int combos = 0;
		while(pos < rango.length()) {
			String mano = "";
			while(pos < rango.length() && rango.charAt(pos) != ',' && rango.charAt(pos) != '-') {
				mano += rango.charAt(pos);
				pos++;
			}
			combos += this.rangos.marcaMano(mano);
			if(pos < rango.length() && rango.charAt(pos) == '-') {
				String manoAux = "";
				pos++;
				while(pos < rango.length() && rango.charAt(pos) != ',') {
					manoAux += rango.charAt(pos);
					pos++;
				}
				combos += this.rangos.marcaIntervaloManos(mano, manoAux);
			}
			pos++;
		}
		return combos;
	}
	
	public void leeRango() {
		String rango = this.rangos.dimeRango();
		escribeEntrada(rango);
	}

	
	public void escribePorcentaje(Double p) {
		this.porcentaje.escribePorcentaje(p);
	}


	public void escribeEntrada(String s) {
		this.entrada.escribe(s);
	}
	
	public void actualizaPorcentaje() {
		this.entrada.ejecutaRango();
	}
	
	
	public void actualizaSlider() {
		this.porcentaje.actualizaSlider();
	}
	
	public ArrayList<String> getCasillasMarcadas(){
		return this.rangos.getCasillasMarcadas();
	}
	
	public ArrayList<Carta> getCartasBoard(){
		return this.panelBoard.getCartasBoard();
	}
	
	
}
