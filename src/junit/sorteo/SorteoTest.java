package junit.sorteo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import exposicion.ExposicionPermanente;
import sistema.Sistema;
import sorteos.Inscripcion;
import sorteos.UsoDiaHora;
import usuarios.ClienteRegistrado;

public class SorteoTest {

	
	
	
	@Test
	void testGenerarCodigo() {
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(), LocalDate.now().plusDays(5) , 5, false, new ExposicionPermanente("expo", "expo permanente", 10.99), LocalDate.now().plusDays(5), LocalTime.parse("12:00") );
		assertEquals(6, sorteo.generarCodigo().length());
		
	}

	@Test
	void testSortear() {
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		ClienteRegistrado cliente2 = new ClienteRegistrado(true, "1233Z", "hola1");
		ClienteRegistrado cliente3 = new ClienteRegistrado(true, "1232Z", "hola2");
		ClienteRegistrado cliente4 = new ClienteRegistrado(true, "1231Z", "hola3");
		ClienteRegistrado cliente5 = new ClienteRegistrado(true, "1230Z", "hola4");
		ClienteRegistrado cliente6 = new ClienteRegistrado(true, "1235Z", "hola5");

		Sistema sistema = Sistema.getInstance();
		sistema.registrarCliente(cliente);
		sistema.registrarCliente(cliente2);
		sistema.registrarCliente(cliente3);
		sistema.registrarCliente(cliente4);
		sistema.registrarCliente(cliente5);
		sistema.registrarCliente(cliente6);
		
		
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(), LocalDate.now().plusDays(5) , 6, false, new ExposicionPermanente("expo", "expo permanente", 10.99), LocalDate.now().plusDays(5), LocalTime.parse("12:00") );
		System.out.println("Entradas sorteadas: "+ sorteo.getNumEntradasSorteadas());
		cliente.participarSorteo(sorteo, 6);
		cliente2.participarSorteo(sorteo, 2);
		cliente3.participarSorteo(sorteo, 2);
		cliente4.participarSorteo(sorteo, 4);
		cliente5.participarSorteo(sorteo, 2);
		cliente6.participarSorteo(sorteo, 4);
		ArrayList<Inscripcion> ganadores =  sorteo.sortear();
		int numEntradasGanadas = 0;
		for(Inscripcion ganador: ganadores) {
			numEntradasGanadas +=ganador.getNumEntradasSolicitidas();
			
			System.out.println(ganador.getCliente().getNif() + " ha ganado " + ganador.getNumEntradasSolicitidas() + " entradas");
		}
		System.out.println("Entradas ganadas totales: "+numEntradasGanadas);
		assertTrue(ganadores.size() == 0);
		
	}
	@Test
	void testSortearSinParticipantes() {
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(), LocalDate.now().plusDays(5) , 6, false, new ExposicionPermanente("expo", "expo permanente", 10.99), LocalDate.now().plusDays(5), LocalTime.parse("12:00") );
		ArrayList<Inscripcion> ganadores =  sorteo.sortear();
		assertEquals(0, ganadores.size());
	}
	@Test
	void testInscribirse() {
		boolean ret;
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		Sistema sistema = Sistema.getInstance();
		sistema.registrarCliente(cliente);
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(), LocalDate.now().plusDays(5) , 5, false, new ExposicionPermanente("expo", "expo permanente", 10.99), LocalDate.now().plusDays(5), LocalTime.parse("12:00") );
		ret = sorteo.inscribirse(cliente, 3);
		System.out.println(cliente.getNotificaciones());
		assertTrue(ret);
	}
	@Test
	void testInscribirseYaInscrito(){
		ClienteRegistrado cliente = new ClienteRegistrado(true, "1234Z", "hola");
		Sistema sistema = Sistema.getInstance();
		sistema.registrarCliente(cliente);
		UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now(), LocalDate.now().plusDays(5) , 5, false, new ExposicionPermanente("expo", "expo permanente", 10.99), LocalDate.now().plusDays(5), LocalTime.parse("12:00") );
		sorteo.inscribirse(cliente, 3);
		boolean ret = sorteo.inscribirse(cliente, 3);
		System.out.println(cliente.getNotificaciones());
		assertFalse(ret);
	}

}
