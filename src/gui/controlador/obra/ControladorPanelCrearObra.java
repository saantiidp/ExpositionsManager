package gui.controlador.obra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.Ventana;

public class ControladorPanelCrearObra implements ActionListener {
	private Ventana ventana;
	
	public ControladorPanelCrearObra(Ventana v) {
		this.ventana = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("cargar")) {
			this.ventana.mostrarPanel("panelCrearObrasFichero");
		}
		else if (e.getActionCommand().equals("audiovisual")) {
			this.ventana.mostrarPanel("panelCrearAudiovisualObraGestor");
		}
		else if (e.getActionCommand().equals("cuadro")) {
			this.ventana.mostrarPanel("panelCrearObraCuadroGestor");
		}
		else if (e.getActionCommand().equals("escultura")) {
			this.ventana.mostrarPanel("panelCrearObraEsculturaGestor");
		}
		else if (e.getActionCommand().equals("foto")) {
			this.ventana.mostrarPanel("panelCrearObrafotoGestor");
		}
		else if (e.getActionCommand().equals("atras")) {
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
	}
	
}
