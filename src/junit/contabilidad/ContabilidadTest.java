package junit.contabilidad;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import sistema.ContabilidadSistema;

public class ContabilidadTest {
	private Exposicion e1 = new ExposicionPermanente("nombre", "desc", 10);
	private Exposicion e2 = new ExposicionTemporal("no", "de", 1);
	ContabilidadSistema cont;
	
	@Before
	public void setUp(){
		this.cont = new ContabilidadSistema();
		cont.anyadirExposicion(e1);
		cont.anyadirExposicion(e2);
	}
	
	@Test
	public void testAnyadirExposicion() {
		ContabilidadSistema c = new ContabilidadSistema();
		c.anyadirExposicion(e1);
		
		assertEquals(0.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		assertEquals(0, c.getEntradasPorExposicion(e1, LocalDate.now(), LocalDate.now()));
	}
	
	@Test
	public void testSumarBeneficioExposicion() {
		ContabilidadSistema c = this.cont;
		this.cont.sumarBeneficioExposicion(e1, 10);
		assertEquals(10.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		this.cont.sumarBeneficioExposicion(e1, 10);
		assertEquals(20.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		this.cont.sumarBeneficioExposicion(e1, 1);
		assertEquals(21.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		assertEquals(21.0, c.getBeneficioTotalExposiciones(LocalDate.now(), LocalDate.now()));
	}
	
	@Test
	public void testRestarBeneficioExposicion() {
		ContabilidadSistema c = this.cont;
		this.cont.sumarBeneficioExposicion(e1, 10);
		assertEquals(10.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		this.cont.sumarBeneficioExposicion(e1, 10);
		assertEquals(20.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		
		this.cont.restarBeneficioExposicion(e1, 5);
		assertEquals(15.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		this.cont.restarBeneficioExposicion(e1, 10);
		assertEquals(5.0, c.getBeneficioPorExposicion(e1, LocalDate.now(), LocalDate.now()));
		assertEquals(5.0, c.getBeneficioTotalExposiciones(LocalDate.now(), LocalDate.now()));
	}
	
	@Test
	public void testSumarEntradaExposicion() {
		ContabilidadSistema c = this.cont;
		this.cont.sumarEntradaExposicion(e2, 2);
		this.cont.sumarEntradaExposicion(e2, 5);
		assertEquals(7, c.getEntradasPorExposicion(e2, LocalDate.now(), LocalDate.now()));
		assertEquals(7, this.cont.getEntradasTotalExposiciones(LocalDate.now(), LocalDate.now()));
	}
	
	@Test
	public void testRestarEntradaExposicion() {
		ContabilidadSistema c = this.cont;
		this.cont.sumarEntradaExposicion(e2, 2);
		this.cont.sumarEntradaExposicion(e2, 5);
		this.cont.restarEntradaExposicion(e2, 3);
		assertEquals(4, c.getEntradasPorExposicion(e2, LocalDate.now(), LocalDate.now()));
		assertEquals(4, this.cont.getEntradasTotalExposiciones(LocalDate.now(), LocalDate.now()));
	}
}
