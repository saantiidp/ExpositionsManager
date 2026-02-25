package gui.controlador.usuario.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.Ventana;
import gui.usuario.cliente.PanelLoginCliente;
import sistema.Sistema;

public class ControladorPanelLoginCliente implements ActionListener{
	

	private Ventana vista;
	private PanelLoginCliente login;

	/**
	 * Constructor de ControladorPanelLoginCliente
	 * @param vista (Ventana)
	 */
	public ControladorPanelLoginCliente(Ventana vista) {
		this.vista = vista;
		this.login = vista.getPanelLoginCliente();
	}
	
	/**
	 * Metodo que defines las acciones realizadas en ControladorPanelLoginCliente
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("atras")) {
			vista.mostrarPanel("panelRegistrarseIniciarSesionCliente");
		} else if (e.getActionCommand().equals("siguiente")) {
			if (String.valueOf(login.getPasswordField().getPassword()).equals("")
					|| login.getNifField().getText().equals("")) {
				JOptionPane.showMessageDialog(vista, "Debe rellenar todos los campos", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (Sistema.getInstance().loginClientes(login.getNifField().getText(),
						String.valueOf(login.getPasswordField().getPassword())) == true) {
					vista.getPanelMenuPrincipalCliente().updatePanel();
					vista.mostrarPanel("panelMenuPrincipalCliente");
				} else {
					JOptionPane.showMessageDialog(vista, "Usuario o contrase\u00F1a incorrectas", "Error",
							JOptionPane.ERROR_MESSAGE);
					vista.getPanelLoginCliente().updatePanel();
				}
			}
		}
	}

}


