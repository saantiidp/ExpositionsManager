package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entrada.Entrada;
import notificaciones.Notificacion;
import sorteos.Inscripcion;
import sorteos.Sorteo;
/**
 * La clase ClienteRegistrado representa a un cliente registrado en el sistema.
 * Hereda de la clase Usuario y contiene información adicional sobre el cliente.
 */
public class ClienteRegistrado extends Usuario{
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -3238749712193071958L;
	/**
	 * Atributo que indica si el cliente quiere recibir publicidad
	 */
	private boolean recibirPublicidad;
	/**
	 * Lista de notificaciones del cliente
	 */
	private ArrayList<Notificacion> notificaciones;
	/**
	 * Mapa de inscripciones del cliente
	 */
	private HashMap<Integer, Inscripcion> inscripciones;
	/**
	 * Lista de entradas compradas por el cliente
	 */
	private ArrayList<Entrada> entradasCompradas;
	/**
	 * Fecha hasta la que el cliente está vetado
	 */
	private LocalDate vetadoHasta;
	

	
	/**
	 * Constructor de la clase ClienteRegistrado
	 * @param recibirPublicidad booleano que indica si el cliente quiere recibir publicidad
	 * @param nif NIF del cliente
	 * @param contraseña Contraseña del cliente
	 */
	public ClienteRegistrado(boolean recibirPublicidad, String nif, String contraseña) {
		super(nif, contraseña);
		this.recibirPublicidad = recibirPublicidad;
		this.notificaciones = new ArrayList<>();
		this.inscripciones = new HashMap<>();
		this.entradasCompradas = new ArrayList<>();
		this.vetadoHasta = null;
	}

	/**
	 * @brief Establece si el cliente quiere recibir publicidad
	 * @param b booleano que indica si el cliente quiere recibir publicidad
	 */
	public void deseaPublicidad(boolean b) {
		this.recibirPublicidad = b;
	}

	/**
	 * @brief Obtiene si el cliente quiere recibir publicidad
	 * @return true si el cliente quiere recibir publicidad, false en caso contrario
	 */
	public boolean isRecibirPublicidad() {
		return recibirPublicidad;
	}

	/**
	 * @brief Obtiene las notificaciones del cliente
	 * @return Las notificaciones del cliente
	 */
	public ArrayList<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @brief Establece las notificaciones del cliente
	 * @param notificaciones Las notificaciones del cliente
	 */
	public void setNotificaciones(ArrayList<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	/**
	 * @brief Obtiene las inscripciones del cliente
	 * @return Las inscripciones del cliente
	 */
	public HashMap<Integer, Inscripcion> getInscripciones() {
		return inscripciones;
	}

	/**
	 * @brief Establece las inscripciones del cliente
	 * @param inscripciones Las inscripciones del cliente
	 */
	public void setInscripciones(HashMap<Integer, Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}


	
	/**
	 * @brief Obtiene las entradas compradas por el cliente
	 * @return Las entradas compradas por el cliente
	 */
	public ArrayList<Entrada> getEntradasCompradas() {
		return entradasCompradas;
	}

	/**
	 * @brief Establece si el cliente quiere recibir publicidad
	 * @param recibirPublicidad true si el cliente quiere recibir publicidad, false en caso contrario
	 */
	public void setRecibirPublicidad(boolean recibirPublicidad) {
		this.recibirPublicidad = recibirPublicidad;
	}

	/**
	 * @brief Establece las entradas compradas por el cliente
	 * @param entradasCompradas Las entradas compradas por el cliente
	 */
	public void setEntradasCompradas(ArrayList<Entrada> entradasCompradas) {
		this.entradasCompradas = entradasCompradas;
	}

	/**
	 * @brief Obtiene la fecha hasta la que el cliente está vetado
	 * @return La fecha hasta la que el cliente está vetado
	 */
	public LocalDate getVetadoHasta() {
		return vetadoHasta;
	}

	/**
	 * @brief Establece la fecha hasta la que el cliente está vetado
	 * @param vetadoHasta La fecha hasta la que el cliente está vetado
	 */
	public void setVetadoHasta(LocalDate vetadoHasta) {
		this.vetadoHasta = vetadoHasta;
	}

	/**
	 * @brief Añade una notificación al cliente
	 * @param notificacion La notificación a añadir
	 */
	public boolean notificar(Notificacion notificacion){
		if(!isRecibirPublicidad())
			return false;
		notificaciones.add(notificacion);
		return true;
	}

	
	/**
	 * @brief Participa en un sorteo
	 * @param sorteo El sorteo en el que participa
	 * @param num_entradas El número de entradas que solicita
	 */
	public boolean participarSorteo(Sorteo sorteo, int num_entradas) {
		if(this.getVetadoHasta() != null && this.getVetadoHasta().isAfter(LocalDate.now())) {
			return false;
		}
		sorteo.inscribirse(this, num_entradas);
		return true;
	}

	/**
	 * @brief Añade una entrada al cliente
	 * @param entrada La entrada a añadir
	 */
	public void añadirEntrada(Entrada entrada) {
		entradasCompradas.add(entrada);
	}
	
	/**
	 * @brief Metodo toString de la clase ClienteRegistrado
	 * @return String con la información del cliente
	 */
	public String toString() {
		return "[NIF: "+ this.getNif() + "]";

	}

	/**
	 * @brief Obtiene el código del sorteo ganado
	 * @return El código del sorteo ganado
	 */
	public String getCodigoSorteo() {
		for(Notificacion notificacion : notificaciones){
			//Has ganado el sorteo de 3 entradas. Aqui el codigo de descuento: ACYXPS.
			if(notificacion.getDescripcion().contains("Has ganado el sorteo de")){
				String[] parts = notificacion.getDescripcion().split(" ");
				return parts[parts.length-1].substring(0, 6);
			}
		}
		return null;
	}

	
}
	