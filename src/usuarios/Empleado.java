package usuarios;

import notificaciones.Notificacion;

/**
 * @brief Clase que representa un empleado
 */
public class Empleado extends Usuario{
	
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -3043137626378756478L;
	/**
	 * Nombre del empleado
	 
	 */
	private String nombre;
	/**
	 * Número de seguridad social del empleado
	 */
	private String numSegSocial;
	/**
	 * Número de cuenta del empleado
	 */
	private String numCuenta;
	/**
	 * Dirección física del empleado
	 */
	private String dirFisica;
	/**
	 * Permiso para vender taquillas
	 */
	private boolean permisoVenderTaquilla;
	/**
	 * Permiso para acceder a la temperatura y humedad
	 */
	private boolean permisoTemperaturaHumedad;
	/**
	 * Permiso para enviar mensajes
	 */
	private boolean permisoEnviarMensajes;
	
	/**
	 * @brief Constructor de la clase Empleado
	 * @param nif NIF del empleado
	 * @param nombre Nombre del empleado
	 * @param numSegSocial Número de seguridad social del empleado
	 * @param numCuenta Número de cuenta del empleado
	 * @param dirFisica Dirección física del empleado
	 * @param permisoVenderTaquilla Permiso para vender taquillas
	 * @param permisoTemperaturaHumedad Permiso para acceder a la temperatura y humedad
	 * @param permisoEnviarMensajes Permiso para enviar mensajes
	 */
	public Empleado(String nif, String nombre, String numSegSocial, String numCuenta,
			String dirFisica, boolean permisoVenderTaquilla, boolean permisoTemperaturaHumedad,
			boolean permisoEnviarMensajes) {
		super(nif, "1234");
		this.nombre = nombre;
		this.numSegSocial = numSegSocial;
		this.numCuenta = numCuenta;
		this.dirFisica = dirFisica;
		this.permisoVenderTaquilla = permisoVenderTaquilla;
		this.permisoTemperaturaHumedad = permisoTemperaturaHumedad;
		this.permisoEnviarMensajes = permisoEnviarMensajes;
	}
	
	/**
	 * @brief Obtiene el nombre del empleado
	 * @return El nombre del empleado
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @brief Establece el nombre del empleado
	 * @param nombre El nombre del empleado
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @brief Obtiene el número de seguridad social del empleado
	 * @return El número de seguridad social del empleado
	 */
	public String getNumSegSocial() {
		return numSegSocial;
	}
	
	/**
	 * @brief Establece el número de seguridad social del empleado
	 * @param numSegSocial El número de seguridad social del empleado
	 */
	public void setNumSegSocial(String numSegSocial) {
		this.numSegSocial = numSegSocial;
	}
	
	/**
	 * @brief Obtiene el número de cuenta del empleado
	 * @return El número de cuenta del empleado
	 */
	public String getNumCuenta() {
		return numCuenta;
	}
	
	/**
	 * @brief Establece el número de cuenta del empleado
	 * @param numCuenta El número de cuenta del empleado
	 */
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}
	
	/**
	 * @brief Obtiene la dirección física del empleado
	 * @return La dirección física del empleado
	 */
	public String getDirFisica() {
		return dirFisica;
	}
	
	/**
	 * @brief Establece la dirección física del empleado
	 * @param dirFisica La dirección física del empleado
	 */
	public void setDirFisica(String dirFisica) {
		this.dirFisica = dirFisica;
	}
	
	/**
	 * @brief Verifica si el empleado tiene permiso para vender taquillas
	 * @return true si el empleado tiene permiso para vender taquillas, false de lo contrario
	 */
	public boolean isPermisoVenderTaquilla() {
		return permisoVenderTaquilla;
	}
	
	/**
	 * @brief Establece el permiso para vender taquillas del empleado
	 * @param permisoVenderTaquilla true si el empleado tiene permiso para vender taquillas, false de lo contrario
	 */
	public void setPermisoVenderTaquilla(boolean permisoVenderTaquilla) {
		this.permisoVenderTaquilla = permisoVenderTaquilla;
	}
	
	/**
	 * @brief Verifica si el empleado tiene permiso para acceder a la temperatura y humedad
	 * @return true si el empleado tiene permiso para acceder a la temperatura y humedad, false de lo contrario
	 */
	public boolean isPermisoTemperaturaHumedad() {
		return permisoTemperaturaHumedad;
	}
	
	/**
	 * @brief Establece el permiso para acceder a la temperatura y humedad del empleado
	 * @param permisoTemperaturaHumedad true si el empleado tiene permiso para acceder a la temperatura y humedad, false de lo contrario
	 */
	public void setPermisoTemperaturaHumedad(boolean permisoTemperaturaHumedad) {
		this.permisoTemperaturaHumedad = permisoTemperaturaHumedad;
	}
	
	/**
	 * @brief Verifica si el empleado tiene permiso para enviar mensajes
	 * @return true si el empleado tiene permiso para enviar mensajes, false de lo contrario
	 */
	public boolean isPermisoEnviarMensajes() {
		return permisoEnviarMensajes;
	}
	
	/**
	 * @brief Establece el permiso para enviar mensajes del empleado
	 * @param permisoEnviarMensajes true si el empleado tiene permiso para enviar mensajes, false de lo contrario
	 */
	public void setPermisoEnviarMensajes(boolean permisoEnviarMensajes) {
		this.permisoEnviarMensajes = permisoEnviarMensajes;
	}
	

	/**
	 * @brief Método que permite enviar una notificación a un cliente
	 * @param notificacion La notificación a enviar
	 * @param cliente El cliente al que se enviará la notificación
	 */
	public void enviarNotificacion(Notificacion notificacion, ClienteRegistrado cliente){
		if (permisoEnviarMensajes == false) {
			return;
		}
		cliente.notificar(notificacion);
	}
	
	
	/**
	 * @brief Devuelve una representación en forma de cadena del objeto Empleado
	 * @return La representación en forma de cadena del objeto Empleado
	 */
	public String toString() {
		return this.nombre + ", NIF: " + this.getNif()
			+ ", Dirección física: " + dirFisica+".";
	}

}
