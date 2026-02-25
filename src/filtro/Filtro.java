package filtro;
import exposicion.Exposicion;
/**
 * Interfaz filtro que ofrece método cumpleFiltro 
 * para las clases que representen los diferentes tipos de filtros
 * implementen dicho metodo
 *
 */
public interface Filtro{
	
	/**
	 * @brief Evalúa que la exposición pasa el filtro
	 * @param Exposicion a evaluar
	 * @return true si cumple filtro, false si no cumple filtro
	 */
	
	public boolean cumpleFiltro(Exposicion exposición);
}
