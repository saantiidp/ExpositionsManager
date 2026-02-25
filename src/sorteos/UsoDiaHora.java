package sorteos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import exposicion.Exposicion;
import notificaciones.Notificacion;

/**
 * Clase que representa un sorteo con un día y hora concretos
 */
public class UsoDiaHora extends Sorteo {
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = 3466518319085283789L;
	/**
	 * Día del sorteo
	 */
	private LocalDate dia;
	/**
	 * Hora del sorteo
	 */
	private LocalTime time;

	/**
	 * Constructor de la clase UsoDiaHora
	 * 
	 * @param inicioInscripcion    Fecha de inicio de la inscripción
	 * @param finInscripcion       Fecha de fin de la inscripción
	 * @param numEntradasSorteadas Número de entradas sorteadas
	 * @param gestorNotificado     Booleano que indica si el gestor ha sido
	 *                             notificado
	 * @param exposicion           Exposición a la que pertenece el sorteo
	 * @param dia                  Día del sorteo
	 * @param time                 Hora del sorteo
	 */
	public UsoDiaHora(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion, LocalDate dia,
			LocalTime time) {
		super(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion);
		this.dia = dia;
		this.time = time;

	}

	/**
	 * @brief Devuelve el día del sorteo
	 * @return Día del sorteo
	 */
	public LocalDate getDia() {
		return dia;
	}

	/**
	 * @brief Establece el día del sorteo
	 * @param dia Día del sorteo
	 */
	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	/**
	 * @brief Devuelve la hora del sorteo
	 * @return Hora del sorteo
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @brief Establece la hora del sorteo
	 * @param time Hora del sorteo
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}

	/**
	 * @brief Realiza el sorteo
	 * @return Lista de inscripciones ganadoras
	 */
	@Override
	public ArrayList<Inscripcion> sortear() {
		if (sorteado) {
			return new ArrayList<>();
		}
		if ((this.getExposicion().getFechaInicio().isBefore(LocalDate.now())
				|| this.getExposicion().getFechaInicio().isEqual(LocalDate.now()))) {
			Random random = new Random();
			ArrayList<Inscripcion> ganadores = new ArrayList<>();
			ArrayList<String> codigosGanadores = new ArrayList<>(this.getCodigos().keySet());

			while (this.getInscripciones().size() > 0 && this.getNumEntradasSorteadas() > 0) {
				int index = random.nextInt(this.getInscripciones().size());
				Inscripcion inscripcion = this.getInscripciones().get(index);
				int aux = inscripcion.getNumEntradasSolicitidas();
				if (aux > this.getNumEntradasSorteadas()) {
					inscripcion.setNumEntradasSolicitidas(this.getNumEntradasSorteadas());
					this.setNumEntradasSorteadas(0);
				} else {
					this.setNumEntradasSorteadas(this.getNumEntradasSorteadas() - aux);
				}
				ganadores.add(inscripcion);
				String codigo_ganador = codigosGanadores.remove(0);
				inscripcion.getCliente().notificar(new Notificacion(
						"Has ganado el sorteo de " + inscripcion.getNumEntradasSolicitidas()
								+ " entradas para la exposición: " + this.getExposicion().getNombre() + ".\n"
								+ "Aquí el código de descuento: " + codigo_ganador + ".",
						LocalDate.now()));
				this.getInscripciones().remove(index);
			}
			for (Inscripcion inscripcion : this.getInscripciones()) {
				inscripcion.getCliente().notificar(new Notificacion("No has ganado el sorteo para la exposición: "
						+ this.getExposicion().getNombre() + ", inténtalo en el siguiente :)", LocalDate.now()));
			}

			this.sorteado = true;
			return ganadores;
		}
		return new ArrayList<>();
	}

	/**
	 * @brief Devuelve el tipo de sorteo
	 * @return Tipo de sorteo
	 */
	@Override
	public String getTipo() {
		return "Dia Hora";
	}

}