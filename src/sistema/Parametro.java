package sistema;

import java.io.Serializable;

/**
 * Clase que representa parametros del sistema
 */
public class Parametro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 12867128712L;

	/**
	 * Horario de apertura del centro
	 */
	Integer horarioApertura;
	
	/**
	 * Horario de cierre del centro
	 */
	Integer horarioCierre;
	
	/**
	 * Meses de penalizacion de sorteos
	 */
	int mesesPenalizacionSorteos;
	
	/**
	 * Constructor
	 * @param a apertura
	 * @param c cierre
	 * @param m meses de penalizacion por no canjear codigos
	 */
	public Parametro (Integer a, Integer c, int m) {
		this.horarioApertura = a;
		this.horarioCierre = c;
		this.mesesPenalizacionSorteos = m;
	}

	/**
	 * Getter apertura
	 * @return apertura
	 */
	public Integer getHorarioApertura() {
		return horarioApertura;
	}

	/**
	 * Getter cierre
	 * @return cierre
	 */
	public Integer getHorarioCierre() {
		return horarioCierre;
	}

	/**
	 * Getter penalizacion
	 * @return int
	 */
	public int getMesesPenalizacionSorteos() {
		return mesesPenalizacionSorteos;
	}

	/**
	 * Setter de apertura
	 * @param horarioApertura apertura
	 */
	public void setHorarioApertura(Integer horarioApertura) {
		this.horarioApertura = horarioApertura;
	}

	/**
	 * Setter de cierre
	 * @param horarioCierre cierre
	 */
	public void setHorarioCierre(Integer horarioCierre) {
		this.horarioCierre = horarioCierre;
	}

	/**
	 * Setter de penalizaciones
	 * @param mesesPenalizacionSorteos penalizaciones en meses
	 */
	public void setMesesPenalizacionSorteos(int mesesPenalizacionSorteos) {
		this.mesesPenalizacionSorteos = mesesPenalizacionSorteos;
	}
}
