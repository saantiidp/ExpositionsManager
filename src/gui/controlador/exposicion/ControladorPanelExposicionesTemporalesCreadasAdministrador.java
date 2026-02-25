package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.Ventana;
/**
 * Clase ControladorPanelExposicionesTemporalesCreadasAdministrador
 * 
 */
public class ControladorPanelExposicionesTemporalesCreadasAdministrador implements ActionListener{

	private Ventana vista;

	/**
	 * Constructor de ControladorPanelExposicionesTemporalesCreadasAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesTemporalesCreadasAdministrador(Ventana vista) {
		this.vista = vista;
	}

	/**
	 * Metodo que defines las acciones realizadas en ControladorPrincipalPanelCliente
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("borrador")) {
			vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().updatePanel();
			vista.mostrarPanel("panelExposicionesTemporalesEnBorradorCreadasAdministrador");
		} else if (e.getActionCommand().equals("alta")) {
			vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador();
			vista.mostrarPanel("panelExposicionesTemporalesDadasDeAltaAdministrador");
		} else if (e.getActionCommand().equals("atras")) {
			vista.mostrarPanel("panelExposicionesCreadasAdministrador");
		}

	}

}
