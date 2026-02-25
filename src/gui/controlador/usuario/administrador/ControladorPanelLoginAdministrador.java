package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.Ventana;
import gui.usuario.adminsitrador.PanelLoginAdministrador;
import sistema.Sistema;

public class ControladorPanelLoginAdministrador implements ActionListener{
	

	private Ventana vista;
	private PanelLoginAdministrador login;

	/**
	 * Constructor de ControladorPanelAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelLoginAdministrador(Ventana vista) {
		this.vista = vista;
		this.login = vista.getPanelLoginAdministrador();
	}
	
	/**
	 * Metodo que defines las acciones realizadas en ControladorPanelAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("atras")) {
			vista.mostrarPanel("panelPrincipal");
		} else if (e.getActionCommand().equals("siguiente")) {
			if (String.valueOf(login.getPasswordField().getPassword()).equals("")) {
				JOptionPane.showMessageDialog(vista, "Debe rellenar todos los campos", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (Sistema.getInstance().loginGestor(String.valueOf(login.getPasswordField().getPassword())) == true) {
					vista.mostrarPanel("panelAccionesAdmin");
				} else {
					JOptionPane.showMessageDialog(vista, "Usuario o contrase\u00F1a incorrectas", "Error",
							JOptionPane.ERROR_MESSAGE);
					vista.getPanelLoginAdministrador().updatePanel();
				}
			}
		}
	}

}
