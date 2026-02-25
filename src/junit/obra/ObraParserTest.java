package junit.obra;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import obra.Audiovisual;
import obra.Cuadro;
import obra.Escultura;
import obra.Foto;
import obra.Obra;
import obra.ObraDimensionada;
import obra.ObraParser;
import obra.Seguro;
import sistema.Sistema;

import static org.junit.Assert.assertTrue;

public class ObraParserTest {
	private Sistema sis = null;
	
	@Before
	public void setUp() {
		sis = Sistema.getInstance();
	}
	
	@After
	public void tearDown() {
		sis.reset();
	}
	
	@Test
	public void testGuardarObras() {
		//TO DO cambiar esto por crear obras del sistema
		Obra o = new Cuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		Obra o1 = new Audiovisual("audiovi", "yo", false, LocalDate.now(), "desc", new Seguro(11, "456"), LocalTime.now(), "Frances");
		ObraDimensionada o2 = new Foto("titulo", "autor", true, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, true);
		ObraDimensionada o3 = new Escultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "material");
		
		this.sis.darAltaCuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, "tecnica");
		this.sis.darAltaAudiovisual("audiovi", "yo", false, LocalDate.now(), "desc", 11, "456", LocalTime.now(), "Frances");
		this.sis.darAltaFoto("titulo", "autor", true, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, true);
		this.sis.darAltaEscultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, "material");
		
		ObraParser.guardarObras("./resources/obras.txt");
		List<Obra> obras;
		try {
			FileReader reader = new FileReader("./resources/obras.txt");
			obras = ObraParser.getObras(reader);
			assertTrue(obras.contains(o));
			assertTrue(obras.contains(o1));
			assertTrue(obras.contains(o2));
			assertTrue(obras.contains(o3));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			Path path = Paths.get("./resources/obras.txt");

	        try {
	            Files.delete(path);
	        } 
	        catch (NoSuchFileException ex) {
	            System.err.printf("No existe el fichero: %s\n", path);
	        } 
	        catch (IOException ex) {
	            System.err.println(ex);
	        }
		}
	}
}
