package hja.main;

import java.io.File;

import javax.swing.SwingUtilities;

import hja.vista.GUI;

public class Main {
	
	
	private static File parseaArg(String[] args) {
		File f = null;
		if(args.length == 1) {
			f = new File(args[0]);
			if(!f.exists()) f = null;
		}
		return f;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				File f = parseaArg(args);
				new GUI(f);
			}
		});
	}

}
