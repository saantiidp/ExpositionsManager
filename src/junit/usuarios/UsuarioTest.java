package junit.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import usuarios.ClienteRegistrado;

class UsuarioTest {

	@Test
	void testUsuario_1() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		assertEquals("1234Z", cliente.getNif());
	}
	@Test
	void testUsuario_2() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		assertEquals("hola", cliente.getContraseña());
	}
	@Test
	void testUsuario_3() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		cliente.setNif("1235Z");
		assertEquals("1235Z", cliente.getNif());
	}
	@Test
	void testUsuario_4() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		cliente.setContraseña("hola2");
		assertEquals("hola2", cliente.getContraseña());
	}
	

}
