package obra;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * Clase que representa una foto
 */
public class Foto extends ObraDimensionada {
	private static final long serialVersionUID = -1981407673422907581L;
	/**
	 * Indica si tiene color o no
	 */
	boolean color;
	
	/**
	 * 
	 * @param autorObra autor
	 * @param obraCentro indica si es del centro
	 * @param anyo año 
	 * @param descripcion descripcion
	 * @param seguro seguro de la obra
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo largo
	 * @param alto se establecera a 0
	 * @param color indica si tiene color
	 */
	public Foto(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro, double tMin,
			double tMax, double hMin, double hMax, double ancho, double largo, double alto, boolean color) {
		super(titulo, autorObra, obraCentro, anyo, descripcion, seguro, tMin, tMax, hMin, hMax, ancho, largo, 0);
		this.color = color;
	}

	/**
	 * To string
	 */
	public String toString() {
		return "Tipo de obra: Foto\n" + super.toString() + "Color: " + this.color + "\n";
	}
	
	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		
		s.add("FOTO|" + super.getFormatoFichero());
		s.add("-|-|" + this.color + "|-|-");
		
		return s.toString();
	}

	/**
	 * Indica si es a color
	 * @return true si es a color, false si no
	 */
	public boolean isColor() {
		return color;
	}
}
