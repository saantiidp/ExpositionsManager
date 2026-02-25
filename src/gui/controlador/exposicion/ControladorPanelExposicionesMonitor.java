package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import gui.Ventana;
import sistema.Sistema;

import usuarios.Empleado;
/**
 * Clase ControladorPanelExposicionesMonitor
 * 
 */
public class ControladorPanelExposicionesMonitor implements ActionListener {

	private Ventana vista;

	JSpinner spinnerHoraCompra;
	
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesMonitor
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesMonitor(Ventana vista) {
		
		this.vista = vista;
		table = vista.getPanelExposicionesMonitor().getTable();
		model = vista.getPanelExposicionesMonitor().getModel();
		spinnerHoraCompra = vista.getPanelExposicionesMonitor().getSpinnerHorasCompra();

	}
	
	
	/**
	 * Metodo que define las acciones del PanelExposicionesMonitor
	 * @param e (ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand() == "Comprar Entradas") {
			JTable table = vista.getPanelExposicionesMonitor().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para comprar entradas.");
			} else {
				if (comprarEntradas() == false) {
					JOptionPane.showMessageDialog(null, "Error al intentar comprar entradas.");
					
				}
				else {
				
				JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(),
                        "Compra realizada con éxito.");
				}
			}
			
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelMenuPrincipalEmpleado");
			
		}
	}
	/**
	 * Metodo para comprar entradas en taquilla
	 */
	private boolean comprarEntradas() {
		int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
        	
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals((String)model.getValueAt(selectedRow,0))){
    				
    				if(e.getNumeroEntradasDisponibles(LocalDate.now(),(int)spinnerHoraCompra.getValue())<1){
    					JOptionPane.showMessageDialog(vista.getPanelExposicionesMonitor(),
    		                    "Se han agotado las entradas");
    					return false;
    				}
    			
				
						boolean status = e.comprarEntradaTaquilla((Empleado)Sistema.getInstance().getUsuarioActivo(),(int)spinnerHoraCompra.getValue());
	    				
	    				return status;
					
    			}
    		}
    		Collection <ExposicionPermanente> valores2 = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista2 = new ArrayList<>(valores2);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista2) {
    			if(e.getNombre().equals((String)model.getValueAt(selectedRow,0))){
    				if(e.getNumeroEntradasDisponibles(LocalDate.now(),(int)spinnerHoraCompra.getValue())<1){
    					JOptionPane.showMessageDialog(vista.getPanelExposicionesMonitor(),
    							"Se han agotado las entradas");
    					return false;
    				}
    				
					boolean status = e.comprarEntradaTaquilla((Empleado)Sistema.getInstance().getUsuarioActivo(),(int)spinnerHoraCompra.getValue());
    				
    				return status;
					
    			}
    		}
    		
    		
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesMonitor(), "Por favor selecciona una fila para comprar entradas de una exposición.");
        }
        return false;
    }
	





}
