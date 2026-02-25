package junit.entrada;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import entrada.Entrada;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import usuarios.ClienteRegistrado;
import usuarios.Usuario;


public class entradaTest {
	
	private static FakeEntrada entrada;
	private static LocalDate date;
	private static int hora;
	/**
	 * Clase dummy que representa una instancia de la Entrada
	 */
	class FakeEntrada extends Entrada {
		private static final long serialVersionUID = 11221L;
		
		public FakeEntrada(LocalDate fecha, Integer hora, double precio, Exposicion exposición,
				Usuario propietario, String numTarjeta) {
			super(fecha, hora, precio, exposición,  propietario, numTarjeta);
		}
	}
	
	@Before
	public void setUp() {
		Exposicion exposición = new ExposicionPermanente("ExpoTest", "exposición de prueba", 20.4);
		ClienteRegistrado cliente = new ClienteRegistrado(false, "7766788", "2499");
		date = LocalDate.of(2024, 1, 19);
		hora = 10;
		entrada = new FakeEntrada(date, hora, exposición.getPrecio(), exposición, cliente, "1254056761678741");
	}
	@Test
	public void testGenerarPDF() throws NonExistentFileException, UnsupportedImageTypeException {
		// Ejecutar el método que genera el PDF
        assertDoesNotThrow(() -> entrada.generarEntradaFormatoPDF(), "El método generaEntradaFormatoPDF() debería ejecutarse sin lanzar excepciones.");

        // Verificar que el archivo PDF se ha creado en la carpeta de salida
        String rutaPDF = "." + File.separator + "files" + File.separatorChar + "entradas" + File.separator + entrada.getIdTicket()+".pdf"; // Reemplaza con el nombre real del archivo generado
        File archivoPDF = new File(rutaPDF);
        assertTrue(archivoPDF.exists(), "El archivo PDF debería haber sido creado en la carpeta de salida.");

        // Eliminar el archivo PDF después de la prueba para limpiar
        assertDoesNotThrow(() -> Files.delete(Path.of(rutaPDF)), "No se pudo eliminar el archivo PDF después de la prueba.");
	}

}
