package gui.controlador.sorteo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import exposicion.Exposicion;

import gui.Ventana;
import sistema.Sistema;

public class ControladorPanelSorteosAdministrador implements ActionListener {

    Ventana vista;

    public ControladorPanelSorteosAdministrador(Ventana vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("crearSorteo")) {

            JTable tabla = vista.getPanelSorteoAdmin().getTable();
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para sortear.");
                return;
            }

            String opcion = (String) vista.getPanelSorteoAdmin().getCbTipoSorteo().getSelectedItem();

            if (opcion == "Entre dos fechas") {
                vista.mostrarPanel("panelSorteoIntervaloFechas");

            }

            else if (opcion == "En un dia y hora") {
                vista.mostrarPanel("panelSorteoDiaHora");
            }

            else if (opcion == "Mientras dure la exposicion") {
                vista.mostrarPanel("panelSorteoMientrasDureLaExposicion");
            }
        }

        else if (e.getActionCommand().equals("volver")) {
            vista.mostrarPanel("panelAccionesAdmin");
        }

        if (e.getActionCommand().equals("sortear")) {
            JTable tabla = vista.getPanelSorteoAdmin().getTable();
            int fila = tabla.getSelectedRow();
            long idExpo = (Long) tabla.getValueAt(fila, 0);
            Sistema s = Sistema.getInstance();
            String tipo = (String) tabla.getValueAt(fila, 5);
            Exposicion exposicion = tipo == "Permanente" ? s.getExposicionesPermanentes().get(idExpo) : s.getExposicionesTemporales().get(idExpo);
            vista.getPanelEmpezarSorteo().cargarSorteos(exposicion);
            vista.mostrarPanel("panelEmpezarSorteo");

        }

    }

}
