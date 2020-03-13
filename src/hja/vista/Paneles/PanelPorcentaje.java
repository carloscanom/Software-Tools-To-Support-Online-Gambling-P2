package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hja.vista.GUI;

public class PanelPorcentaje extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSlider barra;
	
	private JTextField numero;
	
	private boolean actuaSlider;
	private boolean actuaNumero;

	public PanelPorcentaje(GUI g) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		actuaSlider = true;
		actuaNumero = true;
		
		barra = new JSlider(0, 100, 0);
		barra.setPreferredSize(new Dimension(100, 50));
		barra.setMaximumSize(new Dimension(1200, 100));
		barra.setPaintTicks(true);
	    barra.setMajorTickSpacing(10);
	    barra.setMinorTickSpacing(1);
		barra.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(actuaSlider) {
					actuaNumero = false;
					int aux = barra.getValue();
					Double p = (double) aux;
					escribePorcentaje(p);
					g.marcaRango(p);
					g.leeRango();
					actuaNumero = true;
				}
			}
		});
		
		
		JLabel p = new JLabel("%");
		p.setMaximumSize(new Dimension(75, 110));
		p.setFont(new Font("Arial", Font.BOLD, 25));
		numero = new JTextField();
		numero.setEditable(true);
		numero.setFont(new Font("Arial", Font.BOLD, 25));
		numero.setMaximumSize(new Dimension(90, 110));
		numero.setText(this.valorPorcentaje());
		numero.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(actuaNumero) {
					try {
						Double p = Double.parseDouble(numero.getText());
						if(p > 100) p = 100.0;
						numero.setText(p.toString());
						g.marcaRango(p);
						g.leeRango();
						actuaSlider = false;
						barra.setValue(p.intValue());
					}
					catch(NumberFormatException e) {
						actuaSlider = false;
						barra.setValue(0);
						g.desmarcaPanelRangos();
						g.escribeEntrada("");
						numero.setText("0.0");
					}
					actuaSlider = true;
				}
			}
		});
		
		
		this.add(barra);
		this.add(numero);
		this.add(p);
		
		g.add(this, BorderLayout.SOUTH);
	}
	
	public String valorPorcentaje() {
		Double i = (double) barra.getValue();
		return i.toString();
	}
	
	
	public void escribePorcentaje(Double porcent) {
		String s = "";
		Double d = Math.round( porcent * 10.0 ) / 10.0;
		s += d;
		this.numero.setText(s);
	}
	
	
	public void actualizaSlider() {
		actuaSlider = false;
		Double num = Double.parseDouble(numero.getText());
		barra.setValue(num.intValue());
		actuaSlider = true;
	}
	
	
	
}
