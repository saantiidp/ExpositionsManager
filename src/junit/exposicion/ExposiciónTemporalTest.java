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
import exposicion.ExposicionTemporal;
import salas.SalaHoja;
import sistema.Sistema;
import sorteos.UsoDiaHora;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;

public class ExposiciónTemporalTest {
	
	private ExposicionTemporal exposiciónTemporal;
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
		exposiciónTemporal = new ExposicionTemporal("ExposiciónPermanteTest", "descripcionTest", 7.54);
		clienteRegistrado = new ClienteRegistrado(false, "345435554", "2488");
        empleado = new Empleado("343454", "Juan", "123456", "43432443", "calle marques de vizcaya", true, false,true);
        sistema = Sistema.getInstance();
        sistema.configurarParametros(9,18, 4);
        
        sistema.getContabilidad().anyadirExposicion(exposiciónTemporal);
        
		sala1 = new SalaHoja("sala 1", 20, 4, 50, 50, 50, 15, 5, false, false);
	    sala2 = new SalaHoja("sala 2", 20, 4, 50, 50, 50, 15, 5, false, false);
	    
	    
	    exposiciónTemporal.anadirSalaExposición(sala1);
	    exposiciónTemporal.anadirSalaExposición(sala2);
	    
	    descuento1 = new DiasAntelación(20.0, 2);
	    descuento2 = new DiasAntelación(20.0, 2);
	    
	    sorteo = new UsoDiaHora(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5),5,  false, exposiciónTemporal,  LocalDate.now().plusDays(5), LocalTime.parse("12:00"));
	    
	    
	}
	
	@Test
	public void testdarDeAlta() {
		assertTrue(exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20)));
		assertFalse(exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4),LocalDate.now().plusDays(20)));
		assertEquals(EstadoExposicion.DADA_ALTA, exposiciónTemporal.getEstado());

	      // Intentar comprar entrada 
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
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
		assertFalse(exposiciónTemporal.iniciada());
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		assertTrue(exposiciónTemporal.iniciada());
		assertFalse(exposiciónTemporal.iniciada());
		assertEquals(EstadoExposicion.INICIADA, exposiciónTemporal.getEstado());
		
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		
		// Intentar comprar entrada 
        try {
			assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
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
		assertFalse(exposiciónTemporal.cerrarTemporalmente(LocalDate.now(), LocalDate.now().plusDays(1)));
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		assertFalse(exposiciónTemporal.cerrarTemporalmente(LocalDate.now(), LocalDate.now().plusDays(1)));
		exposiciónTemporal.iniciada();
		assertTrue(exposiciónTemporal.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
		assertFalse(exposiciónTemporal.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
		
		assertEquals(EstadoExposicion.CERRADA_TEMPORALMENTE, exposiciónTemporal.getEstado());
		
		// Intentar comprar entrada antes de la fecha de cierre temporal
		
        try {
			assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now(),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		// Intentar comprar entrada entre los dias que va a estar cerrada temporalmente
        try {
			assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
        
     // Intentar comprar entrada después de la fecha de reanudación
        try {
			assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(4),11,1,"" ));
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
		assertFalse(exposiciónTemporal.reanudar());
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4),LocalDate.now().plusDays(20));
		assertFalse(exposiciónTemporal.reanudar());
		exposiciónTemporal.iniciada();
		assertFalse(exposiciónTemporal.reanudar());
		exposiciónTemporal.cerrarTemporalmente(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
		assertFalse(exposiciónTemporal.reanudar());
		exposiciónTemporal.setFechaInicioCierreTemporal(LocalDate.now().minusDays(3));
		exposiciónTemporal.setFechaReanudacionCierreTemporal(LocalDate.now().minusDays(1));
		assertTrue(exposiciónTemporal.reanudar());
		assertEquals(EstadoExposicion.INICIADA, exposiciónTemporal.getEstado());
	}
	
	@Test
	public void testCancelar() {
		try {
			assertFalse(exposiciónTemporal.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4),LocalDate.now().plusDays(20));
		try {
			assertFalse(exposiciónTemporal.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		exposiciónTemporal.iniciada();
		
		// Intentar comprar 1 entrada correctamente
        try {
			exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(9),11,1,"" );
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
        
		// Intentar comprar 3 entradas correctamente
        try {
			exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(11),11,3,"" );
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		
		try {
			assertFalse(exposiciónTemporal.cancelarExposicion(LocalDate.now().plusDays(6)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(exposiciónTemporal.cancelarExposicion(LocalDate.now().plusDays(7)));
		} catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		assertEquals(EstadoExposicion.CANCELADA, exposiciónTemporal.getEstado());
		
		
		
		
	}
	
	@Test
	public void testFinalizar() {
		assertFalse(exposiciónTemporal.finalizar());
		exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4),LocalDate.now().minusDays(1));
		assertFalse(exposiciónTemporal.finalizar());
		exposiciónTemporal.iniciada();
		assertTrue(exposiciónTemporal.finalizar());
		assertFalse(exposiciónTemporal.finalizar());
		assertEquals(EstadoExposicion.FINALIZADA, exposiciónTemporal.getEstado());
	}
	
	 @Test
	 public void testProrrogar() {
		 assertFalse(exposiciónTemporal.prorrogar(LocalDate.now().plusDays(40)));
			exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4),LocalDate.now().plusDays(20));
			assertFalse(exposiciónTemporal.prorrogar(LocalDate.now().plusDays(40)));
			exposiciónTemporal.iniciada();
			assertTrue(exposiciónTemporal.prorrogar(LocalDate.now().plusDays(40)));
			assertEquals(EstadoExposicion.PRORROGADA, exposiciónTemporal.getEstado());
			
			
	 }
	
	@Test
	public void testEsTemporal() {
		assertEquals(exposiciónTemporal.esTemporal(), true);
		
	}
	
	 @Test
	 public void testComprarEntradaTaquilla() {
		 exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		 exposiciónTemporal.iniciada();
	        // Establecer el estado de la exposición
	        exposiciónTemporal.setEstado(EstadoExposicion.INICIADA);

	        // Intentar comprar entrada fuera del horario de apertura
	        assertFalse(exposiciónTemporal.comprarEntradaTaquilla(empleado, 8));

	        // Intentar comprar entrada fuera del horario de cierre
	        assertFalse(exposiciónTemporal.comprarEntradaTaquilla(empleado, 19));

	        // Establecer horario de compra dentro del horario de apertura y cierre
	        assertTrue(exposiciónTemporal.comprarEntradaTaquilla(empleado, 12));

	        // Verificar que se haya añadido la entrada
	        assertEquals(1, exposiciónTemporal.getListasEntradas().size());
	    }
	 
	 
	 @Test
	 public void testComprarEntrada() {
		 exposiciónTemporal.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		 exposiciónTemporal.iniciada();

	        // Establecer fechas de cierre temporal
	        LocalDate fechaInicioCierreTemporal = LocalDate.now().plusDays(8);
	        LocalDate fechaReanudacionCierreTemporal = LocalDate.now().plusDays(10);

	        exposiciónTemporal.cerrarTemporalmente(fechaInicioCierreTemporal, fechaReanudacionCierreTemporal);
	         
	        // Intentar comprar entrada fuera del horario de apertura
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),8,1,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}

	        // Intentar comprar entrada fuera del horario de cierre
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),19,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        // Intentar comprar entrada dentro de los dias de cierre temporal
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(9),11,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        
	     // Intentar comprar entrada en el pasado
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741",  LocalDate.now().minusDays(2),11,1,"" ));
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        
	        // Intentar comprar 1 entrada correctamente
	        try {
				assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,1,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se ha comprado una entrada
	        assertEquals(1, exposiciónTemporal.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(2), 11));
	        
	        // Intentar comprar 4 entradas correctamente
	        try {
				assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),12,4,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 4 entradas
	        assertEquals(4, exposiciónTemporal.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(2), 12));
	        
	        // Intentar comprar mas entradas de las posibles
	        try {
				assertFalse(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(2),11,40,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}

	       exposiciónTemporal.anadirDescuento(descuento1);
	       // Intentar comprar dos entradas con descuento
	        try {
				assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,2,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 2 entradas
	        assertEquals(2, exposiciónTemporal.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(6), 11));
		    
		    exposiciónTemporal.anadirDescuento(descuento2);
		    // Intentar comprar dos entradas con 2 descuentos
		    try {
				assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,2,"" ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
	        //Verificamos que efectivamente se han comprado 2 entradas
	        assertEquals(4, exposiciónTemporal.obtenerNumeroEntradasVendidas(LocalDate.now().plusDays(6), 11));
		    
		    
	        //Intentar comprar gratis porque te ha tocado el sorteo
		    
		    exposiciónTemporal.anadirSorteo(sorteo);
		    clienteRegistrado.participarSorteo(sorteo, 5);
		    sorteo.sortear();
			String codigo = clienteRegistrado.getCodigoSorteo();
		    try {
				assertTrue(exposiciónTemporal.comprarEntradas(clienteRegistrado, "1254056761678741", LocalDate.now().plusDays(6),11,6,codigo ));
			} catch (InvalidCardNumberException e) {
				e.printStackTrace();
			} catch (FailedInternetConnectionException e) {
				e.printStackTrace();
			} catch (OrderRejectedException e) {
				e.printStackTrace();
			}
		    
	        
	 }
	 
	
	


}
