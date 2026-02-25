package gui.controlador.salas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.Ventana;
import salas.Sala;
import sistema.Sistema;


/**
 * Clase ControladorPanelSalaEmpleado
 */
public class ControladorPanelSalaEmpleado implements ActionListener {


    /**
     * Atributos de ControladorPanelSalaEmpleado
     */
    private Ventana vista;


    /**
     * Constructor ControladorPanelSalaEmpleado
     * @param vista (Ventana)
     */
    public ControladorPanelSalaEmpleado(Ventana vista) {
        this.vista = vista;
    }


    /**
     * Método actionPerformed
     * @param e (ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Ajustar condiciones")) {
            JTable table = vista.getPanelSalaEmpleado().getTable();

            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para modificar.");
            } else if (!camposRellenos() && table.getSelectedRow() != -1)
                JOptionPane.showMessageDialog(null, "Por favor rellena todos los campos para modificar.");

            else {
                modificar();
                limpiarFormulario();
            }
            vista.getPanelSalaEmpleado().getModel().setRowCount(0);
            vista.getPanelSalaEmpleado().cargarSalas();

        }
    }


    /**
     * Método modificar
     */
    public void modificar() {
        JTextField txtTemp = vista.getPanelSalaEmpleado().getTxtTemp();
        JTextField txtHumedad = vista.getPanelSalaEmpleado().getTxtHumedad();
        if (txtTemp.getText().matches("[a-zA-Z]+") || txtHumedad.getText().matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(vista.getPanelSalaEmpleado(),
                    "Por favor, introduce valores numéricos.");
            return;
        }

        if (Double.parseDouble(txtTemp.getText()) < 0 || Double.parseDouble(txtHumedad.getText()) < 0) {
            JOptionPane.showMessageDialog(vista.getPanelSalaEmpleado(),
                    "Por favor, introduce valores válidos en los campos numéricos.");
            return;
        }

        double temp = Double.parseDouble(txtTemp.getText());
        double humedad = Double.parseDouble(txtHumedad.getText());
        DefaultTableModel model = vista.getPanelSalaEmpleado().getModel();
        JTable table = vista.getPanelSalaEmpleado().getTable();
        JCheckBox chkTemp = vista.getPanelSalaEmpleado().getChkTemp();
        JCheckBox chkHumedad = vista.getPanelSalaEmpleado().getChkHumedad();
        String nombre = (String) model.getValueAt(table.getSelectedRow(), 1);
        long id = (long) model.getValueAt(table.getSelectedRow(), 0);
        Sistema sistema = Sistema.getInstance();
        List<Sala> salas = sistema.getSalas();

        for (Sala sala : salas) {

            if (sala.getId() == id && sala.getNombre() == nombre) {
                boolean b = sala.modificarCaracteristicasSala(temp, humedad, chkTemp.isSelected(),
                        chkHumedad.isSelected());
                if (!b || !chkTemp.isSelected() || !chkHumedad.isSelected()) {
                    JOptionPane.showMessageDialog(vista.getPanelSalaEmpleado(),
                            "Solo se han modificado los permisos de control de temperatura y humedad.");
                    return;
                }
                JOptionPane.showMessageDialog(vista.getPanelSalaEmpleado(),
                        "Características de la sala modificadas.");
                return;
            }
        }
    }


    /**
     * Método limpiarFormulario
     */
    public void limpiarFormulario() {
        JTextField txtTemp = vista.getPanelSalaEmpleado().getTxtTemp();
        JTextField txtHumedad = vista.getPanelSalaEmpleado().getTxtHumedad();
        txtTemp.setText("");
        txtHumedad.setText("");
    }


    /**
     * Método camposRellenos
     * @return
     */
    private boolean camposRellenos() {
        JTextField txtTemp = vista.getPanelSalaEmpleado().getTxtTemp();
        JTextField txtHumedad = vista.getPanelSalaEmpleado().getTxtHumedad();
        if (txtTemp.getText().equals("") || txtHumedad.getText().equals("")) {
            return false;
        }
        return true;
    }
}
