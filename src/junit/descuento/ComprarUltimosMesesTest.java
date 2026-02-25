package junit.descuento;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.*;

import descuento.ComprarUltimosMeses;
import entrada.Entrada;
import exposicion.ExposicionPermanente;
import salas.SalaHoja;
import sistema.Sistema;
import usuarios.ClienteRegistrado;

public class ComprarUltimosMesesTest {
	private static ComprarUltimosMesesFake comprarUltimosMeses;
	private static Double porcentaje;
	private static Integer nMeses;
	
	private ExposicionPermanente exposiciónPermanente;
	private ClienteRegistrado clienteRegistrado1;
	private ClienteRegistrado clienteRegistrado2;
	private Sistema sistema;
	private SalaHoja sala1;
	private SalaHoja sala2;
	private Entrada entrada1;
	private Entrada entrada2;
	private Entrada entrada3;
	private Entrada entrada4;
	
	/**
	 * Clase dummy que representa una instancia de ComprarUltimosMeses con un porcentaje y  nMeses específico
	 */
    class ComprarUltimosMesesFake extends ComprarUltimosMeses{

		private static final long serialVersionUID = -3353837783235260748L;

		public ComprarUltimosMesesFake(Double porcentaje, Integer nMeses) {
			super(porcentaje, nMeses);
		}
    	
    }
    
    @Before
    public void setup() {
    	ComprarUltimosMesesTest.nMeses = 4;
    	ComprarUltimosMesesTest.porcentaje = 20.0;
    	ComprarUltimosMesesTest.comprarUltimosMeses = new ComprarUltimosMesesFake(porcentaje, nMeses);
    	
    	
    	exposiciónPermanente = new ExposicionPermanente("ExposiciónPermanteTest", "descripcionTest", 7.54);
		clienteRegistrado1 = new ClienteRegistrado(false, "345435554", "2488");
		clienteRegistrado2 = new ClienteRegistrado(false, "345635554", "2498");
        sistema = Sistema.getInstance();
        sistema.configurarParametros(9,18, 4);
        
		sala1 = new SalaHoja("sala 1", 20, 4, 50, 50, 50, 15, 5, false, false);
	    sala2 = new SalaHoja("sala 2", 20, 4, 50, 50, 50, 15, 5, false, false);
	    
	    exposiciónPermanente.anadirSalaExposición(sala1);
	    exposiciónPermanente.anadirSalaExposición(sala2);
	    
	    entrada1 = new Entrada(LocalDate.now().minusDays(4), 11, 20, exposiciónPermanente, clienteRegistrado1,"1254056761678741" );
	    entrada2 = new Entrada(LocalDate.now().minusDays(24), 11, 20, exposiciónPermanente, clienteRegistrado1,"1254056767678741" );
	    
	    exposiciónPermanente.anadirEntrada(entrada1);
	    exposiciónPermanente.anadirEntrada(entrada2);
	    
	    clienteRegistrado1.añadirEntrada(entrada1);
	    clienteRegistrado1.añadirEntrada(entrada2);
	    
	    entrada3 = new Entrada(LocalDate.now().minusMonths(6), 11, 20, exposiciónPermanente, clienteRegistrado1,"1254056761678741" );
	    entrada4 = new Entrada(LocalDate.now().minusMonths(6), 11, 20, exposiciónPermanente, clienteRegistrado1,"1254056767678741" );
	    
	    exposiciónPermanente.anadirDescuento(comprarUltimosMeses);
	    
	    exposiciónPermanente.anadirEntrada(entrada3);
	    exposiciónPermanente.anadirEntrada(entrada4);
	    
	    clienteRegistrado2.añadirEntrada(entrada3);
	    clienteRegistrado2.añadirEntrada(entrada4);
	    
	    
    }

    
	@Test
	public void testTestCondicion(){
		assertTrue(comprarUltimosMeses.testCondicion(null, clienteRegistrado1));
		assertFalse(comprarUltimosMeses.testCondicion(null, clienteRegistrado2));
	}

}
