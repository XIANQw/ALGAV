package com;

import javax.swing.SwingUtilities;

import com.panel.GeneratorPanel;


public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
		    public void run(){
				GeneratorPanel panel = new GeneratorPanel();
		        panel.setVisible(true);
		    }
		});
	}
}
