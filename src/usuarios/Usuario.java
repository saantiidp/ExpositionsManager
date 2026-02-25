package usuarios;

import java.io.Serializable;

/**
 * Clase abstracta que representa a un usuario del sistema
 */
public abstract class Usuario implements Serializable {
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = 16578978675L;
	/**
	 * NIF del usuario
	 */
	private String nif;
	/**
	 * Contraseña del usuario
	 */
	private String contraseña;
	
	/**
	 * @brief Constructor de la clase Usuario
	 * @param nif
	 * @param contraseña
	 */
	public Usuario(String nif, String contraseña) {
		this.nif = nif;
		this.contraseña = contraseña;
	}
	/**
	 * @brief Obtiene la contraseña del usuario
	 * @return La contraseña del usuario
	 */
	public String getContraseña() {
		return contraseña;
	}
	/**
	 * @brief Establece la contraseña del usuario
	 * @param contraseña La contraseña del usuario
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	/**
	 * @brief Obtiene el NIF del usuario
	 * @return El NIF del usuario
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @brief Establece el NIF del usuario
	 * @param nif El NIF del usuario
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
}
