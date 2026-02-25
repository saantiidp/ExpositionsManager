package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


import javax.swing.JOptionPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import es.uam.eps.padsof.telecard.OrderRejectedException;
import exposicion.ExposicionPermanente;

import gui.Ventana;
import gui.exposicion.DatePicker;
import sistema.Sistema;
/**
 * Clase ControladorPanelExposicionesPermanentesDadasDeAltaAdministrador
 * 
 */
public class ControladorPanelExposicionesPermanentesDadasDeAltaAdministrador implements ActionListener {

	private Ventana vista;


	DatePicker fechaCancelacion;
	DatePicker fechaInicioCierreTemporal;
	DatePicker fechaReanudacionCierreTemporal;
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesPermanentesDadasDeAltaAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesPermanentesDadasDeAltaAdministrador(Ventana vista) {
		this.vista = vista;
		table = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getTable();
		model = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getModel();
		fechaCancelacion = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getFechaCancelacion();
		fechaInicioCierreTemporal = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getFechaInicioCierreTemporal();
		fechaReanudacionCierreTemporal = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getFechaReanudacionCierreTemporal();
	
	}
	
	
	/**
	 * Metodo que define las acciones del PanelExposicionesPermanentesDadasDeAltaAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Iniciar") {
			JTable table = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para iniciar.");
			} else {
				if (iniciarExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede iniciar todavía.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().updatePanel();
			}
		}

		else if (e.getActionCommand() == "CerrarTemporalmente") {
			JTable table = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para cerrar temporalmente.");
			} else {
				if (cerrarTemporalmenteExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede cerrar temporalmente la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().updatePanel();
			}

		} else if (e.getActionCommand() == "Cancelar") {

			JTable table = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para dar de alta.");
			} else {
				if (cancelarExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede cancelar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().updatePanel();
			}
		
		} else if (e.getActionCommand() == "Reanudar") {
			JTable table = vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para reanudar.");
			} else {
				if (reanudar() == false) {
					JOptionPane.showMessageDialog(null, "No se puede reanudar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador().updatePanel();
			}
			
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelExposicionesPermanentesCreadasAdministrador");
			
		} else if (e.getActionCommand() == "Añadir Salas") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para añadir salas.");
			
			} else {
				int selectedRow = table.getSelectedRow();
	          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanentes().values();
	        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
	       		for (ExposicionPermanente expo: exposicionesEnFormatoLista) {
	    			if(expo.getNombre().equals(model.getValueAt(selectedRow,1))){
	    				
	    				vista.getPanelAnaydirSalasEnExposicionesPermanentesDadasDeAltaAdministrador().cargarSalas(expo);
	    				vista.mostrarPanel("panelAnaydirSalasEnExposicionesPermanentesDadasDeAltaAdministrador");
	    			}
	       		}	
			}
		}
	}
	/**
	 * Metodo para cancelar una exposición
	 */
	private boolean cancelarExposicion() {
		int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    			
					try {
						boolean status = e.cancelarExposicion(fechaCancelacion.getSelectedDate());
	    				model.removeRow(selectedRow);
	    				limpiarFormulario();
	    				return status;
					} catch (OrderRejectedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cancelar una exposición.");
        }
        return false;
    }
	/**
	 * Metodo para iniciar una exposicion
	 */
	public boolean iniciarExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.iniciada();
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador(), "Por favor selecciona una fila para iniciar una exposición.");
        }
        return false;
	}
	/**
	 * Metodo para cerrar temporalmente una exposicion
	 */
	public boolean cerrarTemporalmenteExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
        	if(fechaInicioCierreTemporal.getSelectedDate().isAfter(fechaReanudacionCierreTemporal.getSelectedDate())) {
        		JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador(),
    					"Por favor, introduce una fecha de reanudación posterior a la de fecha inicio del cierre temporal.");
    			return false;
        	}
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.cerrarTemporalmente(fechaInicioCierreTemporal.getSelectedDate(), fechaReanudacionCierreTemporal.getSelectedDate());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cerrar temporalmente.");
        }
        return false;
	}
	/**
	 * Metodo para reanudar una exposicion
	 */
	public boolean reanudar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.reanudar();
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cerrar temporalmente.");
        }
        return false;
	}


	/**
	 * Metodo para limpiar formulario
	 */
	private void limpiarFormulario() {
        fechaCancelacion.setValue(LocalDate.now());
        fechaInicioCierreTemporal.setValue(LocalDate.now());
        fechaReanudacionCierreTemporal.setValue(LocalDate.now());
	}


	
	

}
