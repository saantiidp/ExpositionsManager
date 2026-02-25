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
import exposicion.ExposicionTemporal;
import gui.Ventana;
import gui.exposicion.DatePicker;
import sistema.Sistema;
/**
 * Clase ControladorPanelExposicionesTemporalesDadasDeAltaAdministrador
 * 
 */
public class ControladorPanelExposicionesTemporalesDadasDeAltaAdministrador implements ActionListener {

	private Ventana vista;


	DatePicker fechaCancelacion;
	DatePicker fechaInicioCierreTemporal;
	DatePicker fechaReanudacionCierreTemporal;
	DatePicker fechaProrrogacion;
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesTemporalesDadasDeAltaAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesTemporalesDadasDeAltaAdministrador(Ventana vista) {
		
		this.vista = vista;
		table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();
		model = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getModel();
		fechaCancelacion = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getFechaCancelacion();
		fechaInicioCierreTemporal = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getFechaInicioCierreTemporal();
		fechaReanudacionCierreTemporal = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getFechaReanudacionCierreTemporal();
		fechaProrrogacion = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getFechaProrrogacion();
	}
	
	
	/**
	 * Metodo que define las acciones del PanelAnaydirSalasEnExposicionesPermanentesDadasDeAltaAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Iniciar") {
			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para iniciar.");
			} else {
				if (iniciarExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede iniciar todavía.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			}
		}

		else if (e.getActionCommand() == "CerrarTemporalmente") {
			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para cerrar temporalmente.");
			} else {
				if (cerrarTemporalmenteExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede cerrar temporalmente la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			}

		} else if (e.getActionCommand() == "Cancelar") {

			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para dar de alta.");
			} else {
				if (cancelarExposicion() == false) {
					JOptionPane.showMessageDialog(null, "No se puede cancelar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			}
		
		} else if (e.getActionCommand() == "Reanudar") {
			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para reanudar.");
			} else {
				if (reanudar() == false) {
					JOptionPane.showMessageDialog(null, "No se puede reanudar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			}
			
		} else if (e.getActionCommand() == "Prorrogar") {
			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para prorrogar.");
			} else {
				if (prorrogar() == false) {
					JOptionPane.showMessageDialog(null, "No se puede prorrogar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			}
			
		} else if (e.getActionCommand() == "Finalizar") {
			JTable table = vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para finalizar.");
			} else {
				if (finalizar() == false) {
					JOptionPane.showMessageDialog(null, "No se puede finalizar la exposición.");
				}
				limpiarFormulario();
				vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador().updatePanel();
			} 
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelExposicionesTemporalesCreadasAdministrador");
			
		} else if (e.getActionCommand() == "Añadir Salas") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
			
			} else {
				int selectedRow = table.getSelectedRow();
	          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
	        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
	       		for (ExposicionTemporal expo: exposicionesEnFormatoLista) {
	    			if(expo.getNombre().equals(model.getValueAt(selectedRow,1))){
	    				
	    				vista.getPanelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador().cargarSalas(expo);
	    				vista.mostrarPanel("panelAnaydirSalasEnExposicionesTemporalesDadasDeAltaAdministrador");
	    			}
	       		}	
			}
		}
	}
	/**
	 * Metodo para cancelar una exposicion
	 */
	private boolean cancelarExposicion() {
		int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
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
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cancelar una exposición.");
        }
        return false;
    }
	/**
	 * Metodo para iniciar una exposicion
	 */
	public boolean iniciarExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.iniciada();
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para iniciar una exposición.");
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
        		JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(),
    					"Por favor, introduce una fecha de reanudación posterior a la de fecha inicio del cierre temporal.");
    			return false;
        	}
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.cerrarTemporalmente(fechaInicioCierreTemporal.getSelectedDate(), fechaReanudacionCierreTemporal.getSelectedDate());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cerrar temporalmente.");
        }
        return false;
	}
	/**
	 * Metodo para reanudar una exposicion despues de haber sido cerrada temporalmente
	 */
	public boolean reanudar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.reanudar();
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para cerrar temporalmente.");
        }
        return false;
	}
	/**
	 * Metodo para prorrogar una exposicion
	 */
	public boolean prorrogar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.prorrogar(fechaProrrogacion.getSelectedDate());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para prorrogar.");
        }
        return false;
	}
	/**
	 * Metodo para finalizar una exposicion
	 */
	public boolean finalizar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				boolean status = e.finalizar();
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return status;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para finalizar.");
        }
        return false;
	}


	/**
	 * Metodo para limpiar el formulario
	 */
	private void limpiarFormulario() {
        fechaCancelacion.setValue(LocalDate.now());
        fechaInicioCierreTemporal.setValue(LocalDate.now());
        fechaReanudacionCierreTemporal.setValue(LocalDate.now());
        fechaProrrogacion.setValue(LocalDate.now());
	}


	
	

}
