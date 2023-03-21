package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Calculadora {
	
	public static int calcularPuntuacion(int puesto, int participantes) {
		int puntuacion = 0;
		
		List<Integer> intervalos = new ArrayList<>();
		intervalos.add((puesto == 1)?1010:0);
		intervalos.add((puesto == 2)?909:0);
		intervalos.add((puesto < 5)?808:0);
		intervalos.add((puesto < 9)?707:0);
		intervalos.add((puesto < 17)?606:0);
		intervalos.add((puesto < 33)?505:0);
		intervalos.add((puesto < 65)?404:0);
		intervalos.add((puesto < 129)?303:0);
		intervalos.add((puesto < 257)?202:0);
		intervalos.add((puesto < 513)?101:0);
		int maxIntervalo = Collections.max(intervalos);
		puntuacion = (int) (maxIntervalo + (1000 * (1.01f - (Math.log10(puesto)/Math.log10(participantes)))));
		return puntuacion;
	}
	
	public static int getInicioTemporadaActual() {
		int añoActual = Calendar.getInstance().get(Calendar.YEAR);
		int mesActual = Calendar.getInstance().get(Calendar.MONTH);
		int añoInicioTemporada = (mesActual >= Calendar.SEPTEMBER) ? añoActual : añoActual - 1;
		return añoInicioTemporada;
	}

}
