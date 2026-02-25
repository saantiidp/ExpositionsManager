package junit.exposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import descuento.DiasAntelación;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import exposicion.EstadoExposicion;
import exposicion.ExposicionPermanente;
import salas.SalaHoja;
import sistema.Sistema;
import sorteos.UsoDiaHora;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;



public class ExposiciónPermanenteTest {
	private ExposicionPermanente exposiciónPermanente;
	private ClienteRegistrado clienteRegistrado;
	private Empleado empleado;
	private Sistema sistema;
	private SalaHoja sala1;
	private SalaHoja sala2;
	private DiasAntelación descuento1;
	private DiasAntelación descuento2;
	private UsoDiaHora sorteo;
	
	@Before
	public void setUp() {
		exposiciónPermanente = new ExposicionPermanente("ExposiciónPermanteTest", "descripcionTest", 7.54);
		clienteRegistrado = new ClienteRegistrado(false, "345435554", "2488");
        empleado = new Empleado("343454", "Juan", "123456", "43432443", "calle marques de vizcaya", true, false,true);
        sistema = Sistema.getInstance();
        sistema.configurarParametros(9,18, 4);
        
        sistema.getContabilidad().anyadirExposicion(exposiciónPermanente);
        
        
		sala1 = new SalaHoja("sala 1", 20, 4, 50, 50, 50, 15, 5, false, false);
	    sala2 = new SalaHoja("sala 2", 20, 4, 50, 50, 50, 15, 5, false, false);
	    
	    exposiciónPermanente.anadirSalaExposición(sala1);
	    exposiciónPermanente.anadirSalaExposición(sala2);
	    
	    descuento1 = new DiasAntelación(20.0, 2);
	    descuento2 = new DiasAntelación(20.0, 2);
	    
	    sorteo = new UsoDiaHora(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5),5,  false, exposiciónPermanente,  LocalDate.now().plusDays(5), LocalTime.parse("12:00"));
	    
	}
	
	@Test
	public void testdarDeAlta() {
		assertTrue(exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4)));
		assertFalse(exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4)));
		assertEquals(EstadoExposicion.DADA_ALTA, exposiciónPermanente.getEstado());

	      // Intentar comprar entrada 
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	      
	      
	}
	
	@Test
	public void testIniciar() {
		assertFalse(exposiciónPermanente.iniciada());
		exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4));
		assertTrue(exposiciónPermanente.iniciada());
		assertFalse(exposiciónPermanente.iniciada());
		assertEquals(EstadoExposicion.INICIADA, exposiciónPermanente.getEstado());
		
		assertFalse(exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4)));
		
		// Intentar comprar entrada 
        try {
			assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCerrarTemporalmente() {
		assertFalse(exposiciónPermanente.cerrarTemporalmente(LocalDate.now(), LocalDate.now().plusDays(1)));
		exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4));
		assertFalse(exposiciónPermanente.cerrarTemporalmente(LocalDate.now(), LocalDate.now().plusDays(1)));
		exposiciónPermanente.iniciada();
		assertTrue(exposiciónPermanente.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
		assertFalse(exposiciónPermanente.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
		
		assertEquals(EstadoExposicion.CERRADA_TEMPORALMENTE, exposiciónPermanente.getEstado());
		
		// Intentar comprar entrada antes de la fecha de cierre temporal
		
        try {
			assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now(),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		// Intentar comprar entrada entre los dias que va a estar cerrada temporalmente
        try {
			assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
        
     // Intentar comprar entrada después de la fecha de reanudación
        try {
			assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(4),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testReanudar() {
		assertFalse(exposiciónPermanente.reanudar());
		exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4));
		assertFalse(exposiciónPermanente.reanudar());
		exposiciónPermanente.iniciada();
		assertFalse(exposiciónPermanente.reanudar());
		exposiciónPermanente.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
		assertFalse(exposiciónPermanente.reanudar());
		exposiciónPermanente.setFechaInicioCierreTemporal(LocalDate.now().minusDays(3));
		exposiciónPermanente.setFechaReanudacionCierreTemporal(LocalDate.now().minusDays(1));
		assertTrue(exposiciónPermanente.reanudar());
		assertEquals(EstadoExposicion.INICIADA, exposiciónPermanente.getEstado());
	}
	
	
	@Test
	public void testCancelar() {
		try {
			assertFalse(exposiciónPermanente.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		exposiciónPermanente.darDeAlta(LocalDate.now().minusDays(4));
		try {
			assertFalse(exposiciónPermanente.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		exposiciónPermanente.iniciada();
		
		// Intentar comprar 1 entrada correctamente
        try {
			exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(9),11,1,"" );
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
        
		// Intentar comprar 3 entradas correctamente
        try {
			exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(11),11,3,"" );
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		try {
			assertFalse(exposiciónPermanente.cancelarExposicion(LocalDate.now().plusDays(6)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(exposiciónPermanente.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		assertEquals(EstadoExposicion.CANCELADA, exposiciónPermanente.getEstado());
		
		
		
		
	}
	
	
	@Test
	public void testEsTemporal() {
		assertEquals(exposiciónPermanente.esTemporal(), false);
		
	}
	
	 @Test
	 public void testComprarEntradaTaquilla() {

	        // Establecer el estado de la exposición
	        exposiciónPermanente.setEstado(EstadoExposicion.INICIADA);

	        // Intentar comprar entrada fuera del horario de apertura
	        assertFalse(exposiciónPermanente.comprarEntradaTaquilla(empleado, 8));

	        // Intentar comprar entrada fuera del horario de cierre
	        assertFalse(exposiciónPermanente.comprarEntradaTaquilla(empleado, 19));

	        // Establecer horario de compra dentro del horario de apertura y cierre
	        assertTrue(exposiciónPermanente.comprarEntradaTaquilla(empleado, 12));

	        // Verificar que se haya añadido la entrada
	        assertEquals(1, exposiciónPermanente.getListasEntradas().size());
	    }
	 
	 @Test
	 public void testComprarEntrada() {
	        
	        // Establecer el estado de la exposición
	        exposiciónPermanente.setEstado(EstadoExposicion.INICIADA);

	        // Establecer fechas de cierre temporal
	        LocalDate fechaInicioCierreTemporal = LocalDate.now().plusDays(8);
	        LocalDate fechaReanudacionCierreTemporal = LocalDate.now().plusDays(10);

	        exposiciónPermanente.cerrarTemporalmente(fechaInicioCierreTemporal, fechaReanudacionCierreTemporal);
	         
	        // Intentar comprar entrada fuera del horario de apertura
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),8,1,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}

	        // Intentar comprar entrada fuera del horario de cierre
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),19,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        // Intentar comprar entrada dentro de los dias de cierre temporal
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(9),11,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        
	     // Intentar comprar entrada en el pasado
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741",  LocalDate.now().minusDays(2),11,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        
	        // Intentar comprar 1 entrada correctamente
	        try {
				assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se ha comprado una entrada
	        assertEquals(1, exposiciónPermanente.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(2), 11));
	        
	        // Intentar comprar 4 entradas correctamente
	        try {
				assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),12,4,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 4 entradas
	        assertEquals(4, exposiciónPermanente.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(2), 12));
	        
	        // Intentar comprar mas entradas de las posibles
	        try {
				assertFalse(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,40,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}

	       exposiciónPermanente.anadirDescuento(descuento1);
	       // Intentar comprar dos entradas con descuento
	        try {
				assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,2,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 2 entradas
	        assertEquals(2, exposiciónPermanente.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(6), 11));
		    
		    exposiciónPermanente.anadirDescuento(descuento2);
		    // Intentar comprar dos entradas con 2 descuentos
		    try {
				assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,2,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 2 entradas
	        assertEquals(4, exposiciónPermanente.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(6), 11));
		    
		    //Intentar comprar gratis porque te ha tocado el sorteo
		    
		    exposiciónPermanente.anadirSorteo(sorteo);
		    clienteRegistrado.participarSorteo(sorteo, 5);
		    sorteo.sortear();
			String codigo = clienteRegistrado.getCodigoSorteo();
		    try {
				assertTrue(exposiciónPermanente.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,6,codigo ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
		    
	        
	 }

}
