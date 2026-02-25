package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import gui.Ventana;
import gui.usuario.adminsitrador.PanelContabilidadAdministrador;
import sistema.ContabilidadSistema;
import sistema.Sistema;



/**
 * Clase ControladorPanelContablilidadAdministrador
 */
public class ControladorPanelContablilidadAdministrador implements ActionListener {

	/**
	 * Atributos de ControladorPanelContablilidadAdministrador
	 */

	 /**
	  *  ventana clase Ventana
	  
	  */
	private Ventana ventana;

	/**
	 * panel clase PanelContabilidadAdministrador
	 */
	private PanelContabilidadAdministrador panel;
	

	/**
	 * Constructor de ControladorPanelContablilidadAdministrador
	 * @param v (Ventana)
	 */
	public ControladorPanelContablilidadAdministrador(Ventana v) {
		this.ventana = v;
		this.panel = v.getPanelContabilidadAdministrador();
	}
	

	/**
	 * Metodo que define las acciones del ControladorPanelContablilidadAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalDate inicio = panel.getInicio().getSelectedDate();
		LocalDate fin = panel.getFin().getSelectedDate();
		ContabilidadSistema contabilidad = Sistema.getInstance().getContabilidad();
		
		if (e.getActionCommand().equals("totales")) {
			Double d = contabilidad.getBeneficioTotalExposiciones(inicio, fin);
			Integer t = contabilidad.getEntradasTotalExposiciones(inicio, fin);
			this.panel.mostrarString("Beneficio Total (" + inicio + " a " + fin + ") : " + d
					,"    Entradas Totales (" + inicio + " a " + fin + ") : " + t);
		}
		else if (e.getActionCommand().equals("exposicion")) {
			this.panel.fillModel();
			this.panel.getExposiciones().setVisible(true);
		}
		else if (e.getActionCommand().equals("cancelar")) {
			this.ventana.mostrarPanel("panelAccionesAdmin");
		}
	}

}
