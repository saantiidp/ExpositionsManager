package junit.filtro;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.*;


import entrada.Entrada;
import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import filtro.FiltroFecha;
import salas.SalaHoja;
import sistema.Sistema;
import usuarios.ClienteRegistrado;

public class FiltroFechaTest {
	private static FakeFiltroFecha filtroFecha;
	private static LocalDate date;
	private static Integer hora;
	/**
	 * Clase dummy que representa una instancia de Exposición con una hora y dia específico totalmente llena
	 */
     class FakeFiltroFecha extends FiltroFecha{
    	 private static final long serialVersionUID = 11221L;
    	 public FakeFiltroFecha(LocalDate fecha, int hora) {
    		 super(fecha, hora);
			
		}
     }

     @Before
     public void setup() {
 		FiltroFechaTest.date = LocalDate.of(2024, 12, 31);
 		FiltroFechaTest.hora = 10;
 		FiltroFechaTest.filtroFecha = new FakeFiltroFecha(date, hora);
     }


    @Test
    public void testCumpleFiltro_SinEntradasDisponibles_DeberíaRetornarFalse() {
    	Sistema.getInstance().configurarParametros(9, 20, 4);
    	
    	SalaHoja sala1 = new SalaHoja("sala 1", 10, 3 , 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
    	SalaHoja sala2 = new SalaHoja("sala 2", 10, 3, 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
    	SalaHoja sala3 = new SalaHoja("sala 3", 10, 3, 20.0, 20.0, 20.0,  15.0, 3.5, false, false );
    	SalaHoja sala4 = new SalaHoja("sala 4", 10, 3, 20.0, 20.0, 20.0, 15.0, 3.5, false, false );
    	

    	
    	Exposicion exposición = new ExposicionPermanente("Expofake", "Exposición para test 1", 4);
    	exposición.setFechaInicio(null);
    	
    	exposición.anadirSalaExposición(sala1);
    	exposición.anadirSalaExposición(sala2);
    	exposición.anadirSalaExposición(sala3);
    	exposición.anadirSalaExposición(sala4);
    	int aforo = exposición.getAforo();
    	ClienteRegistrado cliente1 = new ClienteRegistrado(false, "4344344", "cliente1");
    	
    	for(int i=0; i<=aforo; i++) {
    		Entrada entrada = new Entrada(LocalDate.of(2024, 12, 31), 10, 4, exposición, cliente1, "65656446");
    		exposición.anadirEntrada(entrada);
    		
    	}

        // Act
        boolean resultado = filtroFecha.cumpleFiltro(exposición);
        // Assert
        assertFalse(resultado);
    }

    @Test
    public void testCumpleFiltro_ConEntradasDisponibles_DeberíaRetornarTrue() {
    	Sistema.getInstance().configurarParametros(9, 20, 4);
    	
    	SalaHoja sala1 = new SalaHoja("sala 1", 10, 3 , 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
    	SalaHoja sala2 = new SalaHoja("sala 2", 10, 3, 20.0, 20.0, 20.0, 15.0, 3.5, false, false);
    	SalaHoja sala3 = new SalaHoja("sala 3", 10, 3, 20.0, 20.0, 20.0,  15.0, 3.5, false, false );
    	SalaHoja sala4 = new SalaHoja("sala 4", 10, 3, 20.0, 20.0, 20.0, 15.0, 3.5, false, false );
    	
    	
    	Exposicion exposición = new ExposicionPermanente("Expofake", "Exposición para test 1", 4);
    	exposición.setFechaInicio(null);
    	
    	exposición.anadirSalaExposición(sala1);	
    	exposición.anadirSalaExposición(sala2);
    	exposición.anadirSalaExposición(sala3);
    	exposición.anadirSalaExposición(sala4);
    	int aforo = exposición.getAforo();
    	ClienteRegistrado cliente1 = new ClienteRegistrado(false, "4344344", "cliente1");
    	
    	for(int i=0; i<=aforo/2; i++) {
    		Entrada entrada = new Entrada(LocalDate.of(2024, 12, 31), 10, 4, exposición, cliente1,"65656446" );
    		exposición.anadirEntrada(entrada);
    		
    	}

        // Act
        boolean resultado = filtroFecha.cumpleFiltro(exposición);

        // Assert
        assertTrue(resultado);
    }
}

