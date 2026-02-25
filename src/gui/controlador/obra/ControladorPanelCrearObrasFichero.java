package gui.controlador.obra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.Ventana;
import gui.obra.PanelCrearObrasFichero;
import sistema.Sistema;

public class ControladorPanelCrearObrasFichero implements ActionListener {

	private Ventana ventana;
	private PanelCrearObrasFichero panel;
	
	public ControladorPanelCrearObrasFichero(Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelCrearObrasFichero();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String path = this.panel.getPathField().getText();
		Sistema sis = Sistema.getInstance();
		
		if (e.getActionCommand().equals("aceptar")) {
			if (path.equals("")) {
				JOptionPane.showMessageDialog(null, "Rellena todos los campos.", "Información", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				if (sis.darAltaObraConFichero(path)) {
					JOptionPane.showMessageDialog(null, "Fichero leido.", "Información", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "No se ha encontrado " + path + " en /resources", "Información", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
		else if (e.getActionCommand().equals("atras")) {
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
	}

}
