package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import exposicion.Exposicion;
import gui.Ventana;
import salas.Sala;
import salas.SalaHoja;
import sistema.Sistema;
/**
 * Clase ControladorPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador
 * 
 */
public class ControladorPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador implements ActionListener {

	private Ventana vista;

	
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador(Ventana vista) {
		this.vista = vista;
		table = vista.getPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador().getTable();
		model = vista.getPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador().getModel();

	}
	/**
	 * Metodo que define las acciones del PanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getActionCommand() == "Agregar") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una sala para añadir.");
				
			} else {
				agregarSala();
				vista.getPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador().cargarSalas(vista.getPanelAnaydirSalasEnExposicionesTemporalesEnBorradorDisponiblesAdministrador().getExposicion());
				
			}

		} else if (e.getActionCommand() == "Borrar") {

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
			} else
				borrarSala();

		} else if (e.getActionCommand() == "Atras") {

			vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			vista.mostrarPanel("panelExposicionesTemporalesDadasDeAltaAdministrador");
			

	}
		
	}
	/**
	 * Metodo para borrar sala
	 */
	private void borrarSala() {

		Sistema sistema = Sistema.getInstance();
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			String nombreSala = (String) model.getValueAt(selectedRow, 0);
			for (Sala sala : sistema.getSalas()) {
				if (sala.getNombre().equals(nombreSala)) {
					sistema.borrarSala(sala);
					model.removeRow(selectedRow);
					JOptionPane.showMessageDialog(null, "Sala borrada correctamente.");
					return;
				}
				for (Sala subsala : sala.getSubsalas()) {
					if ((subsala.getNombre() + " [" + subsala.getId() + "]").equals(nombreSala)) {
						JOptionPane.showMessageDialog(null, "Borrar subsala desde la pestaña de subsalas.");
						return;
					}
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
		}

	}
	/**
	 * Metodo para agregar sala a la exposición
	 */
	public void agregarSala() {
		int selectedRow = table.getSelectedRow();
		Sistema sistema = Sistema.getInstance();
		if (selectedRow != -1) {
			String nombreSala = model.getValueAt(selectedRow, 0).toString();
	        for (Sala sala : sistema.getSalas()) {
	        	if(sala.esHoja()) {
	        		if (sala.getNombre().equals(nombreSala)) {
	        			Exposicion exposicion = vista.getPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador().getExposicion();
	        			exposicion.anadirSalaExposición((SalaHoja)sala);
	        		}
				}
			}
	        
		} else {
			JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para añadir sala.");
		}
	}

}
