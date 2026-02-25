package gui.controlador.exposicion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exposicion.ExposicionPermanente;
import gui.Ventana;

import sistema.Sistema;
/**
 * Clase ControladorPanelExposicionesPermanentesEnBorradorCreadasAdministrador
 * 
 */
public class ControladorPanelExposicionesPermanentesEnBorradorCreadasAdministrador implements ActionListener {

	private Ventana vista;

	JTextField txtNombre;
	JTextField txtDescripcion;
	JTextField txtPrecio;

	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesPermanentesEnBorradorCreadasAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesPermanentesEnBorradorCreadasAdministrador(Ventana vista) {
		this.vista = vista;
		txtNombre = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtNombre();
		txtDescripcion = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtDescripcion();
		txtPrecio= vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtPrecio();
		table = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTable();
		model = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getModel();

	}
	
	
	/**
	 * Metodo que define las acciones del PanelExposicionesPermanentesEnBorradorCreadasAdministrador
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Modificar") {
			JTable table = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para modificar.");
			} else if (!camposRellenos() && table.getSelectedRow() != -1)
				JOptionPane.showMessageDialog(null, "Por favor rellena todos los campos para modificar.");

			else
				
				modificarExposicion();
			vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
		}

		else if (e.getActionCommand() == "Agregar") {
			if (camposRellenos()) {
				agregarExposicion();
				vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
			} else {
				JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos para añadir la exposición.");
			}

		} else if (e.getActionCommand() == "Borrar") {

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para borrar.");
			} else
				borrarExposicion();
			    vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
		
		} else if (e.getActionCommand() == "Dar de Alta") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para dar de alta.");
			} else {
				darDeAltaExposicion();
				vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().updatePanel();
			}
			
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelExposicionesPermanentesCreadasAdministrador");
			
		} else if (e.getActionCommand() == "Añadir Salas") {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para añadir salas.");
			
			} else {
				int selectedRow = table.getSelectedRow();
	          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanenteBorrador().values();
	        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
	       		for (ExposicionPermanente expo: exposicionesEnFormatoLista) {
	    			if(expo.getNombre().equals(model.getValueAt(selectedRow,1))){
	    				
	    				vista.getpanelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador().cargarSalas(expo);
	    				vista.mostrarPanel("panelAnaydirSalasEnExposicionesPermanentesEnBorradorDisponiblesAdministrador");
	    			}
	       		}	
			}
		}
	}
	/**
	 * Metodo para borrar exposiciones
	 */
	private void borrarExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanenteBorrador().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				Sistema.getInstance().borrarExposicionPermanenteBorrador(e.getId());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return;
    				
    			}
    		}
    		
    		
    		
        } else {
            JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(), "Por favor selecciona una fila para borrar.");
        }
    }
	/**
	 * Metodo para dar de alta exposiciones
	 */
	public void darDeAltaExposicion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
          	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanenteBorrador().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
    				Sistema.getInstance().publicarExposiciónPermanente(e.getId());
    				model.removeRow(selectedRow);
    				limpiarFormulario();
    				return;
    			}
    		}
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(), "Por favor selecciona una fila para dar de alta.");
     }
	}
	/**
	 * Metodo para limpiar formulario
	 */
	private void limpiarFormulario() {

		txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
	}
	/**
	 * Metodo para verificar que los campos se han rellenado
	 */

	private boolean camposRellenos() {
		JTextField txtNombre = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtNombre();
		JTextField txtDescripcion = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtDescripcion();
		JTextField txtPrecio = vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador().getTxtPrecio();


		return !txtNombre.getText().isEmpty() &&
				!txtDescripcion.getText().isEmpty() &&
				!txtPrecio.getText().isEmpty();
	}
	/**
	 * Metodo paraagregar exposiciones
	 */
	private void agregarExposicion() {
		String nombre = txtNombre.getText();
	    String descripcion = txtDescripcion.getText();
	        
		if (txtPrecio.getText().matches("[a-zA-Z]+")) {
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
        }
		
		if (Integer.parseInt(txtPrecio.getText()) < 0 ){
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores válidos en los campos numéricos.");
			return;
        }
		
		Double precio = Double.parseDouble(txtPrecio.getText());
		
		
    	Sistema.getInstance().crearExposicion(nombre, descripcion, precio, false);
    	
    	List<Map.Entry<Long, ExposicionPermanente>> listaExposiciones = new ArrayList<>(Sistema.getInstance().getExposicionesPermanenteBorrador().entrySet());
    	Map.Entry<Long, ExposicionPermanente> ultimaExposicion = listaExposiciones.get(listaExposiciones.size() - 1);
    	ExposicionPermanente exposicion =ultimaExposicion.getValue();
    	model.addRow(new Object[] {
    			exposicion.getId(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                0,
                Double.parseDouble(txtPrecio.getText()),
                exposicion.getEstado(), 
        });
      
        limpiarFormulario();
    }
	/**
	 * Metodo para modificar exposiciones
	 */
	private void modificarExposicion() {
        if (txtPrecio.getText().matches("[a-zA-Z]+")) {
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
        }
        
        if (Integer.parseInt(txtPrecio.getText()) < 0 ){
        	JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
					"Por favor, introduce valores válidos en los campos numéricos.");
			return;
        }
        
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        Double precio = Double.parseDouble((txtPrecio.getText().toString()));
        
        
    	Collection <ExposicionPermanente> valores = Sistema.getInstance().getExposicionesPermanenteBorrador().values();
    	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista = new ArrayList<>(valores);
    	
		int selectedRow = table.getSelectedRow();

		if (selectedRow != -1 && camposRellenos()) {
			for (ExposicionPermanente e: exposicionesEnFormatoLista) {
				if(e.getNombre().equals(model.getValueAt(selectedRow,1))){
					e.setNombre(nombre);
					e.setDescripcion(descripcion);
					e.setPrecio(precio);
					model.setValueAt(txtNombre.getText(), selectedRow, 1);
		            model.setValueAt(txtDescripcion.getText(), selectedRow, 2);
		            model.setValueAt(precio, selectedRow, 3);
		            JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
		            		"Exposición modificada correctamente.");
					
				}
				limpiarFormulario();
				return;
			}
		}else {
			JOptionPane.showMessageDialog(vista.getPanelExposicionesPermanentesEnBorradorCreadasAdministrador(),
				"Por favor selecciona una fila para modificar.");
		}
    }


}
