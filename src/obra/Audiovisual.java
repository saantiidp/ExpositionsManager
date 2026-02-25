package obra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.StringJoiner;

/**
 * Clase que representa una audiovisual
 */
public class Audiovisual extends Obra {
	private static final long serialVersionUID = 56781L;
	private LocalTime duracion;
	private String idioma;
	
	/**
	 * Constructor
	 * @param autorObra autor
	 * @param obraCentro indica si es del centro
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param seguro seguro de la obra
 	 * @param duracion duracion
	 * @param idioma idioma
	 */
	public Audiovisual(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro,
			LocalTime duracion, String idioma) {
		super(titulo, autorObra, obraCentro, anyo, descripcion, seguro);
		this.duracion = duracion;
		this.idioma = idioma;
	}

	/**
	 * Getter de la duracion
	 * @return duracion
	 */
	public LocalTime getDuracion() {
		return duracion;
	}

	/**
	 * Getter del idioma
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}
	
	/**
	 * To string
	 */
	public String toString() {
		return "Tipo de obra: Audiovisual\n" + super.toString() + "Idioma: " + this.idioma + ", Duracion: " + this.duracion + "\n";
	}
	
	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		
		s.add("AUDIOVISUAL|" + super.getFormatoFichero());
		s.add("-|-|-|-|-|" + "-|-|-|" + this.idioma + "|" + this.duracion);
		
		return s.toString();
	}
}
