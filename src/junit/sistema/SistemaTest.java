package junit.sistema;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import enums.EstadoObra;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import notificaciones.Notificacion;
import obra.Audiovisual;
import obra.Cuadro;
import obra.Escultura;
import obra.Foto;
import obra.Obra;
import obra.Seguro;
import salas.SalaHoja;
import sistema.Sistema;
import sorteos.Sorteo;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;
import obra.ObraDimensionada;
import obra.ObraParser;

public class SistemaTest {
	private Sistema sis = null;
	
	@Before
	public void setUp() {
		String rutaArchivo = "./sistema.data";
        File archivo = new File(rutaArchivo);

        if (archivo.exists())
            archivo.delete();
		sis = Sistema.getInstance();
	}
	
	@After
	public void tearDown() {
		String rutaArchivo = "./sistema.data";
        File archivo = new File(rutaArchivo);

        if (archivo.exists())
            archivo.delete();
		sis.reset();
	}
	
	@Test
	public void testConfigurarParametros() {
		this.sis.configurarParametros(10, 20, 4);
		
		assertEquals(10, this.sis.getHorarioAperturaExposicion());
		assertEquals(20, this.sis.getHorarioCierreExposicion());
		assertEquals(4, this.sis.getMesesPenalizacionSorteos());
	}
	
	@Test
	public void testRegistrarCliente() {
		boolean ret;
		ClienteRegistrado cliente = new ClienteRegistrado(false, "nif123", "contra");
		
		ret = this.sis.registrarCliente(cliente);
		assertTrue(ret);
		
		ret = this.sis.registrarCliente(cliente);
		assertFalse(ret);
		
		assertEquals(cliente, this.sis.getClienteByNif(cliente.getNif()));
	}
	
	@Test
	public void testRegistrarEmpleado() {
		Empleado e = new Empleado("nif", "nombre", "numSeg", "numCuenta", "dir", true, true, true);
		boolean ret;
		
		ret = this.sis.registrarEmpleado(e);
		assertTrue(ret);
		
		ret = this.sis.registrarEmpleado(e);
		assertFalse(ret);
		
		assertEquals(e, this.sis.getEmpleadoByNif(e.getNif()));
	}
	
	@Test
	public void testLoginClientes() {
		ClienteRegistrado cliente = new ClienteRegistrado(false, "nif123", "contra");
		this.sis.registrarCliente(cliente);
		
		this.sis.loginClientes("nif123", "contra");
		assertEquals(cliente, this.sis.getUsuarioActivo());
		
		this.sis.cerrarSesion();
		this.sis.loginClientes("nif123", "incorrecto");
		assertEquals(null, this.sis.getUsuarioActivo());
		
		this.sis.cerrarSesion();
		this.sis.loginClientes("incorrecto", "contra");
		assertEquals(null, this.sis.getUsuarioActivo());
	}
	
	@Test
	public void testLoginEmpleados() {
		Empleado e = new Empleado("nif", "nombre", "numSeg", "numCuenta", "dir", true, true, true);
		
		this.sis.registrarEmpleado(e);
		this.sis.loginEmpleados("nif", e.getContraseña());
		assertEquals(null, this.sis.getUsuarioActivo());
		
		Empleado e1 = new Empleado("nif2", "nombre", "numSeg", "numCuenta", "dir", true, true, true);
		this.sis.registrarEmpleado(e1);
		this.sis.loginEmpleados("nif2", "12345");
		assertEquals(e1, this.sis.getUsuarioActivo());
		
		this.sis.cerrarSesion();
		this.sis.loginEmpleados("no existe", e1.getContraseña());
		assertEquals(null, this.sis.getUsuarioActivo());
	}
	
	@Test
	public void testCerrarSesion() {
		ClienteRegistrado cliente = new ClienteRegistrado(false, "nif123", "contra");
		
		this.sis.registrarCliente(cliente);
		this.sis.loginClientes("nif123", "contra");
		this.sis.cerrarSesion();
		
		assertEquals(null, this.sis.getUsuarioActivo());
	}
	
	@Test
	public void testLoginGestor() {
		this.sis.loginGestor("1234");
		
		assertEquals(null, this.sis.getUsuarioActivo());
		assertTrue(this.sis.isUsuarioEsGestor());
		
		this.sis.cerrarSesion();
		this.sis.loginGestor("incorrecta");
		assertEquals(null, this.sis.getUsuarioActivo());
		assertFalse(this.sis.isUsuarioEsGestor());
	}
	
	@Test
	public void testActualizacionSistema() {
		List<Long> l;
		long id;
		ExposicionTemporal e1;
		ExposicionTemporal e2;
		
		this.sis.crearExposicion("e1", "ss", 10, true);
		this.sis.crearExposicion("e2", "dd", 12, true);
		l = new ArrayList<>(this.sis.getExposicionesTemporalBorrador().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		e1 = this.sis.getExposicionesTemporalBorrador().get(id);
		l.remove(id);
		id = l.get(l.size() - 1);
		e2 = this.sis.getExposicionesTemporalBorrador().get(id);
		
		/*Añadir obras*/
		ObraDimensionada o1= new Foto("titulo", "autor", false, LocalDate.now(), "desc", null, 0, 10, 0, 1, 1, 0.2, 0, false);
		ObraDimensionada o2 = new Cuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		ObraDimensionada o3 = new Escultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		
		SalaHoja salaHoja = new SalaHoja("sala", 1, 1, 10, 10, 10, 1, 0.1, true, true);
		e1.anadirSalaExposición(salaHoja);
		e1.anadirObra(o1, salaHoja);
		
		SalaHoja salaHoja2 = new SalaHoja("sala", 1, 1, 10, 10, 10, 1, 0.1, true, true);
		e2.anadirSalaExposición(salaHoja2);
		e2.anadirObra(o2, salaHoja2);
		e2.anadirObra(o3, salaHoja2);
		
		/*E1 se publica para terminar ayer*/
		this.sis.publicarExposiciónTemporal(e1.getId(), LocalDate.now().minusYears(1), LocalDate.now().minusDays(1));
		/*E2 se publica para terminar mañana*/
		this.sis.publicarExposiciónTemporal(e2.getId(), LocalDate.now().minusYears(1), LocalDate.now().plusDays(1));

		/*Sorteos*/
		this.sis.crearSorteoUsoIntervaloFechas(LocalDate.now().minusYears(1), LocalDate.now().plusDays(1), 1, false, e2, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
		this.sis.crearSorteoUsoIntervaloFechas(LocalDate.now().minusYears(1), LocalDate.now().minusDays(1), 1, false, e2, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
		this.sis.actualizacionSistema();
		
		assertEquals(EstadoObra.ALMACEN, o1.getEstadoObra());
		assertEquals(EstadoObra.EXPUESTA, o2.getEstadoObra());
		assertEquals(EstadoObra.EXPUESTA, o3.getEstadoObra());
		
		assertEquals(this.sis.getNotificacionesGestor().size(), 1);
		Notificacion n = this.sis.getNotificacionesGestor().get(0);
		assertTrue(n.getDescripcion().contains(e2.getNombre()));
	}
	
	@Test
	public void testCrearExposicion() {
		this.sis.crearExposicion("expo1", "desc1", 10, false);
		List<Long> l;
		long id;
		
		l = new ArrayList<>(this.sis.getExposicionesPermanenteBorrador().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		ExposicionPermanente e = this.sis.getExposicionesPermanenteBorrador().get(id);
		
		assertEquals("expo1", e.getNombre());
		assertEquals("desc1", e.getDescripcion());
		assertEquals(10, e.getPrecio());
		assertEquals(false, e.esTemporal());
		
		
		this.sis.crearExposicion("expo2", "desc2", 10, true);
		l = new ArrayList<>(this.sis.getExposicionesTemporalBorrador().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		ExposicionTemporal ep = this.sis.getExposicionesTemporalBorrador().get(id);
		
		assertEquals("expo2", ep.getNombre());
		assertEquals("desc2", ep.getDescripcion());
		assertEquals(10, ep.getPrecio());
		assertEquals(true, ep.esTemporal());
	}
	
	@Test
	public void testPublicarExposicionPermanente() {
		List<Long> l;
		long id;
		ObraDimensionada o1= new Foto("titulo", "autor", false, LocalDate.now(), "desc", null, 0, 10, 0, 1, 1, 0.2, 0, false);
		ObraDimensionada o2 = new Cuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		
		SalaHoja salaHoja = new SalaHoja("sala", 1, 1, 10, 10, 10, 1, 0.1, true, true);
		
		this.sis.crearExposicion("expo1", "desc1", 10, false);
		l = new ArrayList<>(this.sis.getExposicionesPermanenteBorrador().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		ExposicionPermanente e = this.sis.getExposicionesPermanenteBorrador().get(id);
		e.anadirSalaExposición(salaHoja);
		e.anadirObra(o1, salaHoja);
		e.anadirObra(o2, salaHoja);
		this.sis.publicarExposiciónPermanente(e.getId());
		
		assertEquals(e, this.sis.getExposicionesPermanentes().get(e.getId()));
		assertTrue(this.sis.getExposicionesPermanenteBorrador().isEmpty());
		assertEquals(EstadoObra.EXPUESTA, o1.getEstadoObra());
		assertEquals(EstadoObra.EXPUESTA, o2.getEstadoObra());
	}
	
	@Test
	public void testPublicarExposicionTemporal() {
		List<Long> l;
		long id;
		
		ObraDimensionada o1= new Foto("titulo", "autor", false, LocalDate.now(), "desc", null, 0, 10, 0, 1, 1, 0.2, 0, false);
		ObraDimensionada o2 = new Cuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		
		SalaHoja salaHoja = new SalaHoja("sala", 1, 1, 10, 10, 10, 1, 0.1, true, true);
		
		this.sis.crearExposicion("expo2", "desc2", 10, true);
		l = new ArrayList<>(this.sis.getExposicionesTemporalBorrador().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		ExposicionTemporal e = this.sis.getExposicionesTemporalBorrador().get(id);
		e.anadirSalaExposición(salaHoja);
		e.anadirObra(o1, salaHoja);
		e.anadirObra(o2, salaHoja);
		this.sis.publicarExposiciónTemporal(e.getId(), LocalDate.of(1, 1, 1), LocalDate.of(2, 2, 2));
		assertEquals(e, this.sis.getExposicionesTemporales().get(e.getId()));
		assertTrue(this.sis.getExposicionesTemporalBorrador().isEmpty());
		assertEquals(EstadoObra.EXPUESTA, o1.getEstadoObra());
		assertEquals(EstadoObra.EXPUESTA, o2.getEstadoObra());
	}
	
	@Test
	public void testCrearSorteo() {
		LocalDate inicio = LocalDate.of(1, 1, 1);
		LocalDate fin = LocalDate.of(2, 3, 2);
		int num = 3;
		List<Long> l;
		long id;
		
		ExposicionPermanente e = new ExposicionPermanente("expo", "des", 10);
		
		this.sis.crearSorteoUsoIntervaloFechas(inicio, fin, num, false, e, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
		l = new ArrayList<>(this.sis.getSorteos().keySet());
		Collections.sort(l);
		id = l.get(l.size() - 1);
		
		Sorteo s = this.sis.getSorteos().get(id);
		
		assertEquals(inicio, s.getInicioInscripcion());
		assertEquals(fin, s.getFinInscripcion());
		assertEquals(num, s.getNumEntradasSorteadas());
		assertEquals(id, s.getId());
	}
	
	@Test
	public void testBorrarExposicionPermanenteBorrador() {
		this.sis.crearExposicion("nombre", "desc", 10, false);
		assertEquals(1, this.sis.getExposicionesPermanenteBorrador().size());
		List<ExposicionPermanente> l = new ArrayList<ExposicionPermanente>(this.sis.getExposicionesPermanenteBorrador().values());
		ExposicionPermanente e = l.get(0);
		this.sis.borrarExposicionPermanenteBorrador(e.getId());
		assertEquals(0, this.sis.getExposicionesTemporalBorrador().size());
	}
	
	@Test
	public void testBorrarExposicionTemporalBorrador() {
		this.sis.crearExposicion("nombre", "desc", 10, true);
		assertEquals(1, this.sis.getExposicionesTemporalBorrador().size());
		List<ExposicionTemporal> l = new ArrayList<ExposicionTemporal>(this.sis.getExposicionesTemporalBorrador().values());
		ExposicionTemporal e = l.get(0);
		this.sis.borrarExposicionTemporalBorrador(e.getId());
		assertEquals(0, this.sis.getExposicionesTemporalBorrador().size());
	}
	
	//TO DO test de cargar y guardar
	@Test
	public void testGuardarFichero() {
		this.sis.guardarFichero();
		File archivo = new File("./sistema.data");
		assertTrue(archivo.exists());
	}
	
	@Test
	public void testCargarFichero() {
		
	}
	
	@Test
	public void testDarAltaCuadro() {
		LocalDate date = LocalDate.of(1, 1, 1);
		this.sis.darAltaCuadro("tituloC", "autorC", false, date, "descC", 12.1, "1234", 0, 10, 0, 1, 10, 20, 0, "tecnicaC");
		assertEquals(1, this.sis.getObrasRegistradas().size());
		
		List<Obra> l = new ArrayList<>(this.sis.getObrasRegistradas().values());
		Cuadro o = (Cuadro)l.get(0);
		assertEquals("tituloC", o.getTitulo());
		assertEquals("autorC", o.getAutorObra());
		assertEquals(false, o.isObraCentro());
		assertEquals(date, o.getAnyo());
		assertEquals("descC", o.getDescripcion());
		assertEquals(12.1, o.getSeguro().getCuantia());
		assertEquals("1234", o.getSeguro().getNumPoliza());
		assertEquals(0, o.gettMin());
		assertEquals(10, o.gettMax());
		assertEquals(0, o.gethMin());
		assertEquals(1, o.gethMax());
		assertEquals(10, o.getAncho());
		assertEquals(20, o.getLargo());
		assertEquals(0, o.getAlto());
		assertEquals("tecnicaC", o.getTecnica());
	}
	
	@Test
	public void testDarAltaFoto() {
		LocalDate date = LocalDate.of(1, 1, 1);
		this.sis.darAltaFoto("tituloC", "autorC", false, date, "descC", 12.1, "1234", 0, 10, 0, 1, 10, 20, 0, false);
		assertEquals(1, this.sis.getObrasRegistradas().size());
		
		List<Obra> l = new ArrayList<>(this.sis.getObrasRegistradas().values());
		Foto o = (Foto)l.get(0);
		assertEquals("tituloC", o.getTitulo());
		assertEquals("autorC", o.getAutorObra());
		assertEquals(false, o.isObraCentro());
		assertEquals(date, o.getAnyo());
		assertEquals("descC", o.getDescripcion());
		assertEquals(12.1, o.getSeguro().getCuantia());
		assertEquals("1234", o.getSeguro().getNumPoliza());
		assertEquals(0, o.gettMin());
		assertEquals(10, o.gettMax());
		assertEquals(0, o.gethMin());
		assertEquals(1, o.gethMax());
		assertEquals(10, o.getAncho());
		assertEquals(20, o.getLargo());
		assertEquals(0, o.getAlto());
		assertEquals(false, o.isColor());
	}
	
	@Test
	public void testDarAltaEscultura() {
		LocalDate date = LocalDate.of(1, 1, 1);
		this.sis.darAltaEscultura("tituloC", "autorC", false, date, "descC", 12.1, "1234", 0, 10, 0, 1, 10, 20, 30, "materiaal");
		assertEquals(1, this.sis.getObrasRegistradas().size());
		
		List<Obra> l = new ArrayList<>(this.sis.getObrasRegistradas().values());
		Escultura o = (Escultura)l.get(0);
		assertEquals("tituloC", o.getTitulo());
		assertEquals("autorC", o.getAutorObra());
		assertEquals(false, o.isObraCentro());
		assertEquals(date, o.getAnyo());
		assertEquals("descC", o.getDescripcion());
		assertEquals(12.1, o.getSeguro().getCuantia());
		assertEquals("1234", o.getSeguro().getNumPoliza());
		assertEquals(0, o.gettMin());
		assertEquals(10, o.gettMax());
		assertEquals(0, o.gethMin());
		assertEquals(1, o.gethMax());
		assertEquals(10, o.getAncho());
		assertEquals(20, o.getLargo());
		assertEquals(30, o.getAlto());
		assertEquals("materiaal", o.getMaterial());
	}
	
	@Test
	public void testDarAltaAudiovisual() {
		LocalDate date = LocalDate.of(1, 1, 1);
		LocalTime duracion = LocalTime.now();
		this.sis.darAltaAudiovisual("tituloo", "autoro", true, date, "deesc", 12.1, "1234", duracion, "esperanto");
		assertEquals(1, this.sis.getObrasRegistradas().size());
		
		List<Obra> l = new ArrayList<>(this.sis.getObrasRegistradas().values());
		Audiovisual o = (Audiovisual)l.get(0);
		assertEquals("tituloo", o.getTitulo());
		assertEquals("autoro", o.getAutorObra());
		assertEquals(true, o.isObraCentro());
		assertEquals(date, o.getAnyo());
		assertEquals("deesc", o.getDescripcion());
		assertEquals(12.1, o.getSeguro().getCuantia());
		assertEquals("1234", o.getSeguro().getNumPoliza());
		assertEquals(duracion, o.getDuracion());
		assertEquals("esperanto", o.getIdioma());
	}
	
	@Test
	public void testDarAltaObraConFichero() {
		Obra o = new Cuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "tecnica");
		Obra o1 = new Audiovisual("audiovi", "yo", false, LocalDate.now(), "desc", new Seguro(11, "456"), LocalTime.now(), "Frances");
		ObraDimensionada o2 = new Foto("titulo", "autor", true, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, true);
		ObraDimensionada o3 = new Escultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", new Seguro(10, "123"), 0, 10, 0, 1, 1, 1, 0, "material");
		
		this.sis.darAltaCuadro("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, "tecnica");
		this.sis.darAltaAudiovisual("audiovi", "yo", false, LocalDate.now(), "desc", 11, "456", LocalTime.now(), "Frances");
		this.sis.darAltaFoto("titulo", "autor", true, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, true);
		this.sis.darAltaEscultura("titulo", "autor", false, LocalDate.of(1, 1, 1), "desc", 10, "123", 0, 10, 0, 1, 1, 1, 0, "material");
		
		ObraParser.guardarObras("./obras.txt");
		this.sis.darAltaObraConFichero("./obras.txt");
		List<Obra> l = new ArrayList<>(this.sis.getObrasRegistradas().values());
		assertTrue(l.contains(o));
		assertTrue(l.contains(o1));
		assertTrue(l.contains(o2));
		assertTrue(l.contains(o3));

		Path path = Paths.get("./obras.txt");
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

















