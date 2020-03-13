package hja.vista.Paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import hja.vista.GUI;

public class PanelLeerFichero extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton elegirFich;
	private JFileChooser chooser;

	private JPanel casos;
	private JScrollPane scroll;
	
	private File fich;
	
	public PanelLeerFichero(GUI g, File f, JPanel panel) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		// fichero de entrada
		fich = null;
		
		// chooser del fichero
		chooser = new JFileChooser("src/ejemplos");
		
		casos = new JPanel();
		casos.setMaximumSize(new Dimension(250, 110));
		casos.setLayout(new BoxLayout(casos, BoxLayout.Y_AXIS));
		scroll = new JScrollPane(casos);
		scroll.setPreferredSize(new Dimension(150, 110));
		scroll.setMaximumSize(new Dimension(500, 110));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createEtchedBorder());
		if(f != null) {
			fich = f;
			this.leerFichero(g);
		}
		
		
		// boton para elegir el fichero a leer
		elegirFich = new JButton(new ImageIcon("src/iconos/document-2.png"));
		elegirFich.setToolTipText("Selecciona y lee un fichero");
		elegirFich.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cargaCasos(g);
			}
		});
		elegirFich.setPreferredSize(new Dimension(110, 110));
		elegirFich.setMaximumSize(new Dimension(110, 110));
		
		
		this.add(elegirFich);
		this.add(scroll);
		
		
		this.setPreferredSize(new Dimension(350,110));
		this.setMaximumSize(new Dimension(500,110));
		
		panel.add(this, BorderLayout.NORTH);
	}
	
	
	
	private void leerFichero(GUI g) {
		try {
			String linea;
			@SuppressWarnings("resource")
			BufferedReader r = new BufferedReader(new FileReader(fich));
			while((linea = r.readLine()) != null) {
				JLabel aux = new JLabel(linea);
				aux.setBorder(BorderFactory.createEtchedBorder());
				aux.setFont(new Font("Dialog", Font.BOLD, 20));
				aux.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						String s = aux.getText();
						g.escribeEntrada(s);
					}
					@Override
					public void mouseEntered(MouseEvent arg0) {}
					@Override
					public void mouseExited(MouseEvent arg0) {}
					@Override
					public void mousePressed(MouseEvent arg0) {}
					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
				casos.add(aux);
				
			}
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this,
										"Se ha producido un error en la lectura del fichero",
										"Error",
										JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void cargaCasos(GUI g) {
		int returnValue = this.chooser.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File f = this.chooser.getSelectedFile();
			if(f != null) {
				this.casos.removeAll();
				this.casos.updateUI();
				this.fich = f;
				leerFichero(g);
				this.casos.updateUI();
			}
		}
	}
	

	
}
