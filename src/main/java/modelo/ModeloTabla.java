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
	
	public void a�adirDeportista(Deportista deportista) {
		temporada.a�adirDeportista(deportista);
		this.fireTableDataChanged();
	}
	
	public void modificarDeportista(Deportista deportista) {
		temporada.modificarDeportista(deportista);
		this.fireTableDataChanged();
	}
	
	public void a�adirResultados(String listadoLineas, String denominacion, boolean masculino) {
		String lineas[] = listadoLineas.split(SEPARADOR_LINEAS);
		int participantes = lineas.length;
		for(int i = 0; i < participantes; i++) {
			a�adirResultado(lineas[i], denominacion, masculino);
		}
	}
	
	public void a�adirResultado(String linea, String denominacion, boolean masculino) {
		String campos[] = linea.split(Deportista.SEPARADOR);
		Deportista dep = new Deportista(linea, masculino);
		int puesto = Integer.valueOf(campos[0]);
		temporada.a�adirResultado(dep, denominacion, puesto);
		this.fireTableDataChanged();
	}
	
	public void a�adirResultado(Deportista deportista, String denominacion, int puesto) {
		temporada.a�adirResultado(deportista, denominacion, puesto);
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
