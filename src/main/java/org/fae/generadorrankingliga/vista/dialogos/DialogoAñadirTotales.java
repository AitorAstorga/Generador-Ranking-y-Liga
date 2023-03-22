package org.fae.generadorrankingliga.vista.dialogos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.fae.generadorrankingliga.modelo.ModeloTabla;

public class DialogoAñadirTotales extends JDialog {
	final static String ACC_ACEPTAR = "Aceptar";
	final static String ACC_CANCELAR = "Cancelar";
	AccionBoton accAceptar, accCancelar;
	ModeloTabla modelo;
	JTextArea txtArea;
	JTextField denominacion, club;
	JCheckBox masculino;

	public DialogoAñadirTotales(ModeloTabla modelo) {
		this.modelo = modelo;
		this.setResizable(false);
		setTitle("Añadir Resultados Totales");
		setBounds(100, 100, 450, 229);
		crearAcciones();
		
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(crearAreaDatos(), BorderLayout.NORTH);
		panel.add(crearAreaTexto(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Container crearAreaDatos() {
		JPanel panel = new JPanel(new GridLayout());

		denominacion = new JTextField("");
		denominacion.setBorder(BorderFactory.createTitledBorder("Denominación"));
		club = new JTextField("");
		club.setBorder(BorderFactory.createTitledBorder("Club"));
		masculino = new JCheckBox("Masculino?", true);
		
		panel.add(denominacion);
		panel.add(masculino);
		panel.add(club);
		
		return panel;
	}
	
	public Container crearAreaTexto() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 424, 146);
		
		txtArea = new JTextArea();
		txtArea.setText("");
		txtArea.setLineWrap(true);
		txtArea.setAutoscrolls(true);
		scrollPane.setViewportView(txtArea);
		
		return scrollPane;
	}
	
	public Container crearPanelBotones() {
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btAceptar = new JButton(accAceptar);
		JButton btCancelar = new JButton(accCancelar);
		
		buttonPane.add(btAceptar);
		buttonPane.add(btCancelar);
		
		return buttonPane;
	}
	
	private void crearAcciones() {
		accAceptar = new AccionBoton(ACC_ACEPTAR);
		accCancelar = new AccionBoton(ACC_CANCELAR);
	}
	
	private class AccionBoton extends AbstractAction {
		String texto;
		
		public AccionBoton (String texto){
			super(texto);
			this.texto = texto;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (texto) {
				case ACC_ACEPTAR:
					modelo.añadirResultados(txtArea.getText(), denominacion.getText(), masculino.isSelected());
					modelo.fireTableDataChanged();
					dispose();
					break;
				case ACC_CANCELAR:
					System.out.println(ACC_CANCELAR);
					dispose();
					break;
			}
		}
	}
		
}
