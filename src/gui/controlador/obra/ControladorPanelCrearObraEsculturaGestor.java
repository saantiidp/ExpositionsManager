package gui.controlador.obra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import gui.Ventana;
import gui.obra.PanelCrearObraEsculturaGestor;
import sistema.Sistema;

public class ControladorPanelCrearObraEsculturaGestor implements ActionListener {
	private Ventana ventana;
	private PanelCrearObraEsculturaGestor panel;
	
	public ControladorPanelCrearObraEsculturaGestor (Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelCrearObraEsculturaGestor();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String titulo = this.panel.getTituloField().getText();
		String autor = this.panel.getAutorObraField().getText();
		boolean centro = this.panel.getObraCentroField().isSelected();
		String anyo = this.panel.getAnyoField().getText();
		String descripcion = this.panel.getDescripcionField().getText();
		String cuantia = this.panel.getCuantiaField().getText();
		String nunPoliza = this.panel.getNumPolizaField().getText();
		String tMin = this.panel.gettMinField().getText();
		String hMin = this.panel.gethMinField().getText();
		String hMax = this.panel.gethMaxField().getText();
		String ancho = this.panel.getAnchoField().getText();
		String largo = this.panel.getLargoField().getText();
		String alto = this.panel.getAltoField().getText();
		String tMax = this.panel.gettMaxField().getText();
		String material = this.panel.getMaterialField().getText();
		Sistema sis = Sistema.getInstance();
		
		if (e.getActionCommand().equals("aceptar")) {
			if (titulo.equals("") && autor.equals("") && anyo.equals("") && descripcion.equals("") && cuantia.equals("") && nunPoliza.equals("") && tMin.equals("") && tMax.equals("")
					&& ancho.equals("") && largo.equals("") && alto.equals("") && material.equals("")) {
				JOptionPane.showMessageDialog(null, "Rellena todos los campos.", "Información", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				try {
					LocalDate dAnyo = LocalDate.parse(anyo, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					Double dCuantia = Double.parseDouble(cuantia);
					Double dTMin = Double.parseDouble(tMin);
					Double dTMax = Double.parseDouble(tMax);
					Double dHMin = Double.parseDouble(hMin)/100;
					Double dHMax = Double.parseDouble(hMax)/100;
					Double dAncho = Double.parseDouble(ancho);
					Double dLargo = Double.parseDouble(largo);
					Double dAlto = Double.parseDouble(alto);
					
					sis.darAltaEscultura(titulo, autor, centro, dAnyo, descripcion, dCuantia, nunPoliza, dTMin, dTMax, dHMin, dHMax, dAncho, dLargo, dAlto, material);
				}
				catch (DateTimeParseException ex) {
					JOptionPane.showMessageDialog(null, "Formato de Año incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
		        }
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Formato de Cuantia o Tempera o Humedad o Medidas incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "Cuadro Dado de Alta.", "Información", JOptionPane.INFORMATION_MESSAGE);
				this.ventana.mostrarPanel("panelCrearObraGestor");
			}
		}
		else if (e.getActionCommand().equals("cancelar")) {
			this.ventana.mostrarPanel("panelCrearObraGestor");
		}
	}
}
