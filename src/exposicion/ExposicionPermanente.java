package exposicion;


import java.io.Serializable;
import java.time.LocalDate;

import descuento.Descuento;
import entrada.Entrada;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import sistema.Sistema;
import sorteos.Sorteo;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;
/**
 * La clase ExposiciónPermanente representa una exposición de tipo permanente del sistema.
 */
public class ExposicionPermanente extends Exposicion implements Serializable {
	
	private static final long serialVersionUID = -4495980452444556972L;
	/**
	 * Constructor de la clase ExposiciónPermanente
	 * 
	 * @param nombre               Nombre de la Exposición
	 * @param descripción          Descripción de la Exposición
	 * @param precio               Precio de la Exposición
	 */
	public ExposicionPermanente(String nombre, String descripcion, double precio) {
		super(nombre, descripcion, precio);
	}
	/**
	 * @brief Compra de entradas en taquilla de un monitor a una hora concreta 
	 * 
	 * @param empleado (Empleado)
	 * @param hora (Integer)
	 * 
	 * @return true si se ha podido comprar entradas correctamente, false si no se ha podido comprar entradas correctamente 
	 */
	@Override
	public boolean comprarEntradaTaquilla(Empleado empleado, int hora) {
		Sistema sistema = Sistema.getInstance();
		
		Integer horaApertura = sistema.getHorarioAperturaExposicion();
		Integer horaCierre = sistema.getHorarioCierreExposicion();
		if(!empleado.isPermisoVenderTaquilla()) {
			return false;
		}
		if(this.estado==EstadoExposicion.CANCELADA) {
			if(LocalDate.now().isAfter(this.fechaCancelacion)) {
				return false;
			}
		}
		if(this.estado!=EstadoExposicion.CANCELADA && this.estado!=EstadoExposicion.INICIADA && this.estado!=EstadoExposicion.PRORROGADA && this.estado!=EstadoExposicion.CERRADA_TEMPORALMENTE) {
			return false;
		}
		if(this.estado==EstadoExposicion.CERRADA_TEMPORALMENTE) {
			if(LocalDate.now().isAfter(fechaInicioCierreTemporal)&& LocalDate.now().isBefore(fechaReanudacionCierreTemporal)) {
				return false;
			}
		}
		
		if (hora < horaApertura || hora > horaCierre) {
			return false;
		}
		
		if (getNumeroEntradasDisponibles(LocalDate.now(), hora) < 1) {
			return false;
		}
		
		
		Entrada entrada = new Entrada(LocalDate.now(), hora, this.precio, this, empleado, null);
		anadirEntrada(entrada);
		try {
			entrada.generarEntradaFormatoPDF();
		} catch (NonExistentFileException | UnsupportedImageTypeException e) {
			e.printStackTrace();
		}
		sistema.getContabilidad().sumarEntradaExposicion(this, 1);
		sistema.getContabilidad().sumarBeneficioExposicion(this, this.precio);

			
		return true;
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
	@Override
	public boolean comprarEntradas(ClienteRegistrado cliente, String numTarjeta, LocalDate fecha, Integer hora, int numeroEntradas, String codigoSorteo) 
			throws 	InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException{
				
				Sistema sistema = Sistema.getInstance();
				
				Integer horaApertura = sistema.getHorarioAperturaExposicion();
				Integer horaCierre = sistema.getHorarioCierreExposicion();
				
				if(this.estado==EstadoExposicion.CANCELADA) {
					if(fecha.isAfter(this.fechaCancelacion)) {
						return false;
					}
				}
				if(this.estado!=EstadoExposicion.CANCELADA && this.estado!=EstadoExposicion.INICIADA && this.estado!=EstadoExposicion.PRORROGADA && this.estado!=EstadoExposicion.CERRADA_TEMPORALMENTE) {
					return false;
				}
				if(this.estado==EstadoExposicion.CERRADA_TEMPORALMENTE) {
					if(fecha.isAfter(fechaInicioCierreTemporal)&& fecha.isBefore(fechaReanudacionCierreTemporal)) {
						return false;
					}
				}
				
				if (hora < horaApertura || hora > horaCierre) {
					return false;
				}
				
				
				if (fecha.isBefore(LocalDate.now()))
					return false;
				
				if (getNumeroEntradasDisponibles(fecha, hora) < numeroEntradas) {
					return false;
				}
				
				
				Double precioFinal = this.precio;
				for (Sorteo sorteo: this.sorteos) {
					boolean statusTest = sorteo.esUsable(codigoSorteo);
					
							
					if (statusTest == true) {
						precioFinal = 0.0;
						sorteo.setUsado(codigoSorteo);
						
						for (int i = 0; i<numeroEntradas; i++) {
							Entrada entrada = new Entrada(fecha, hora, precioFinal, this, cliente, numTarjeta);
							anadirEntrada(entrada);
							cliente.añadirEntrada(entrada);
							sistema.getContabilidad().sumarBeneficioExposicion(this, precioFinal);
							sistema.getContabilidad().sumarEntradaExposicion(this, 1);
						}
						return true;
					}
				}
				
				for (Descuento descuento: this.descuentos) {
					boolean statusCondicion = descuento.testCondicion(fecha, cliente);
					
					if (statusCondicion == true) {
						Double porcentaje = descuento.getPorcentaje();
						precioFinal = precioFinal * (porcentaje / 100);
					}
				}
				
				if (TeleChargeAndPaySystem.isValidCardNumber(numTarjeta)) {
					try {
						TeleChargeAndPaySystem.charge(numTarjeta, "Compra de entradas", precioFinal*numeroEntradas*(-1), true);
					} catch (OrderRejectedException e) {
						e.printStackTrace();
						return false;
					}
					for (int i = 0; i<numeroEntradas; i++) {
						Entrada entrada = new Entrada(fecha, hora, precioFinal, this, cliente, numTarjeta);
						anadirEntrada(entrada);
						cliente.añadirEntrada(entrada);
						sistema.getContabilidad().sumarBeneficioExposicion(this, precioFinal);
						sistema.getContabilidad().sumarEntradaExposicion(this, 1);

					}
					return true;
					}
				else {
					System.err.println("Numero de tarjeta introducido no valido");
					return false;
				}
			}
	/**
	 * @brief Cambia el estado de la Exposición a dada de alta 
	 * 
	 * @param fechaInicio (LocalDate)
	 * 
	 * @return true si se ha podido cambiar de estado, false si no se ha podido cambiar 
	 */	
	public boolean darDeAlta(LocalDate fechaInicio) {
		if (this.estado == EstadoExposicion.BORRADOR) {
			this.estado = EstadoExposicion.DADA_ALTA;
			this.fechaInicio = fechaInicio;	
			return true;
		}
		
		return false;
	}
	/**
	 * @brief Verifica que la obra sea temporal
	 * @return false
	 */
	@Override
	public boolean esTemporal() {
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
	@Override
	public boolean cerrarTemporalmente(LocalDate fechaInicioCierre, LocalDate fechaReanudacion) {
		if(this.estado!=EstadoExposicion.INICIADA && this.estado!=EstadoExposicion.PRORROGADA) {
			return false;
			
		}
		if (fechaReanudacion.isBefore(fechaInicioCierre)||fechaReanudacion.isBefore(LocalDate.now())) {
			return false;
		}
		for (LocalDate fecha = fechaInicioCierre; fecha.isBefore(fechaReanudacion.plusDays(1)); fecha = fecha.plusDays(1)) {
			for(int i=Sistema.getInstance().getHorarioAperturaExposicion(); i<Sistema.getInstance().getHorarioCierreExposicion();i++) {
				if(obtenerNumeroEntradasVendidas(fecha, i)!=0) {
					return false;
				}
			}
		}
		this.fechaInicioCierreTemporal = fechaInicioCierre;
		this.fechaReanudacionCierreTemporal = fechaReanudacion;
		this.estado=EstadoExposicion.CERRADA_TEMPORALMENTE;
		return true;
		
	}

	
}
