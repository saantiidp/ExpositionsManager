package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.Ventana;
import gui.usuario.adminsitrador.PanelConfigurarParametros;
import sistema.Sistema;


/**
 *  Clase ControladorPanelConfigurarParametros
 * 
 */
public class ControladorPanelConfigurarParametros implements ActionListener {


	/**
	 * Atributos de ControladorPanelConfigurarParametros
	 */
	private Ventana ventana;

	/**
	 * panel clase PanelConfigurarParametros
	 
	 */
	private PanelConfigurarParametros panel;
	

	/**
	 * Constructor de ControladorPanelConfigurarParametros
	 * @param v (Ventana)
	 */
	public ControladorPanelConfigurarParametros(Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelConfigurarParametros();
	}

	/**
	 * Metodo que define las acciones del ControladorPanelConfigurarParametros
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame f = new JFrame();
		if (e.getActionCommand().equals("aceptar")) {
			String apertura = this.panel.getHorarioAperturaField().getText();
			String cierre = this.panel.getHorarioCierreField().getText();
			String penalizacion = this.panel.getMesesPenalizacionField().getText();
			
			if (apertura.equals("") || cierre.equals("") || penalizacion.equals("")) {
				JOptionPane.showMessageDialog(f, "Rellena todos los campos", "Información", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Sistema.getInstance().configurarParametros(Integer.parseInt(apertura), Integer.parseInt(cierre), Integer.parseInt(penalizacion));
			JOptionPane.showMessageDialog(f, "Configuracion realizada con exito", "Información", JOptionPane.INFORMATION_MESSAGE);
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
		else if (e.getActionCommand().equals("cancelar")) {
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
	}

}
