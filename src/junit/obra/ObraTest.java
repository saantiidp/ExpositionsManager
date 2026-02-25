package junit.obra;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import obra.Obra;
import obra.Seguro;

import org.junit.*;

import enums.EstadoObra;

public class ObraTest {
	private static FakeObra obra; 
	private static LocalDate date;
	/**
	 * Clase dummy que representa una instancia de la Obra
	 */
	class FakeObra extends Obra {
		private static final long serialVersionUID = 11221L;

		public FakeObra(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, Seguro seguro) {
			super(titulo, autorObra, obraCentro, anyo, descripcion, seguro);
		}	
	}
	
	@Before
	public void setUp() {
		Seguro seguro = new Seguro(12, "1234");
		date = LocalDate.of(2024, 1, 19);
		obra = new FakeObra("titulo", "autorTest", false, date, "descripcion test", seguro);
	}
	
	@Test
	public void testAlmacenar () {
		obra.exponer();
		obra.almacenar();
		
		assertEquals(EstadoObra.ALMACEN, obra.getEstadoObra());
	}
	
	@Test
	public void testExponer () {
		obra.exponer();
		assertEquals(EstadoObra.EXPUESTA, obra.getEstadoObra());
	}
	
	@Test
	public void testPrestar () {
		obra.prestar();
		assertEquals(EstadoObra.PRESTADA, obra.getEstadoObra());
	}
	
	@Test
	public void testRestaurar () {
		obra.restaurar();
		assertEquals(EstadoObra.RESTAURACION, obra.getEstadoObra());
	}
	
	@Test
	public void testRetirar () {
		obra.retirar();
		assertEquals(EstadoObra.RETIRADA, obra.getEstadoObra());
	}
	
	@Test
	public void testEsTipoObra() {
		assertTrue(obra.esTipoObra("fakeobra"));
	}
	
	@Test
	public void testEquals() {
		FakeObra o1 = new FakeObra("titulo", "autor", false, LocalDate.of(1, 1, 1), "descripcion", new Seguro(10, "123"));
		FakeObra o2 = new FakeObra("titulo", "autor", false, LocalDate.of(1, 1, 1), "descripcion", new Seguro(10, "123"));
		FakeObra o3 = new FakeObra("titulo", "dd", false, LocalDate.of(1, 1, 1), "descripcion", new Seguro(10, "123"));
		FakeObra o4 = new FakeObra("titulo", "autor", true, LocalDate.of(1, 1, 1), "descripcion", new Seguro(10, "123"));
		FakeObra o5 = new FakeObra("titulo", "autor", false, LocalDate.of(2, 1, 1), "descripcion", new Seguro(10, "123"));
		FakeObra o6 = new FakeObra("titulo", "autor", false, LocalDate.of(1, 1, 1), "de", new Seguro(10, "123"));
		FakeObra o7 = new FakeObra("titulo", "autor", false, LocalDate.of(1, 1, 1), "descripcion", new Seguro(11, "123"));
		FakeObra o8 = new FakeObra("titugyguulo", "autor", false, LocalDate.of(1, 1, 1), "descripcion", new Seguro(10, "123"));
		
		assertTrue(o1.equals(o1));
		assertTrue(o1.equals(o2));
		assertFalse(o1.equals(null));
		assertFalse(o1.equals("algo"));
		assertFalse(o1.equals(o3));
		assertFalse(o1.equals(o4));
		assertFalse(o1.equals(o5));
		assertFalse(o1.equals(o6));
		assertFalse(o1.equals(o7));
		assertFalse(o1.equals(o8));
	}
}
