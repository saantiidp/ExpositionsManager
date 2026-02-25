package gui.controlador.notificaciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import gui.Ventana;
import sistema.Sistema;
import usuarios.ClienteRegistrado;

public class ControladorPanelNotificacionesCliente implements ActionListener{
	private Ventana vista;
	
	public ControladorPanelNotificacionesCliente(Ventana vista) {
		this.vista = vista;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Siguiente")) {
			vista.mostrarPanel("panelMenuPrincipalCliente");			
		}
		else if(e.getActionCommand().equals("Aceptar")) {
			JCheckBox chkNotis = vista.getPanelNotificacionesCliente().getChkNotificaciones();
			ClienteRegistrado cliente = (ClienteRegistrado) Sistema.getInstance().getUsuarioActivo();
			if(!cliente.isRecibirPublicidad() && chkNotis.isSelected()) {
				cliente.setRecibirPublicidad(true);
				JOptionPane.showMessageDialog(vista, "Se ha suscrito a las notificaciones", "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
				vista.getPanelNotificacionesCliente().updatePanel();
				return;
			}
			else if(cliente.isRecibirPublicidad() && !chkNotis.isSelected()) {
				cliente.setRecibirPublicidad(false);
				JOptionPane.showMessageDialog(vista, "Se ha desuscrito a las notificaciones", "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
				vista.getPanelNotificacionesCliente().updatePanel();
				return;
			}
			else {
				JOptionPane.showMessageDialog(vista, "No se ha realizado ningun cambio", "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
	}

}
