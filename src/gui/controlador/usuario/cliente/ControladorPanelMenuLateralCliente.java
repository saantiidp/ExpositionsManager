package gui.controlador.usuario.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.Controlador;
import gui.Ventana;
import sistema.Sistema;

/**
 * Clase ControladorPanelMenuLateralCliente
 * 
 */
public class ControladorPanelMenuLateralCliente implements ActionListener {


	private Ventana vista;
	private Controlador c;
	
	/**
	 * Constructor ControladorPanelMenuLateralCliente
	 * @param vista (Ventana)
	 * @param c (Controlador)
	 */
	public ControladorPanelMenuLateralCliente(Ventana vista, Controlador c) {
		this.vista = vista;
		this.c = c;
	}
	
	/**
	 * Metodo que define las acciones del ControladorPanelMenuLateralCliente 
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("exposiciones")) {
			vista.getPanelExposicionesClientes().updatePanel();
			vista.mostrarPanel("panelExposicionesClientes");
		} else if (e.getActionCommand().equals("sorteos")) {
			vista.getPanelSorteosCliente().cargarSorteos();
			vista.mostrarPanel("panelSorteosCliente");
		} else if (e.getActionCommand().equals("notificaciones")) {
			vista.getPanelNotificacionesCliente().updatePanel();
			vista.mostrarPanel("panelNotificacionesCliente");
		} else if (e.getActionCommand().equals("entradas")) {
			vista.getPanelEntradasCliente().updatePanel();
			vista.getPanelEntradasCliente().setControlador(c.getPanelEntradasCliente());
			vista.mostrarPanel("panelListaEntradasCliente");
		} else {
			Sistema.getInstance().cerrarSesion(); 
			vista.mostrarPanel("panelPrincipal");
			JOptionPane.showMessageDialog(vista, "Se ha cerrado sesi\u00F3n correctamente", "Cerrar sesi\u00F3n",
					JOptionPane.INFORMATION_MESSAGE);	
		}
	}
	
	
}
