package exposicion;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;

import salas.SalaHoja;
import obra.Obra;
/**
 * Clase SalaExposición
 *
 */
public class SalaExposicion implements Serializable{

	private static final long serialVersionUID = 1926699055720857876L;
	
	private SalaHoja sala;
	
	private HashMap<Long, Obra> obras;
	private Long contadorObras = 0L;
	
	/**
	 * Constructor de la clase SalaExposición
	 * @param sala		sala que representa		  
	 */
	public SalaExposicion(SalaHoja sala) {
		this.sala = sala;
		this.obras = new HashMap<Long, Obra>();
	}
	/**
	 * @brief retorna las obras en formato ArrayList
	 * @return obras (ArrayList<Entrada>)
	 */
	public ArrayList<Obra> obtenerObras(){
		ArrayList<Obra> obras = new ArrayList<Obra>();
		for (long i =0; i<this.contadorObras;i++) {
			Obra obra = this.obras.get(i);
			obras.add(obra);
		}
		return obras;
	}
	/**
	 * @brief añade una obra 
	 * @param obra (Obra)
	 */
	
	public void anadirObra(Obra obra) {
		obras.put(contadorObras, obra);
		contadorObras++;
		
	}
	/**
	 * Getter para el atributo sala de la clase SalaExposición
	 * 
	 * @return sala (SalaHoja)
	 */
	public SalaHoja getSala() {
		return sala;
	}
	/**
	 * Metodo setter para que se pueda cambiar la sala de la clase SalaExposición.
	 * 
	 * @param sala (SalaHoja)
	 */
	public void setSala(SalaHoja sala) {
		this.sala = sala;
	}
	/**
	 * Getter para el atributo obras de la clase SalaExposición
	 * 
	 * @return obras (HashMap<Long, Obra>)
	 */
	
	public HashMap<Long, Obra> getObras() {
		return obras;
	}
	/**
	 * Metodo setter para que se pueda cambiar el hasMap obras de la clase SalaExposición.
	 * 
	 * @param obras (HashMap<Long, Obra>)
	 */
	public void setObras(HashMap<Long, Obra> obras) {
		this.obras = obras;
	}
	/**
	 * @brief Elimina una obra de la salaExposición
	 * 
	 * @return true si se ha eliminado bien, false caso contrario (boolean)
	 * @param obra (Obra)
	 */
	public boolean eliminarObra(Obra obra) {
		for (long i =0; i<this.contadorObras;i++) {
			Obra obraAux = this.obras.get(i);
			if(obraAux.equals(obra)) {
				obras.remove(i);
				for(long j=i+1;j<this.contadorObras;j++) {
					Obra obraReplace = this.obras.get(j);
					this.obras.put(j-1, obraReplace);
				}
				this.obras.remove(this.contadorObras);
				this.contadorObras--;
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	

}
