package junit.filtro;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;


import org.junit.*;

import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import exposicion.SalaExposicion;
import filtro.FiltroTipoObra;
import obra.*;
import salas.SalaHoja;



public class FiltroTipoObraTest {

	private static FakeFiltroTipoObra filtroTipoObra;
	private static String tipoObra;
	/**
	 * Clase dummy que representa una instancia de Exposición con una hora y dia específico totalmente llena
	 */
     class FakeFiltroTipoObra extends FiltroTipoObra{
    	 private static final long serialVersionUID = 11221L;
    	 public FakeFiltroTipoObra(String tipoObra) {
    		 super(tipoObra);
			
		}
     }
     
     @Before
     public void setup() {
  		FiltroTipoObraTest.tipoObra = "Foto";
  		FiltroTipoObraTest.filtroTipoObra = new FakeFiltroTipoObra(tipoObra);
      }
     
     @Test
     public void testCumpleFiltro1() {
    	 Exposicion exposición = new ExposicionPermanente("Expofake", "Exposición para test 1", 4);
     	 exposición.setFechaInicio(null);
     	Seguro seguro = new Seguro(12, "1234");
     	 Obra obra1 = new Foto("titulo", "Santiago", false, LocalDate.now(), "foto test", seguro, 4,
     			6, 2, 5, 4, 4, 4, false);
     	 
     	 Obra obra2 = new Cuadro("titulo", "Santiago", false, LocalDate.now(), "cuadro test", seguro, 4,
      			6, 2, 5, 4, 4, 4,  "lienzo sobre óleo");
     	 
     	SalaHoja sala1 = new SalaHoja("sala 1", 10, 3 , 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
     	exposición.anadirSalaExposición(sala1);
     	SalaExposicion salaExposicion1 = exposición.getSalasExposición().get(0L);
     	salaExposicion1.anadirObra(obra1);
     	salaExposicion1.anadirObra(obra2);
     	exposición.anadirSalaExposición(sala1);
     	// Act
        boolean resultado = filtroTipoObra.cumpleFiltro(exposición);
        // Assert
        assertTrue(resultado);
     }
     
     
     @Test
     public void testCumpleFiltro2() {
    	 Exposicion exposición2 = new ExposicionPermanente("Expofake", "Exposición para test 1", 4);
     	 exposición2.setFechaInicio(null);
     	Seguro seguro = new Seguro(12, "1234");
     	 
     	 
     	 Obra obra2 = new Cuadro("titulo", "Santiago", false, LocalDate.now(), "cuadro test", seguro, 4,
      			6, 2, 5, 4, 4, 4,  "lienzo sobre óleo");
     	 
     	SalaHoja sala1 = new SalaHoja("sala 1", 10, 3 , 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
     	exposición2.anadirSalaExposición(sala1);
     	SalaExposicion salaExposicion2 = exposición2.getSalasExposición().get(0L);
     	salaExposicion2.anadirObra(obra2);
     
     	// Act
        boolean resultado = filtroTipoObra.cumpleFiltro(exposición2);
        // Assert
        assertFalse(resultado);
     }

}
