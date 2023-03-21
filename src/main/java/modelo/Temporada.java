package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import clasificaciones.Clasificacion;
import vista.Principal;

public class Temporada implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String FICHERO_TEMPORADA = "./files/Temporada";
	int añoInicio;
	List<Deportista> deportistas;
	List<Competicion> competiciones;
	
	public Temporada(int añoInicio) {
		this.añoInicio = añoInicio;
		this.deportistas = new ArrayList<>();
		this.competiciones = new ArrayList<>();
		inicializarCompeticiones();
		//inicializarDeportistasEjemplo();
		//inicializarResultadosEjemplo(true);
	}

	private void inicializarCompeticiones() {
		for(int i = 0; i < Clasificacion.TODAS.getCompeticiones().length; i++) {
			añadirCompeticion(Clasificacion.TODAS.getCompeticiones()[i]);
		}
	}
	
	private void inicializarDeportistasEjemplo() {
		añadirDeportista(new Deportista("Señor", "Apellido1 Apellido2", true, 1950, "CLUB1"));
		añadirDeportista(new Deportista("2 ASTORGA Aitor CEGA", true));
		añadirDeportista(new Deportista("Joven", "Ejemplo Apellido2", true, 2015, "CLUB2"));
		añadirDeportista(new Deportista("4 APELLIDO Nombre CEGA", true));
		añadirDeportista(new Deportista("4 A A A", true));
		
		añadirDeportista(new Deportista("4 APELLIDO maria CLUB", false));
		añadirDeportista(new Deportista("4 C C CLOB", false));
		añadirDeportista(new Deportista("4 D D DLEB", false));
		añadirDeportista(new Deportista("Señorita", "Apellido1 Apellido2", false, 2014, "CLUB1"));
		añadirDeportista(new Deportista("4 E E CLIB", false));
	}
	
	private void inicializarResultadosEjemplo(boolean masculino) {
		añadirResultado(deportistas.get(0), "1LA", 1);
		añadirResultado(deportistas.get(1), "1LA", 2);
		añadirResultado(deportistas.get(1), "3LA", 2);
		añadirResultado(deportistas.get(2), "1LA", 3);
		añadirResultado(deportistas.get(3), "3LA", 1);
		añadirResultado(deportistas.get(4), "3LA", 3);
		
		añadirResultado(deportistas.get(5), "3LA", 1);
		añadirResultado(deportistas.get(6), "3LA", 2);
		añadirResultado(deportistas.get(7), "3LA", 3);
	}
	
	public boolean añadirDeportista(Deportista deportista) {
		if(existeDeportista(deportista)) return false;
		deportistas.add(deportista);
		return true;
	}
	
	public boolean modificarDeportista(Deportista deportista) {
		if(existeDeportista(deportista)) {
			int index = deportistas.indexOf(deportista);
			Deportista modificar = deportistas.get(index);
			modificar.setAñoNacimiento(deportista.getAñoNacimiento());
			modificar.setClub(deportista.getClub());
			modificar.setMasculino(deportista.isMasculino());
			return true;
		}
		deportistas.add(deportista);
		return false;
	}
	
	public boolean añadirCompeticion(String competicion) {
		if(existeCompeticion(competicion)) return false;
		competiciones.add(new Competicion(competicion));
		return true;
	}
	
	public boolean añadirResultado(Deportista deportista, String denominacion, int puesto) {
		Competicion competicion;
		if(!existeDeportista(deportista)) añadirDeportista(deportista);
		añadirCompeticion(denominacion);
		competicion = getCompeticion(denominacion);// HAZ ESTO BIEN DE ALGUNA FORMA BRUH QUE DESASTRE
		competicion.añadirParticipante(deportista, puesto);
		return true;
	}
	
	public List<Deportista> getDeportistas(boolean isMasculino) {
		List<Deportista> resultado = new ArrayList<>();
		for(Deportista dep:deportistas) {
			if(isMasculino == dep.isMasculino()) resultado.add(dep);
		}
		return resultado;
	}
	
	public String[] getCompeticiones(boolean isMasculino) {
		List<String> resultado = new ArrayList<>();
		for(Competicion com:competiciones) {
			if(isMasculino == com.isMasculino()) resultado.add(com.getDenominacion());
		}
		return resultado.toArray(new String[0]);
	}
	
	public Competicion getCompeticion(String denominacion) {
		return competiciones.get(competiciones.indexOf(new Competicion(denominacion)));
	}
	
	public boolean existeDeportista(String nombre, String apellidos) {
		for(Deportista dep:deportistas) {
			if(dep.getNombre().equals(nombre) && dep.getApellidos().equals(apellidos))
				return true;
		}
		return false;
	}
	
	public boolean existeDeportista(Deportista deportista) {
		return !(deportistas.indexOf(deportista) == -1);
	}
	
	public boolean existeCompeticion(String competicion) {
		return !(competiciones.indexOf(new Competicion(competicion)) == -1);
	}
	
	public int getNumeroCompeticiones() {
		return competiciones.size();
	}
	
	public List<Competicion> getCompeticionesSeleccionadas() {
		List<Competicion> seleccionadas = new ArrayList<>();
		for(Competicion com:competiciones) {
			if(Principal.clasificacionSeleccionada.contains(com.getDenominacion()))
				seleccionadas.add(com);
		}
		return seleccionadas;
	}
	
	public Deportista getDeportista(int rowIndex) {
		boolean masculino = Principal.clasificacionSeleccionada.isMasculino();
		return getDeportistas(masculino).get(rowIndex);
	}
	
	private Integer getPuntuacionTotal(Deportista deportista) {
		int puntuacion = 0;
		for(Competicion com:getCompeticionesSeleccionadas()) {
			puntuacion += com.getPuntuacion(deportista);
		}
		return Integer.valueOf(puntuacion);
	}
	
	public Integer getFieldPuntuacion(int rowIndex, int columnIndex) {
		Deportista dep = getDeportista(rowIndex);
		Competicion com = getCompeticionesSeleccionadas().get(columnIndex - 2);
		return com.getPuntuacion(dep);
	}
	
	public Object getFieldAt(int rowIndex, int columnIndex) {
		int numColumnas = 2 + getCompeticionesSeleccionadas().size() + 1;
		if(columnIndex == 0) return getDeportista(rowIndex);
		else if (columnIndex == 1) return getDeportista(rowIndex).getClub();
		else if(columnIndex > 1 && columnIndex < numColumnas - 1) {
			return getFieldPuntuacion(rowIndex, columnIndex);
		}
		else if(columnIndex == numColumnas - 1) return getPuntuacionTotal(getDeportista(rowIndex));
		else return "(" + rowIndex + " " + columnIndex + ")";
	}
	
}
