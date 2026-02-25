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
 * Clase ControladorPanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador
 * 
 */
public class ControladorPanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador implements ActionListener {

	private Ventana vista;

	
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador(Ventana vista) {
		this.vista = vista;
		table = vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().getTable();
		model = vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().getModel();

	}
	/**
	 * Metodo que define las acciones del PanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrado
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getActionCommand() == "Agregar") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una sala para añadir.");
				
			} else {
				agregarSala();
				vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().cargarSalas(vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().getExposicion());
				
			}

		} else if (e.getActionCommand() == "Borrar") {

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
			} else
				borrarSala();

		} else if (e.getActionCommand() == "Atras") {

			vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
			vista.mostrarPanel("panelExposicionesPermanentesEnBorradorCreadasAdministrador");
			

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
	        			Exposicion exposicion = vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().getExposicion();
	        			exposicion.anadirSalaExposición((SalaHoja)sala);
						JOptionPane.showMessageDialog(null, "Sala añadida correctamente.");
						return;
	        		}
				}
			}
	        
		} else {
			JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para añadir sala.");
		}
	}


}
