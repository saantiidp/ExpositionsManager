package demostrador;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import descuento.DiasAntelación;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import exposicion.EstadoExposicion;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import obra.Audiovisual;
import obra.Cuadro;
import obra.Escultura;
import obra.Obra;
import obra.ObraDimensionada;
import obra.ObraParser;
import salas.Sala;
import salas.SalaHoja;
import salas.Subsala;
import sistema.Sistema;
import sorteos.Inscripcion;
import sorteos.UsoDiaHora;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;

public class Demostrador {

	public static void main(String[] args){

		Sistema sistema = Sistema.getInstance();
		sistema.loginGestor("1234");
		sistema.configurarParametros(10, 14, 0);
		ClienteRegistrado cliente = new ClienteRegistrado(true, "12345Z", "hola");
		ClienteRegistrado cliente2 = new ClienteRegistrado(true, "43311X", "hola");
		ClienteRegistrado cliente3 = new ClienteRegistrado(true, "21321Z", "hola");

		sistema.registrarCliente(cliente);
		sistema.registrarCliente(cliente2);
		sistema.registrarCliente(cliente3);

		System.out.println("Clientes registrados en el sistema: " + sistema.getClientesRegistrados().values());

		SalaHoja sala = new SalaHoja("Sala_1", 25, 5, 10, 15, 5, 22, 50, true, true);
		SalaHoja sala2 = new SalaHoja("Sala_2", 30, 4, 15, 20, 5, 22, 50, true, true);

		
		Scanner scanner = new Scanner(System.in);

		/*Breakpoint para hacer más legible la salida*/
		System.out.println("Presiona Enter para continuar");
		scanner.nextLine();

		/*Creo dos exposiciones, una de cada tipo*/

		ExposicionTemporal expo1 = new ExposicionTemporal("Exposicion Temporal", "Arte moderno", 9.99);
		ExposicionPermanente expo2 = new ExposicionPermanente("Exposicion Permanente", "Arte contemporraneo", 6.99);

		
		/*Las añado a la contabilidad del sistema*/
		sistema.getContabilidad().anyadirExposicion(expo2);
		sistema.getContabilidad().anyadirExposicion(expo2);

		
		/*Añado las salas de exposicion a las exposiciones*/
		expo1.anadirSalaExposición(new SalaHoja("Sala exposicion 1", 20, 2, 40, 40, 5, 5, 22, true, true));
		expo2.anadirSalaExposición(new SalaHoja("Sala exposicion 2", 20, 2, 40, 40, 5, 5, 22, true, true));

		
		System.out.println("Exposicion '" + expo1.getNombre() + "' creada con exito en la sala '"
				+ expo1.getSalasExposición().values().stream().findFirst().get().getSala().getNombre());
		System.out.println("Exposicion '" + expo2.getNombre() + "' creada con exito en la sala '"
				+ expo2.getSalasExposición().values().stream().findFirst().get().getSala().getNombre());

		
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();
		
		
		/*Doy de alta varias obras en el sistema*/
		sistema.darAltaAudiovisual("Interstellar", "Christopher Nolan", true, LocalDate.of(2014, 10, 26), "Película de ciencia ficción y drama estadounidense",
				1, "23020000", LocalTime.of(2, 49), "Inglés");
		sistema.darAltaEscultura("Discobolo", "Mirón", false, LocalDate.of(-450, 1, 1), "Es una escultura de bronce que representa a un atleta en el momento de lanzar el disco",
				1, "23020000", 19, 30, 40, 60, 0.53, 0.77, 0.2, "Bronce");
		sistema.darAltaCuadro("La ultima cena", "Leonardo Da Vinci", true, LocalDate.of(1495, 1, 1), "Pintura mural renacentista italiana",
				1, "23020000", 12, 25, 30, 70, 0.5, 0.5, 0.5, "Óleo sobre yeso");


		/*Guardo las obras del sistema en el fichero*/
		ObraParser.guardarObras("./obras.txt");
		System.out.println("Obras registradas en el fichero: [");
		for (Obra obra : sistema.getObrasRegistradas().values()) {
			System.out.println(obra.getTitulo());
		}
		System.out.println("]");

	
		
		sistema.darAltaCuadro("Noche Estrellada", "Van Gogh", true, LocalDate.of(1495, 1, 1), "Pintura mural renacentista italiana",
				1, "23020000", 20, 42, 40, 80, 0.5, 0.5, 0.5, "Óleo sobre yeso");
		
		/*Obtengo las obras del sistema y las añadiré a las exposiciones*/
		ObraDimensionada cuadro = (Cuadro)sistema.getObrasRegistradas().values().stream().filter(obra -> obra.getTitulo().equals("Noche Estrellada")).findFirst().get();
		ObraDimensionada escultura = (Escultura)sistema.getObrasRegistradas().values().stream().filter(obra -> obra.getTitulo().equals("Discobolo")).findFirst().get();
		Obra audiovisual = sistema.getObrasRegistradas().values().stream().filter(obra -> obra.getTitulo().equals("Interstellar")).findFirst().get();

		if (expo1.anadirObra((Escultura) escultura, sala) == true) {
			System.out.println("Obra '" + escultura.getTitulo() + "' añadida a la exposicion " + expo1.getNombre()
					+ " en la sala " + sala.getNombre());

		} else {

			System.out.println("No se ha podido añadir la obra" + escultura.getTitulo() +  " a la exposicion");
		}
		
		/*Añado la obra audiovisual a expo1*/
		if (expo1.anadirObra((Audiovisual)audiovisual, sala)) {
			System.out.println("Obra '" + audiovisual.getTitulo() + "' añadida a la exposicion " + expo1.getNombre()
					+ " en la sala " + sala.getNombre());
		} else {

			System.out.println("No se ha podido añadir la obra" + audiovisual.getTitulo() +  " a la exposicion");
		}
		
		/*Añado la obra cuadro a expo2*/
		if (expo2.anadirObra(cuadro, sala2) == true) {
			System.out.println("Obra '" + cuadro.getTitulo() + "' añadida a la exposicion " + expo2.getNombre()
					+ " en la sala " + sala2.getNombre());
		} else {

			System.out.println("No se ha podido añadir la obra " + cuadro.getTitulo() +  " a la exposicion");
		}

		/*Breakpoint para hacer más legible la salida*/
		System.out.println("Presiona Enter para continuar");
		scanner.nextLine();

		
		/*Doy de alta las exposiciones*/
		if (expo1.darDeAlta(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5))) {
			System.out.println("Exposicion  '" + expo1.getNombre() + "' dada de alta con exito con estas obras: "
					+ expo1.getObras());
		}
		if (expo2.darDeAlta(LocalDate.now().minusDays(5))) {
			System.out.println("Exposicion  '" + expo2.getNombre() + "' dada de alta con exito con estas obras: "
					+ expo2.getObras());
		}

		/*Breakpoint para hacer más legible la salida*/
		System.out.println("Presiona Enter para continuar");
		scanner.nextLine();
		
		
		/* Sorteos */
	    UsoDiaHora sorteo = new UsoDiaHora(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 5 ,  false, expo1,  LocalDate.now().plusDays(5), LocalTime.parse("12:00"));
		expo1.anadirSorteo(sorteo);
		cliente.participarSorteo(sorteo, 2);
		cliente2.participarSorteo(sorteo, 3);
		cliente3.participarSorteo(sorteo, 6);
		
		System.out.println("Sorteo creado con exito");

		
		/*Imprimo las notificaciones de los clientes*/
		System.out.println("Notificaciones de " + cliente.getNif()+ ": " + cliente.getNotificaciones());
		System.out.println("Notificaciones de " + cliente2.getNif()+ ": " + cliente2.getNotificaciones());
		System.out.println("Notificaciones de " + cliente3.getNif()+ ": " + cliente3.getNotificaciones());
		
		
		/*Breakpoint para hacer más legible la salida*/
		System.out.println("Presiona Enter para continuar");
		scanner.nextLine();

		
		/*Ganadores del sorteo*/
		ArrayList<Inscripcion> ganadores = sorteo.sortear();
		System.out.println("Sorteo realizado con exito");
		System.out.println("Ganadores: " + ganadores);
		
		System.out.println("Notificaciones de " + cliente.getNif()+ ": " + cliente.getNotificaciones());
		System.out.println("Notificaciones de " + cliente2.getNif()+ ": " + cliente2.getNotificaciones());
		System.out.println("Notificaciones de " + cliente3.getNif()+ ": " + cliente3.getNotificaciones());

		
		
		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();

		
		
		/* Comprar entradas */
		expo1.setEstado(EstadoExposicion.INICIADA);
		expo2.setEstado(EstadoExposicion.INICIADA);
		expo1.cerrarTemporalmente(LocalDate.now().plusDays(3), LocalDate.now().plusDays(4));
		
		try {expo1.comprarEntradas(cliente, "1254056761678741", LocalDate.now().plusDays(1), 12, 2, cliente.getCodigoSorteo());
			System.out.println("Compra mediante codigo de sorteo "+ cliente.getCodigoSorteo() + " por " +cliente.getNif()+ " realizada con exito");
			try{cliente.getEntradasCompradas().get(0).generarEntradaFormatoPDF();}
			catch (Exception e) {
				e.printStackTrace();
			}

	 }
		catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}	

		try {expo1.comprarEntradas(cliente2, "3942942347327235", LocalDate.now().plusDays(1), 12, 3, cliente2.getCodigoSorteo());
			System.out.println("Compra mediante codigo de sorteo "+ cliente2.getCodigoSorteo() + " por " +cliente2.getNif()+ " realizada con exito");
			try{cliente2.getEntradasCompradas().get(0).generarEntradaFormatoPDF();}
			catch (Exception e) {
				e.printStackTrace();
			}
	 }
		catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}	
		
		
		/*Comprar entrada para una exposicion descontada con dia de antelacion*/
		DiasAntelación descuento = new DiasAntelación(50.0, 7);
		
		/*Añado el descuento a expo1 con 7 dias de antelacion*/
		expo2.anadirDescuento(descuento);
		try {
			/*Intento comorar con 9 dias de antelacion*/
			expo2.comprarEntradas(cliente, "1254056761678741", LocalDate.now().plusDays(9), 12, 2, null);
		}
		catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}

		/*Comprar entrada de una exposicion sin descuento y genera PDF de la entrada*/
		try {expo1.comprarEntradas(cliente3, "2987109821098294", LocalDate.now().plusDays(1), 12, 5, cliente3.getCodigoSorteo());
			try{cliente3.getEntradasCompradas().get(0).generarEntradaFormatoPDF();}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
		catch (InvalidCardNumberException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
	

		/*Imprime las entradas de los clientes*/
		System.out.println("Entradas de cliente " + cliente.getNif()+ ": " + cliente.getEntradasCompradas());
		System.out.println("Entradas de cliente " + cliente2.getNif()+ ": " + cliente2.getEntradasCompradas());
		System.out.println("Entradas de cliente " + cliente3.getNif()+ ": " + cliente3.getEntradasCompradas());
		
		
		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();
		
		
		/*Registro empleados en el sistema*/
		Empleado empleado = new Empleado("7613Z", "Juan", "2138921412", "ES-54056761678741", "Calle Einstein", true, true, true);
		Empleado empleado2 = new Empleado("2513Z", "Mateo", "21389214132", "ES-22036341674711", "Calle de Francisco Tomás y Valiente", false, false, false);
		
		sistema.registrarEmpleado(empleado);
		sistema.registrarEmpleado(empleado2);

		System.out.println("Empleados registrados en el sistema: " + sistema.getEmpleados().values());

		
		/*Intento comprar entradas en taquilla, si tiene permiso de entrada, permite comprarla, en caso contrario, no*/
		if(expo1.comprarEntradaTaquilla(empleado, 12)){
			System.out.println("Entrada comprada por taquilla por " + empleado2.getNif() + " con exito");
		}
		else {
			if (empleado2.isPermisoVenderTaquilla()) {
				System.out.println("El empleado " + empleado2.getNif() + " no tiene permiso para vender en taquilla");
			}
			else System.out.println("No se ha podido comprar la entrada por taquilla");
		}

		if(expo2.comprarEntradaTaquilla(empleado2, 12)){
			System.out.println("Entrada comprada por taquilla por " + empleado.getNif() + " con exito");
		}
		else {
			if (empleado2.isPermisoVenderTaquilla()) {
				System.out.println("El empleado " + empleado2.getNif() + " no tiene permiso para vender en taquilla");
			}
			else System.out.println("No se ha podido comprar la entrada por taquilla");
		}
		

		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();

		/*Borrar exposicion permanente: */
		sistema.crearExposicion("expo1", "Exposicion temporal", 9.99, true);
		sistema.crearExposicion("expo2", "Exposicion permanente", 9.99, false);
		sistema.crearExposicion("expo3", "Exposicion permanente borrada", 9.99, false);
		sistema.crearExposicion("expo4", "Exposicion permanente", 9.99, false);
		sistema.crearExposicion("expo5", "Exposicion temporal borrada", 9.99, true);

		ExposicionPermanente expo3 = (ExposicionPermanente)sistema.getExposicionesPermanenteBorrador().values().stream().filter(expo -> expo.getNombre().equals("expo3")).findFirst().get();
		sistema.borrarExposicionPermanenteBorrador(expo3.getId());
		System.out.println("Exposicion '" + expo3.getNombre() + "' borrada con exito");
		System.out.println("Exposiciones permanentes en el sistema: [");
		for(int i = 0; i < sistema.getExposicionesPermanenteBorrador().values().size(); i++)
			 System.out.println(sistema.getExposicionesPermanenteBorrador().values().stream().toList().get(i).getNombre());
		System.out.println("]"); 
		
		
		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();


		/*Borrar exposicion temporales */
		ExposicionTemporal expo5 = (ExposicionTemporal)sistema.getExposicionesTemporalBorrador().values().stream().filter(expo -> expo.getNombre().equals("expo5")).findFirst().get();
		sistema.borrarExposicionTemporalBorrador(expo5.getId());
		System.out.println("Exposicion '" + expo5.getNombre() + "' borrada con exito");
		System.out.println("Exposiciones temporales en el sistema: [");
		for(int i = 0; i < sistema.getExposicionesTemporalBorrador().values().size(); i++)
			 System.out.println(sistema.getExposicionesTemporalBorrador().values().stream().toList().get(i).getNombre());
		System.out.println("]");

		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();

		
		/*Crear subsalas*/
		Subsala sala_padre = new Subsala("Sala_Principal", 25, 5, 10, 15, 5, 22, 50, true, true);
		if(sala_padre.crearSubsalas(20, 5.0, 2, true, true, false)) {
			System.out.println("Subsalas creadas con exito");
		}
		else {
			System.out.println("No se han podido crear las subsalas");
		}
		System.out.println("Subsalas de la sala padre '" + sala_padre.getNombre() + "': ");
		DecimalFormat formato = new DecimalFormat("#.##");
		for (Sala subsala : sala_padre.getSubsalas()) {
			subsala.crearSubsalas(5, 2.0, 2, true, true, true);
			System.out.println(subsala);
		}

		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();
		

		Sala sala_padre2 = new Subsala("Sala Mayor", 25, 5, 10, 15, 5, 22, 50, true, true);
		sala_padre2.crearSubsalas(15, 5.0, 2, true, true, false);
		sala_padre2.crearSubsalas(5, 3.0, 2, true, true, false);
		sala_padre2.crearSubsalas(5, 2.0, 1, true, true, false);

		ArrayList<Sala> subsalas = new ArrayList<>();
		int aforoTotal  = 0;
		double anchoTotal = 0;
		int tomasTotal = 0;
		for(Sala sub : sala_padre2.getSubsalas()){
			aforoTotal += sub.getAforo();
			anchoTotal += sub.getAncho();
			tomasTotal += sub.getNumTomasCorriente();
			subsalas.add(sub);
		}
		
		if(aforoTotal == sala_padre2.getAforo() && anchoTotal == sala_padre2.getAncho() && tomasTotal == sala_padre2.getNumTomasCorriente()){
			System.out.println("Subsalas creadas con exito :)");
		}
		else {
			
			for(Sala sub : subsalas){
				sala_padre2.getSubsalas().remove(sub);
			}
			System.out.println("No se han podido crear las subsalas :(");
		}

		
		
		
	
		/*Breakpoint para hacer más legible la salida*/
		System.out.println("\nPresiona Enter para continuar\n");
		scanner.nextLine();

		/*Cierro sesión y cargo el sistema en el fichero*/
		sistema.cerrarSesion();
		sistema.guardarFichero();
		
		/*Recupero los datos del sistema y compruebo que se han guardado los parametros*/
		sistema = Sistema.getInstance();
		System.out.println("Horario de apertura "+sistema.getParametro().getHorarioApertura());
		System.out.println("Horario de cierre "+ sistema.getParametro().getHorarioCierre());
		System.out.println("Meses de penalización por sorteo: "+sistema.getParametro().getMesesPenalizacionSorteos());
	
		scanner.close();
	}
	
	

}
