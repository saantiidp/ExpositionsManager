package gui.controlador.notificaciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import gui.Ventana;
import notificaciones.Notificacion;
import sistema.Sistema;
import usuarios.ClienteRegistrado;

public class ControladorPanelNotificacionesEmpleado implements ActionListener{

    Ventana vista;
    public ControladorPanelNotificacionesEmpleado(Ventana vista) {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Notificar")) {
            JTable table = vista.getPanelNotificacionesEmpleado().getTable();
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para modificar.");
			}
            int row = table.getSelectedRow();
            String dni = (String) table.getValueAt(row, 0);
            ClienteRegistrado cliente  = Sistema.getInstance().getClienteByNif(dni);
            String mensaje = vista.getPanelNotificacionesEmpleado().getTxtMensaje().getText();
            notificar(cliente, mensaje);
        }
        
    }


    public void notificar(ClienteRegistrado cliente, String mensaje){

        JTextField txtMensaje = vista.getPanelNotificacionesEmpleado().getTxtMensaje();
        if (txtMensaje.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista.getPanelNotificacionesEmpleado(),
                "Por favor, introduce un mensaje.");
            return;
        }
        if(cliente.notificar(new Notificacion(mensaje, LocalDate.now()))){
            JOptionPane.showMessageDialog(vista.getPanelNotificacionesEmpleado(),
                "Notificación enviada correctamente.");
        }else{
           JOptionPane.showMessageDialog(vista.getPanelNotificacionesEmpleado(),
                "El cliente no tiene activadas las notificaciones.");
            return;
        }        
    }
    
}
