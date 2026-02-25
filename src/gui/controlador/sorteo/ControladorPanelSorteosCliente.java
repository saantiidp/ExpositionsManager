package gui.controlador.sorteo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.Ventana;
import sistema.Sistema;
import sorteos.Sorteo;
import usuarios.ClienteRegistrado;

public class ControladorPanelSorteosCliente implements ActionListener{

    Ventana vista;

    /**
     * Constructor de ControladorPanelSorteosCliente
     * @param vista  (Ventana)
     */
    public ControladorPanelSorteosCliente(Ventana vista) {
        this.vista = vista;
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("atras")) {
            vista.mostrarPanel("panelMenuPrincipalCliente");
        } else if(e.getActionCommand().equals("participar")) {
            JTable tabla = vista.getPanelSorteosCliente().getTable();
            int fila =  tabla.getSelectedRow();
            int numEntradas = (int)vista.getPanelSorteosCliente().getNumEntradas().getValue();
            if(fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un sorteo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long idSorteo = (Long) tabla.getValueAt(fila, 0);
            Sistema s = Sistema.getInstance();
            Sorteo sorteo = s.getSorteos().get(idSorteo);
            if(sorteo.inscribirse((ClienteRegistrado)s.getUsuarioActivo(), numEntradas)) {
                vista.getPanelSorteosCliente().cargarSorteos();
                JOptionPane.showMessageDialog(vista, "Inscripcion realizada con exito", "Inscripcion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "No se ha podido realizar la inscripcion. Consulte sus notificaciones", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
	}

}
