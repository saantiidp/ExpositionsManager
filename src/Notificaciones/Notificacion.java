package notificaciones;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase que representa una notificación
 */
public class Notificacion implements Serializable{
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = 123456L;
	/**
	 * Descripción de la notificación
	 */
	private String descripcion;
	/**
	 * Fecha de la notificación
	 */
	private LocalDate fecha;
	
	/**
	 * Constructor de la clase Notificacion
	 * @param descripcion Descripción de la notificación
	 * @param fecha Fecha de la notificación
	 */
	public Notificacion(String descripcion, LocalDate fecha) {
		this.descripcion = descripcion;
		this.fecha = fecha;
	}
	
	/**
	 * Obtiene la descripción de la notificación
	 * @return La descripción de la notificación
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción de la notificación
	 * @param descripcion La descripción de la notificación
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene la fecha de la notificación
	 * @return La fecha de la notificación
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha de la notificación
	 * @param fecha La fecha de la notificación
	 */
	public void setFecha(LocalDate fecha){
		this.fecha = fecha;
	}
	
	@Override
	public String toString() {
		return "[" + descripcion + ","  + fecha + "]";
	}
}
