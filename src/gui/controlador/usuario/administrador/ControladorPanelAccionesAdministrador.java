package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import gui.Ventana;
import gui.usuario.adminsitrador.PanelAccionesAdministrador;

/**
 * Clase ControladorPanelAccionesAdministrador
 */
public class ControladorPanelAccionesAdministrador implements ActionListener {
	/**
	 * Atributos de ControladorPanelAccionesAdministrador
	 */

	 /**
	  * Ventana
	  */
	Ventana ventana;

	/**
	 * panel
	 */
	PanelAccionesAdministrador panel;
	

	/**
	 * Constructor de ControladorPanelAccionesAdministrador
	 * @param v (Ventana)
	 */
	public ControladorPanelAccionesAdministrador(Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelAccionesAdmin();
	}


	/**
	 * Metodo que define las acciones del ControladorPanelAccionesAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("configurarParametros")) {
			this.ventana.mostrarPanel("panelConfigurarParametros");
		}
		else if (e.getActionCommand().equals("crearSorteo")) {
			this.ventana.getPanelSorteoAdmin().cargarExpos();
			this.ventana.mostrarPanel("panelSorteosAdmin");
		}

		else if(e.getActionCommand().equals("empleados")){
			this.ventana.getPanelDarAltaEmpleadoAdministrador().cargarEmpleados();
			this.ventana.mostrarPanel("panelDarAltaEmpleadoAdministrador");
		}
		else if (e.getActionCommand().equals("exposicion")) {
			this.ventana.mostrarPanel("panelExposicionesCreadasAdministrador");
		}
		else if (e.getActionCommand().equals("darAltaObra")) {
			this.ventana.mostrarPanel("panelCrearObraGestor");
		}
		else if (e.getActionCommand().equals("datosContabilidad")) {
			this.ventana.mostrarPanel("panelContabilidadAdministrador");
		}
		else if (e.getActionCommand().equals("salas")) {
			this.ventana.mostrarPanel("panelSalaAdministrador");
		}
		else if (e.getActionCommand().equals("salir")) {
			this.ventana.mostrarPanel("panelPrincipal");
		}
	}
}
