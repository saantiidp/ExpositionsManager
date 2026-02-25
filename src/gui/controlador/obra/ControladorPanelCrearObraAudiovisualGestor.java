package gui.controlador.obra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import gui.Ventana;
import gui.obra.PanelCrearObraAudiovisualGestor;
import sistema.Sistema;

public class ControladorPanelCrearObraAudiovisualGestor implements ActionListener {
	private Ventana ventana;
	private PanelCrearObraAudiovisualGestor panel;
	
	public ControladorPanelCrearObraAudiovisualGestor(Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelCrearObraAudiovisualGestor();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String titulo = this.panel.getTituloField().getText();
		String autor = this.panel.getAutorField().getText();
		String anyo = this.panel.getAñoField().getText();
		String cuantia = this.panel.getCuantiaField().getText();
		String descripcion = this.panel.getDescripcionField().getText();
		String duracion = this.panel.getDuracionField().getText();
		String idioma = this.panel.getIdiomaField().getText();
		String numPoliza = this.panel.getNumPolizaField().getText();
		Sistema sis = Sistema.getInstance();
		boolean centro = this.panel.getDelCentro().isSelected();
		
		if (e.getActionCommand().equals("aceptar")) {
			if (autor.equals("") && anyo.equals("") && cuantia.equals("") && descripcion.equals("") && duracion.equals("")
					&& idioma.equals("") && numPoliza.equals("")){
				JOptionPane.showMessageDialog(null, "Rellena todos los campos.", "Información", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				try {					
					sis.darAltaAudiovisual(titulo, autor, centro, LocalDate.parse(anyo, DateTimeFormatter.ofPattern("dd/MM/yyyy")), descripcion, Double.parseDouble(cuantia), numPoliza, LocalTime.parse(duracion, DateTimeFormatter.ofPattern("HH:mm:ss")), idioma);
				}
				catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(null, "Formato de Año o Duracion incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Formato de Cuantia incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				JOptionPane.showMessageDialog(null, "Cuadro Dado de Alta.", "Información", JOptionPane.INFORMATION_MESSAGE);
				this.ventana.mostrarPanel("panelCrearObraGestor");
			}
		}
		else if (e.getActionCommand().equals("cancelar")) {
			this.ventana.mostrarPanel("panelCrearObraGestor");
		}
	}

//	if (!sis.darAltaObraConFichero(path)) {						
//		JOptionPane.showMessageDialog(null, "No se ha podido cargar el fichero, comprueba que este bien escrito el nombre o que se encuentre en el directorio /src", "Error", JOptionPane.ERROR_MESSAGE);
//		return;
//	}
//	else {
//		JOptionPane.showMessageDialog(null, "Obras cargadas desde el fichero.", "Información", JOptionPane.INFORMATION_MESSAGE);
//		return;
//	}
}
