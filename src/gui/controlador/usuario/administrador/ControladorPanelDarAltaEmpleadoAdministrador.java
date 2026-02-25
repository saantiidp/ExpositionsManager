package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.Ventana;


/**
 * Clase ControladorPanelDarAltaEmpleadoAdministrador
 
 */
public class ControladorPanelDarAltaEmpleadoAdministrador implements ActionListener{


	/**
	 * Atributos de ControladorPanelDarAltaEmpleadoAdministrador
	 */
	Ventana vista;


	/**
	 * Constructor de ControladorPanelDarAltaEmpleadoAdministrador
	 * @param v (Ventana)
	 */
	public ControladorPanelDarAltaEmpleadoAdministrador(Ventana v) {
		this.vista = v;
	}

	/**
	 * Metodo que define las acciones del ControladorPanelDarAltaEmpleadoAdministrador
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("atras")) {
			this.vista.mostrarPanel("panelAccionesAdmin");
		}
		else if (e.getActionCommand().equals("darAlta")) {
			this.vista.mostrarPanel("panelFormularioDarAltaEmpleadosGestor");
		}
		
	}

}
