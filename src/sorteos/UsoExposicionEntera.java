package sorteos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import exposicion.Exposicion;
import notificaciones.Notificacion;

/**
 * Clase que representa un sorteo con una exposición entera
 */
public class UsoExposicionEntera extends Sorteo {

	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = 471758085852335782L;

	/**
	 * Constructor de la clase UsoExposicionEntera
	 * 
	 * @param inicioInscripcion    Fecha de inicio de la inscripción
	 * @param finInscripcion       Fecha de fin de la inscripción
	 * @param numEntradasSorteadas Número de entradas sorteadas
	 * @param gestorNotificado     Booleano que indica si el gestor ha sido
	 *                             notificado
	 * @param exposicion           Exposición a la que pertenece el sorteo
	 */
	public UsoExposicionEntera(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion) {
		super(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion);
	}


	/**
	 * @brief Realiza el sorteo de la exposición entera
	 * @return Lista de inscripciones ganadoras
	 */
	@Override
	public ArrayList<Inscripcion> sortear() {

		if(sorteado) {
			return new ArrayList<>();
		}
		if ((this.getExposicion().getFechaInicio().isBefore(LocalDate.now())
				|| this.getExposicion().getFechaInicio().isEqual(LocalDate.now()))) {
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
						.get(getNumGanadores());
			    this.setNumGanadores(this.getNumGanadores() + 1);
				inscripcion.getCliente().notificar(new Notificacion(
						"Has ganado el sorteo de " + inscripcion.getNumEntradasSolicitidas() + " entradas para la exposicion: " + this.getExposicion().getNombre() + ".\n"
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
		return "Exposición entera";
	}

	/**
	 * @brief Método toString de la clase UsoExposicionEntera
	 * @return String con la información del sorteo
	 */
	@Override
	public String toString() {
		return "Sorteo de uso de exposición entera para la exposición: " + this.getExposicion().getNombre() + "."
				+ " Fecha de inicio de inscripción: " + this.getInicioInscripcion() + ". Fecha de fin de inscripción: "
				+ this.getFinInscripcion() + ". Número de entradas sorteadas: " + this.getNumEntradasSorteadas() + "."
				+ " Gestor notificado: " + this.isGestorNotificado() + ".";
	}
	

}
