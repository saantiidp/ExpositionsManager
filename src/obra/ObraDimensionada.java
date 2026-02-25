package obra;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * Clase abstracta para las obras que esten dimensionadas y permiten control de temperatura y humedad
 */
public abstract class ObraDimensionada extends Obra {
	/**
	 * version serial
	 */
	private static final long serialVersionUID = 17675L;
	
	/**
	 * Dimensiones a lo ancho largo y alto
	 */
	private double ancho;
	private double largo;
	private double alto;
	
	/**
	 * Temperaturas minimas y maximas
	 */
	private double tMin; 
	private double tMax;
	
	/**
	 * Humedades minimas y maximas
	 */
	private double hMin;
	private double hMax;

	/**
	 * Constructor
	 * @param autorObra autor
	 * @param obraCentro indica si es del centro o no
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param seguro, seguro
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo largo
	 * @param alto alto
	 */
	public ObraDimensionada(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro,
			double tMin, double tMax, double hMin, double hMax, double ancho, double largo, double alto) {
		super(titulo, autorObra, obraCentro, anyo, descripcion, seguro);
		this.tMin = tMin;
		this.tMax = tMax;
		this.hMin = hMin;
		this.hMax = hMax;
		this.ancho = ancho;
		this.largo = largo;
		this.alto = alto;
	}

	/**
	 * Getter del ancho
	 * @return
	 */
	public double getAncho() {
		return ancho;
	}

	/**
	 * Setter del ancho
	 * @param ancho
	 */
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	/**
	 * Getter del largo
	 * @return
	 */
	public double getLargo() {
		return largo;
	}

	/**
	 * Setter del largo
	 * @param largo
	 */
	public void setLargo(double largo) {
		this.largo = largo;
	}

	/**
	 * Getter del alto
	 * @return alto
	 */
	public double getAlto() {
		return alto;
	}

	/**
	 * Setter del alto
	 * @param alto alto
	 */
	public void setAlto(double alto) {
		this.alto = alto;
	}

	/**
	 * Getter de temperatura minima
	 * @return temperatura minima
	 */
	public double gettMin() {
		return tMin;
	}

	/**
	 * Setter de la temperatura minima
	 * @param tMin temperatura minima
	 */
	public void settMin(double tMin) {
		this.tMin = tMin;
	}

	/**
	 * Getter de la temperatura maxima
	 * @return temperatura maxima
	 */
	public double gettMax() {
		return tMax;
	}

	/**
	 * Setter de la temperatura maxima
	 * @param tMax la temperatura maxima
	 */
	public void settMax(double tMax) {
		this.tMax = tMax;
	}

	/**
	 * Getter de la humedad minima
	 * @return humedad minima
	 */
	public double gethMin() {
		return hMin;
	}

	/**
	 * setter de humedad minima
	 * @param hMin humedad minima
	 */
	public void sethMin(double hMin) {
		this.hMin = hMin;
	}

	/**
	 * Getter de humedad maxima
	 * @return humedad maxima
	 */
	public double gethMax() {
		return hMax;
	}

	/**
	 * setter de humedad maxima
	 * @param hMax humedad maxima
	 */
	public void sethMax(double hMax) {
		this.hMax = hMax;
	}
	
	/**
	 * To string
	 */
	public String toString() {
		return super.toString() + "Medidas: "+ this.ancho + "x" + this.largo + "x" + this.alto + "\nRango de temperaturas: (" + this.tMin + "C--" + this.tMax + "C), Rango de humedad: ("+ this.hMin*100 + "%--" + this.hMax*100 + "%)\n";
	}
	
	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		
		s.add(super.getFormatoFichero());
		s.add(String.valueOf(this.ancho));
		s.add(String.valueOf(this.largo));
		s.add(String.valueOf(this.alto));
		s.add(this.tMin + "--" + this.tMax);
		s.add(this.hMin + "--" + this.hMax);
		
		return s.toString();
	}
}
