package salas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase abstracta Sala
 *
 */
public abstract class Sala implements Serializable{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 123456L;
	/**
	 * Nombre de la sala
	 */
	private String nombre;
	/**
	 * Aforo de la sala y numero de tomas de corriente
	 */
	private Integer aforo, numTomasCorriente;
	/**
	 * Ancho, largo y alto de la sala
	 */
	private double ancho, largo, alto, temperatura, humedad;
	/**
	 * Indica si la sala permite controlar la temperatura y la humedad
	 */
	private boolean permiteControlTemp, permiteControlHum;

	/**
	 * Contador de salas
	 */
	protected static long count = 0;
	/**
	 * Id de la sala
	 */
	protected long id;


	/**
	 * Sala padre
	 */
	private Sala padre = null;

	/**
	 * Indica si la sala esta ocupada
	 */
	private boolean ocupada;
	

	
	/**
	 * Constructor de la clase Sala
	 * @param nombre
	 * @param aforo
	 * @param numTomasCorriente
	 * @param ancho
	 * @param largo
	 * @param alto
	 * @param temperatura
	 * @param humedad
	 * @param permiteControlTemp
	 * @param permiteControlHum
	 */
	public Sala(String nombre, int aforo, int numTomasCorriente, double ancho, double largo, double alto,
			double temperatura, double humedad, boolean permiteControlTemp, boolean permiteControlHum) {
		this.nombre = nombre;
		this.aforo = aforo;
		this.numTomasCorriente = numTomasCorriente;
		this.ancho = ancho;
		this.largo = largo;
		this.alto = alto;
		this.temperatura = temperatura;
		this.humedad = humedad;
		this.permiteControlTemp = permiteControlTemp;
		this.permiteControlHum = permiteControlHum;
		this.id = count++;
		this.setOcupada(false);
		
	}
	/**
	 * Comprueba si la sala es una hoja
	 * @return true si la sala es una hoja, false en caso contrario
	 */
	public abstract boolean esHoja();
	/**
	 * Obtiene el padre de la sala
	 * @return El padre de la sala
	 */
	public Sala getPadre() {
		return padre;
	}
	
	/**
	 * Establece el padre de la sala
	 * @param padre El padre de la sala
	 */
	public void setPadre(Sala padre) {
		this.padre = padre;
	}

	/**
	 * Obtiene el id de la sala
	 * @return El id de la sala
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece la temperatura de la sala
	 * @param temperatura
	 * @return true si se ha establecido la temperatura, false en caso contrario
	 */
	public boolean establecerTemperatura(double temperatura) {
		if (permiteControlTemp == false) {
			return false;
		}
		this.temperatura = temperatura;
		return true;
	}


	/**
	 * Establece la humedad de la sala
	 * @param humedad
	 * @return true si se ha establecido la humedad, false en caso contrario
	 */
	public boolean establecerHumedad(double humedad) {
		if (permiteControlHum == false) {
			return false;
		}
		this.humedad = humedad;
		return true;
	}

	/**
	 * Comprueba si la sala es una hoja
	 * @return true si la sala es una hoja, false en caso contrario
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece el nombre de la sala
	 * @param nombre El nombre de la sala
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el aforo de la sala
	 * @return El aforo de la sala
	 */
	public int getAforo() {
		return aforo;
	}

	/**
	 * Establece el aforo de la sala
	 * @param aforo El aforo de la sala
	 */
	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	/**
	 * Obtiene el número de tomas de corriente de la sala
	 * @return El número de tomas de corriente de la sala
	 */
	public int getNumTomasCorriente() {
		return numTomasCorriente;
	}

	/**
	 * Establece el número de tomas de corriente de la sala
	 * @param numTomasCorriente El número de tomas de corriente de la sala
	 */
	public void setNumTomasCorriente(int numTomasCorriente) {
		this.numTomasCorriente = numTomasCorriente;
	}

	/**
	 * Obtiene el ancho de la sala
	 * @return El ancho de la sala
	 */
	public double getAncho() {
		return ancho;
	}

	/**
	 * Establece el ancho de la sala
	 * @param ancho El ancho de la sala
	 */
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	/**
	 * Obtiene el largo de la sala
	 * @return El largo de la sala
	 */
	public double getLargo() {
		return largo;
	}

	/**
	 * Establece el largo de la sala
	 * @param largo El largo de la sala
	 */
	public void setLargo(double largo) {
		this.largo = largo;
	}

	/**
	 * Obtiene el alto de la sala
	 * @return El alto de la sala
	 */
	public double getAlto() {
		return alto;
	}

	/**
	 * Establece el alto de la sala
	 * @param alto El alto de la sala
	 */
	public void setAlto(double alto) {
		this.alto = alto;
	}

	/**
	 * Obtiene la temperatura de la sala
	 * @return La temperatura de la sala
	 */
	public double getTemperatura() {
		return temperatura;
	}
	
	/**
	 * Obtiene la humedad de la sala
	 * @param temperatura La humedad de la sala
	 */
	public double getHumedad() {
		return humedad;
	}

	/**
	 * Obtiene si la sala permite controlar la temperatura
	 * @return true si la sala permite controlar la temperatura, false en caso contrario
	 */
	public boolean isPermiteControlTemp() {
		return permiteControlTemp;
	}

	/**
	 * Establece si la sala permite controlar la humedad
	 * @param permiteControlTemp true si la sala permite controlar la temperatura, false en caso contrario
	 */
	public void setPermiteControlTemp(boolean permiteControlTemp) {
		this.permiteControlTemp = permiteControlTemp;
	}

	/**
	 * Establece si la sala permite controlar la humedad
	 * @return true si la sala permite controlar la humedad, false en caso contrario
	 */
	public boolean isPermiteControlHum() {
		return permiteControlHum;
	}

	/**
	 * Establece si la sala permite controlar la humedad
	 * @param permiteControlHum true si la sala permite controlar la humedad, false en caso contrario
	 */
	public void setPermiteControlHum(boolean permiteControlHum) {
		this.permiteControlHum = permiteControlHum;
	}

	/**
	 * Metodo para crear subsalas
	 * @param aforo
	 * @param ancho
	 * @param tomasElectricas
	 * @param controlTemp
	 * @param controlHum
	 * @param hoja
	 * @return true si se han creado las subsalas, false en caso contrario
	 */
	public abstract boolean crearSubsalas(int aforo, Double ancho, int tomasElectricas, Boolean controlTemp,
			Boolean controlHum, Boolean hoja);

	/**
	 * Metodo toString
	 * @return String con la información de la sala
	 */
	@Override
	public String toString() {
		return getNombre() + " [" + this.id + "]" + " Aforo: " +getAforo() + " Ancho: " + getAncho() + " Largo: " + getLargo() + " Alto: " + getAlto() + " Temperatura: " + getTemperatura() + " Humedad: " + getHumedad() + " Tomas de corriente: " + getNumTomasCorriente();
		
	}


	/**
	 * Metodo para modificar las caracteristicas de la sala
	 * @param temperatura
	 * @param humedad
	 * @param permiteControlTemp
	 * @param permiteControlHum
	 * @param permiteControlHum
	 * @param permiteControlHum
	 * @return true si se han modificado las caracteristicas, false en caso contrario
	 */
	public abstract boolean modificarCaracteristicasSala(Double temperatura, Double humedad, boolean permiteControlTemp, boolean permiteControlHum);

	/**
	 * Obtiene las subsalas de la sala
	 * @return Las subsalas de la sala
	 */
	public abstract ArrayList<Sala> getSubsalas();

	/**
	 * Establece las subsalas de la sala
	 * @param subsalas
	 */
	public abstract void setSubsalas(ArrayList<Sala> subsalas);

	/**
	 * Obtiene si la sala esta ocupada
	 * @return true si la sala esta ocupada, false en caso contrario
	 */
	public boolean isOcupada() {
		return ocupada;
	}

	/**
	 * Establece si la sala esta ocupada
	 * @param ocupada
	 */
	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
	
	
	
	

}
