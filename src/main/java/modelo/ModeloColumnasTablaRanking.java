package modelo;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import vista.RendererRankingABS;

public class ModeloColumnasTablaRanking extends DefaultTableColumnModel {
	RendererRankingABS renderer;
	int numeroResultados;
	
	public ModeloColumnasTablaRanking(RendererRankingABS renderer, Temporada temporada) {
		super();
		int ultimaColumna = 0;
		this.renderer = renderer;
		this.addColumn(crearColumna("TIRADOR", ultimaColumna++, 150));
		this.addColumn(crearColumna("CLUB", ultimaColumna++, 60));
		for(Competicion com:temporada.getCompeticionesSeleccionadas()) {
			this.addColumn(crearColumna(com.getDenominacion(), ultimaColumna++, 30));
		}
		this.addColumn(crearColumna("TOTAL", ultimaColumna++, 30));
	}

	public TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice, ancho);
		System.out.println(texto + " " + indice + " " + ancho);
		columna.setHeaderValue(texto);
		columna.setCellRenderer(renderer);
		return columna;
	}

}
