package gui.controlador.sorteo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.Ventana;
import sistema.Sistema;
import sorteos.Sorteo;

public class ControladorPanelEmpezarSorteo implements ActionListener{

    Ventana ventana;
    public ControladorPanelEmpezarSorteo(Ventana ventana) {
        this.ventana = ventana;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
         
        if (e.getActionCommand().equals("sortear")) {
            JTable tabla = ventana.getPanelEmpezarSorteo().getTable();

            if (tabla.getSelectedRow() == -1) {
             JOptionPane.showMessageDialog(null, "Por favor selecciona una fila para sortear.");
                return;
            }    

            else{
                int fila =  tabla.getSelectedRow();
                long idSorteo = (Long) tabla.getValueAt(fila, 0);
                Sorteo sorteo = Sistema.getInstance().getSorteos().get(idSorteo);
                if(sorteo.isSorteado()){
                    JOptionPane.showMessageDialog(null, "El sorteo ya fue realizado.");
                    return;
                }
                if(sorteo.getInscripciones().size() == 0){
                    JOptionPane.showMessageDialog(null, "No hay participantes para sortear.");
                    return;
                }
                
                if(sorteo.sortear().size() == 0){
                    JOptionPane.showMessageDialog(null, "Error al sortear. Asegurese de introducir bien las fechas", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "Sorteo realizado con exito.");
                ventana.getPanelEmpezarSorteo().cargarSorteos(sorteo.getExposicion());
            }
        }
        else if (e.getActionCommand().equals("atras")){
            this.ventana.mostrarPanel("panelSorteosAdmin");
        }

    }
    
}
