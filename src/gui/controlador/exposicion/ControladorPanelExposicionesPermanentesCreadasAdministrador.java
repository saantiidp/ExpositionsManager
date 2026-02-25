package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.Ventana;

public class ControladorPanelExposicionesPermanentesCreadasAdministrador implements ActionListener{

	private Ventana vista;

	/**
	 * Constructor de ControladorPanelExposicionesPermanentesCreadasAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesPermanentesCreadasAdministrador(Ventana vista) {
		this.vista = vista;
	}

	/**
	 * Metodo que defines las acciones realizadas en ControladorPrincipalPanelCliente
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("borrador")) {
			vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
			vista.mostrarPanel("panelExposicionesPermanentesEnBorradorCreadasAdministrador");
		} else if (e.getActionCommand().equals("alta")) {
			vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().updatePanel();
			vista.mostrarPanel("panelExposicionesPermanentesDadasDeAltaAdministrador");
		} else if (e.getActionCommand().equals("atras")) {
			vista.getPanelRegistrarseCliente().updatePanel();
			vista.mostrarPanel("panelExposicionesCreadasAdministrador");
		}

	}

}



