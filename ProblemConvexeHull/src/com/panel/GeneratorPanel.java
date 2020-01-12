/*
This java class is used for generate a Canvas Panel 
*/

package com.panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GeneratorPanel extends JFrame implements KeyListener{

  private Panel panel;

  public GeneratorPanel(){
    panel = new Panel();
    add(panel);
    setTitle("Panel");
    setSize(800,600);
    addKeyListener(this);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_N){
    	panel.init();
    }
    if(e.getKeyCode() == KeyEvent.VK_E){
    	panel.enveloppe();
    }
    if(e.getKeyCode() == KeyEvent.VK_C){
    	panel.circle();
    }
    if(e.getKeyCode() == KeyEvent.VK_D){
    	panel.diameter();
    }
    if(e.getKeyCode() == KeyEvent.VK_R){
    	panel.rectangle();
    }

  }

  @Override
  public void keyReleased(KeyEvent arg0) {
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }


}
