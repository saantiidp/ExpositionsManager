package obra;

import java.io.Serializable;

/**
 * Guarda informacion acerca del seguro de una obra
 */
public class Seguro implements Serializable{
	/**
	 * version serial
	 */
	private static final long serialVersionUID = 1L;
	private double cuantia;
	private String numPoliza;
	
	/**
	 * Constructor
	 * @param c cantidad
	 * @param n numero de poliza
	 */
	public Seguro(double c, String n) {
		this.cuantia = c;
		this.numPoliza = n;
	}
	
	/**
	 * Getter del seguro
	 * @return
	 */
	public double getCuantia() {
		return cuantia;
	}
	
	/**
	 * Getter del numero de poliza
	 * @return
	 */
	public String getNumPoliza() {
		return numPoliza;
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
        
        Seguro o = (Seguro) obj;
        return this.cuantia == o.getCuantia() && this.numPoliza.equals(o.getNumPoliza());
    }
}
