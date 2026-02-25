package filtro;

import java.io.Serializable;

import exposicion.Exposicion;

/**
 * @brief Clase que representa un filtro por tipo de exposición: temporal/permanente
 */
public class FiltroExposición implements Serializable, Filtro{
	
	/**
	 * @brief Serial
	 */
	private static final long serialVersionUID = -6906944746437175689L;
	/**
	 * @brief Evalúa que la exposición sea temporal y que por tanto pasa el filtro
	 * @param Exposicion a evaluar
	 * @return true si cumple filtro, false si no cumple filtro
	 */
	@Override
	public boolean cumpleFiltro(Exposicion exposición) {
		if (exposición.esTemporal())
			return true;
		
		return false;
	}
	

	
}
