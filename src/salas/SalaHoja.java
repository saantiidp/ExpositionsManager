package salas;

import java.util.ArrayList;

/**
 * Clase SalaHoja
 *
 */
public class SalaHoja extends Sala{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 123456L;

	/**
	 * Constructor de la clase SalaHoja
	 * @param nombre nombre de la sala
	 * @param aforo aforo de la sala 
	 * @param numTomasCorriente número de tomas de corriente de la sala
	 * @param ancho ancho de la sala
	 * @param largo largo de la sala
	 * @param alto alto de la sala
	 * @param temperatura temperatura de la sala
	 * @param humedad humedad de la sala 
	 * @param permiteControlTemp indica si la sala tiene control de temperatura 
	 * @param permiteControlHum indica si la sala tiene control de humedad
	 */
	public SalaHoja(String nombre, int aforo, int numTomasCorriente, double ancho, double largo, double alto,
			double temperatura, double humedad, boolean permiteControlTemp, boolean permiteControlHum) {
		super(nombre, aforo, numTomasCorriente, ancho, largo, alto, temperatura, humedad, permiteControlTemp, permiteControlHum);

	}

	/**
	 * Crea subsalas en la sala
	 * @param aforo aforo de la sala
	 * @param ancho ancho de la sala
	 * @param tomasElectricas número de tomas de corriente
	 * @param controlTemp indica si la sala tiene control de temperatura
	 * @param controlHum indica si la sala tiene control de humedad
	 * @param hoja indica si la sala es una hoja
	 * @return true si se han creado las subsalas, false en caso contrario
	 */
	@Override
	public boolean crearSubsalas(int aforo, Double ancho, int tomasElectricas, Boolean controlTemp, Boolean controlHum,
			Boolean hoja) {
		return false;
	}

	public ArrayList<Sala> getSubsalas() {
		return null;
	}

	

	/**
	 * metodo toString
	 * @return String con la información de la sala
	 */
@Override
	public String toString(){
		return getNombre() + " [" + getId() + "]" + " Aforo: " +getAforo()+ " Tomas de corriente: " + getNumTomasCorriente()+ " Ancho: " + getAncho() +"m";
	}



/**
 * Establece las subsalas de la sala
 * @param subsalas lista de subsalas
 */
@Override
public void setSubsalas(ArrayList<Sala> subsalas) {
	return;
}



/**
 * metodo que devuelve si la sala es hoja
 * @return true si la sala es hoja, false en caso contrario
 */
@Override
public boolean esHoja() {
	return true;
}







/**
 * Modifica las características de la sala
 * @param temperatura temperatura de la sala
 * @param humedad humedad de la sala
 * @param permiteControlTemp indica si la sala tiene control de temperatura
 * @param permiteControlHum indica si la sala tiene control de humedad
 * @return true si se han modificado las características, false en caso contrario
 */
@Override
public boolean modificarCaracteristicasSala(Double temperatura, Double humedad, boolean permiteControlTemp,
		boolean permiteControlHum) {
		this.setPermiteControlHum(permiteControlHum);
		this.setPermiteControlTemp(permiteControlTemp);
		boolean h = establecerHumedad(humedad);
		boolean t = establecerTemperatura(temperatura);
		return h && t;
		
}



	
}
