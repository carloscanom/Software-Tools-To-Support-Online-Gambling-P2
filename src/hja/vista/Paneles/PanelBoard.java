package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hja.modelo.game.Carta;
import hja.vista.GUI;
import hja.vista.Botones.BotonCarta;
import hja.vista.LabelCarta.LabelCarta;

public class PanelBoard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private static final String colorRojo = "#ffb3b3";
	private static final String colorVerde = "#99ff99";
	private static final String colorAzul = "#b3b3ff";
	private static final String colorGris = "#ebebe0";
	
	private static final String[] colores = { colorRojo, colorVerde, colorAzul, colorGris };
	public static final char[] palos = { 'h', 'c', 'd', 's' };
	
	private static final int filas = 13;
	private static final int columnas = 4;
	
	private BotonCarta[][] cartas;

	private LabelCarta carta1;
	private LabelCarta carta2;
	private LabelCarta carta3;
	private LabelCarta carta4;
	private LabelCarta carta5;

	public PanelBoard(GUI gui, JPanel panelLateral) {
		super();
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "Tablero"));

		JPanel pSup = new JPanel();
		GridLayout g = new GridLayout(1, 5);
		g.setHgap(2);
		g.setVgap(2);
		pSup.setLayout(g);
		carta1 = new LabelCarta(null);
		carta1.setMaximumSize(new Dimension(50, 50));
		carta1.setHorizontalAlignment(SwingConstants.CENTER);
		carta1.setBorder(BorderFactory.createEtchedBorder());
		pSup.add(carta1);
		
		carta2 = new LabelCarta(null);
		carta2.setMaximumSize(new Dimension(50, 50));
		carta2.setHorizontalAlignment(SwingConstants.CENTER);
		carta2.setBorder(BorderFactory.createEtchedBorder());
		pSup.add(carta2);
		
		carta3 = new LabelCarta(null);
		carta3.setMaximumSize(new Dimension(50, 50));
		carta3.setHorizontalAlignment(SwingConstants.CENTER);
		carta3.setBorder(BorderFactory.createEtchedBorder());
		pSup.add(carta3);
		
		carta4 = new LabelCarta(null);
		carta4.setMaximumSize(new Dimension(50, 50));
		carta4.setHorizontalAlignment(SwingConstants.CENTER);
		carta4.setBorder(BorderFactory.createEtchedBorder());
		pSup.add(carta4);
		
		carta5 = new LabelCarta(null);
		carta5.setMaximumSize(new Dimension(50, 50));
		carta5.setHorizontalAlignment(SwingConstants.CENTER);
		carta5.setBorder(BorderFactory.createEtchedBorder());
		pSup.add(carta5);
		
		pSup.setPreferredSize(new Dimension(300, 60));
		
		
		cartas = new BotonCarta[filas][columnas];
		JPanel tabla = new JPanel();
		GridLayout layout = new GridLayout(filas, columnas);
		layout.setHgap(1);
		layout.setVgap(1);
		tabla.setLayout(layout);
		int w = 0;
		for(int i = filas - 1; i >= 0; i--) {
			for(int j = 0; j < columnas; j++) {
				Carta aux = new Carta(i, palos[j]);
				BotonCarta bt = new BotonCarta(aux, colores[j]);
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(bt.estaMarcado()) {
							bt.setForeground(Color.BLACK);
							bt.setDefaultColor();
							desSeleccionaCarta(bt.getCarta());
						}
						else {
							if(seleccionaCarta(bt.getCarta())) {
								bt.setForeground(Color.WHITE);
								bt.marcaBoton(colorOpuesto(bt.getColorAct()));
							}
						}
					}
				});
				tabla.add(bt);
				cartas[w][j] = bt;
			}
			w++;
		}
		tabla.setPreferredSize(new Dimension(10, 10));
		tabla.setMaximumSize(new Dimension(200, 200));
		
		
		this.setPreferredSize(new Dimension(500, 400));
		this.setMaximumSize(new Dimension(500, 400));
		
		this.add(pSup,  BorderLayout.NORTH);
		this.add(tabla, BorderLayout.CENTER);
		
		panelLateral.add(this, BorderLayout.CENTER);
	}
	
	public boolean seleccionaCarta(Carta c) {
		boolean ok = false;
		if(carta1.getIcon() == null) {
			ok = true;
			carta1.setIcon(c.getImg());
			carta1.setCarta(c);
		}
		else if(carta2.getIcon() == null) {
			ok = true;
			carta2.setIcon(c.getImg());
			carta2.setCarta(c);
		}
		else if(carta3.getIcon() == null) {
			ok = true;
			carta3.setIcon(c.getImg());
			carta3.setCarta(c);
		}
		else if(carta4.getIcon() == null) {
			ok = true;
			carta4.setIcon(c.getImg());
			carta4.setCarta(c);
		}
		else if(carta5.getIcon() == null) {
			ok = true;
			carta5.setIcon(c.getImg());
			carta5.setCarta(c);
		}
		return ok;
	}
	
	public void desSeleccionaCarta(Carta c) {
		ImageIcon i = c.getImg();
		if(carta1.getIcon() == i) {
			carta1.setIcon(null);
			carta1.setCarta(null);
		}
		else if(carta2.getIcon() == i) {
			carta2.setIcon(null);
			carta2.setCarta(null);
		}
		else if(carta3.getIcon() == i) {
			carta3.setIcon(null);
			carta3.setCarta(null);
		}
		else if(carta4.getIcon() == i) {
			carta4.setIcon(null);
			carta4.setCarta(null);
		}
		else if(carta5.getIcon() == i) {
			carta5.setIcon(null);
			carta5.setCarta(null);
		}
	}
	
	
	public String colorOpuesto(String c) {
		String r = null;
		switch(c) {
		case colorRojo: r = "#ff0000"; break;
		case colorVerde: r = "#009933"; break;
		case colorAzul: r = "#0000ff"; break;
		case colorGris: r = "#5c5c3d"; break;
		}
		return r;
	}
	
	
	public ArrayList<Carta> getCartasBoard(){
		ArrayList<Carta> result = new ArrayList<Carta>();
		if(carta1.getIcon() != null) {
			result.add(carta1.getCarta());
		}
		if(carta2.getIcon() != null) {
			result.add(carta2.getCarta());
		}
		if(carta3.getIcon() != null) {
			result.add(carta3.getCarta());
		}
		if(carta4.getIcon() != null) {
			result.add(carta4.getCarta());
		}
		if(carta5.getIcon() != null) {
			result.add(carta5.getCarta());
		}
		return result;
	}

	

}
