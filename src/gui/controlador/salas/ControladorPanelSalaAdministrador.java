package gui.controlador.salas;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.Ventana;
import gui.salas.PanelSubsalasAdministrador;
import salas.Sala;
import sistema.Sistema;


/**
 * Clase ControladorPanelSalaAdministrador
 */
public class ControladorPanelSalaAdministrador implements ActionListener {


	/**
	 * Atributos de ControladorPanelSalaAdministrador
	 */
	private Ventana vista;


	/**
	 * JTextField txtNombre, txtAforo, txtTomas, txtTemp, txtHumedad, txtAncho, txtLargo, txtAlto
	 */
	JTextField txtNombre;
	JTextField txtAforo;
	JTextField txtTomas;
	JTextField txtTemp;
	JTextField txtHumedad;
	JTextField txtAncho;
	JTextField txtLargo;
	JTextField txtAlto;
	JCheckBox chkHumedad;
	JCheckBox chkTemp;



	/**
	 * JTable table
	 */
	JTable table;

	/**
	 * DefaultTableModel model
	 */
	DefaultTableModel model;


	/**
	 * Constructor de ControladorPanelSalaAdministrador
	 * @param vista (Ventana)
	 */
	public ControladorPanelSalaAdministrador(Ventana vista) {
		this.vista = vista;
		txtNombre = vista.getPanelSalaAdministrador().getTxtNombre();
		txtAforo = vista.getPanelSalaAdministrador().getTxtAforo();
		txtTomas = vista.getPanelSalaAdministrador().getTxtTomas();
		txtTemp = vista.getPanelSalaAdministrador().getTxtTemp();
		txtHumedad = vista.getPanelSalaAdministrador().getTxtHumedad();
		txtAncho = vista.getPanelSalaAdministrador().getTxtAncho();
		txtLargo = vista.getPanelSalaAdministrador().getTxtLargo();
		txtAlto = vista.getPanelSalaAdministrador().getTxtAlto();
		chkHumedad = vista.getPanelSalaAdministrador().getChkHumedad();
		chkTemp = vista.getPanelSalaAdministrador().getChkTemp();
		table = vista.getPanelSalaAdministrador().getTable();
		model = vista.getPanelSalaAdministrador().getModel();

	}


	/**
	 * Metodo que define las acciones del ControladorPanelSalaAdministrador
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "Modificar") {
			JTable table = vista.getPanelSalaAdministrador().getTable();

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para modificar.");

			} else if (!camposRellenos() && table.getSelectedRow() != -1)
				JOptionPane.showMessageDialog(null, "Por favor rellena todos los campos para modificar.");

			else {
				modificarSala();
				limpiarFormulario();
				vista.getPanelSalaAdministrador().getModel().setRowCount(0);
				vista.getPanelSalaAdministrador().cargarSalas();
			}
		}

		else if (e.getActionCommand() == "Agregar") {
			if (camposRellenos()) {
				agregarSala();
			} else {
				JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos para añadir la sala.");
			}

		}

		else if (e.getActionCommand() == "Subsalas") {

			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una sala para ver sus subsalas.");
			} else {

				Sistema sistema = Sistema.getInstance();
				long id = (long) model.getValueAt(table.getSelectedRow(), 0);
				String nombreSala = (String) model.getValueAt(table.getSelectedRow(), 1);

				if (model.getValueAt(table.getSelectedRow(), 11).equals("Hoja")) {
					JOptionPane.showMessageDialog(null, "No se pueden añadir subsalas a una sala hoja.");
					return;
				}
				for (Sala sala : sistema.getSalas()) {
					if (sala.getId() == id && sala.getNombre() == nombreSala) {
						PanelSubsalasAdministrador panelSubsalas = vista.getPanelSubsalasAdministrador();

						panelSubsalas.getListModel().clear();
						if (sala.getSubsalas() != null) {
							for (Sala subsala : sala.getSubsalas()) {
								panelSubsalas.getListModel()
										.addElement(subsala.getNombre() + " - " + "[" + subsala.getId() + "]");
							}
						}
						panelSubsalas.getListaSubsalas().setModel(panelSubsalas.getListModel());
						vista.mostrarPanel("panelSubsalasAdmin");
						return;
					}

				}

			}

		}
		else if (e.getActionCommand() == "volver") {
			vista.mostrarPanel("panelAccionesAdmin");
		}
	}


	/**
	 * Método que limpia los campos del formulario
	 
	 */
	private void limpiarFormulario() {

		txtNombre.setText("");
		txtAforo.setText("");
		txtTomas.setText("");
		txtTemp.setText("");
		txtHumedad.setText("");
		chkHumedad.setSelected(false);
		chkTemp.setSelected(false);
		txtAncho.setText("");
		txtLargo.setText("");
		txtAlto.setText("");
	}


	/**
	 * Método que comprueba si los campos del formulario están rellenos
	 * @return boolean
	 */
	private boolean camposRellenos() {
		JTextField txtNombre = vista.getPanelSalaAdministrador().getTxtNombre();
		JTextField txtAforo = vista.getPanelSalaAdministrador().getTxtAforo();
		JTextField txtTomas = vista.getPanelSalaAdministrador().getTxtTomas();
		JTextField txtTemp = vista.getPanelSalaAdministrador().getTxtTemp();
		JTextField txtHumedad = vista.getPanelSalaAdministrador().getTxtHumedad();
		JTextField txtAncho = vista.getPanelSalaAdministrador().getTxtAncho();
		JTextField txtLargo = vista.getPanelSalaAdministrador().getTxtLargo();
		JTextField txtAlto = vista.getPanelSalaAdministrador().getTxtAlto();

		return !txtNombre.getText().isEmpty() &&
				!txtAforo.getText().isEmpty() &&
				!txtTomas.getText().isEmpty() &&
				!txtTemp.getText().isEmpty() &&
				!txtHumedad.getText().isEmpty() &&
				!txtAncho.getText().isEmpty() &&
				!txtLargo.getText().isEmpty() &&
				!txtAlto.getText().isEmpty();
	}


	/**
	 * Método que agrega una sala
	 */
	public void agregarSala() {

		String nombre = txtNombre.getText();
		if (txtAforo.getText().matches("[a-zA-Z]+") || txtTomas.getText().matches("[a-zA-Z]+") ||
			txtTemp.getText().matches("[a-zA-Z]+") || txtHumedad.getText().matches("[a-zA-Z]+") ||
			txtAncho.getText().matches("[a-zA-Z]+") || txtLargo.getText().matches("[a-zA-Z]+") ||
			txtAlto.getText().matches("[a-zA-Z]+")) {
			JOptionPane.showMessageDialog(null,
				"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
		}

		if (Integer.parseInt(txtAforo.getText()) < 0 || Integer.parseInt(txtTomas.getText()) < 0 ||
				Double.parseDouble(txtTemp.getText()) < 0 || Double.parseDouble(txtHumedad.getText()) < 0
				|| Double.parseDouble(txtAncho.getText()) < 0 ||
				Double.parseDouble(txtLargo.getText()) < 0 || Double.parseDouble(txtAlto.getText()) < 0) {
			JOptionPane.showMessageDialog(null, "Por favor, introduce valores válidos en los campos numéricos.");
			return;
		}
		int aforo = Integer.parseInt(txtAforo.getText());
		int tomas = Integer.parseInt(txtTomas.getText());
		double temp = Double.parseDouble(txtTemp.getText());
		double humedad = Double.parseDouble(txtHumedad.getText());
		double ancho = Double.parseDouble(txtAncho.getText());
		double largo = Double.parseDouble(txtLargo.getText());
		double alto = Double.parseDouble(txtAlto.getText());
		boolean controlTemp = chkTemp.isSelected();
		boolean controlHum = chkHumedad.isSelected();
		Sistema sistema = Sistema.getInstance();
		sistema.crearSala(false, nombre, aforo, tomas, ancho, largo, alto, temp, humedad, controlTemp, controlHum);
		long id = sistema.getSalas().get(sistema.getSalas().size() - 1).getId();

		model.addRow(new Object[] {
				id,
				nombre,
				aforo,
				tomas,
				temp + "ºC",
				humedad + "%",
				controlHum ? "Sí" : "No",
				controlTemp ? "Sí" : "No",
				ancho + "m",
				largo + "m",
				alto + "m",
				"Sala"

		});
		limpiarFormulario();
	}


	/**
	 * Método que modifica una sala
	 */
	private void modificarSala() {

		
		if (txtAforo.getText().matches("[a-zA-Z]+") || txtTomas.getText().matches("[a-zA-Z]+") ||
				txtTemp.getText().matches("[a-zA-Z]+") || txtHumedad.getText().matches("[a-zA-Z]+") ||
				txtAncho.getText().matches("[a-zA-Z]+") || txtLargo.getText().matches("[a-zA-Z]+") ||
				txtAlto.getText().matches("[a-zA-Z]+")) {
			JOptionPane.showMessageDialog(vista.getPanelSalaAdministrador(),
					"Por favor, introduce valores numéricos en los campos correspondientes.");
			return;
		}

		if (Integer.parseInt(txtAforo.getText()) < 0 || Integer.parseInt(txtTomas.getText()) < 0 ||
				Double.parseDouble(txtTemp.getText()) < 0 || Double.parseDouble(txtHumedad.getText()) < 0
				|| Double.parseDouble(txtAncho.getText()) < 0 ||
				Double.parseDouble(txtLargo.getText()) < 0 || Double.parseDouble(txtAlto.getText()) < 0) {
			JOptionPane.showMessageDialog(vista.getPanelSalaAdministrador(),
					"Por favor, introduce valores válidos en los campos numéricos.");
			return;
		}

		JCheckBox chkHumedad = vista.getPanelSalaAdministrador().getChkHumedad();
		JCheckBox chkTemp = vista.getPanelSalaAdministrador().getChkTemp();
		long id = (long) model.getValueAt(table.getSelectedRow(), 0);
		double temp = Double.parseDouble(txtTemp.getText());
		double humedad = Double.parseDouble(txtHumedad.getText());
		Sistema sistema = Sistema.getInstance();
		List<Sala> salas = sistema.getSalas();

		for (Sala sala : salas) {
			if (sala.getId() == id && sala.getNombre() == (String) model.getValueAt(table.getSelectedRow(), 1)) {
				boolean b = sala.modificarCaracteristicasSala(temp, humedad, chkTemp.isSelected(),
						chkHumedad.isSelected());
				if (!b) {
					JOptionPane.showMessageDialog(vista.getPanelSalaAdministrador(),
							"No se han podido modificar las características de la sala. Compruebe que la sala permite controlar la temperatura y la humedad.");
					return;
				}
				JOptionPane.showMessageDialog(vista.getPanelSalaAdministrador(),
						"Características de la sala modificadas.");
				return;
			}
		}
	}

}
