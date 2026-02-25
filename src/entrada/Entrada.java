package entrada;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;


import es.uam.eps.padsof.tickets.ITicketInfo;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import exposicion.Exposicion;
import usuarios.ClienteRegistrado;
import usuarios.Usuario;
/**
 * Clase Entrada
 *
 */
public class Entrada implements Serializable, ITicketInfo{

	private static final long serialVersionUID = -6379269350298620498L;

	private LocalDate fecha;
	private Integer hora;
	private double precio;
	private String numTarjeta;
	private static int contador = 0;
	private int id;
	
	private Exposicion exposición;
	private Usuario propietario;
	/**
	 * Constructor de la clase Entrada
	 * @param fecha		  Día para el que ha comprado el usuario la entrada
	 * @param hora		  hora para el que ha comprado el usuario la entrada
	 * @param precio	  precio que ha tenido pagar por la entrada el usuario
	 * @param exposición  exposición a la que pertenece la entrada
	 * @param propietario usuario que ha comprado la entrada 
	 * @param numTarjeta  número de tarjeta con la que se ha realizado el cobro
	 */
	
	public Entrada(LocalDate fecha, Integer hora, double precio, Exposicion exposición,
			Usuario propietario, String numTarjeta) {
		this.fecha = fecha;
		this.hora = hora;
		this.precio = precio;
		this.exposición = exposición;
		this.propietario = propietario;
		this.numTarjeta = numTarjeta;
		this.id = contador;
		contador++;
	}
	
	/**
	 * Getter para el atributo fecha de la clase Entrada
	 * 
	 * @return fecha (LocalDate)
	 */
	public LocalDate getFecha() {
		return fecha;
	}
	/**
	 * Metodo setter para que se pueda cambiar el día de la entrada.
	 * 
	 * @param fecha (LocalDate)
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	/**
	 * Getter para el atributo hora de la clase Entrada
	 * 
	 * @return hora (Integer)
	 */
	public Integer getHora() {
		return hora;
	}
	/**
	 * Metodo setter para que se pueda cambiar la hora de la entrada.
	 * 
	 * @param hora(Integer)
	 */
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	/**
	 * Getter para el atributo precio de la clase Entrada
	 * 
	 * @return precio (Double)
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * Metodo setter para que se pueda cambiar el precio de la entrada.
	 * 
	 * @param precio(Double)
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * Getter para el atributo exposición de la clase Entrada
	 * 
	 * @return exposición (Exposición)
	 */
	public Exposicion getExposición() {
		return exposición;
	}
	/**
	 * Metodo setter para que se pueda cambiar la exposición de la entrada.
	 * 
	 * @param exposición(Exposición)
	 */
	public void setExposición(Exposicion exposición) {
		this.exposición = exposición;
	}
	/**
	 * Getter para el atributo propietario de la clase Entrada
	 * 
	 * @return propietario (Usuario)
	 */
	public Usuario getPropietario() {
		return propietario;
	}
	/**
	 * Metodo setter para que se pueda cambiar el propietario de la entrada.
	 * 
	 * @param propietario(Usuario)
	 */
	public void setPropietario(ClienteRegistrado propietario) {
		this.propietario = propietario;
	}
	/**
	 * Getter para el atributo numTarjeta de la clase Entrada
	 * 
	 * @return numTarjeta (String)
	 */
	
	public String getNumTarjeta() {
		return numTarjeta;
	}
	/**
	 * Metodo setter para que se pueda cambiar el número de tarjeta de la entrada.
	 * 
	 * @param numTarjeta(String)
	 */
	public void setNumTarjeta(String numTarjeta) {
		this.numTarjeta = numTarjeta;
	}
	/**
	 * Método de la interfaz ITicketInfo,
	 * retorna el identificador del Ticket
	 * 
	 * @return id (Integer)
	 * 
	 */
	@Override
	public int getIdTicket() {
		return id;
	}
	/**
	 * Método de la interfaz ITicketInfo,
	 * retorna el precio del ticket sin aplicar ningún descuento 
	 * 
	 * @return precio (Double)
	 * 
	 */
	@Override
	public double getPrice() {
		return this.precio;
	}
	/**
	 * @brief genera la entrada en formato pdf en la carpeta ./files/entradas/
	 * 		  utilizando el método createTicket de la interfaz TicketSystem
	 */
	public void generarEntradaFormatoPDF () throws NonExistentFileException, UnsupportedImageTypeException{
		TicketSystem.createTicket(
				this,		    
				"." + File.separator + "files" + File.separator + "entradas" + File.separator // Output folder
		);    
	}
	
	@Override
	public String toString() {
		return "[Entrada " + " para la exposicion " + exposición.getNombre() + " el día " + exposición.getFechaInicio()+ ". Precio: " + precio + "€.]";
	}

	
}
