package sorteos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import exposicion.Exposicion;
import notificaciones.Notificacion;
/**
 * Clase que representa un sorteo con un intervalo de fechas
 */
public class UsoIntervaloFechas extends Sorteo{
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -323295786470248341L;
	/**
	 * Fecha de inicio del sorteo
	 */
	private LocalDate fechaInicio;
	/**
	 * Fecha de fin del sorteo
	 */
	private LocalDate fechaFin;

	/**
	 * Constructor de la clase UsoIntervaloFechas
	 * @param inicioInscripcion Fecha de inicio de la inscripción
	 * @param finInscripcion Fecha de fin de la inscripción
	 * @param numEntradasSorteadas Número de entradas sorteadas
	 * @param gestorNotificado Booleano que indica si el gestor ha sido notificado
	 * @param exposicion Exposición a la que pertenece el sorteo
	 * @param fechaInicio Fecha de inicio del sorteo
	 * @param fechaFin Fecha de fin del sorteo
	 */
	public UsoIntervaloFechas(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion, LocalDate fechaInicio, LocalDate fechaFin) {
		super(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}
	
	/**
	 * @brief Devuelve la fecha de inicio del sorteo
	 * @return Fecha de inicio del sorteo
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @brief Establece la fecha de inicio del sorteo
	 * @param fechaInicio Fecha de inicio del sorteo
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @brief Devuelve la fecha de fin del sorteo
	 * @return Fecha de fin del sorteo
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * @brief Establece la fecha de fin del sorteo
	 * @param fechaFin Fecha de fin del sorteo
	 */
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}


	/**
	 * @brief Sortea el sorteo
	 * @return Lista de inscripciones ganadoras
	 */
	@Override
	public ArrayList<Inscripcion> sortear() {

		if(sorteado) {
			return new ArrayList<>();
		}
		if ((this.getFechaInicio().isBefore(LocalDate.now())
				|| this.getFechaInicio().isEqual(LocalDate.now()) && this.getFechaFin().isAfter(LocalDate.now()) || 
					this.getFechaFin().isEqual(LocalDate.now()))){
			Random random = new Random();
			ArrayList<Inscripcion> ganadores = new ArrayList<>();
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
				String codigo_ganador = this.getCodigos().keySet().stream()
						.filter(codigo -> !this.getCodigos().get(codigo)).toList()
						.get(this.getNumGanadores());
				this.setNumGanadores(this.getNumGanadores() + 1);
				inscripcion.getCliente().notificar(new Notificacion(
						"Has ganado el sorteo de " + inscripcion.getNumEntradasSolicitidas() + " entradas. "
								+ "Aqui el codigo de descuento: " + codigo_ganador + ".",
						LocalDate.now()));
				this.getCodigos().put(codigo_ganador, true);
				this.getInscripciones().remove(index);
			}

			for (Inscripcion inscripcion : this.getInscripciones()) {
				inscripcion.getCliente().notificar(new Notificacion("No has ganado el sorteo para la exposicion: "
						+ this.getExposicion().getNombre() + ", intentalo en el siguiente :)", LocalDate.now()));
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
		return "Intervalo de fechas";
	}
		

	
	
	
	

}
