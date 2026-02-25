package junit.usuarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import exposicion.ExposicionPermanente;
import notificaciones.Notificacion;
import sistema.Sistema;
import sorteos.UsoDiaHora;
import usuarios.ClienteRegistrado;

class ClienteRegistradoTest {

	@Test
	void notificarTest() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		Sistema.getInstance().registrarCliente(cliente);
		cliente.notificar(new Notificacion("Notificado correctamente", LocalDate.now()));
		assertEquals("Notificado correctamente", cliente.getNotificaciones().get(0).getDescripcion());
	}
	
	@Test
	void inscribirseTest(){
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		Sistema.getInstance().registrarCliente(cliente);
		LocalDate date = LocalDate.now();
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(),date , 5, false, new ExposicionPermanente("expo", "expo permanente", 10.99), date, LocalTime.parse("12:00") );
		cliente.participarSorteo(sorteo, 3);
		assertEquals("Inscripción realizada para el sorteo de la exposicion: expo", cliente.getNotificaciones().get(0).getDescripcion());
	}

}
