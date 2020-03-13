package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import hja.vista.GUI;

public class Toolbar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField areaRango;
	private JButton limpiar;
	private JButton desmarcar;
	private JButton marcarRango;
	private JButton leerRango;


	public Toolbar(GUI g) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// campo de texto de la entrada
		areaRango = new JTextField();
		areaRango.setEditable(true);
		areaRango.setFont(new Font("Dialog", Font.BOLD, 25));
		areaRango.setBorder(BorderFactory.createTitledBorder(
											BorderFactory.createLineBorder(Color.BLACK),
											"Representacion textual del rango"));
		areaRango.setMaximumSize(new Dimension(800, 110));
		JScrollPane scroll = new JScrollPane(areaRango);
		scroll.setMaximumSize(new Dimension(900, 110));
		scroll.setPreferredSize(new Dimension(800, 110));
		
		// boton de limpiar area de texto
		limpiar = new JButton(new ImageIcon("src/iconos/replay.png"));
		limpiar.setToolTipText("Limpia el area de texto");
		limpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				limpia();
			}
		});
		limpiar.setMaximumSize(new Dimension(110, 110));
		limpiar.setPreferredSize(new Dimension(110, 110));
		
		
		// boton de desmarcar tablero
		desmarcar = new JButton(new ImageIcon("src/iconos/cursor.png"));
		desmarcar.setToolTipText("Desmarca todas las casillas de la tabla");
		desmarcar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.desmarcaPanelRangos();
				g.escribePorcentaje(0.0);
				g.actualizaSlider();
			}
		});
		desmarcar.setMaximumSize(new Dimension(110, 110));
		desmarcar.setPreferredSize(new Dimension(110, 110));
		
		// boton de marcar rango
		marcarRango = new JButton(new ImageIcon("src/iconos/import.png"));
		marcarRango.setToolTipText("Marca el rango introducido en el area de texto");
		marcarRango.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int combos = g.marcaRango(areaRango.getText());
				Double p = calculaPorcentaje(combos);
				g.escribePorcentaje(p);
				g.actualizaSlider();
				
			}
		});
		marcarRango.setMaximumSize(new Dimension(110, 110));
		marcarRango.setPreferredSize(new Dimension(110, 110));
		
		// boton de leer rango
		leerRango = new JButton(new ImageIcon("src/iconos/export.png"));
		leerRango.setToolTipText("Devuelve el rango que hay en la tabla");
		leerRango.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.leeRango();
			}
		});
		leerRango.setMaximumSize(new Dimension(110, 110));
		leerRango.setPreferredSize(new Dimension(110, 110));
		
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Toolbar"));

		this.add(limpiar);
		this.add(desmarcar);
		this.add(scroll);
		this.add(marcarRango);
		this.add(leerRango);
		
		g.add(this, BorderLayout.NORTH);
	}
	
	
	public void limpia() {
		areaRango.setText("");
	}
	
	public void escribe(String s) {
		areaRango.setText(s);
	}
	
	public String getText() {
		return areaRango.getText();
	}
	
	
	private Double calculaPorcentaje(int combos) {
		Double result = combos * 100.0;
		result /= 1326;
		return result;
	}


	public void ejecutaRango() {
		this.marcarRango.doClick();
	}
	
	
	
	
	
}
