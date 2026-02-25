package sistema;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import notificaciones.Notificacion;

import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import exposicion.SalaExposicion;
import filtro.Filtro;
import obra.Audiovisual;
import obra.Cuadro;
import obra.Escultura;
import obra.Foto;
import obra.Obra;
import obra.ObraParser;
import obra.Seguro;
import salas.Sala;
import salas.SalaHoja;
import salas.Subsala;
import sorteos.Sorteo;
import sorteos.UsoDiaHora;
import sorteos.UsoExposicionEntera;
import sorteos.UsoIntervaloFechas;
import usuarios.ClienteRegistrado;
import usuarios.Empleado;
import usuarios.Usuario;

/**
 * Clase que representa el sistema y el gestor
 */
public class Sistema implements Serializable {
	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = 1564789L;
	/**
	 * Instancia del sistema para el Singleton
	 */
	private static Sistema sistemaInstance = null;
	/**
	 * Parametros del sistema
	 */
	private Parametro parametro = null;
	/**
	 * Usuario logeado
	 */
	private Usuario usuarioActivo = null;
	/**
	 * Indica si es el gestor, usuarioActivo estara a null
	 */
	private boolean usuarioEsGestor = false;
	/**
	 * Contraseña gestor
	 */
	private String contrasenyaGestor = "1234";
	/**
	 * Contraseña de empleados
	 */
	private String contrasenyaEmpleados = "12345";
	/**
	 * Notificaciones del gestor
	 */
	private List<Notificacion> notificacionesGestor = new LinkedList<>();
	
	/************	Relaciones	************/
	/**
	 * Sorteos
	 */
	private Map<Long, Sorteo> sorteos = new HashMap<>();
	/**
	 * Filtros del sistema
	 */
	private Map<Long, Filtro> filtros = new HashMap<>();
	/**
	 * 	Obras registradas
	 */
	private Map<Long, Obra> obrasRegistradas = new HashMap<>();
	/**
	 * Borrador exposiciones permanentes
	 */
	private Map<Long, ExposicionPermanente> exposicionesPermanenteBorrador = new HashMap<>();
	/**
	 * Borrador exposiciones temporales
	 */
	private Map<Long, ExposicionTemporal> exposicionesTemporalBorrador = new HashMap<>();
	/**
	 * Exposiciones publicadas
	 */
	private Map<Long, ExposicionTemporal> exposicionesTemporales = new HashMap<>();
	/**
	 * Exposiciones publicadas
	 */
	private Map<Long, ExposicionPermanente> exposicionesPermanentes = new HashMap<>();
	/**
	 * Salas del sistema
	 */
	private List<Sala> salas = new ArrayList<>();
	/**
	 * Relaciona nifs a clientes registrados
	 */
	private Map<String, ClienteRegistrado> clientesRegistrados = new HashMap<>();
	/**
	 * Contraseñas clientes registrados
	 */
	private Map<String, String> contrasClientes = new HashMap<>();
	/**
	 * Empleados
	 */
	private Map<String, Empleado> empleados = new HashMap<>();
	/**
	 * Notificaciones
	 */
	private Map<Long, Notificacion> notificaciones = new HashMap<>();
	/**
	 * Datos de contabilidad de las exposiciones
	 */
	private ContabilidadSistema contabilidad = new ContabilidadSistema();
	
	
	/************	Metodos		************/
	/**
	 * Constructor del sistema
	 */
	private Sistema () {
		File archivo = new File("./sistema.data");

	        if (archivo.exists()) {
	            Sistema s = this.cargarFichero();
	            
	            this.clientesRegistrados = s.getClientesRegistrados();
	            this.contabilidad = s.getContabilidad();
	            this.contrasClientes = s.getContrasClientes();
	            this.contrasenyaEmpleados = s.getContrasenyaEmpleados();
	            this.contrasenyaGestor = s.getContrasenyaGestor();
	            this.empleados = s.getEmpleados();
	            this.exposicionesPermanenteBorrador = s.getExposicionesPermanenteBorrador();
	            this.exposicionesPermanentes = s.getExposicionesPermanentes();
	            this.exposicionesTemporalBorrador = s.getExposicionesTemporalBorrador();
	            this.exposicionesTemporales = s.getExposicionesTemporales();
	            this.filtros = s.getFiltros();
	            this.notificaciones = s.getNotificaciones();
	            this.notificacionesGestor = s.getNotificacionesGestor();
	            this.obrasRegistradas = s.getObrasRegistradas();
	            this.salas = s.getSalas();
	            this.sorteos = s.getSorteos();
	            this.usuarioActivo = null;
	            this.usuarioEsGestor = false;
	            this.parametro = s.getParametro();
	            this.parametro.setHorarioApertura(11);
	            this.parametro.setHorarioCierre(20);
	            sistemaInstance = null;
	        } else {
	            parametro = new Parametro(null, null, 0);
	        }
	}
	
	/**
	 * Resetea los datos del sistema
	 */
	public void reset() {
		sistemaInstance = null;
	    parametro = null;
	    usuarioActivo = null;
	    usuarioEsGestor = false;
	    contrasenyaGestor = "1234";
	    contrasenyaEmpleados = "12345";
	    notificacionesGestor = new LinkedList<>();
	    sorteos = new HashMap<>();
	    filtros = new HashMap<>();
	    obrasRegistradas = new HashMap<>();
	    exposicionesTemporalBorrador = new HashMap<>();
	    exposicionesPermanenteBorrador = new HashMap<>();
	    exposicionesTemporales = new HashMap<>();
	    exposicionesPermanentes = new HashMap<>();
	    salas = new ArrayList<>();
	    clientesRegistrados = new HashMap<>();
	    contrasClientes = new HashMap<>();
	    empleados = new HashMap<>();
	    notificaciones = new HashMap<>();
	    contabilidad = new ContabilidadSistema();
	}
	
	/******* Getters *******/
	/**
	 * Obtiene la instancia del sistema
	 * @return el sistema
	 */
	public static Sistema getInstance() {
		if (sistemaInstance == null)
			sistemaInstance = new Sistema();
		return sistemaInstance;
	}
	
	/**
	 * Getter de la hora de apertura
	 * @return hora
	 */
	public Integer getHorarioAperturaExposicion() {
		return parametro.getHorarioApertura();
	}
	
	/**
	 * Getter de la hora de cierre
	 * @return cierre
	 */
	public Integer getHorarioCierreExposicion() {
		return parametro.getHorarioCierre();
	}
	
	/**
	 * Getter de los meses de penalizacion por no asistir a sorteos
	 * @return los meses
	 */
	public int getMesesPenalizacionSorteos () {
		return parametro.getMesesPenalizacionSorteos();
	}
	
	/**
	 * Devuelve el cliente asociado al nif dado
	 * @param n nif
	 * @return cliente asociado al nif
	 */
	public ClienteRegistrado getClienteByNif (String n) {
		return this.clientesRegistrados.get(n);
	}
	
	/**
	 * Devuelve el empleado asociado al nif dado
	 * @param n nif
	 * @return empleado asociado al nif
	 */
	public Empleado getEmpleadoByNif (String n) {
		return this.empleados.get(n);
	}
	
	/**
	 * Getter del usuario activo
	 * @return Usuario si es un empleado o cliente el que ha logueado, null si es el gestor
	 */
	public Usuario getUsuarioActivo() {
		return usuarioActivo;
	}
	
	/**
	 * Indica si el usuario es el gestor
	 * @return true si es el gestor, false si no
	 */
	public boolean isUsuarioEsGestor() {
		return usuarioEsGestor;
	}
	
	/**
	 * Getter de la contraseña de empleados
	 * @return la contraseña
	 */
	public String getContrasenyaEmpleados() {
		return contrasenyaEmpleados;
	}

	/**
	 * Getter de las exposiciones temporales
	 * @return exposiciones temporales
	 */
	public Map<Long, ExposicionTemporal> getExposicionesTemporales() {
		return exposicionesTemporales;
	}

	/**
	 * Getter de las exposiciones permanentes
	 * @return exposiciones permanentes
	 */
	public Map<Long, ExposicionPermanente> getExposicionesPermanentes() {
		return exposicionesPermanentes;
	}

	/**
	 * Getter de las exposiciones permanentes en borrador
	 * @return las exposiciones
	 */
	public Map<Long, ExposicionPermanente> getExposicionesPermanenteBorrador() {
		return exposicionesPermanenteBorrador;
	}

	/**
	 * Getter de las exposiciones temporales en borrador
	 * @return las exposiciones
	 */
	public Map<Long, ExposicionTemporal> getExposicionesTemporalBorrador() {
		return exposicionesTemporalBorrador;
	}

	/**
	 * Getter de sorteos
	 * @return sorteos
	 */
	public Map<Long, Sorteo> getSorteos() {
		return sorteos;
	}

	/**
	 * Getter de obras
	 * @return obras
	 */
	public Map<Long, Obra> getObrasRegistradas() {
		return obrasRegistradas;
	}

	/**
	 * Getter de las salas
	 * @return salas
	 */
	public List<Sala> getSalas() {
		return salas;
	}

	/**
	 * Getter de la contabilidad
	 * @return contabilidad
	 */
	public ContabilidadSistema getContabilidad() {
		return contabilidad;
	}

	/**
	 * Getter de las notificaciones del gestor
	 * @return notificaciones del gestor
	 */
	public List<Notificacion> getNotificacionesGestor() {
		return notificacionesGestor;
	}

	/**
	 * Getter de los filtros
	 * @return los filtros
	 */
	public Map<Long, Filtro> getFiltros() {
		return filtros;
	}

	/**
	 * Getter de los clientes registrados
	 * @return clientes registrados
	 */
	public Map<String, ClienteRegistrado> getClientesRegistrados() {
		return clientesRegistrados;
	}

	/**
	 * Getter de las contraseñas de los clientes
	 * @return las contraseñas
	 */
	public Map<String, String> getContrasClientes() {
		return contrasClientes;
	}

	/**
	 * Getter de los empleados
	 * @return los empleados
	 */
	public Map<String, Empleado> getEmpleados() {
		return empleados;
	}

	/**
	 * Getter de las notificaciones
	 * @return las notificaciones
	 */
	public Map<Long, Notificacion> getNotificaciones() {
		return notificaciones;
	}

	/**
	 * Getter de la contraseña del gestor
	 * @return contraseña
	 */
	public String getContrasenyaGestor() {
		return contrasenyaGestor;
	}

	/**
	 * Getter de parametro
	 * @return parametro
	 */
	public Parametro getParametro() {
		return parametro;
	}

	/******************************* Fin Getters **************************************/
	/**
	 * Configura parametros del sistema
	 * @param apertura horario apertura
	 * @param cierre horario de cierre
	 * @param penalizacionSorteos penalizaciones del sorteo
	 */
	public void configurarParametros(Integer apertura, Integer cierre, int penalizacionSorteos) {
		parametro.setHorarioApertura(apertura);
		parametro.setHorarioCierre(cierre);
		parametro.setMesesPenalizacionSorteos(penalizacionSorteos);
	}
	
	/**
	 * Loguea cliente
	 * @param nif nif de usuario
	 * @param contra contraseña
	 * @return true si se ha autenticado, false si no
	 */
	public boolean loginClientes(String nif, String contra) {
		this.actualizacionSistema();
		if (this.clientesRegistrados.containsKey(nif) == false)
			return false;
		
		if (this.contrasClientes.get(nif).equals(contra)) {
			this.usuarioActivo = this.clientesRegistrados.get(nif);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Loguea empleado
	 * @param nif nif de usuario
	 * @param contra contraseña
	 * @return true si se ha autenticado, false si no
	 */
	public boolean loginEmpleados(String nif, String contra) {
		this.actualizacionSistema();
		if (this.empleados.containsKey(nif) == false)
			return false;
		
		if (this.contrasenyaEmpleados.equals(contra)) {
			this.usuarioActivo = this.empleados.get(nif);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Loguea gestor
	 * @param contra contraseña
	 * @return true si se ha autenticado, false si no
	 */
	public boolean loginGestor(String contra) {
		this.actualizacionSistema();
		if (this.contrasenyaGestor.equals(contra)) {
			this.usuarioEsGestor = true;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Registra un cliente
	 * @param c cliente
	 */
	public boolean registrarCliente(ClienteRegistrado c) {
		
		if (this.clientesRegistrados.containsKey(c.getNif()))
			return false;
		
		this.clientesRegistrados.put(c.getNif(), c);
		this.contrasClientes.put(c.getNif(), c.getContraseña());
		
		return true;
	}
	
	/**
	 * Registra un empleado
	 * @param e empleado
	 */
	public boolean registrarEmpleado(Empleado e) {
		if (this.empleados.containsKey(e.getNif()))
			return false;
		
		this.empleados.put(e.getNif(), e);
		return true;
	}
	
	/**
	 * Cierra sesion
	 */
	public void cerrarSesion() {
		this.usuarioActivo = null;
		this.usuarioEsGestor = false;
	}
	
	
	/**
	 * Lleva acabo todo lo automatico, notis, almacenar
	 */
	public void actualizacionSistema() {
		LocalDate actual = LocalDate.now();
		//TO DO banear gente que no ha 
		
		/*Busca sorteos finalizados*/
		for (Sorteo s : this.sorteos.values()) {
			if (s.getFinInscripcion().isAfter(actual)) {
				notificacionesGestor.add(new Notificacion("Ha acabado un sorteo para la exposicion: " + 
															s.getExposicion().getNombre(), actual));
			}
		}
		
		for (ExposicionTemporal e : this.exposicionesTemporales.values()) {
			if (e.getFechaFin().isBefore(actual)) {
				for (SalaExposicion s : e.getSalasExposición().values()) {
					for (Obra o : s.obtenerObras())
						o.almacenar();
				}
			}
		}
	}
	
	
	/**
	 * Crea una exposicion
	 * @param nombre nombre de la exposicion
	 * @param descripcion descripcion
	 * @param precio precio
	 * @param tempo indica si es temporal o permanente la exposicion
	 */
	public void crearExposicion(String nombre, String descripcion, double precio, boolean tempo) {
		Exposicion e;
		if (tempo) {
			e = new ExposicionTemporal(nombre, descripcion, precio);
			this.exposicionesTemporalBorrador.put(e.getId(), (ExposicionTemporal) e);			
		}
		else {
			e = new ExposicionPermanente(nombre, descripcion, precio);
			this.exposicionesPermanenteBorrador.put(e.getId(), (ExposicionPermanente) e);
		}		
	}
	
	/**
	 * Publica una exposicion dado un ID
	 * @param id id de la exposicion
	 */
	public void publicarExposiciónPermanente(Long id) {
		ExposicionPermanente e = this.exposicionesPermanenteBorrador.get(id);

		e.darDeAlta(LocalDate.now());
		
		for (SalaExposicion se : e.getSalasExposición().values()) {
			for (Obra o : se.getObras().values()) {
				o.exponer();
			}
		}
		
		this.exposicionesPermanenteBorrador.remove(id);
		this.exposicionesPermanentes.put(id, e);
		this.contabilidad.anyadirExposicion(e);
	}
	
	/**
	 * Publica una exposicion temporal dado un id
	 * @param id id de la exposicion
	 * @param inicio fecha de inicio
	 * @param fin fecha de fin
	 */
	public void publicarExposiciónTemporal(Long id, LocalDate inicio, LocalDate fin) {
		ExposicionTemporal e = this.exposicionesTemporalBorrador.get(id);

		e.darDeAlta(inicio, fin);
		
		for (SalaExposicion se : e.getSalasExposición().values()) {
			for (Obra o : se.obtenerObras()) {
				o.exponer();
			}
		}
		
		this.exposicionesTemporalBorrador.remove(id);
		this.exposicionesTemporales.put(id, e);
		this.contabilidad.anyadirExposicion(e);
	}
	
	/**
	 * Borra una exposicion permanente del borrador
	 * @param id id de la exposicion
	 */
	public void borrarExposicionPermanenteBorrador(Long id) {
		this.exposicionesPermanenteBorrador.remove(id);
	}
	
	/**
	 * Borra una exposicion temporal del borrador
	 * @param id id de la exposicion
	 */
	public void borrarExposicionTemporalBorrador(Long id) {
		this.exposicionesTemporalBorrador.remove(id);
	}
	
	//TO DO probar guardar y carga
	/**
	 * Carga en ./sistema.data el sistema
	 */
	public void guardarFichero() {
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("./sistema.data"))) {
            salida.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Lee del fichero ./sistema.data los datos previos
	 * @return Sistema
	 */
	public Sistema cargarFichero() {
		Sistema sis = null;
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("./sistema.data"))) {
            sis = (Sistema) entrada.readObject();
            return sis;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		return sis;
	}
	
	/**
	 * Crea un cuadro dado sus caracteristicas
	 * @param autorObra autor
	 * @param obraCentro si es del centro o prestada
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param cuantiaSeguro cuantia del seguro
	 * @param numPoliza numero de poliza
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo latgo
	 * @param alto alto
	 * @param t tecnica
	 */
	public void darAltaCuadro(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, double cuantiaSeguro,
			String numPoliza, double tMin, double tMax, double hMin, double hMax, double ancho, double largo, double alto, String t) {
		Cuadro c = new Cuadro(titulo, autorObra, obraCentro, anyo, descripcion, new Seguro(cuantiaSeguro, numPoliza), tMin, tMax, hMin, hMax, ancho, largo, alto, t);
		this.obrasRegistradas.put(c.getId(), c);
	}
	
	/**
	 * Crea una escultura dado sus caracteristicas
	 * @param autorObra autor
	 * @param obraCentro si es del centro o prestada
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param cuantiaSeguro cuantia del seguro
	 * @param numPoliza numero de poliza	
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo latgo
	 * @param alto alto
	 * @param material material
	 */
	public void darAltaEscultura(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, double cuantiaSeguro,
			String numPoliza, double tMin, double tMax, double hMin, double hMax, double ancho, double largo, double alto, String material) {
		Escultura e = new Escultura(titulo, autorObra, obraCentro, anyo, descripcion, new Seguro(cuantiaSeguro, numPoliza), tMin, tMax, hMin, hMax, ancho, largo, alto, material);
		this.obrasRegistradas.put(e.getId(), e);
	}
	
	/**
	 * Crea una foto dado sus caracteristicas
	 * @param autorObra autor
	 * @param obraCentro si es del centro o prestada
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param cuantiaSeguro cuantia del seguro
	 * @param numPoliza numero de poliza
	 * @param tMin temperatura minima
	 * @param tMax temperatura maxima
	 * @param hMin humedad minima
	 * @param hMax humedad maxima
	 * @param ancho ancho
	 * @param largo latgo
	 * @param alto alto
	 * @param color color
	 */
	public void darAltaFoto(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, double cuantiaSeguro,
			String numPoliza, double tMin, double tMax, double hMin, double hMax, double ancho, double largo, double alto, boolean color) {
		Foto f = new Foto(titulo, autorObra, obraCentro, anyo, descripcion, new Seguro(cuantiaSeguro, numPoliza), tMin, tMax, hMin, hMax, ancho, largo, alto, color);
		this.obrasRegistradas.put(f.getId(), f);
	}

	/**
	 * Crea una audiovisual
	 * @param titulo titulo
	 * @param autorObra autor obra
	 * @param obraCentro obra centro
	 * @param anyo año
	 * @param descripcion descripcion
	 * @param cuantiaSeguro cuantia del seguro
	 * @param numPoliza numero de poliza
	 * @param duracion duracion
	 * @param idioma idioma
	 */
	public void darAltaAudiovisual(String titulo, String autorObra, boolean obraCentro, LocalDate anyo, String descripcion, double cuantiaSeguro,
			String numPoliza, LocalTime duracion, String idioma) {
		Audiovisual a = new Audiovisual(titulo, autorObra, obraCentro, anyo, descripcion, new Seguro(cuantiaSeguro, numPoliza), duracion, idioma);
		this.obrasRegistradas.put(a.getId(), a);
	}
	
	/**
	 * Carga un fichero de obras en el sistema
	 * @param pathFichero fichero
	 */
	public boolean darAltaObraConFichero(String pathFichero) {
		try {
			for (Obra o : ObraParser.getObras(new FileReader("./resources/" + pathFichero))) {
				this.obrasRegistradas.put(o.getId(), o);
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Entradas por exposicion
	 * @param e exposicion
	 * @param i primera fecha
	 * @param f segunda fecha
	 * @return entradas por exposicion dadas dos fechas
	 */
	public int contabilidadEntradasExposicion(Exposicion e, LocalDate i, LocalDate f) {
		return this.contabilidad.getEntradasPorExposicion(e, i, f);
	}
	
	/**
	 * Beneficio por exposicion
	 * @param e exposicion
	 * @param i primera fecha
	 * @param f segunda fecha
	 * @return entradas por exposicion dadas dos fechas
	 */
	public double contabilidadBeneficioExposicion(Exposicion e, LocalDate i, LocalDate f) {
		return this.contabilidad.getBeneficioPorExposicion(e, i, f);
	}
	
	/**
	 * Beneficio por exposicion
	 * @param e exposicion
	 * @param i primera fecha
	 * @param f segunda fecha
	 * @return entradas por exposicion dadas dos fechas
	 */
	public int contabilidadEntradasTotal(Exposicion e, LocalDate i, LocalDate f) {
		return this.contabilidad.getEntradasTotalExposiciones(i, f);
	}
	
	/**
	 * Beneficio por exposicion
	 * @param e exposicion
	 * @param i primera fecha
	 * @param f segunda fecha
	 * @return entradas por exposicion dadas dos fechas
	 */
	public double contabilidadBeneficioTotal(Exposicion e, LocalDate i, LocalDate f) {
		return this.contabilidad.getBeneficioTotalExposiciones(i, f);
	}
	
	/**
	 * Envia notificaciones a todos los clientes por un empleado
	 * @param e empleado
	 * @param n notificacion
	 */
	public void enviarNotificacionTodos(Empleado e, Notificacion n) {
		if (e.isPermisoEnviarMensajes() == false)
			return;
		
		for (ClienteRegistrado c : this.clientesRegistrados.values()) {
			c.notificar(n);
		}
	}
	
	/**
	 * Obtiene las exposiciones sin filtro
	 * @return exposiciones sin filtrar
	 */
	public List<Exposicion> obtenerExposiciones() {
		List<Exposicion> l = new ArrayList<>();
		for (Exposicion e : this.exposicionesPermanentes.values())
			l.add(e);
				
		for (Exposicion e : this.exposicionesTemporales.values()) {
			l.add(e);
		}
		
		return l;
	}
	
	/**
	 * Obtiene exposiciones dado un filtro
	 * @param f filtro
	 * @return las exposiciones filtradas
	 */
	public List<Exposicion> obtenerExposiciones(Filtro f) {
		List<Exposicion> l = new ArrayList<>();
		for (Exposicion e : this.exposicionesPermanentes.values()) {
			if (f.cumpleFiltro(e))
				l.add(e);
		}
				
		for (Exposicion e : this.exposicionesTemporales.values()) {
			if (f.cumpleFiltro(e))
				l.add(e);
		}
		
		return l;
	}
	
	/**
	 * Crea una sala
	 * @param hoja sala hoja
	 * @param nombre nombre
	 * @param aforo aforo
	 * @param numTomasCorriente numero de tomas de
	 * @param ancho ancho
	 * @param largo largo
	 * @param alto alto
	 * @param temperatura temperatura
	 * @param humedad humedad
	 * @param permiteControlTemp control de temperatura 
	 * @param permiteControlHum control de humedad
	 */
	public void crearSala(boolean hoja, String nombre, int aforo, int numTomasCorriente, double ancho, double largo, double alto,
			double temperatura, double humedad, boolean permiteControlTemp, boolean permiteControlHum) {
		Sala s;
		
		if (hoja)
			s = new SalaHoja(nombre, aforo, numTomasCorriente, ancho, largo, alto, temperatura, humedad, permiteControlTemp, permiteControlHum);
		else
			s = new Subsala(nombre, aforo, numTomasCorriente, ancho, largo, alto, temperatura, humedad, permiteControlTemp, permiteControlHum);
		
		this.salas.add(s);
	}
	
	/**
	 * Crea un sorteo de uso dia hora 
	 * @param inicioInscripcion inicio
	 * @param finInscripcion fin 
	 * @param numEntradasSorteadas numero de entradas
	 * @param gestorNotificado gestor notificado
	 * @param exposicion exposicion
	 * @param dia dia 
	 * @param time hora
	 */
	public void crearSorteoUsoDiaHora (LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion, LocalDate dia, LocalTime time) {
		Sorteo s = new UsoDiaHora(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion, dia, time);
		exposicion.anadirSorteo(s);
		this.sorteos.put(s.getId(), s);
	}
	
	/**
	 * Crea sorteo de uso de exposicion entera
	 * @param inicioInscripcion inicio de inscripcion
	 * @param finInscripcion fin de inscripcion
	 * @param numEntradasSorteadas  numero de entradas sorteadas
	 * @param gestorNotificado gestor notificado
	 * @param exposicion exposicion
	 */
	public void crearSorteoUsoExposicionEntera(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion) {
		Sorteo s = new UsoExposicionEntera(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion);
		exposicion.anadirSorteo(s);
		this.sorteos.put(s.getId(), s);
	}
	
	/**
	 * Crea sorteos para uso por intervalo de fechas
	 * @param inicioInscripcion inicio 
	 * @param finInscripcion fin
	 * @param numEntradasSorteadas numero 
	 * @param gestorNotificado gestor notificado
	 * @param exposicion exposicion
	 * @param fechaInicio fecha de inicio
	 * @param fechaFin fecha fin
	 */
	public void crearSorteoUsoIntervaloFechas(LocalDate inicioInscripcion, LocalDate finInscripcion, int numEntradasSorteadas,
			boolean gestorNotificado, Exposicion exposicion, LocalDate fechaInicio, LocalDate fechaFin) {
		Sorteo s = new UsoIntervaloFechas(inicioInscripcion, finInscripcion, numEntradasSorteadas, gestorNotificado, exposicion, fechaInicio, fechaFin);
		exposicion.anadirSorteo(s);
		this.sorteos.put(s.getId(), s);
	}
	
	/**
	 * Cambia contraseña de empleados
	 * @param c contra
	 */
	public void cambiarContrasenyaEmpleados(String c) {
		this.contrasenyaEmpleados = c;
	}
	
	/**
	 * Borra una sala
	 * @param s sala
	 */
	public void borrarSala (Sala s) {
		this.salas.remove(s);
	}

	/**
	 * Obtiene sorteos por exposicion
	 * @param e exposicion
	 * @return sorteos
	 */
	public ArrayList<Sorteo> getSorteosbyExpo(Exposicion e) {

		ArrayList<Sorteo> sorteosExpo = new ArrayList<Sorteo>();
		for (Sorteo s : this.sorteos.values()) {
			if (s.getExposicion().equals(e))
				sorteosExpo.add(s);
		}
		return sorteosExpo;
	}
	
	/**
	 * Obtiene exposicion por nombre
	 * @param s nombre
	 * @return exposicion
	 */
	public Exposicion getExposicionByName(String s) {
		for (Exposicion e : this.exposicionesPermanentes.values()) {
			if (e.getNombre().equals(s))
				return e;
		}
		for (Exposicion e : this.exposicionesTemporales.values()) {
			if (e.getNombre().equals(s))
				return e;
		}
		return null;
	}
}













