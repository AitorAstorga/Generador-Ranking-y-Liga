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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.fae.generadorrankingliga.modelo.Deportista;
import org.fae.generadorrankingliga.modelo.ModeloTabla;
import org.fae.generadorrankingliga.vista.Principal;

public class DialogoModificarDeportista extends JDialog implements ListSelectionListener {
	final static String ACC_ACEPTAR = "Aceptar";
	final static String ACC_CANCELAR = "Cancelar";
	AccionBoton accAceptar, accCancelar;
	ModeloTabla modelo;
	JTextArea txtArea;
	JTextField nombre, apellidos, club, año;
	JList jlDeportistas;
	Deportista deportista;
	JCheckBox masculino;

	public DialogoModificarDeportista(ModeloTabla modelo) {
		this.modelo = modelo;
		this.setResizable(false);
		setTitle("Añadir Deportista");
		setBounds(100, 100, 450, 229);
		crearAcciones();
		
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(crearPanelCentral(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}
	
	public Container crearPanelCentral() {
		JPanel panel = new JPanel(new GridLayout());
		panel.add(crearAreaListado());
		panel.add(crearAreaDatos());
		return panel;
	}
	
	public Container crearAreaListado() {
		JScrollPane scrollPane = new JScrollPane();
		jlDeportistas = new JList(modelo.getDeportistas(Principal.clasificacionSeleccionada.isMasculino()).toArray());
		jlDeportistas.addListSelectionListener(this);
		scrollPane.setViewportView(jlDeportistas);
		
		return scrollPane;
	}
	
	public Container crearAreaDatos() {
		JPanel panel = new JPanel(new GridLayout(4,1));

		nombre = new JTextField("");
		nombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
		apellidos = new JTextField("");
		apellidos.setBorder(BorderFactory.createTitledBorder("Apellidos"));
		año = new JTextField("");
		año.setBorder(BorderFactory.createTitledBorder("Año"));
		club = new JTextField("");
		club.setBorder(BorderFactory.createTitledBorder("Club"));
		masculino = new JCheckBox("Masculino?", true);
		
		panel.add(nombre);
		panel.add(apellidos);
		panel.add(año);
		panel.add(club);
		panel.add(masculino);
		
		return panel;
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
					Deportista deportista = new Deportista(nombre.getText(), apellidos.getText(), masculino.isSelected(), Integer.valueOf(año.getText()), club.getText());
					modelo.modificarDeportista(deportista);
					modelo.fireTableDataChanged();
					dispose();
					break;
				case ACC_CANCELAR:
					dispose();
					break;
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		deportista = (Deportista) jlDeportistas.getSelectedValue();
		nombre.setText(deportista.getNombre());
		apellidos.setText(deportista.getApellidos());
		club.setText(deportista.getClub());
		año.setText(String.valueOf(deportista.getAñoNacimiento()));
		masculino.setSelected(deportista.isMasculino());
	}
		
}
