package modelo;

import java.io.Serializable;

public class Deportista implements Serializable {
	private static final long serialVersionUID = 3L;
	final static int PRIMER_AŅO_FEDERADO = Calculadora.getInicioTemporadaActual() - 14;
	public final static String SEPARADOR = " ";
	private String nombre;
	private String apellidos;
	private boolean masculino;
	private int aņoNacimiento;
	private String club;
	
	public Deportista(String nombre, String apellidos, boolean masculino, int aņoNacimiento, String club) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.masculino = masculino;
		this.aņoNacimiento = aņoNacimiento;
		this.club = club;
	}

	public Deportista(String linea, boolean masculino) {
		String campos[] = linea.split(Deportista.SEPARADOR);
		this.nombre = campos[campos.length - 2];
		this.apellidos = Deportista.leerApellidos(linea);
		this.masculino = masculino;
		this.aņoNacimiento = 0;
		this.club = campos[campos.length - 1];
	}
	
	public static String leerApellidos(String linea) {
		String split[] = linea.split(SEPARADOR);
		StringBuilder apellidosTmp = new StringBuilder();
		for (int i = 1; i < split.length - 2; i++) {
			apellidosTmp.append(split[i]);
			if (i < split.length - 3)
				apellidosTmp.append(" ");
		}
		return apellidosTmp.toString();
	}
	
	public String leerLinea() {
		String linea = nombre + Deportista.SEPARADOR
				+ apellidos + Deportista.SEPARADOR
				+ masculino + Deportista.SEPARADOR
				+ aņoNacimiento + Deportista.SEPARADOR
				+ club + Deportista.SEPARADOR;
		return linea;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public boolean isMasculino() {
		return masculino;
	}
	
	public void setMasculino(boolean masculino) {
		this.masculino = masculino;
	}

	public int getAņoNacimiento() {
		return aņoNacimiento;
	}
	
	public void setAņoNacimiento(int aņoNacimiento) {
		this.aņoNacimiento = aņoNacimiento;
	}

	public String getClub() {
		return club;
	}
	
	public void setClub(String club) {
		this.club = club;
	}

	public boolean esEscolar() {
		return (PRIMER_AŅO_FEDERADO - aņoNacimiento) < 0;
	}

	public boolean esJuvenil() {
		return (PRIMER_AŅO_FEDERADO - 6 - aņoNacimiento) < 0;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Deportista)) return false;
		if(((Deportista)obj).getNombre().equals(this.nombre) 
			&& (((Deportista)obj).getApellidos().equals(this.apellidos)))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return apellidos.toUpperCase() + ", " + nombre;
	}

}
