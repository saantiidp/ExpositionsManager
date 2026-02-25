package descuento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import usuarios.ClienteRegistrado;
/**
 * @brief Clase que representa un descuento en función de con cuantos días de antelación quiere comprar la entrada
 */
public class DiasAntelación extends Descuento implements Serializable{
	private static final long serialVersionUID = 1541598217025484002L;

	/**
	 * Metodo constructor
	 * 
	 * @param porcentaje(Double)
	 * @param diasAntelación (Integer)
	 */
	public DiasAntelación(Double porcentaje, Integer diasAntelación) {
		super(porcentaje, diasAntelación);
	}
	/**
	 * @brief Evalúa que el cliente esté comprando con n días de antelación
	 * @param Cliente a evaluar
	 * @param día a evaluar
	 * @return true si cumple condición, false si no cumple condición
	 */
	@Override
	public boolean testCondicion(LocalDate dia, ClienteRegistrado clienteRegistrado) {
        long diasDiferencia = Math.abs(ChronoUnit.DAYS.between(dia, LocalDate.now()));
        int diasDiferenciaInt = (int) diasDiferencia;
		
		if (diasDiferenciaInt>this.nCondicion) {
			return true;
		}
		
		return false;
	}

}
