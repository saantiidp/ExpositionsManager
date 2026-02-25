package obra;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * Clase que representa un cuadro
 */
public class Cuadro extends ObraDimensionada {
	/**
	 * Version serial
	 */
	private static final long serialVersionUID = 7500802536289986864L;
	/**
	 * Tecnica
	 */
	private String tecnica;

	/**
	 * Constructor
	 * @param autorObra autor
	 * @param obraCentro si es del centro o prestada
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param seguro seguro
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo latgo
	 * @param alto alto
	 * @param t tecnica
	 */
	public Cuadro(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro, double tMin,
			double tMax, double hMin, double hMax, double ancho, double largo, double alto, String t) {
		super(titulo, autorObra, obraCentro, anyo, descripcion, seguro, tMin, tMax, hMin, hMax, ancho, largo, alto);
		this.tecnica = t;
	}

	/**
	 * Getter de la tecnica
	 * @return
	 */
	public String getTecnica() {
		return tecnica;
	}
	
	/**
	 * To string
	 */
	public String toString() {
		return "Tipo de obra: Cuadro\n" + super.toString() + "Tecnica: " + this.tecnica + "\n";
	}
	
	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		
		s.add("CUADRO|" + super.getFormatoFichero());
		s.add(this.tecnica + "|-|-|-|-");
		
		return s.toString();
	}
}
