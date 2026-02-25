package obra;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Escultura extends ObraDimensionada {
	private static final long serialVersionUID = 12332L;
	private String material;

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
	 * @param material
	 */
	public Escultura(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro,
			double tMin, double tMax, double hMin, double hMax, double ancho, double largo, double alto, String material) {
		super(titulo, autorObra, obraCentro, anyo, descripcion, seguro, tMin, tMax, hMin, hMax, ancho, largo, alto);
		this.material = material;
	}

	/**
	 * To string
	 */
	public String toString() {
		return "Tipo de obra: Escultura\n" + super.toString() + "Material: " + this.material + "\n";
	}
	
	/**
	 * Getter del material
	 * @return material
	 */
	public String getMaterial() {
		return material;
	}
	
	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		
		s.add("ESCULTURA|" + super.getFormatoFichero());
		s.add("-|" + this.material + "|-|-|-");
		
		return s.toString();
	}
}
