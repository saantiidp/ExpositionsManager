package junit.descuento;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.*;

import descuento.DiasAntelación;

import usuarios.ClienteRegistrado;

public class DiasAntelacionTest {

	private static DiasAntelacionFake diasAntelacion;
	private static Double porcentaje;
	private static Integer nDias;

	private ClienteRegistrado clienteRegistrado1;
	

	

	/**
	 * Clase dummy que representa una instancia de diasAntelacion con un porcentaje y  nMeses específico
	 */
	class DiasAntelacionFake extends DiasAntelación{

		private static final long serialVersionUID = -1969918653471106080L;

		public DiasAntelacionFake(Double porcentaje, Integer diasAntelación) {
			super(porcentaje, diasAntelación);
		}
		
	}
	
	@Before
    public void setup() {
		DiasAntelacionTest.nDias = 4;
    	DiasAntelacionTest.porcentaje = 20.0;
    	DiasAntelacionTest.diasAntelacion= new DiasAntelacionFake(porcentaje, nDias);

		clienteRegistrado1 = new ClienteRegistrado(false, "345435554", "2488");
	}
	
	   
		@Test
		public void testTestCondicion(){
			assertFalse(diasAntelacion.testCondicion(LocalDate.now().minusDays(4), clienteRegistrado1));
			assertTrue(diasAntelacion.testCondicion(LocalDate.now().minusDays(5), clienteRegistrado1));
		}

}
