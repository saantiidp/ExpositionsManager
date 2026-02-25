package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.Ventana;
/**
 * Clase ControladorPanelExposicionesCreadasAdministrador
 * 
 */
public class ControladorPanelExposicionesCreadasAdministrador implements ActionListener{

	private Ventana vista;

	/**
	 * Constructor de ControladorPanelExposicionesCreadasAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesCreadasAdministrador(Ventana vista) {
		this.vista = vista;
	}

	/**
	 * Metodo que defines las acciones realizadas en ControladorPanelExposicionesCreadasAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("permanente")) {
			vista.mostrarPanel("panelExposicionesPermanentesCreadasAdministrador");
		} else if (e.getActionCommand().equals("temporal")) {
			vista.mostrarPanel("panelExposicionesTemporalesCreadasAdministrador");
		} else if (e.getActionCommand().equals("atras")) {
			vista.mostrarPanel("panelAccionesAdmin");
		}

	}

}




