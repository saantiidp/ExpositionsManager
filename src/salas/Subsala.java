package salas;

import java.util.ArrayList;

import sistema.Sistema;

/**
 * Clase Subsala
 *
 */
public class Subsala extends Sala {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 123456L;

	/**
	 * Subsalas de la sala
	 */
	private ArrayList<Sala> subsalas;

	/**
	 * Constructor de la clase Subsala
	 * 
	 * @param nombre             nombre
	 * @param aforo              aforo
	 * @param numTomasCorriente  número de tomas de corriente
	 * @param ancho              ancho
	 * @param largo              largo
	 * @param alto               alto
	 * @param temperatura        temperatura
	 * @param humedad            humedad
	 * @param permiteControlTemp permite control de temperatura
	 * @param permiteControlHum  permite control de humedad
	 */
	public Subsala(String nombre, int aforo, int numTomasCorriente, double ancho, double largo, double alto,
			double temperatura, double humedad, boolean permiteControlTemp, boolean permiteControlHum) {
		super(nombre, aforo, numTomasCorriente, ancho, largo, alto, temperatura, humedad, permiteControlTemp,
				permiteControlHum);
		this.subsalas = new ArrayList<Sala>();
	}


	/**
	 * Crea un número de subsalas en la sala padre. El número de subsalas se indica
	 * en el parámetro num.
	 * 
	 * @param num             Número de subsalas a crear
	 * @param aforo           Aforo de la sala padre
	 * @param ancho           Ancho de la sala padre
	 * @param tomasElectricas Tomas de corriente de la sala padre
	 * @param controlTemp     Indica si la sala padre tiene control de temperatura
	 * @param controlHum      Indica si la sala padre tiene control de humedad
	 * @param hoja            Indica si las subsalas son hojas o no
	 * @return true si se han creado las subsalas, false en caso contrario
	 * @see Sala
	 */
	@Override
	public boolean crearSubsalas(int aforo, Double ancho, int tomasElectricas, Boolean controlTemp, Boolean controlHum,
			Boolean hoja) {

		if (aforo > this.getAforo() || ancho > this.getAncho() || tomasElectricas > this.getNumTomasCorriente()) {
			return false;
		}

		if (hoja) {
			Sistema.getInstance().crearSala(true, "Sala hoja de " +this.getNombre(), aforo, tomasElectricas, ancho, super.getLargo(),
					super.getAlto(), super.getTemperatura(), super.getHumedad(), controlTemp, controlHum);
			Sala s  = Sistema.getInstance().getSalas().get(Sistema.getInstance().getSalas().size()-1);
			s.setPadre(this);
			subsalas.add(s);
		} else {
			Sistema.getInstance().crearSala(false,"Subsala de " + this.getNombre(), aforo, tomasElectricas, ancho, super.getLargo(),
					super.getAlto(), super.getTemperatura(), super.getHumedad(), controlTemp, controlHum);
			Sala s  = Sistema.getInstance().getSalas().get(Sistema.getInstance().getSalas().size()-1);
			s.setPadre(this);
			subsalas.add(s);
		}

	return true;
	}

	/**
	 * Elimina las subsalas de la sala
	 * @return true si se han eliminado las subsalas, false en caso contrario
	 */
	public boolean eliminarSubsalas() {
		subsalas.clear();
		return true;
	}

	/**
	 * Obtiene las subsalas de la sala
	 * @return Las subsalas de la sala
	 */
	public ArrayList<Sala> getSubsalas() {
		return subsalas;
	}
	
	/**
	 * Establece las subsalas de la sala
	 * @param subsalas lista de subsalas
	 */
	public void setSubsalas(ArrayList<Sala> subsalas) {
		this.subsalas = subsalas;
	}

	
	/**
	 * Modifica las características de la sala
	 * @param temperatura temperatura
	 * @param humedad humedad
	 * @param permiteControlTemp indica si la sala permite controlar la temperatura
	 * @param permiteControlHum indica si la sala permite controlar la humedad
	 * @return true si se han modificado las características, false en caso contrario
	 */
	@Override
	public boolean modificarCaracteristicasSala(Double temperatura, Double humedad, boolean permiteControlTemp,
			boolean permiteControlHum) {

		this.setPermiteControlTemp(permiteControlTemp);
		this.setPermiteControlHum(permiteControlHum);
		/*for(Sala s : subsalas) {
			s.establecerHumedad(humedad);
			s.establecerTemperatura(temperatura);
		}*/
		boolean h = establecerHumedad(humedad);
		boolean t = establecerTemperatura(temperatura);
		return h && t;
	}
	

	/**
	 * Método toString
	 * @return String con la información de la sala
	 */
	@Override
	public String toString() {
		return getNombre() + " [" + this.getId() + "]" + " Aforo: " +this.getAforo() + "Tomas de corriente: " + this.getNumTomasCorriente() + "Ancho: " + this.getAncho() + "m"  + "\n Lista de subsalas: " + subsalas;

	}

	/**
	 * Método que devuelve si la sala es hoja
	 * @return true si la sala es hoja, false en caso contrario
	 */
	@Override
	public boolean esHoja() {
		return false;
	}

}
