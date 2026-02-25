package exposicion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import descuento.Descuento;
import entrada.Entrada;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import notificaciones.Notificacion;
import obra.Audiovisual;
import obra.Obra;
import obra.ObraDimensionada;
import salas.SalaHoja;
import sistema.Sistema;
import sorteos.Sorteo;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;
/**
 * La clase Exposición representa una exposición del sistema.
 */
public abstract class Exposicion implements Serializable{
	private static final long serialVersionUID = 865324L;
	protected String nombre;
	protected String descripcion;
	protected double precio;
	protected HashMap<Long, SalaExposicion> salasExposición;
	protected HashMap<LocalDate, HashMap<Integer, ArrayList<Entrada>>> listasEntradas;
	protected ArrayList<Sorteo> sorteos;
	protected ArrayList<Descuento> descuentos;
	private Long contadorSalasExposición = 0L;
	protected LocalDate fechaInicio;
	protected LocalDate fechaInicioCierreTemporal;
	protected LocalDate fechaReanudacionCierreTemporal;
	protected LocalDate fechaCancelacion;
	protected EstadoExposicion estado;
	private static long contador = 0;
	private long id;
	
	/**
	 * Constructor de la clase Exposición
	 * 
	 * @param nombre               Nombre de la Exposición
	 * @param descripción          Descripción de la Exposición
	 * @param precio               Precio de la Exposición
	 */
	public Exposicion(String nombre, String descripcion, double precio) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.salasExposición = new HashMap<Long, SalaExposicion>();
		this.listasEntradas = new HashMap<LocalDate, HashMap<Integer,ArrayList<Entrada>>>();
		this.sorteos = new ArrayList<Sorteo>();
		this.descuentos = new ArrayList<Descuento>();
		this.estado = EstadoExposicion.BORRADOR;
		this.id = contador++;
	
	}
	/**
	 * @brief Obtiene las obras de la exposición
	 * @return Las obras de la exposición
	 */
	public ArrayList<Obra> getObras(){
		ArrayList<Obra> obras = new ArrayList<Obra>();
		for (long i=0L; i<this.contadorSalasExposición ;i++) {
			SalaExposicion salaExposicion = this.salasExposición.get(i);
			for(Obra obra: salaExposicion.obtenerObras()) {
				obras.add(obra);
			}
		}

		return obras;
	}
	/**
	 * Getter para el atributo nombre de la clase Exposición
	 * 
	 * @return nombre (String)
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Metodo setter para que se pueda cambiar el nombre de la clase Exposición.
	 * 
	 * @param nombre (String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Getter para el atributo descripción de la clase Exposición
	 * 
	 * @return nombre (String)
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Metodo setter para que se pueda cambiar la descripción de la clase Exposición.
	 * 
	 * @param descripción (String)
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Getter para el atributo precio de la clase Exposición
	 * 
	 * @return nombre (Double)
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * Metodo setter para que se pueda cambiar el precio de la clase Exposición.
	 * 
	 * @param precio (Double)
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	/**
	 * @brief Añade una salaExposición a la exposición
	 * @param salaExposición SalaExposición a añadir
	 */
	public void anadirSalaExposición(SalaHoja sala) {
		SalaExposicion salaExposicion = new SalaExposicion(sala);
		salasExposición.put(contadorSalasExposición, salaExposicion);
		contadorSalasExposición++;
		sala.setOcupada(true);
	}
	
	/**
	 * @brief Añade una entrada a la exposición
	 * @param entrada Entrada a añadir
	 */
	public void anadirEntrada(Entrada entrada) {
		if (!this.listasEntradas.containsKey(entrada.getFecha())){
			
			this.listasEntradas.put(entrada.getFecha(), new HashMap<Integer, ArrayList<Entrada>>());
			
			HashMap<Integer, ArrayList<Entrada>>entradasDia= this.listasEntradas.get(entrada.getFecha());
			
			for(int i=Sistema.getInstance().getHorarioAperturaExposicion();i<=Sistema.getInstance().getHorarioCierreExposicion();i++) {
				ArrayList<Entrada> nuevaListaentradasHora = new ArrayList<Entrada>();
				entradasDia.put(i, nuevaListaentradasHora);
			}
		}
		
		HashMap<Integer, ArrayList<Entrada>> entradasDia = this.listasEntradas.get(entrada.getFecha());
		ArrayList<Entrada>  entradasHora = entradasDia.get(entrada.getHora());
		if(entradasHora==null) {
			entradasDia.put(entrada.getHora(), new ArrayList<Entrada>());
		}
		entradasHora = entradasDia.get(entrada.getHora());
		entradasHora.add(entrada);
		return;
	
	}
	/**
	 * Getter para el atributo salasExposición de la clase Exposición
	 * 
	 * @return salasExposición(HashMap<Long, SalaExposición>)
	 */
	public HashMap<Long, SalaExposicion> getSalasExposición() {
		return salasExposición;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo salasExposición de la clase Exposición.
	 * 
	 * @param salasExposición(HashMap<Long, SalaExposición>)
	 */
	public void setSalasExposición(HashMap<Long, SalaExposicion> salasExposición) {
		this.salasExposición = salasExposición;
	}
	/**
	 * Getter para el atributo listasEntradas de la clase Exposición
	 * 
	 * @return listasEntradas(HashMap<LocalDate, HashMap<Integer, ArrayList<Entrada>>>)
	 */
	public HashMap<LocalDate, HashMap<Integer, ArrayList<Entrada>>> getListasEntradas() {
		return listasEntradas;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo listasEntradas de la clase Exposición.
	 * 
	 * @param listasEntradas(HashMap<LocalDate, HashMap<Integer, ArrayList<Entrada>>>)
	 */
	public void setListasEntradas(HashMap<LocalDate, HashMap<Integer, ArrayList<Entrada>>> listasEntradas) {
		this.listasEntradas = listasEntradas;
	}
	
	public HashMap<Integer, ArrayList<Entrada>> obtenerEntradasDia(LocalDate dia){
		if (!this.listasEntradas.containsKey(dia))
			this.listasEntradas.put(dia, new HashMap<Integer, ArrayList<Entrada>>());
			
		return this.listasEntradas.get(dia);
	}

	/**
	 * @brief Obtiene una lista de las entradas de la exposición de un día y hora concreta
	 * @return entradas (ArrayList<Entrada>)
	 */
	public ArrayList<Entrada> obtenerEntradasDiaHora(LocalDate dia, Integer hora){
		HashMap<Integer, ArrayList<Entrada>> entradasDia = obtenerEntradasDia(dia);
		return entradasDia.get(hora);
	}
	/**
	 * @brief Obtiene el aforo de la exposición
	 * @return aforo (Integer)
	 */
	public int getAforo() {
		int numeroEntradasExposicion = 0;
		for (long i=0L; i<this.contadorSalasExposición;i++) {

			numeroEntradasExposicion+=this.salasExposición.get(i).getSala().getAforo();
		}
		return numeroEntradasExposicion;
	}
	
	/**
	 * @brief Obtiene las entradas disponibles de la exposición de un día y hora concreta
	 * @return número (Integer)
	 */
	public int getNumeroEntradasDisponibles(LocalDate dia, Integer hora) {;
		
		ArrayList<Entrada> entradas = obtenerEntradasDiaHora(dia, hora);
		if (entradas == null) {
			return getAforo();
		}
		return getAforo() - entradas.size();
	}
	
	/**
	 * @brief borra una entrada de la exposición
	 * 
	 * @param entrada (Entrada)
	 * 
	 * @return true si se ha podido borrar correctamente, false si no se ha podido borrar correctamente
	 */
	public boolean borrarEtrada(Entrada entrada) {
		LocalDate dia = entrada.getFecha();
		Integer hora = entrada.getHora();
		
		ArrayList<Entrada> entradas = obtenerEntradasDiaHora(dia, hora);
		if (entradas == null)
			return false;
		entradas.remove(entrada);
		return true; 
		
	}
	
	/**
	 * @brief Añade un sorteo a la exposición
	 * @param sorteo Sorteo a añadir
	 */
	public void anadirSorteo(Sorteo sorteo) {
		this.sorteos.add(sorteo);
	}
	
	
	/**
	 * @brief Añade un descuento a la exposición
	 * @param descuento Descuento a añadir
	 */
	public void anadirDescuento(Descuento descuento) {
		this.descuentos.add(descuento);
	}
	/**
	 * @brief Cambia el estado de la Exposición a dada de alta
	 * @return true si se ha podido cambiar de estado, false si no se ha podido cambiar 
	 */
	public boolean iniciada() {
		if(this.estado == EstadoExposicion.DADA_ALTA) {
			if(LocalDate.now().isAfter(this.fechaInicio)) {
				this.estado = EstadoExposicion.INICIADA;
				return true;
			}
		}
		return false;
	}
	/**
	 * @brief Cambia el estado de la Exposición a cerrada temporalmente
	 * 
	 * @param fechaInicioCierre (LocalDate)
	 * @param fechaReanudacion (LocalDate)
	 * 
	 * @return true si se ha podido cambiar de estado, false si no se ha podido cambiar 
	 */
	public abstract boolean cerrarTemporalmente(LocalDate fechaInicioCierre, LocalDate fechaReanudacion);
	/**
	 * @brief reanudar una exposición que está cerrada temporalmente
	 * @return true si se ha podido cambiar de estado, false si no se ha podido cambiar 
	 */
	public boolean reanudar() {
		if(this.estado==EstadoExposicion.CERRADA_TEMPORALMENTE && LocalDate.now().isAfter(fechaReanudacionCierreTemporal)) {
			this.estado=EstadoExposicion.INICIADA;
			return true;
		}
		return false;
	}
	/**
	 * @brief Compra de entradas de un cliente registrado a una hora y día concreto con un número de tarjeta válido
	 * 
	 * @param cliente (ClienteRegistrado)
	 * @param numTarjeta (String)
	 * @param fecha (LocalDate)
	 * @param hora (Integer)
	 * @param numeroEntradas (Integer)
	 * @param codigoSorteo (String)
	 * 
	 * @return true si se ha podido comprar entradas correctamente, false si no se ha podido comprar entradas correctamente 
	 */
	public abstract boolean comprarEntradas(ClienteRegistrado cliente, String numTarjeta, LocalDate fecha, Integer hora, int numeroEntradas, String codigoSorteo) 
	throws 	InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException;
	/**
	 * @brief Compra de entradas en taquilla de un monitor a una hora concreta 
	 * 
	 * @param empleado (Empleado)
	 * @param hora (Integer)
	 * 
	 * @return true si se ha podido comprar entradas correctamente, false si no se ha podido comprar entradas correctamente 
	 */
	public abstract boolean comprarEntradaTaquilla(Empleado empleado,int hora);
	/**
	 * @brief Obtiene el número de entradas vendidas de la exposición un día y hora concreta
	 * 
	 * @param día (LocalDate)
	 * @param hora (Integer)
	 * 
	 * @return númeroEntradas (Integer)
	 */	
	public int obtenerNumeroEntradasVendidas(LocalDate dia, int hora) {
		if(hora<Sistema.getInstance().getHorarioAperturaExposicion() || hora>Sistema.getInstance().getHorarioCierreExposicion()) {
			return 0;
		}
		if(obtenerEntradasDiaHora(dia, hora)==null) {
			return 0;
		}
		return obtenerEntradasDiaHora(dia, hora).size();
	}
		
	/**
	 * @brief Cambia el estado de la Exposición a cancelada y devuelve el dinero a los cliente que han comprado en fechas posteriores a la de cancelacion
	 * 
	 * @param fechaCancelación (LocalDate)
	 * 
	 * @return true si se ha podido cambiar de estado, false si no se ha podido cambiar 
	 */	
	public boolean cancelarExposicion(LocalDate fechaCancelacion) throws InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException{
		if(this.estado!=EstadoExposicion.INICIADA && this.estado!=EstadoExposicion.CERRADA_TEMPORALMENTE && this.estado!=EstadoExposicion.PRORROGADA) {
			return false;
		}
		
		long diasEntreFechas = ChronoUnit.DAYS.between(LocalDate.now(),fechaCancelacion);
		int diasEntreFechasInt = (int) diasEntreFechas;	
		if(diasEntreFechasInt<7) {
			return false;
		}
		
		this.fechaCancelacion = fechaCancelacion;
		this.estado = EstadoExposicion.CANCELADA;
		
		 // Clave específica desde la que queremos empezar
        LocalDate fechaInicio = LocalDate.now().plusDays(7);

        // Obtener un conjunto de entradas
        for (Entry<LocalDate, HashMap<Integer, ArrayList<Entrada>>> entrada : this.listasEntradas.entrySet()) {
            LocalDate fecha = entrada.getKey();
            // Verificar si es el momento de empezar a recorrer
            if (fecha.equals(fechaInicio) || fecha.isAfter(fechaInicio)) {
                // Iterar sobre las entradas a partir de esta clave
                HashMap<Integer, ArrayList<Entrada>> entradasPorHora = entrada.getValue();
                for (Map.Entry<Integer, ArrayList<Entrada>> entradaHora : entradasPorHora.entrySet()) {
                    ArrayList<Entrada> entradas = entradaHora.getValue();
                    for(int i=0; i<entradas.size();i++) {
                    	Entrada entradaUsuario = entradas.get(i);
                    	if (entradaUsuario.getNumTarjeta()!= null) {
                    		//como obtner numeroTarjeta
                    		if (TeleChargeAndPaySystem.isValidCardNumber(entradaUsuario.getNumTarjeta())) {
                    			try {
                    				TeleChargeAndPaySystem.charge(entradaUsuario.getNumTarjeta(), "Devolución del dinero", entradaUsuario.getPrecio(), true);
                    				Sistema.getInstance().getContabilidad().restarBeneficioExposicion(this, entradaUsuario.getPrecio());
                    				ClienteRegistrado cliente = (ClienteRegistrado) entradaUsuario.getPropietario();
                    				cliente.notificar(new Notificacion("se ha cancelado la exposición " + this.nombre + ", se te ha devuelto el dinero", LocalDate.now()));
                    				//Sistema.getInstance().getContabilidad().restarBeneficioExposicion(this, entradaUsuario.getPrecio());
                    				//Sistema.getInstance().getContabilidad().restarEntradaExposicion(this, 1);
                    			} catch (OrderRejectedException e) {
                    				e.printStackTrace();
                    				return false;
                    			}
                    		}
                    		else {
                    			System.err.println("Numero de tarjeta introducido no valido");
                    			return false;
                    		}
                    	}
                    }
                }
            }
        }
        return true;
	}
	/**
	 * @brief Elimina una obra de una salaExposición
	 * 
	 * @param obra (Obra)
	 * @param sala (SalaHoja)
	 * 
	 * @return true si se ha podido eliminar, false si no se ha podido eliminar
	 */
	public boolean eliminarObra(Obra obra, SalaHoja sala) {
		if(this.estado != EstadoExposicion.BORRADOR) {
			return false;
		}
		
		for(long i=0; i<this.contadorSalasExposición;i++) {
			SalaExposicion salaExposicion = this.salasExposición.get(i);
			if(sala.equals(salaExposicion.getSala())) {
				return salaExposicion.eliminarObra(obra);
			}
		}
		
		return false;
		
	}
	
	/**
	 * @brief Añade una obra Audiovisual a una salaExposición de la exposición
	 * 
	 * @param obra (AudioVisual)
	 * @param sala (SalaHoja)
	 * 
	 * @return true si se ha podido añadir, false si no se ha podido añadir
	 * 
	 */
	public boolean anadirObra(Audiovisual obra, SalaHoja sala) {
		if(this.estado != EstadoExposicion.BORRADOR) {
			return false;
		}
		for(long i=0; i<this.contadorSalasExposición;i++) {
			SalaExposicion salaExposicion = this.salasExposición.get(i);
			if(sala.equals(salaExposicion.getSala())) {
					if(sala.getNumTomasCorriente()<1) {
						return false;
					}
					else {
						salaExposicion.anadirObra(obra);
						return true;
					}
			}
		}
		return false;
	}
	/**
	 * @brief Añade una obra ObraDimensionada a una salaExposición de la exposición
	 * @param obra (ObraDimensionada)
	 * @param sala (SalaHoja)
	 * 
	 * @return true si se ha podido añadir, false si no se ha podido añadir
	 */
	public boolean anadirObra(ObraDimensionada obra, SalaHoja sala ) {
		if(this.estado != EstadoExposicion.BORRADOR) {
			return false;
		}
		for(long i=0; i<this.contadorSalasExposición;i++) {
			SalaExposicion salaExposicion = this.salasExposición.get(i);
			if(sala.equals(salaExposicion.getSala())) {
				double humedadMaximaObra = obra.gethMax();
				double humedadMinimaObra = obra.gethMin();
				double humedadSala = sala.getHumedad();
				if (humedadSala < humedadMinimaObra || humedadSala > humedadMaximaObra) {
					return false;
				}
				
				double temperaturaMaximaObra = obra.gettMax();
				double temperaturaMinimaObra = obra.gettMin();
				double temperaturaSala = sala.getTemperatura();
				if (temperaturaSala < temperaturaMinimaObra || temperaturaSala > temperaturaMaximaObra) {
					return false;
				}
				
				double altoSala = sala.getAlto();
				double anchoSala = sala.getAncho();
				
				double altoObra = obra.getAlto();
				double anchoObra = obra.getAncho();
				
				if(altoObra > altoSala || anchoObra > anchoSala) {
					return false;
					
				}
				
				if(obra.esTipoObra("Escultura")) {
					double largoSala  = sala.getLargo();
					
					double largoObra = obra.getLargo();
					
					if(largoObra > largoSala) {
						return false;
					}
				}
				
				salaExposicion.anadirObra(obra);
				return true;
				
			}
		}
		return false;
	}
	
	/**
	 * @brief Verifica que la obra sea temporal
	 * @return true si es temporal, false si no lo es
	 */
	public abstract boolean esTemporal();
	/**
	 * Getter para el atributo fechaInicio de la clase Exposición
	 * 
	 * @return fechaInicio (LocalDate)
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo fechaInicio de la clase Exposición.
	 * 
	 * @param fechaInicio(LocalDate)
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * Getter para el atributo fechaInicioCierreTemporal de la clase Exposición
	 * 
	 * @return fechaInicioCierreTemporal (LocalDate)
	 */
	public LocalDate getFechaInicioCierreTemporal() {
		return fechaInicioCierreTemporal;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo fechaInicioCierreTemporal de la clase Exposición.
	 * 
	 * @param fechaInicioCierreTemporal (LocalDate)
	 */
	public void setFechaInicioCierreTemporal(LocalDate fechaInicioCierreTemporal) {
		this.fechaInicioCierreTemporal = fechaInicioCierreTemporal;
	}
	/**
	 * Getter para el atributo fechaReanudacionCierreTemporal de la clase Exposición
	 * 
	 * @return fechaReanudacionCierreTemporal (LocalDate)
	 */
	public LocalDate getFechaReanudacionCierreTemporal() {
		return fechaReanudacionCierreTemporal;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo fechaReanudacionCierreTemporal de la clase Exposición.
	 * 
	 * @param fechaReanudacionCierreTemporal (LocalDate)
	 */
	public void setFechaReanudacionCierreTemporal(LocalDate fechaReanudacionCierreTemporal) {
		this.fechaReanudacionCierreTemporal = fechaReanudacionCierreTemporal;
	}
	/**
	 * Getter para el atributo fechaCancelación de la clase Exposición
	 * 
	 * @return fechaCancelacion (LocalDate)
	 */
	public LocalDate getFechaCancelacion() {
		return fechaCancelacion;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo fechaCancelacion de la clase Exposición.
	 * 
	 * @param fechaCancelacion (LocalDate)
	 */
	public void setFechaCancelacion(LocalDate fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	/**
	 * Getter para el atributo estado de la clase Exposición
	 * 
	 * @return estado (EstadoExposición)
	 */
	public EstadoExposicion getEstado() {
		return estado;
	}
	/**
	 * Metodo setter para que se pueda cambiar el atributo estado de la clase Exposición.
	 * 
	 * @param estado (EstadoExposición)
	 */
	public void setEstado(EstadoExposicion estado) {
		this.estado = estado;
	}
	/**
	 * Getter para el atributo id de la clase Exposición
	 * 
	 * @return id (Long)
	 */
	public long getId() {
		return id;
	}
	/**
	 * Getter para el atributo sorteos de la clase Exposición
	 * 
	 * @return sorteos (ArrayList<Sorteo>)
	 */
	public ArrayList<Sorteo> getSorteos() {
		return sorteos;
	}
	
}
