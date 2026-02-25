package junit.exposicion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import exposicion.SalaExposicion;
import obra.Audiovisual;
import obra.Obra;
import obra.Seguro;
import salas.SalaHoja;


public class SalaExposiciónTest {
	private static SalaExposiciónFake salaExposiciónFake;
	
	/**
	 * Clase dummy que representa una instancia de SalaExposición
	 */
	public class SalaExposiciónFake extends SalaExposicion {
		private static final long serialVersionUID = 11221L;

		public SalaExposiciónFake(SalaHoja sala) {
			super(sala);
		}
	}
	
	@Before
	public void setUp() {
		 SalaHoja salaHoja = new SalaHoja("sala 1", 10, 3, 20.0, 20.0, 20.0, 15.0, 3.5, false, false );
		 Obra obra1 = new Audiovisual("titulo", "Santi", false, LocalDate.of(1900, 8, 23), "cuadro", new Seguro(12, "1234"), LocalTime.of(0, 0, 40), "Español");
		 salaExposiciónFake = new SalaExposiciónFake(salaHoja);
		 salaExposiciónFake.anadirObra(obra1);
	}

	@Test
	public void testEliminarObra() {
	    // Crear una obra para eliminar
	    Obra obraEliminar = new Audiovisual("titulo", "Juan", false, LocalDate.of(2000, 1, 1), "pintura", new Seguro(123, "abcd"), LocalTime.of(0, 0, 50), "Inglés");

	    // Añadir la obra a la sala de exposición
	    salaExposiciónFake.anadirObra(obraEliminar);


	    // Eliminar la obra
	    boolean eliminado = salaExposiciónFake.eliminarObra(obraEliminar);

	    // Verificar que la obra fue eliminada correctamente
	    assertTrue(eliminado);
	    assertEquals(1, salaExposiciónFake.obtenerObras().size(), "El número de obras no es correcto después de eliminar");
	}


}
