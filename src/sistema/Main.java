package sistema;

import java.io.File;
import java.time.LocalDate;

import entrada.Entrada;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import gui.Controlador;
import gui.Ventana;
import salas.Sala;
import salas.SalaHoja;
import sorteos.Sorteo;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;

/**
 * Clase main para ejecutar la aplicacion entera
 *
 */
public class Main {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			Sistema sistema = Sistema.getInstance(); /* iniciar el sistema */
			File archivo = new File("./sistema.data");

	        if (!archivo.exists()) {
	        	dataGenerator(); /*generador de datos*/
	        }
			Ventana frame = new Ventana();
			frame.setTitle("ExpoManager");
			Controlador controlador = new Controlador(frame);
			frame.setControlador(controlador);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dataGenerator() {
		Sistema sistema = Sistema.getInstance();
		
		sistema.configurarParametros(9, 20, 5);
		ClienteRegistrado cliente1 = new ClienteRegistrado(true, "ES-00877893P", "903178jJ");
		ClienteRegistrado cliente2 = new ClienteRegistrado(true, "ES-00877893L", "084178lL");
		ClienteRegistrado cliente3 = new ClienteRegistrado(false, "ES-00877893J", "084865nM");
		ClienteRegistrado cliente4 = new ClienteRegistrado(false, "ES-00877893X", "124865jK");
		
		
		
		Sistema.getInstance().crearExposicion("Siglo de Oro","Exposición que trata sobre todo el Siglo de Oro en España " , 20.0, false);
		Sistema.getInstance().crearExposicion("Edad Media","Exposición que trata sobre la Edad Media en Europa" , 20.0, true);
		Sistema.getInstance().crearExposicion("Guerra de las Galaxias","Exposición que trata sobre la Guerra de las Galaxias " , 20.0, false);
		Sistema.getInstance().crearExposicion("Catolicismo","Exposición que trata sobre todo el catolicismo y la Evangelización de medio orbe " , 20.0, true);
		Sistema.getInstance().crearExposicion("Tenis","Exposición que trata sobre toda la historia del tenis" , 20.0, false);
		Sistema.getInstance().crearExposicion("Futbol","Exposición que trata sobre toda la historia del futbol " , 20.0, true);
		Sistema.getInstance().crearExposicion("Alejandro Magno","Exposición que trata sobre la vida de Alejandro Magno" , 20.0, false);
		Sistema.getInstance().crearExposicion("Julio Cesar","Exposición que trata sobre la vida de Julio Cesar " , 20.0, true);
		Sistema.getInstance().crearExposicion("Descubrimiento de América","Exposición que trata sobre el descubrimiento de América" , 20.0, false);		
		Sistema.getInstance().crearExposicion("Miguel de Cervantes","Exposición que trata sobre las obras de Miguel de Cervantes" , 20.0, true);		
		Sistema.getInstance().crearExposicion("Cleopatra","Exposición que trata sobre Cleopatra y Egipto" , 20.0, false);
		
		
		ExposicionTemporal expo2 = Sistema.getInstance().getExposicionesTemporalBorrador().get(1L);
		expo2.setFechaInicio(LocalDate.now().minusDays(4));
		expo2.setFechaFin(LocalDate.now().plusDays(20));
		
		ExposicionTemporal expo3 = Sistema.getInstance().getExposicionesTemporalBorrador().get(3L);
		expo3.setFechaInicio(LocalDate.now().minusDays(4));
		expo3.setFechaFin(LocalDate.now().plusDays(20));
		
		ExposicionTemporal expo4 = Sistema.getInstance().getExposicionesTemporalBorrador().get(5L);
		expo4.setFechaInicio(LocalDate.now().minusDays(4));
		expo4.setFechaFin(LocalDate.now().plusDays(20));
		
		ExposicionTemporal expo5 = Sistema.getInstance().getExposicionesTemporalBorrador().get(7L);
		expo5.setFechaInicio(LocalDate.now().minusDays(4));
		expo5.setFechaFin(LocalDate.now().plusDays(20));
		
		ExposicionTemporal expo6 = Sistema.getInstance().getExposicionesTemporalBorrador().get(9L);
		expo6.setFechaInicio(LocalDate.now().minusDays(4));
		expo6.setFechaFin(LocalDate.now().plusDays(20));
		

		 sistema.crearSala(false, "Sala 1", 40, 2, 4, 5, 6, 4, 6, true, true);   
		 sistema.crearSala(false, "Sala 2", 40, 2, 4, 5, 6, 4, 6, true, true);
	     sistema.crearSala(false, "Sala 3", 40, 2, 4, 5, 6, 4, 6, true, true);
	     sistema.crearSala(false, "Sala 4", 40, 2, 4, 5, 6, 4, 6, true, true);
	     sistema.crearSala(false, "Sala 5", 40, 2, 4, 5, 6, 4, 6, true, true);
	     sistema.crearSala(false, "Sala 6", 40, 2, 4, 5, 6, 4, 6, true, true);
	     sistema.crearSala(false, "Sala 7", 40, 2, 4, 5, 6, 4, 6, true, true);
		
	     Sala sala1 = null; 
		 Sala sala2 = null;
	     for(Sala sala: sistema.getSalas()) {
	    	 if(sala.getNombre().equals("Sala 1")) {
	    		 sala1 = sala;
	    		 
	    	 }
			 
			if(sala.getNombre().equals("Sala 2")) {
				sala2 = sala;
			}

			
	     }
	     
	     sala1.crearSubsalas(20, 2.5, 1, true, true, true);
		 sala2.crearSubsalas(20, 2.5, 1, true, true, true);
		 sala2.getSubsalas().get(0).crearSubsalas(20, 2.5, 1, true, true, true);



		ExposicionPermanente expo1 = Sistema.getInstance().getExposicionesPermanenteBorrador().get(0L);
		
		expo1.anadirSalaExposición((SalaHoja)sala1.getSubsalas().get(0));
		
		expo1.darDeAlta(LocalDate.now().minusDays(4));
		expo1.iniciada();
		
		Sistema.getInstance().publicarExposiciónPermanente(0L);
		
		
	
	     for(Sala sala: sistema.getSalas()) {
	    	 if(sala.getNombre().equals("Sala 2")) {
	    		 sala2 = sala;
	    		 
	    	 }
	     }
	     
	     sala2.crearSubsalas(20, 2.5, 1, true, true, true);

		
		expo3.anadirSalaExposición((SalaHoja)sala1.getSubsalas().get(0));
		
		expo3.darDeAlta(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20));
		expo3.iniciada();
		
		Sistema.getInstance().publicarExposiciónPermanente(2L);

		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));
		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));
		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));
		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));
		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));
		cliente1.añadirEntrada(new Entrada(LocalDate.now().minusDays(50), 13, 20.0, expo1, cliente1, "5402056761676020"));

		sistema.registrarCliente(cliente1);
		sistema.registrarCliente(cliente2);
		sistema.registrarCliente(cliente3);
		sistema.registrarCliente(cliente4);

		sistema.loginGestor("1234");
		sistema.registrarEmpleado(new Empleado("ES-12345678Z","Santiago", "123-45-6789", "5402056761676020", "Gran via", true, false, false));
		sistema.registrarEmpleado(new Empleado("ES-98765432W","Valentina", "987-65-4321", "5402056761676022", "Plaza mayor", false, true, false));
		sistema.registrarEmpleado(new Empleado("ES-45678901R","Mateo", "456-78-9012", "5402056761676023", "Gran via", false, false, false));
		sistema.registrarEmpleado(new Empleado("ES-87654321X","Isabella", "876-54-3210", "5402056761676024", "Avenida America", true, false, false));
		sistema.registrarEmpleado(new Empleado("ES-34567890A","Sebastian", "345-67-8901", "5402056761676067", "Calle Grande", false, true, true));
		sistema.registrarEmpleado(new Empleado("ES-21098765B","Camila", "210-98-7654", "5402056761676075", "Calle Grande", true, false, false));
		sistema.registrarEmpleado(new Empleado("ES-54321098Y","Juan", "543-21-0987", "5402056761676034", "Plaza Espanya", false, true, false));
		sistema.registrarEmpleado(new Empleado("ES-89012345T","Sofia", "890-12-3456", "5402056761676088", "Cibeles", true, false, true));
		sistema.registrarEmpleado(new Empleado("ES-67890123H","Alejandro", "678-90-1234", "5402056761676099", "Einstein", false, false, false));
		sistema.registrarEmpleado(new Empleado("ES-32109876J","Valeria", "321-09-8765", "5402056761676000", "Einstein", true, true, true));
		

		sistema.crearSorteoUsoExposicionEntera(LocalDate.now().minusDays(4), LocalDate.now().plusDays(20), 5, true, expo1);
		Sorteo sorteo = sistema.getSorteos().get(0L);
		sorteo.inscribirse(cliente1, 2);
		sorteo.inscribirse(cliente2, 2);

		sistema.crearSorteoUsoExposicionEntera(LocalDate.now().minusDays(7), LocalDate.now().plusDays(20), 5, true, expo3);
		Sorteo sorteo2 = sistema.getSorteos().get(1L);
		sorteo2.inscribirse(cliente1, 2);
		sorteo2.inscribirse(cliente2, 2);




		sistema.cerrarSesion();
	}
}
