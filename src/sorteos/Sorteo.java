package sorteos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import exposicion.Exposicion;
import notificaciones.Notificacion;
import usuarios.ClienteRegistrado;

/**
 * La clase Sorteo representa un sorteo de entradas para una exposición.
 */
public abstract class Sorteo implements Serializable {
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -3243497332145962652L;
	/**
	 * Fecha de inicio de la inscripción
	 */
	private LocalDate inicioInscripcion;
	/**
	 * Fecha de fin de la inscripción
	 */
	private LocalDate finInscripcion;
	/**
	 * Número de entradas sorteadas
	 */
	private int numEntradasSorteadas;
	/**
	 * Códigos de las entradas sorteadas
	 */
	private Map<String, Boolean> codigos;
	/**
	 * Booleano que indica si el gestor ha sido notificado
	 */
	private boolean gestorNotificado;
	/**
	 * Exposición a la que pertenece el sorteo
	 */
	private Exposicion exposicion;
	/**
	 * Lista de inscripciones al sorteo
	 */
	private ArrayList<Inscripcion> inscripciones;
	/**
	 * Id del sorteo
	 */
	protected long id;
	/**
	 * Contador de sorteos
	 */
	protected static long contador = 0;
	/**
	 * Numero de ganadores
	 */
	private int numGanadores;

	/**
	 * Booleano que indica si el sorteo ha sido sorteado
	 */
	protected boolean sorteado;

	/**
	 * Constructor de la clase Sorteo
	 * 
	 * @param inicioInscripcion    Fecha de inicio de la inscripción
	 * @param finInscripcion       Fecha de fin de la inscripción
	 * @param numEntradasSorteadas Número de entradas sorteadas
	 * @param gestorNotificado     Booleano que indica si el gestor ha sido
	 *                             notificado
	 * @param exposicion           Exposición a la que pertenece el sorteo
	 */
	public Sorteo(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion) {
		this.inicioInscripcion = inicioInscripcion;
		this.finInscripcion = finInscripcion;
		this.numEntradasSorteadas = numEntradasSorteadas;
		this.codigos = new HashMap<>();
		this.gestorNotificado = gestorNotificado;
		this.exposicion = exposicion;
		this.inscripciones = new ArrayList<>();
		for (int i = 0; i < this.numEntradasSorteadas; i++) {
			codigos.put(generarCodigo(), false);
		}
		this.id = contador++;
		this.numGanadores = 0;
		this.sorteado = false;
	}

	/**
	 * @brief Añade una inscripción al sorteo
	 * @param inscripcion Inscripción a añadir
	 */
	public void añadirInscripcion(Inscripcion inscripcion) {
		inscripciones.add(inscripcion);
	}

	/**
	 * @brief Obtiene las inscripciones del sorteo
	 * @return Las inscripciones del sorteo
	 */
	public Map<String, Boolean> getCodigos() {
		return codigos;
	}

	/**
	 * @brief Establece los códigos del sorteo
	 * @param codigos Los códigos del sorteo
	 */
	public String generarCodigo() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = (char) (random.nextInt(26) + 'A');
			sb.append(c);
		}
		return sb.toString();

	}

	/**
	 * @brief Obtiene si un código es usable
	 * @param codigo Código a comprobar
	 * @return true si el codigo ya ha sido usado, o false si no ha sido usado
	 */
	public boolean esUsable(String codigo) {
		if (codigo == null) {
			return false;

		}
		if (codigos.get(codigo)) {
			return true;
		}
		return false;
	}

	/**
	 * @brief Establece un código como usado
	 * @param codigo Código a establecer como usado
	 */
	public void setUsado(String codigo) {
		codigos.put(codigo, true);
	}

	/**
	 * @brief Inscribirse en el sorteo
	 * @param cliente      Cliente que participa en el sorteo
	 * @param num_entradas Número de entradas solicitadas
	 * @return true si la inscripción se ha realizado correctamente, false en caso
	 *         contrario
	 */
	public boolean inscribirse(ClienteRegistrado cliente, int num_entradas) {


		if (cliente.getVetadoHasta() != null && cliente.getVetadoHasta().isAfter(LocalDate.now())) {
			cliente.notificar(new Notificacion("Estás vetado hasta el " + cliente.getVetadoHasta(), LocalDate.now()));
			return false;
		}

		if(sorteado){
			cliente.notificar(new Notificacion("Este sorteo ya ha sido sorteado", LocalDate.now()));
			return false;
		}
		if (LocalDate.now().isBefore(inicioInscripcion) || LocalDate.now().isAfter(finInscripcion)) {
			cliente.notificar(new Notificacion("El sorteo no está abierto", LocalDate.now()));
			return false;
		}
		if (num_entradas > numEntradasSorteadas) {
			cliente.notificar(new Notificacion("No hay suficientes entradas", LocalDate.now()));
			return false;
		}

		Inscripcion inscripcion = new Inscripcion(num_entradas, this, cliente);
		for (Inscripcion inscripcion_loop : inscripciones) {
			if (inscripcion_loop.getCliente().equals(cliente)) {
				cliente.notificar(new Notificacion("Ya estás inscrito en el sorteo", LocalDate.now()));
				return false;
			}
		}
		añadirInscripcion(inscripcion);
		cliente.notificar(new Notificacion("Inscripción realizada para el sorteo de la exposicion: " + this.exposicion.getNombre(), LocalDate.now()));
		return true;
	}

	/**
	 * @brief Obtiene si el gestor ha sido notificado
	 * @return true si el gestor ha sido notificado, false en caso contrario
	 */
	public abstract ArrayList<Inscripcion> sortear();

	/**
	 * @brief Obtiene la fecha de inicio de la inscripción
	 * @return La fecha de inicio de la inscripción
	 */
	public LocalDate getInicioInscripcion() {
		return inicioInscripcion;
	}

	/**
	 * @brief Establece la fecha de inicio de la inscripción
	 * @param inicioInscripcion La fecha de inicio de la inscripción
	 */
	public void setInicioInscripcion(LocalDate inicioInscripcion) {
		this.inicioInscripcion = inicioInscripcion;
	}

	/**
	 * @brief Obtiene la fecha de fin de la inscripción
	 * @return La fecha de fin de la inscripción
	 */
	public LocalDate getFinInscripcion() {
		return finInscripcion;
	}

	/**
	 * @brief Establece la fecha de fin de la inscripción
	 * @param finInscripcion La fecha de fin de la inscripción
	 */
	public void setFinInscripcion(LocalDate finInscripcion) {
		this.finInscripcion = finInscripcion;
	}

	/**
	 * @brief Obtiene el número de entradas sorteadas
	 * @return El número de entradas sorteadas
	 */
	public int getNumEntradasSorteadas() {
		return numEntradasSorteadas;
	}

	/**
	 * @brief Establece el número de entradas sorteadas
	 * @param numEntradasSorteadas El número de entradas sorteadas
	 */
	public void setNumEntradasSorteadas(int numEntradasSorteadas) {
		this.numEntradasSorteadas = numEntradasSorteadas;
	}

	/**
	 * @brief Obtiene si el gestor ha sido notificado
	 * @return true si el gestor ha sido notificado, false en caso contrario
	 */
	public boolean isGestorNotificado() {
		return gestorNotificado;
	}

	/**
	 * @brief Establece si el gestor ha sido notificado
	 * @param gestorNotificado true si el gestor ha sido notificado, false en caso
	 *                         contrario
	 */
	public void setGestorNotificado(boolean gestorNotificado) {
		this.gestorNotificado = gestorNotificado;
	}

	/**
	 * @brief Obtiene la exposición a la que pertenece el sorteo
	 * @return La exposición a la que pertenece el sorteo
	 */
	public Exposicion getExposicion() {
		return exposicion;
	}

	/**
	 * @brief Establece la exposición a la que pertenece el sorteo
	 * @param exposicion La exposición a la que pertenece el sorteo
	 */
	public void setExposicion(Exposicion exposicion) {
		this.exposicion = exposicion;
	}

	/**
	 * @brief Getter del id del sorteo
	 * @return el id del sorteo
	 */
	public long getId() {
		return id;
	}

	/**
	 * @briefGetter de las inscripciones del sorteo
	 * @return las inscripciones del sorteo
	 */
	public ArrayList<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public abstract String getTipo();

	/**
	 * @brief Obtiene el número de ganadores
	 * @return El número de ganadores
	 */
	public int getNumGanadores() {
		return numGanadores;
	}


	/**
	 * @brief Establece el número de ganadores
	 * @param i El número de ganadores
	 */
	public void setNumGanadores(int i) {
		this.numGanadores = i;
	}


	/**
	 * @brief Obtiene si el sorteo ha sido sorteado
	 * @return true si el sorteo ha sido sorteado, false en caso contrario
	 */
	public boolean isSorteado() {
		return sorteado;
	}
}
