package junit.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import notificaciones.Notificacion;
import sistema.Sistema;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;

class EmpleadoTest {

	@Test
	void testModificarNumSegSocial() {
		Empleado empleado = new Empleado("1234Z", "Javier", "019201", "5429-1923-9128", "Calle Falsa 123", true, true, true);
		Sistema.getInstance().registrarEmpleado(empleado);
		empleado.setNumSegSocial("019202");
		assertEquals("019202", empleado.getNumSegSocial());
	}
	@Test
	void testModificarNumCuenta() {
		Empleado empleado = new Empleado("1234Z", "Javier", "019201", "5429-1923-9128", "Calle Falsa 123", true, true, true);
		Sistema.getInstance().registrarEmpleado(empleado);
		empleado.setNumCuenta("5429-1923-9129");
		assertEquals("5429-1923-9129", empleado.getNumCuenta());
	}
	@Test
	void testModificarDireccionFisica() {
		Empleado empleado = new Empleado("1234Z", "Javier", "019201", "5429-1923-9128", "Calle Falsa 123", true, true, true);
		Sistema.getInstance().registrarEmpleado(empleado);
		empleado.setDirFisica("Calle Einstein 124");
		assertEquals("Calle Einstein 124", empleado.getDirFisica());
	}

	@Test
	void testEnviarNotificacion() {
		Empleado empleado = new Empleado("1234Z", "Javier", "019201", "5429-1923-9128", "Calle Falsa 123", true, true, true);
		Sistema.getInstance().registrarEmpleado(empleado);
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1254Z", "hola");
		Sistema.getInstance().registrarCliente(cliente);
		empleado.enviarNotificacion(new Notificacion("Notificacion enviada", LocalDate.now()), cliente);
		System.out.println("Notificaciones del cliente: "+cliente.getNotificaciones());
		assertEquals("Notificacion enviada", cliente.getNotificaciones().get(0).getDescripcion());
	}
	@Test
	void testEnviarNotificacionSinPermiso() {
		Empleado empleado = new Empleado("1234Z", "Javier", "019201", "5429-1923-9128", "Calle Falsa 123", true, true, false);
		Sistema.getInstance().registrarEmpleado(empleado);
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1254Z", "hola");
		Sistema.getInstance().registrarCliente(cliente);
		empleado.enviarNotificacion(new Notificacion("Notificacion enviada", LocalDate.now()), cliente);
		assertEquals(0, cliente.getNotificaciones().size());
		
	}
	
}


