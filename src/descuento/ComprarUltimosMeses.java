package descuento;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import entrada.Entrada;
import usuarios.ClienteRegistrado;
/**
 * @brief Clase que representa un descuento en función de si ha comprado estradas en los últimos n meses
 */
public class ComprarUltimosMeses extends Descuento {

	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -8947579707327657162L;
	/**
	 * Metodo constructor
	 * 
	 * @param porcentaje(Double)
	 * @param nMeses (Integer)
	 */
	public ComprarUltimosMeses(Double porcentaje, Integer nMeses) {
		super(porcentaje, nMeses);
	}

	/**
	 * @brief Evalúa que el cliente haya comprado entradas en los últimos n meses
	 * @param Cliente a evaluar
	 * @param día a evaluar
	 * @return true si cumple condición, false si no cumple condición
	 */
	@Override
	public boolean testCondicion(LocalDate dia, ClienteRegistrado clienteRegistrado) {
		ArrayList<Entrada> entradas = clienteRegistrado.getEntradasCompradas();
		for(Entrada entrada: entradas) {
			LocalDate diaCompra = entrada.getFecha();
			Period periodo = Period.between(LocalDate.now(), diaCompra);
			int diferenciaMeses = periodo.getMonths()*-1;
			
			if(diferenciaMeses<nCondicion) {
					return true;
				}
			
		}
		return false;

	}

}
