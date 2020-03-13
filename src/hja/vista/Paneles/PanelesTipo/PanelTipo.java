package hja.vista.Paneles.PanelesTipo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import hja.vista.Paneles.PanelJugadas;

public class PanelTipo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel nombre;

	private JProgressBar num;
	private static final int maxCombos = 800;

	private JTextField combis;

	private JTextField result;

	private Integer numCombos;

	public PanelTipo(String name, PanelJugadas p) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setMaximumSize(new Dimension(1000, 50));
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.numCombos = 0;

		///////////////////////////////////////////////////
		JPanel panelIzq = new JPanel();
		panelIzq.setMaximumSize(new Dimension(80, 25));
		panelIzq.setPreferredSize(new Dimension(80, 25));

		this.nombre = new JLabel(name, JLabel.CENTER);
		panelIzq.add(nombre);
		///////////////////////////////////////////////////

		///////////////////////////////////////////////////
		JPanel panelCen = new JPanel();
		panelCen.setMaximumSize(new Dimension(150, 10));

		num = new JProgressBar(0, maxCombos);
		num.setPreferredSize(new Dimension(1, 30));
		num.setMaximumSize(new Dimension(1, 30));
		panelCen.add(num);
		num.setForeground(Color.BLUE);

		combis = new JTextField(this.numCombos.toString()) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setBorder(Border border) {
			}
		};
		combis.setEditable(false);
		panelCen.add(combis);
		JScrollPane scroll2 = new JScrollPane(panelCen) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setBorder(Border border) {
			}
		};
		scroll2.setMaximumSize(new Dimension(100, 60));
		scroll2.setPreferredSize(new Dimension(100, 60));
		///////////////////////////////////////////////////

		///////////////////////////////////////////////////
		JPanel panelDer = new JPanel();
		JScrollPane scroll = new JScrollPane(panelDer) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setBorder(Border border) {
			}
		};
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setMaximumSize(new Dimension(280, 60));
		scroll.setPreferredSize(new Dimension(280, 60));

		this.result = new JTextField("") {
			private static final long serialVersionUID = 1L;

			@Override
			public void setBorder(Border border) {
			}
		};
		this.result.setEditable(false);
		panelDer.add(result);
		///////////////////////////////////////////////////

		this.add(panelIzq);
		this.add(scroll2);
		this.add(scroll);

		p.add(this);
	}

	public void anyade(String s) {
		String ss = this.result.getText();
		if (ss.length() > 0)
			ss += ", ";
		this.result.setText(ss + ' ' + s);
	}

	public void sumaCombos(int n) {
		this.numCombos += n;
		if (this.numCombos > maxCombos)
			this.numCombos = maxCombos;
		this.num.setValue(numCombos);
		this.combis.setText(this.numCombos.toString());
		num.setPreferredSize(new Dimension(this.numCombos + 1, 30));
		num.setMaximumSize(new Dimension(this.numCombos + 1, 30));
	}

	public void limpia() {
		this.num.setValue(0);
		num.setPreferredSize(new Dimension(1, 30));
		num.setMaximumSize(new Dimension(1, 30));
		this.numCombos = 0;
		this.combis.setText("0");
		this.result.setText("");
	}

}
