package gui.controlador.exposicion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import gui.Ventana;
import gui.exposicion.DatePicker;
import sistema.Sistema;
import usuarios.ClienteRegistrado;
/**
 * ClaseControladorPanelExposicionesClientes
 * 
 */
public class ControladorPanelExposicionesClientes implements ActionListener {

	private Ventana vista;

	JSpinner spinnerHoras;
	JSpinner spinnerObras;
	JSpinner spinnerHoraCompra;
	DatePicker diaDeFiltro;
	DatePicker diaDeCompra;
	JCheckBox chkTemporal;
	JTextField txtNumTarjeta;
	JTextField txtCodigoSorteo;
	JSpinner spinnerNumEntradas;
	
	JTable table;
	DefaultTableModel model;
	/**
	 * Constructor ControladorPanelExposicionesClientes
	 * @param vista (Ventana)
	 */
	public ControladorPanelExposicionesClientes(Ventana vista) {
		
		this.vista = vista;
		table = vista.getPanelExposicionesClientes().getTable();
		model = vista.getPanelExposicionesClientes().getModel();
		diaDeFiltro = vista.getPanelExposicionesClientes().getDiaDeFiltro();
		spinnerHoras = vista.getPanelExposicionesClientes().getSpinnerHoras();
		spinnerObras = vista.getPanelExposicionesClientes().getSpinnerObras();
		chkTemporal = vista.getPanelExposicionesClientes().getChkTemporal();
		txtNumTarjeta = vista.getPanelExposicionesClientes().getTxtNumTarjeta();
		spinnerHoraCompra = vista.getPanelExposicionesClientes().getSpinnerHorasCompra();
		diaDeCompra = vista.getPanelExposicionesClientes().getDiaDeCompra();
		txtCodigoSorteo = vista.getPanelExposicionesClientes().getTxtCodigoSorteo();
		spinnerNumEntradas = vista.getPanelExposicionesClientes().getSpinnerNumEntradas();

	}
	
	/**
	 * Metodo que define las acciones del PanelExposicionesClientes
	 * @param e (ActionEvent)
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Filtrar por Fecha") {
			
				vista.getPanelExposicionesClientes().filtrarPanelPorFiltroFecha(diaDeFiltro.getSelectedDate(), (int) spinnerHoras.getValue());		
				JOptionPane.showMessageDialog(null, "Filtrado por fecha.");
				limpiarFormulario();
			
		} else if (e.getActionCommand() == "Filtrar por Tipo Obra") {
			
			vista.getPanelExposicionesClientes().filtrarPanelPorFiltroTipoObra((String)spinnerObras.getValue());		
			JOptionPane.showMessageDialog(null, "Filtrado por tipo obra.");
			limpiarFormulario();
		
		} else if (e.getActionCommand() == "Filtrar por Tipo Exposición") {
			
			vista.getPanelExposicionesClientes().filtrarPanelPorFiltroTipoExposicion((boolean)chkTemporal.isSelected());		
			JOptionPane.showMessageDialog(null, "Filtrado por tipo exposicion");
			limpiarFormulario();
		
		} else if (e.getActionCommand() == "Comprar Entradas") {
			JTable table = vista.getPanelExposicionesClientes().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para comprar entradas.");
			} else {
				if (comprarEntradas() == false) {
					JOptionPane.showMessageDialog(null, "Error al intentar comprar entradas.");
					limpiarFormulario();
				}
				else {
				limpiarFormulario();
				JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(),
                        "Compra realizada con éxito.");
				}
			}
			
		} else if (e.getActionCommand() == "Atras") {
			vista.mostrarPanel("panelMenuPrincipalCliente");
			
		}
	}
	/**
	 * Metodo para comprar entradas
	 */
	private boolean comprarEntradas() {
		int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
        	
        	if (txtNumTarjeta.getText().equals("")) {
				JOptionPane.showMessageDialog(vista.getPanelExposicionesClientes(),
	                    "Por favor, introduce el número de tarjeta para proceder al pago.");
				return false;
			}
			else if (!TeleChargeAndPaySystem.isValidCardNumber(txtNumTarjeta.getText())){
				JOptionPane.showMessageDialog(vista.getPanelExposicionesClientes(),
	                    "Por favor, introduce un número de tarjeta válido para proceder al pago."
	                    + " Debe contener 16 dígitos");
				return false;
			}
		
        	
          	Collection <ExposicionTemporal> valores = Sistema.getInstance().getExposicionesTemporales().values();
        	ArrayList<ExposicionTemporal> exposicionesEnFormatoLista = new ArrayList<>(valores);
    		for (ExposicionTemporal e: exposicionesEnFormatoLista) {
    			if(e.getNombre().equals((String)model.getValueAt(selectedRow,0))){
    				
    				if(e.getNumeroEntradasDisponibles(diaDeCompra.getSelectedDate(),(int)spinnerHoraCompra.getValue())<(int)spinnerNumEntradas.getValue()){
    					JOptionPane.showMessageDialog(vista.getPanelExposicionesClientes(),
    		                    "Por favor, introduce un número de entradas menor que el de las disponibles."
    		                    + " quedan disponibles: " + e.getNumeroEntradasDisponibles(diaDeCompra.getSelectedDate(),(int)spinnerHoraCompra.getValue()));
    					return false;
    				}
    			
					try {
						boolean status = e.comprarEntradas((ClienteRegistrado)Sistema.getInstance().getUsuarioActivo(), txtNumTarjeta.getText(), diaDeCompra.getSelectedDate(), (int)spinnerHoraCompra.getValue(), (int)spinnerNumEntradas.getValue(), txtCodigoSorteo.getText());
	    				limpiarFormulario();
	    				return status;
					} catch (OrderRejectedException e1) {
						e1.printStackTrace();
					}
    			}
    		}
    		Collection <ExposicionPermanente> valores2 = Sistema.getInstance().getExposicionesPermanentes().values();
        	ArrayList<ExposicionPermanente> exposicionesEnFormatoLista2 = new ArrayList<>(valores2);
    		for (ExposicionPermanente e: exposicionesEnFormatoLista2) {
    			if(e.getNombre().equals((String)model.getValueAt(selectedRow,0))){
    				if(e.getNumeroEntradasDisponibles(diaDeCompra.getSelectedDate(),(int)spinnerHoraCompra.getValue())<(int)spinnerNumEntradas.getValue()){
    					JOptionPane.showMessageDialog(vista.getPanelExposicionesClientes(),
    		                    "Por favor, introduce un número de entradas menor que el de las disponibles."
    		                    + " quedan disponibles: " + e.getNumeroEntradasDisponibles(diaDeCompra.getSelectedDate(),(int)spinnerHoraCompra.getValue()));
    					return false;
    				}
					try {
						boolean status = e.comprarEntradas((ClienteRegistrado)Sistema.getInstance().getUsuarioActivo(), txtNumTarjeta.getText(), diaDeCompra.getSelectedDate(), (int)spinnerHoraCompra.getValue(), (int)spinnerNumEntradas.getValue(), txtCodigoSorteo.getText());
	    				limpiarFormulario();
	    				return status;
					} catch (OrderRejectedException e1) {
						e1.printStackTrace();
					}
    			}
    		}
    		
    		
        } else {
         JOptionPane.showMessageDialog(vista.getPanelExposicionesTemporalesDadasDeAltaAdministrador(), "Por favor selecciona una fila para comprar entradas de una exposición.");
        }
        return false;
    }
	/**
	 * Metodo para limpiar formulario
	 */
	private void limpiarFormulario() {

		txtNumTarjeta.setText("");
		diaDeFiltro.setValue(LocalDate.now());
        spinnerHoras.setValue(Sistema.getInstance().getHorarioAperturaExposicion());
        chkTemporal.setSelected(false);
	}




}
