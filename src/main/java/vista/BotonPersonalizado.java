package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import modelo.Navegador;

public class BotonPersonalizado extends JButton {
	
	public BotonPersonalizado(Action a) {
		super(a);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.BLACK);
		this.addFocusListener(focusListener);
		this.addMouseListener(mouseListener);
	}
	
	public BotonPersonalizado(String t, String url) {
		super(t);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setForeground(Color.BLACK);
		this.addFocusListener(focusListener);
		this.addMouseListener(mouseListener);
		this.setActionCommand(url);
		this.addActionListener(actionListener);
	}
	
	private AbstractAction actionListener = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String url = e.getActionCommand();
			try {
				Navegador.openWebpage(new URL(url));
			} catch (MalformedURLException e1) { e1.printStackTrace(); }
		}
	};
	
	private MouseAdapter mouseListener = new MouseAdapter() {
		
		public void mouseEntered(java.awt.event.MouseEvent evt) {
			setBackground(Color.GRAY);
	    	setForeground(Color.WHITE);
	    }

	    public void mouseExited(java.awt.event.MouseEvent evt) {
	    	setBackground(Color.LIGHT_GRAY);
			setForeground(Color.BLACK);
	    }
	};
	
	private FocusListener focusListener =new FocusListener() {
	
		public void focusGained(FocusEvent e) {
	    	setBackground(Color.GRAY);
	    	setForeground(Color.WHITE);
	    }
	
		public void focusLost(FocusEvent e) {
			setBackground(Color.LIGHT_GRAY);
			setForeground(Color.BLACK);
		}
    };
     
}
