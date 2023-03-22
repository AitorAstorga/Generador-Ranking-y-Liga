package org.fae.generadorrankingliga.vista;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.fae.generadorrankingliga.modelo.Deportista;

public class RendererRankingABS extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JLabel celda = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Deportista deportista = (Deportista)table.getValueAt(row, 0);
		celda.setBackground(Color.WHITE);
		if(deportista.esJuvenil()) {
			celda.setBackground(Color.LIGHT_GRAY);
		}
		celda.setOpaque(true);
		if(value == null) celda.setText("0");
		if(column == 0) celda.setHorizontalAlignment(JLabel.LEFT);
		else celda.setHorizontalAlignment(JLabel.CENTER);
		
		return celda;
	}

}
