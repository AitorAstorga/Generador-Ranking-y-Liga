package modelo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import vista.Principal;

public class ModeloTabla extends AbstractTableModel {
	public final static String SEPARADOR_LINEAS = "\\n";
	Temporada temporada;
	
	public ModeloTabla(Temporada temporada) {
		this.temporada = temporada;
	}
	
	public void aņadirDeportista(Deportista deportista) {
		temporada.aņadirDeportista(deportista);
		this.fireTableDataChanged();
	}
	
	public void modificarDeportista(Deportista deportista) {
		temporada.modificarDeportista(deportista);
		this.fireTableDataChanged();
	}
	
	public void aņadirResultados(String listadoLineas, String denominacion, boolean masculino) {
		String lineas[] = listadoLineas.split(SEPARADOR_LINEAS);
		int participantes = lineas.length;
		for(int i = 0; i < participantes; i++) {
			aņadirResultado(lineas[i], denominacion, masculino);
		}
	}
	
	public void aņadirResultado(String linea, String denominacion, boolean masculino) {
		String campos[] = linea.split(Deportista.SEPARADOR);
		Deportista dep = new Deportista(linea, masculino);
		int puesto = Integer.valueOf(campos[0]);
		temporada.aņadirResultado(dep, denominacion, puesto);
		this.fireTableDataChanged();
	}
	
	public void aņadirResultado(Deportista deportista, String denominacion, int puesto) {
		temporada.aņadirResultado(deportista, denominacion, puesto);
		this.fireTableDataChanged();
	}
	
	public List<Deportista> getDeportistas(boolean masculino) {
		return temporada.getDeportistas(masculino);
	}

	@Override
	public int getRowCount() {
		return temporada.getDeportistas(Principal.clasificacionSeleccionada.isMasculino()).size();
	}

	@Override
	public int getColumnCount() {
		return 2 + temporada.getNumeroCompeticiones() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return temporada.getFieldAt(rowIndex, columnIndex);
	}

}
