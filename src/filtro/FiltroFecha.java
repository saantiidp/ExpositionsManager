package filtro;

import java.io.Serializable;
import java.time.LocalDate;

import exposicion.Exposicion;
/**
 * @brief Clase que representa un filtro que verifica si hay entradas disponibles en un día y hora concreto
 */
public class FiltroFecha implements Serializable, Filtro{

	/**
	 * @brief Serial
	 */
	private static final long serialVersionUID = -3258720842146042737L;
	/**
	 * Atributo fecha (LocalDate)
	 */
	private LocalDate fecha;
	/**
	 * Atributo hora (int)
	 */
	private Integer hora;
	/**
	 * Metodo constructor
	 * 
	 * @param fecha (LocalDate)
	 * @param hora (int)
	 */
	
	public FiltroFecha(LocalDate fecha, int hora) {
		super();
		this.fecha = fecha;
		this.hora = hora;
	}

	/**
	 * @brief Evalúa que la exposición tenga entradas disponibles un día y hora concreta
	 * @param Exposicion a evaluar
	 * @return true si cumple filtro, false si no cumple filtro
	 */
	@Override
	public boolean cumpleFiltro(Exposicion exposición) {
		if(exposición.getNumeroEntradasDisponibles(this.fecha, this.hora) > 0) {
			return true;
		}
		
		return false;
	}
	/**
	 * @brief Getter para el atributo fecha de la clase FiltroFecha
	 * 
	 * @return fecha (LocalDate)
	 */

	public LocalDate getFecha() {
		return fecha;
	}
	/**
	 * @brief Método setter para el atributo fecha de la clase FiltroFecha
	 *  para que el cliente quiera filtrar por otro día
	 * @param fecha (LocalDate)
	 */

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	/**
	 * @brief Getter para el atributo hora de la clase FiltroFecha
	 * 
	 * @return hora (Integer)
	 */

	public int getHora() {
		return hora;
	}
	/**
	 * @brief Método setter para el atributo hora de la clase FiltroFecha 
	 * para que el cliente quiera filtrar por otra hora
	 * 
	 * @param hora (Integer)
	 */


	public void setHora(int hora) {
		this.hora = hora;
	}
	
	

}
