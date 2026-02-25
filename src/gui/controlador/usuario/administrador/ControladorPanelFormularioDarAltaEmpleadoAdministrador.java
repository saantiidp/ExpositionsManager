package gui.controlador.usuario.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import gui.Ventana;
import sistema.Sistema;
import usuarios.Empleado;


/**
 * Clase ControladorPanelFormularioDarAltaEmpleadoAdministrador
 */
public class ControladorPanelFormularioDarAltaEmpleadoAdministrador  implements ActionListener{

    /**
     * Atributo vista
     */
    Ventana vista;

    /**
     * Constructor de ControladorPanelFormularioDarAltaEmpleadoAdministrador
     * @param v
     */
    public ControladorPanelFormularioDarAltaEmpleadoAdministrador(Ventana v) {
        this.vista = v;
    }

    @Override
    /**
     * Metodo que defines las acciones realizadas en ControladorPanelFormularioDarAltaEmpleadoAdministrador
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("atras")) {
            this.vista.mostrarPanel("panelDarAltaEmpleadoAdministrador");
        }
        else if (e.getActionCommand().equals("darAlta")) {
           
            String nif  = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getNifTxt().getText();
            String nombre = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getNombreTxt().getText();
            String numSegSoc = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getSegSocialTxt().getText();
            boolean permisoSalas = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getPermisoAjustarSalas().isSelected();
            boolean permisoNotificar = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getPermisoNotificar().isSelected();
            boolean permisoTaquilla = this.vista.getPanelFormularioDarAltaEmpleadosGestor().getPermisoTaquilla().isSelected();

            if(Sistema.getInstance().registrarEmpleado(new Empleado(nif, nombre, numSegSoc, nombre, numSegSoc, permisoTaquilla, permisoSalas, permisoNotificar))){
            JOptionPane.showMessageDialog(vista, "Empleado dado de alta correctamente", "Empleado dado de alta", JOptionPane.INFORMATION_MESSAGE);
            this.vista.getPanelDarAltaEmpleadoAdministrador().cargarEmpleados();
            this.vista.mostrarPanel("panelDarAltaEmpleadoAdministrador");
            }else{
                JOptionPane.showMessageDialog(vista, "Error al dar de alta el empleado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }


}
