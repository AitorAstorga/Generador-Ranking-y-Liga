package modelo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import vista.Principal;

public class Competicion implements Serializable {
	private static final long serialVersionUID = 23L;
	String denominacion;
	boolean masculino;
	Map<Deportista,Integer> clasificacion;
	
	public Competicion(String denominacion) {
		this.denominacion = denominacion;
		clasificacion = new LinkedHashMap<>();
	}

	public void añadirParticipante(Deportista dep, int puesto) {
		clasificacion.put(dep,Integer.valueOf(puesto));
	}
	
	public String getDenominacion() {
		return denominacion;
	}

	public boolean isMasculino() {
		return masculino;
	}

	public int getNumParticipantes(boolean masculino) {
		int resultado = 0;
		for (Entry<Deportista, Integer> entry : clasificacion.entrySet()) {
	        if (entry.getKey().isMasculino() == masculino) {
	        	resultado++;
	        }
	    }
		return resultado;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Competicion)) return false;
		if(((Competicion)obj).getDenominacion().equals(this.denominacion) 
			&& (((Competicion)obj).isMasculino() == this.masculino))
			return true;
		return false;
	}
	
	public Integer getPuesto(Deportista deportista) {
	    for (Entry<Deportista, Integer> entry : clasificacion.entrySet()) {
	        if (deportista.equals(entry.getKey())) {
	        	return entry.getValue();
	        }
	    }
	    return -2;
	}

	public Integer getPuntuacion(Deportista deportista) {
		for(int i = 0; i < Principal.clasificacionSeleccionada.getCompeticiones().length; i++) {
			if(this.getDenominacion().equals(Principal.clasificacionSeleccionada.getCompeticiones()[i])) {
				int puntuacion = Calculadora.calcularPuntuacion(this.getPuesto(deportista), this.getNumParticipantes(Principal.clasificacionSeleccionada.isMasculino()));
				return puntuacion;
			}
		}
		return -1;
	}
	
}
