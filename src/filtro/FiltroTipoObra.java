package filtro;

import java.io.Serializable;
import java.util.ArrayList;

import exposicion.Exposicion;
import obra.Obra;
/**
 * @brief Clase que representa un filtro que verifica si la exposición contiene obras de un tipo determinado
 */
public class FiltroTipoObra implements Serializable, Filtro {

	private static final long serialVersionUID = 6383883889113094809L;
	/**
	 * Atributo tipoObra(String)
	 */
	private String tipoObra;
	/**
	 * Metodo constructor
	 * 
	 * @param tipoObra(String)
	 */
	
	public FiltroTipoObra(String tipoObra) {
		super();
		this.tipoObra = tipoObra;
	}
	/**
	 * @brief Evalúa que la exposición tenga obras de un tipo determinado
	 * @param Exposicion a evaluar
	 * @return true si cumple filtro, false si no cumple filtro
	 */

	@Override
	public boolean cumpleFiltro(Exposicion exposición) {
		ArrayList<Obra> obras = exposición.getObras();
		for (Obra obra: obras) {
			if(obra.esTipoObra(tipoObra))
				return true;
		}
		
		return false;
	}
	/**
	 * @brief Getter para el atributo tipoObra de la clase FiltroTipoObra
	 * 
	 * @return tipoObra (String)
	 */

	public String getTipoObra() {
		return tipoObra;
	}
	/**
	 * @brief Método setter para el atributo tipoObra de la clase FiltroTipoObra
	 * para que el cliente quiera filtar por otro tipos de obras
	 * 
	 * @param tipoObra (String)
	 */

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}
	
	

}
