package junit.sala;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import salas.SalaHoja;
import salas.Subsala;

public class SalaTest {

	@Test
	void testEsHoja_1() {
		SalaHoja sala = new SalaHoja("Sala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true);
		assertTrue(sala.esHoja());
	}
	@Test 
	void testEsHoja_2() {
		Subsala sala = new Subsala("Sala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true);
		assertFalse(sala.esHoja());
	}

	@Test
	void testEstablecerTemperatura() {
		Subsala sala = new Subsala("Sala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true);
		sala.establecerTemperatura(30.0);
		assertEquals(30.0, sala.getTemperatura());
	}

	@Test
	void testEstablecerHumedad() {
		Subsala sala = new Subsala("Sala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true);
		sala.establecerHumedad(60.0);
		assertEquals(60.0, sala.getHumedad());
	}

}
