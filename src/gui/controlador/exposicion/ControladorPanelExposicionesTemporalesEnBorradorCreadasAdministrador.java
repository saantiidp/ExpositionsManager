package gui.controlador.exposicion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exposicion.ExposicionTemporal;
import gui.Ventana;
import gui.exposicion.DatePicker;
import sistema.Sistema;
/**
 * Clase ControladorPanelExposicionesTemporalesEnBorradorCreadasAdministrador
 * 
 */
public class ControladorPanelExposicionesTemporalesEnBorradorCreadasAdministrador implements ActionListener {

	private Ventana vista;

	JTextField txtNombre;
	JTextField txtDescripcion;
	JTextField txtPrecio;
	DatePicker fechaInicio;
	DatePicker fechaFin;
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesTemporalesEnBorradorCreadasAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesTemporalesEnBorradorCreadasAdministrador(Ventana vista) {
		this.vista = vista;
		txtNombre = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtNombre();
		txtDescripcion = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtDescripcion();
		txtPrecio= vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtPrecio();
		fechaInicio = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getFechaInicio();
		fechaFin = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getFechaFin();
		table = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTable();
		model = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getModel();
		
	}
	
	/**
	 * Metodo que define las acciones del PanelExposicionesTemporalesEnBorradorCreadasAdministrador
	 * @param e (ActionEvent)
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Modificar") {
			JTable table = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para modificar.");
			} else if (!camposRellenos() && table.getSelectedRow() != -1)
				JOptionPane.showMessageDialog(null, "Por favor rellena todos los campos para modificar.");

			else
				
				modificarExposicion();
			vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().updatePanel();
		}

		else if (e.getActionCommand() == "Agregar") {
			if (camposRellenos()) {
				agregarExposicion();
				vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().updatePanel();
			} else {
				JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos para añadir la exposición.");
			}

		} else if (e.getActionCommand() == "Borrar") {

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
			} else
				borrarExposicion();
			    vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().updatePanel();
		
		} else if (e.getActionCommand() == "Dar de Alta") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para dar de alta.");
			} else {
				darDeAltaExposicion();
				vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().updatePanel();
			}
			
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelExposicionesTemporalesCreadasAdministrador");
			
		} else if (e.getActionCommand() == "Añadir Salas") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para añadir sala.");
			
			} else {
				int selectedRow = table.getSelectedRow();
	          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporalBorrador().values();
	        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
	       		for (ExposicionTemporal expo: exposicionesEnFormatoLista) {
	    			if(expo.getNombre().equals(model.getValueAt(selectedRow,1))){
	    				
	    				vista.getPanelAnaydirSalasEnExposicionesTemporalesEnBorradorDisponiblesAdministrador().cargarSalas(expo);
	    				vista.mostrarPanel("panelAnaydirSalasEnExposicionesTemporalesEnBorradorDisponiblesAdministrador");
	    			}
	       		}	
			}
		}
	}
	/**
	 * Metodo para borrar exposicion
	 */
	private void borrarExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporalBorrador().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				Sistema.getInstance().borrarExposicionTemporalBorrador(e.getId());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return;
    				
    			}
    		}
    		
    		
    		
        } else {
            JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(), "Por favor selecciona una fila para borrar.");
        }
    }
	/**
	 * Metodo para dar de alta una exposicion
	 */
	public void darDeAltaExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporalBorrador().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				Sistema.getInstance().publicarExposiciónTemporal(e.getId(), e.getFechaInicio(), e.getFechaFin());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(), "Por favor selecciona una fila para dar de alta.");
     }
	}
	/**
	 * Metodo para limpiar formulario
	 */
	private void limpiarFormulario() {

		txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        fechaInicio.setValue(LocalDate.now());
        fechaFin.setValue(LocalDate.now());
	}
	/**
	 * Metodo para comprobar que s ehan rellenado los campos
	 */
	private boolean camposRellenos() {
		JTextField txtNombre = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtNombre();
		JTextField txtDescripcion = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtDescripcion();
		JTextField txtPrecio = vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador().getTxtPrecio();


		return !txtNombre.getText().isEmpty() &&
				!txtDescripcion.getText().isEmpty() &&
				!txtPrecio.getText().isEmpty();
	}
	/**
	 * Metodo para crear una exposicion
	 */
	private void agregarExposicion() {
		String nombre = txtNombre.getText();
	    String descripcion = txtDescripcion.getText();
	    
	    if(fechaFin.getSelectedDate().isBefore(fechaInicio.getSelectedDate())) {
	    	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce una fecha de fin posterior a la de inicio.");
			return;
	    }
	    
	        
		if (txtPrecio.getText().matches("[a-zA-Z]+")) {
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
        }
		
		if (Integer.parseInt(txtPrecio.getText()) < 0 ){
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores válidos en los campos numéricos.");
			return;
        }
		
	
		
		Double precio = Double.parseDouble(txtPrecio.getText());
		
		
    	Sistema.getInstance().crearExposicion(nombre, descripcion, precio, true);
    	
    	
    	List<Map.Entry<Long, ExposicionTemporal>> listaExposiciones = new ArrayList<>(Sistema.getInstance().getExposicionesTemporalBorrador().entrySet());
    	Map.Entry<Long, ExposicionTemporal> ultimaExposicion = listaExposiciones.get(listaExposiciones.size() - 1);
    	ExposicionTemporal exposicion =ultimaExposicion.getValue();
    	exposicion.setFechaInicio(fechaInicio.getSelectedDate());
    	exposicion.setFechaFin(fechaFin.getSelectedDate());
    	model.addRow(new Object[] {
    			exposicion.getId(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                exposicion.getAforo(),
                Double.parseDouble(txtPrecio.getText()),
                fechaInicio.getSelectedDate(),
                fechaFin.getSelectedDate(),
                exposicion.getEstado(), 
        });
      
        limpiarFormulario();
    }
	/**
	 * Metodo para modificar una exposicion
	 */
	private void modificarExposicion() {
        if (txtPrecio.getText().matches("[a-zA-Z]+")) {
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
        }
        
        if (Integer.parseInt(txtPrecio.getText()) < 0 ){
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores válidos en los campos numéricos.");
			return;
        }
        
        if(fechaFin.getSelectedDate().isBefore(fechaInicio.getSelectedDate())) {
	    	JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
					"Por favor, introduce una fecha de fin anterior a la de inicio.");
			return;
	    }
	    
        
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        Double precio = Double.parseDouble((txtPrecio.getText().toString()));
        
        
    	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporalBorrador().values();
    	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    	
		int selectedRow = table.getSelectedRow();

		if (selectedRow != -1 && camposRellenos()) {
			for (ExposicionTemporal e: exposicionesEnFormatoLista) {
				if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
					e.setNombre(nombre);
					e.setDescripcion(descripcion);
					e.setPrecio(precio);
	                e.setFechaInicio(fechaInicio.getSelectedDate());
	                e.setFechaFin(fechaFin.getSelectedDate());
					model.setValueAt(txtNombre.getText(), selectedRow, 1);
		            model.setValueAt(txtDescripcion.getText(), selectedRow, 2);
		            model.setValueAt(precio, selectedRow, 3);
		            model.setValueAt(fechaInicio.getSelectedDate(), selectedRow, 4);
		            model.setValueAt(fechaFin.getSelectedDate(), selectedRow, 5);
		            JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
		            		"Exposición modificada correctamente.");
					
				}
				limpiarFormulario();
			}
		}else {
			JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesEnBorradorCreadasAdministrador(),
				"Por favor selecciona una fila para modificar.");
		}
    }


}
