package gui.controlador.entradas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import entrada.Entrada;
import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import gui.Controlador;
import gui.Ventana;
import sistema.Sistema;
import usuarios.ClienteRegistrado;

/**
 * Clase guiControladorPanelEntradasCliente
 * 
 */
public class ControladorPanelEntradasCliente implements ActionListener{
	private Ventana vista;
	private Controlador c;

	/**
	 * Constructor de guiControladorPanelEntradasCliente
	 * @param vista (Ventana)
	 * @param c (Controlador)
	 */
	public ControladorPanelEntradasCliente(Ventana vista, Controlador c) {
		this.vista = vista;
		this.c = c;
	}
	
	/**
	 * Metodo que define las acciones realiza en ControladorEntradas
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ClienteRegistrado cliente = (ClienteRegistrado) Sistema.getInstance().getUsuarioActivo();
		Entrada entrada = cliente.getEntradasCompradas().get(Integer.parseInt(e.getActionCommand()));
		try {
			entrada.generarEntradaFormatoPDF();
		} catch (NonExistentFileException | UnsupportedImageTypeException e1) {
			JOptionPane.showMessageDialog(vista, "Nose ha podido generar el pdf", "Error",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		vista.getPanelEntradasCliente().updatePanel();
		vista.getPanelEntradasCliente().setControlador(c.getPanelEntradasCliente());
		 
		JOptionPane.showMessageDialog(vista.getPanelEntradasCliente(),
                 "Entrada descargada en formato pdf.");
         return;
	}


}
