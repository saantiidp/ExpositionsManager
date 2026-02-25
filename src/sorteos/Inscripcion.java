package sorteos;


import java.io.Serializable;

import usuarios.ClienteRegistrado;
/**
 * Clase que representa una inscripción a un sorteo
 */
public class Inscripcion implements Serializable {
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -2741191830592843184L;
	/**
	 * Número de entradas solicitadas
	 */
	private int numEntradasSolicitidas;
	/**
	 * Sorteo al que se inscribe
	 */
	private Sorteo sorteo;
	/**
	 * Cliente que se inscribe
	 */
	private ClienteRegistrado cliente;

	/**
	 * Constructor de la clase Inscripcion
	 * @param numEntradasSolicitidas Número de entradas solicitadas
	 * @param sorteo  Sorteo al que se inscribe
	 * @param cliente Cliente que se inscribe
	 */
	public Inscripcion(int numEntradasSolicitidas, Sorteo sorteo, ClienteRegistrado cliente) {
		this.numEntradasSolicitidas = numEntradasSolicitidas;
		this.sorteo = sorteo;
		this.cliente = cliente;
	}	
	
	/**
	 * @brief Obtiene el número de entradas solicitadas
	 * @return El número de entradas solicitadas
	 */
	public int getNumEntradasSolicitidas() {
		return numEntradasSolicitidas;
	}
	/**
	 * @brief Establece el número de entradas solicitadas
	 * @param numEntradasSolicitidas El número de entradas solicitadas
	 */
	public void setNumEntradasSolicitidas(int numEntradasSolicitidas) {
		this.numEntradasSolicitidas = numEntradasSolicitidas;
	}
	/**
	 * @brief Obtiene el sorteo al que se inscribe
	 * @return El sorteo al que se inscribe
	 */
	public Sorteo getSorteo() {
		return sorteo;
	}
	/**
	 * @brief Establece el sorteo al que se inscribe
	 * @param sorteo El sorteo al que se inscribe
	 */
	public void setSorteo(Sorteo sorteo) {
		this.sorteo = sorteo;
	}
	/**
	 * @brief Obtiene el cliente que se inscribe
	 * @return El cliente que se inscribe
	 */
	public ClienteRegistrado getCliente() {
		return cliente;
	}
	/**
	 * @brief Establece el cliente que se inscribe
	 * @param cliente El cliente que se inscribe
	 */
	public void setCliente(ClienteRegistrado cliente) {
		this.cliente = cliente;
	}
	
	/**
	 * @brief Metodo toString de la clase Inscripcion
	 */
	@Override
	public String toString() {
		return "Inscripcion [numEntradasSolicitidas=" + numEntradasSolicitidas + ", cliente="
				+ cliente + "]";
	}
}
