package junit.sala;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import salas.Subsala;

class SubsalaTest {

	@Test
	void testCrearSubsalas() {
		Subsala subsala = new Subsala("Subsala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true); 
		assertTrue(subsala.crearSubsalas(2, 20.0, 2, true, true, true));
	}

	@Test
	void testCrearSubsalasFallido() {
		Subsala subsala = new Subsala("Subsala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true); 
		assertFalse(subsala.crearSubsalas(2, 10.0, 20 , true, true, false));
	}
	@Test
	void testEliminarSubsalas() {
		Subsala subsala = new Subsala("Subsala 1", 20, 10, 25.0, 50.0, 5.0, 23, 50.0, true, true); 
		subsala.crearSubsalas(2, 20.0, 10, true, true, true);
		assertTrue(subsala.eliminarSubsalas());
	}

}
