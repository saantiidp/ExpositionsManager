package sistema;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import exposicion.Exposicion;

/**
 * Guarda los datos de contabilidad del sistema
 */
public class ContabilidadSistema implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 774607132349903192L;
	/**
	 * Lleva el registro de beneficio recaudado por exposicion
	 */
	private Map<LocalDate, Map<Exposicion, Double>> beneficioPorExposicion = new HashMap<>();
	/**
	 * Lleva el registro de entradas por exposicion
	 */
	private Map<LocalDate, Map<Exposicion, Integer>> entradasPorExposicion = new HashMap<>();
	
	/**
	 * Suma a una exposicion una cantidad como beneficio
	 * @param e
	 * @param cantidad
	 */
	public void sumarBeneficioExposicion(Exposicion e, double cantidad) {
		LocalDate fecha = LocalDate.now();
		if (this.beneficioPorExposicion.containsKey(fecha)) {
			Map<Exposicion, Double> nnn = this.beneficioPorExposicion.get(fecha);
			double suma = nnn.get(e);
			this.beneficioPorExposicion.get(fecha).put(e, suma + cantidad);
		}
		else {
			Map<Exposicion, Double> m = new HashMap<>();
			m.put(e, cantidad);
			this.beneficioPorExposicion.put(fecha, m);
		}
	}
	
	/**
	 * Resta a una exposicion una cantidad como beneficio
	 * @param e
	 * @param cantidad
	 */
	public void restarBeneficioExposicion(Exposicion e, double cantidad) {
		LocalDate fecha = LocalDate.now();
		if (this.beneficioPorExposicion.containsKey(fecha)) {
			double resta = this.beneficioPorExposicion.get(fecha).get(e);
			this.beneficioPorExposicion.get(fecha).put(e, resta - cantidad);
		}
	}
	

	/**
	 * Suma a una exposicion una cantidad como numero de entradas
	 * @param e
	 * @param cantidad
	 */
	public void sumarEntradaExposicion(Exposicion e, int cantidad) {
		LocalDate fecha = LocalDate.now();
		if (this.entradasPorExposicion.containsKey(fecha)) {
			int suma = this.entradasPorExposicion.get(fecha).get(e);
			this.entradasPorExposicion.get(fecha).put(e, suma + cantidad);
		}
		else {
			Map<Exposicion, Integer> m = new HashMap<>();
			m.put(e, cantidad);
			this.entradasPorExposicion.put(fecha, m);
		}
	}
	
	/**
	 * Resta a una exposicion una cantidad como numero de entradas
	 * @param e
	 * @param cantidad
	 */
	public void restarEntradaExposicion(Exposicion e, int cantidad) {
		LocalDate fecha = LocalDate.now();
		if (this.entradasPorExposicion.containsKey(fecha)) {
			int resta = this.entradasPorExposicion.get(fecha).get(e);
			this.entradasPorExposicion.get(fecha).put(e, resta - cantidad);
		}
	}

	/**
	 * Getter del total recaudado dada dos fechas
	 * @return el total
	 */
	public double getBeneficioTotalExposiciones(LocalDate i, LocalDate f) {
		double ret = 0;
		LocalDate auxDate = i;
            
        while (auxDate.isBefore(f) || auxDate.isEqual(f)) {
        	if (this.beneficioPorExposicion.containsKey(auxDate)) {
        		for (double d : this.beneficioPorExposicion.get(auxDate).values())
        			ret += d;
        	}
        	auxDate = auxDate.plusDays(1);
        }
        
		return ret;
	}

	/**
	 * Getter del dinero por exposicion dada dos fechas
	 * @return el beneficio
	 */
	public double getBeneficioPorExposicion(Exposicion e, LocalDate i, LocalDate f) {
		double ret = 0;
		LocalDate auxDate = i;
            
        while (auxDate.isBefore(f) || auxDate.isEqual(f)) {
        	if (this.beneficioPorExposicion.containsKey(auxDate) &&
        			this.beneficioPorExposicion.get(auxDate).keySet().contains(e)) {
        		for (double d : this.beneficioPorExposicion.get(auxDate).values())
        			ret += d;
        	}
        	auxDate = auxDate.plusDays(1);
        }
        
		return ret;
	}
	
	/**
	 * Getter del total de entradas vendidas dada dos fechas
	 * @return el total
	 */
	public int getEntradasTotalExposiciones(LocalDate i, LocalDate f) {
		int ret = 0;
		LocalDate auxDate = i;
            
        while (auxDate.isBefore(f) || auxDate.isEqual(f)) {
        	if (this.entradasPorExposicion.containsKey(auxDate)) {
        		for (int d : this.entradasPorExposicion.get(auxDate).values())
        			ret += d;
        	}
        	auxDate = auxDate.plusDays(1);
        }
        
		return ret;
	}

	/**
	 * Getter del numero de entradas vendidas por exposicion dada dos fechas
	 * @return el beneficio
	 */
	public int getEntradasPorExposicion(Exposicion e, LocalDate i, LocalDate f) {
		int ret = 0;
		LocalDate auxDate = i;
            
        while (auxDate.isBefore(f) || auxDate.isEqual(f)) {
        	if (this.entradasPorExposicion.containsKey(auxDate) &&
        			this.entradasPorExposicion.get(auxDate).keySet().contains(e)) {
        		for (double d : this.entradasPorExposicion.get(auxDate).values())
        			ret += d;
        	}
        	auxDate = auxDate.plusDays(1);
        }
        
		return ret;
	}
	
	public void anyadirExposicion(Exposicion e) {
		LocalDate f = LocalDate.now();
		
		this.beneficioPorExposicion.putIfAbsent(f, new HashMap<>());
		this.entradasPorExposicion.putIfAbsent(f, new HashMap<>());
		
		this.beneficioPorExposicion.get(f).put(e, 0.0);
		this.entradasPorExposicion.get(f).put(e, 0);
	}
}
