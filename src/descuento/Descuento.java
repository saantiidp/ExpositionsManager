package descuento;

import java.io.Serializable;
import java.time.LocalDate;

import usuarios.ClienteRegistrado;
/**
 * @brief Clase que representa la clase abstracta descuento
 */
public abstract class Descuento implements Serializable {

	private static final long serialVersionUID = -3158567654856364762L;
	protected Double porcentaje;
	protected Integer nCondicion;
	
	/**
	 * Metodo constructor
	 * 
	 * @param porcentaje (Double)
	 * @param nCondicion (Integer)
	 */
	public Descuento(Double porcentaje, Integer nCondicion) {
		this.porcentaje = porcentaje;
		this.nCondicion = nCondicion;
	}

	
	/**
	 * @brief Getter para el atributo porcentaje de la clase Descuento
	 * 
	 * @return porcentaje (Double)
	 */
	public Double getPorcentaje() {
		return porcentaje;
	}
	/**
	 * @brief Método setter para el atributo porcentaje de la clase Descuento
	 *  para que se quiera poner otro porcentaje a ese descuento
	 * @param porcentaje (Double)
	 */
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}


	/**
	 * @brief Evalúa que el cliente pasa la condición del descuento
	 * @param día a evaluar
	 * @param cliente a evaluar
	 * @return true si cumple condición, false si no cumple condición
	 */
	public abstract boolean testCondicion(LocalDate dia, ClienteRegistrado cliente);
}
