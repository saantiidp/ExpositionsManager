package obra;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

import enums.EstadoObra;

/**
 * Class que representa una Obra
 */
public abstract class Obra implements Serializable {
	private static final long serialVersionUID = 4896164533265543612L;
	private boolean obraCentro;
	private String titulo;
	private String autorObra;
	private LocalDate anyo;
	private String descripcion;
	private Seguro seguro;
	private EstadoObra estadoObra;
	private long id;
	private static long contador = 0;

	/**
	 * Constructor de la clase
	 * @param autorObra, author of the artwork
	 * @param obraCentro,  whether it is owned or not
	 * @param anyo, year
	 * @param descripcion, description
	 * @param seguro, insurance
	 */
	public Obra(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro) {
		this.titulo = titulo;
		this.autorObra = autorObra;
		this.obraCentro = obraCentro;
		this.anyo = anyo;
		this.descripcion = descripcion;
		this.estadoObra = EstadoObra.ALMACEN;
		this.seguro = seguro;
		this.id = contador++;
	}

	/**
	 * Cambia el estado a almacenada
	 */
	public void almacenar() {
		this.estadoObra = EstadoObra.ALMACEN;
	}

	/**
	 * Cambia el estado a expuesta
	 */
	public void exponer() {
		this.estadoObra = EstadoObra.EXPUESTA;
	}

	/**
	 * Cambia el estado a prestada
	 */
	public void prestar() {
		this.estadoObra = EstadoObra.PRESTADA;
	}

	/**
	 * Cambia el estado a en restauracion
	 */
	public void restaurar() {
		this.estadoObra = EstadoObra.RESTAURACION;
	}

	/**
	 * Cambia el estado a retirada
	 */
	public void retirar() {
		this.estadoObra = EstadoObra.RETIRADA;
	}

	/**
	 * Getter del autor de la obra
	 * @return el autor de la obra
	 */
	public String getAutorObra() {
		return autorObra;
	}

	/**
	 * Indica si es obra del centro
	 * @return true si lo es, false si no
	 */
	public boolean isObraCentro() {
		return obraCentro;
	}

	/**
	 * Getter del año
	 * @return el año
	 */
	public LocalDate getAnyo() {
		return anyo;
	}

	/**
	 * Getter de la descripcion
	 * @return la descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Getter del estado de obra
	 * @return el estado
	 */
	public EstadoObra getEstadoObra() {
		return estadoObra;
	}

	/**
	 * Getter del seguro
	 * @return el seguro
	 */
	public Seguro getSeguro() {
		return seguro;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Getter del id
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Indica si es del tipo pasado como argumento
	 * @param tipoObra, nombre de una de las subclases
	 * @return true si es del tipo pasado como argumento, false si no
	 */
	public boolean esTipoObra(String tipoObra) {
		return this.getClass().getSimpleName().toLowerCase().equals(tipoObra.toLowerCase());
	}
	
	/**
	 * To string
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		if (this.obraCentro)
			s.append("Obra del centro");
		else
			s.append("Obra prestada, ");
		s.append("Titulo: " + this.titulo + ", ");
		s.append("Autor: " + this.autorObra + ", ");
		s.append("Anyo: " + this.anyo + "\n");
		s.append("Descripcion: " + this.descripcion + "\n");
		s.append("Cuantia del seguro: " + this.seguro.getCuantia() + " ");
		s.append("Numero de poliza: " + this.seguro.getNumPoliza() + "\n");
		
		return s.toString();
	}

	/**
	 * Obtiene el formato de la obra en una String
	 * @return formato fichero
	 */
	public String getFormatoFichero() {
		StringJoiner s = new StringJoiner("|");
		if (this.obraCentro)
			s.add("CENTRO");
		else
			s.add("EXTERNA");
		s.add(this.titulo);
		s.add(this.autorObra);
		s.add(this.anyo.toString());
		s.add(this.descripcion);
		s.add(String.valueOf(this.seguro.getCuantia()));
		s.add(this.seguro.getNumPoliza());
		
		return s.toString();
	}
	
	/**
	 * Metodo equals
	 */
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Obra obraArg = (Obra) obj;
        return this.titulo.equals(obraArg.getTitulo()) && this.autorObra.equals(obraArg.getAutorObra()) && this.anyo.equals(obraArg.getAnyo())
        		&& this.descripcion.equals(obraArg.getDescripcion()) && this.seguro.equals(obraArg.getSeguro()) && this.obraCentro == obraArg.isObraCentro();
    }
}
