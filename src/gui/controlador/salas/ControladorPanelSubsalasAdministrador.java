package gui.controlador.salas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import gui.Ventana;
import gui.salas.PanelSalaAdministrador;
import gui.salas.PanelSubsalasAdministrador;
import salas.Sala;
import sistema.Sistema;


/**
 * Clase ControladorPanelSubsalasAdministrador
 */
public class ControladorPanelSubsalasAdministrador implements ActionListener {


    /**
     * Atributos de ControladorPanelSubsalasAdministrador
     */
    private Ventana vista;

    
    private JPanel formPanel;
    private JPanel innerPanel;
    private JPanel subPanel;

    private JTextField[] txtAnchoArray;
    private JTextField[] txtTomasArray;
    private JTextField[] txtAforoArray;
    private JCheckBox chkHoja;
    private JCheckBox[] chkHojaArray;
    private Sala salaSeleccionada;




    /**
     * Constructor de ControladorPanelSubsalasAdministrador
     * @param vista (Ventana)
     */
    public ControladorPanelSubsalasAdministrador(Ventana vista) {
        this.vista = vista;
    }

    


    /**
     * Metodo que define las acciones del ControladorPanelSubsalasAdministrador
     * @param e (ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "Atras") {
            PanelSubsalasAdministrador panelSubsalas = vista.getPanelSubsalasAdministrador();
            panelSubsalas.restaurar();
            vista.getPanelSalaAdministrador().cargarSalas();
            vista.mostrarPanel("panelSalaAdministrador");

        } else if (e.getActionCommand() == "Aplicar") {
            PanelSubsalasAdministrador panelSubsalas = vista.getPanelSubsalasAdministrador();
            int numSubsalas = (int) panelSubsalas.getSpinnerNumSubsalas().getValue();
            panelSubsalas.getFormPanel().removeAll();
            formPanel = panelSubsalas.getFormPanel();
            chkHojaArray = new JCheckBox[numSubsalas];
            txtAnchoArray = new JTextField[numSubsalas];
            txtTomasArray = new JTextField[numSubsalas];
            txtAforoArray = new JTextField[numSubsalas];
            innerPanel = new JPanel();
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

            for (int i = 0; i < numSubsalas; i++) {
                if (e.getActionCommand() == "Atras") {
                    break;
                }
                subPanel = new JPanel(new GridLayout(5, 2));
                subPanel.setBorder(BorderFactory.createTitledBorder("Subsala " + (i + 1)));

                JTextField txtAncho = new JTextField();
                JTextField txtTomas = new JTextField();
                JTextField txtAforo = new JTextField();
                chkHoja = new JCheckBox("¿Es una subsala de hoja?");
                chkHojaArray[i] = chkHoja;
                txtAnchoArray[i] = txtAncho;
                txtTomasArray[i] = txtTomas;
                txtAforoArray[i] = txtAforo;

                subPanel.add(new JLabel("Ancho:"));
                subPanel.add(txtAncho);
                subPanel.add(new JLabel("Número de tomas corrientes:"));
                subPanel.add(txtTomas);
                subPanel.add(new JLabel("Aforo:"));
                subPanel.add(txtAforo);
                subPanel.add(new JLabel());
                subPanel.add(chkHoja);

                innerPanel.add(subPanel);
            }
            String nombreSubsala = (String) vista.getPanelSalaAdministrador().getModel()
                    .getValueAt(vista.getPanelSalaAdministrador().getTable().getSelectedRow(), 0);

            for (Sala s : Sistema.getInstance().getSalas()) {
                if (s.getNombre().equals(nombreSubsala)) {
                    salaSeleccionada = s;
                    break;
                }
                if ((s.getNombre() + " [" + s.getId() + "]").equals(nombreSubsala)) {
                    salaSeleccionada = s;
                    break;
                }
            }

            JScrollPane innerScrollPane = new JScrollPane(innerPanel);
            formPanel.add(innerScrollPane, BorderLayout.CENTER);

            formPanel.revalidate();
            formPanel.repaint();

        }

        else if (e.getActionCommand() == "Detalles subsala") {
            JList<String> listaSubsalas = vista.getPanelSubsalasAdministrador().getListaSubsalas();
            if (!listaSubsalas.isSelectionEmpty()) {
                int selectedIndex = listaSubsalas.getSelectedIndex();
                String nombreSubsala = listaSubsalas.getModel().getElementAt(selectedIndex);

                Sistema sistema = Sistema.getInstance();
                for (Sala s : sistema.getSalas()) {
                    if (nombreSubsala.equals(s.getNombre() + " - " + "[" + s.getId() + "]")) {
                        salaSeleccionada = s;
                        break;
                    }
                }
                JOptionPane.showMessageDialog(null, "Detalles de la subsala:\n" + salaSeleccionada + "\n",
                        "Detalles de la subsala", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una subsala para ver sus detalles.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
            }

        }

        else if (e.getActionCommand() == "Crear subsalas") {

            if (todosFormulariosRellenados() && salaSeleccionada != null) {
                crearSubsalas();
            } else {
                JOptionPane.showMessageDialog(null, "Por favor rellena todos los formularios.",
                        "Formularios incompletos", JOptionPane.WARNING_MESSAGE);
            }

        }

        else if (e.getActionCommand() == "Borrar") {
            JList<String> listaSubsalas = vista.getPanelSubsalasAdministrador().getListaSubsalas();
            if (!listaSubsalas.isSelectionEmpty()) {
                int selectedIndex = listaSubsalas.getSelectedIndex();
                String nombreSubsala = listaSubsalas.getModel().getElementAt(selectedIndex);
                Sistema sistema = Sistema.getInstance();
                for (Sala s : sistema.getSalas()) {
                    if (nombreSubsala.equals(s.getNombre() + " - " + "[" + s.getId() + "]")) {
                        salaSeleccionada = s;
                        if (salaSeleccionada.esHoja() && salaSeleccionada.isOcupada()) {
                            JOptionPane.showMessageDialog(null,
                                    "No se puede borrar la subsala porque tiene una exposicion activa.",
                                    "Subsala ocupada", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (salaSeleccionada.esHoja()) {
                            salaSeleccionada.getPadre().getSubsalas().remove(salaSeleccionada);
                            Sistema.getInstance().borrarSala(salaSeleccionada);
                            actualizarSubsalas();
                            JOptionPane.showMessageDialog(null, "Subsala borrada correctamente.", "Subsala borrada",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        if (salaSeleccionada.getSubsalas().size() == 0) {
                            salaSeleccionada.getPadre().getSubsalas().remove(salaSeleccionada);
                            actualizarSubsalas();
                            Sistema.getInstance().borrarSala(salaSeleccionada);
                            JOptionPane.showMessageDialog(null, "Subsala borrada correctamente.", "Subsala borrada",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        // Si la sala no es hoja y tiene subsalas, se elimina sala y se añaden
                        // al padre
                        else if (salaSeleccionada.getSubsalas().size() > 0) {
                            salaSeleccionada.getPadre().getSubsalas().addAll(salaSeleccionada.getSubsalas());
                            for (Sala subsala : salaSeleccionada.getSubsalas()) {
                                subsala.setPadre(salaSeleccionada.getPadre());
                            }
                            salaSeleccionada.getPadre().getSubsalas().remove(salaSeleccionada);
                            Sistema.getInstance().borrarSala(salaSeleccionada);
                            actualizarSubsalas();
                            JOptionPane.showMessageDialog(null, "Subsala borrada correctamente.", "Subsala borrada",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una subsala para borrarla.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
            }
        }

        else if (e.getActionCommand() == "Sala Padre") {

            PanelSalaAdministrador panelSala = vista.getPanelSalaAdministrador();
            int filaSeleccionada = panelSala.getTable().getSelectedRow();
            String nombreSala = panelSala.getModel().getValueAt(filaSeleccionada, 1).toString();
            long idSala = (long) panelSala.getModel().getValueAt(filaSeleccionada, 0);

            for (Sala s : Sistema.getInstance().getSalas()) {
                if (s.getId() == idSala && s.getNombre() == nombreSala) {
                    salaSeleccionada = s;
                    break;
                }
                if ((s.getNombre() + " [" + s.getId() + "]").equals(nombreSala)) {
                    salaSeleccionada = s;
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Sala padre de la sala seleccionada:\n" + salaSeleccionada.getNombre()
                    + " Aforo: " + salaSeleccionada.getAforo() + " Tomas de corriente: "
                    + salaSeleccionada.getNumTomasCorriente() + " Ancho: " + salaSeleccionada.getAncho() + "m",
                    "Sala padre", JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     * Metodo que verifica si todos los formularios estan rellenados
     * @return boolean
     */
    private boolean todosFormulariosRellenados() {

        if (txtAnchoArray == null || txtTomasArray == null || txtAforoArray == null) {
            return false;
        }
        for (int i = 0; i < txtAnchoArray.length; i++) {
            if (txtAnchoArray[i].getText().isEmpty() ||
                    txtTomasArray[i].getText().isEmpty() ||
                    txtAforoArray[i].getText().isEmpty()) {

                return false;
            }
        }
        return true;
    }


    /**
     * Metodo que crea las subsalas
     */
    private void crearSubsalas() {

        int aforo = 0;
        double ancho = 0;
        int tomasElectricas = 0;
        boolean esHoja = false;

        for (int i = 0; i < txtAnchoArray.length; i++) {
            if (txtAforoArray[i].getText().matches("[a-zA-Z]+") || txtTomasArray[i].getText().matches("[a-zA-Z]+") ||
                    txtAnchoArray[i].getText().matches("[a-zA-Z]+")) {

                JOptionPane.showMessageDialog(null,
                        "Por favor, introduce valores numéricos en los campos correspondientes.");
                return;
            }
            aforo = Integer.parseInt(txtAforoArray[i].getText());
            ancho = Double.parseDouble(txtAnchoArray[i].getText());
            tomasElectricas = Integer.parseInt(txtTomasArray[i].getText());
            esHoja = chkHojaArray[i].isSelected();
            boolean creacionExitosa = salaSeleccionada.crearSubsalas(aforo, ancho, tomasElectricas,
                    salaSeleccionada.isPermiteControlTemp(), salaSeleccionada.isPermiteControlHum(), esHoja);

            if (!creacionExitosa) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo crear la subsala " + (i + 1) + ". Verifica los valores ingresados.",
                        "Error al crear subsala", JOptionPane.ERROR_MESSAGE);
            }
        }

        int aforoTotal = 0;
        double anchoTotal = 0;
        int tomasTotal = 0;

        ArrayList<Sala> subsalas = new ArrayList<Sala>();
        for (Sala subsala : salaSeleccionada.getSubsalas()) {
            aforoTotal += subsala.getAforo();
            anchoTotal += subsala.getAncho();
            tomasTotal += subsala.getNumTomasCorriente();
            subsalas.add(subsala);
        }
        if (aforoTotal != salaSeleccionada.getAforo() || anchoTotal != salaSeleccionada.getAncho()
                || tomasTotal != salaSeleccionada.getNumTomasCorriente()) {
            JOptionPane.showMessageDialog(null, "No se pudieron crear las subsalas. Verifica los valores ingresados.",
                    "Error al crear subsalas", JOptionPane.ERROR_MESSAGE);

            for (Sala subsala : subsalas) {
                salaSeleccionada.getSubsalas().remove(subsala);
            }
            return;
        }

        salaSeleccionada.setSubsalas(subsalas);
        limpiarFormulario();
        actualizarSubsalas();
        JOptionPane.showMessageDialog(null, "Subsalas creadas correctamente.", "Subsalas creadas",
                JOptionPane.INFORMATION_MESSAGE);

    }


    /**
     * Metodo que limpia el formulario
     */
    public void limpiarFormulario() {
        for (int i = 0; i < txtAnchoArray.length; i++) {
            txtAnchoArray[i].setText("");
            txtTomasArray[i].setText("");
            txtAforoArray[i].setText("");
            chkHojaArray[i].setSelected(false);
        }
    }


    /**
     * Metodo que actualiza las subsalas
     */
    public void actualizarSubsalas() {
        PanelSubsalasAdministrador panelSubsalas = vista.getPanelSubsalasAdministrador();
        panelSubsalas.getListModel().clear();
       
        if (salaSeleccionada.getPadre() == null) {
            for (Sala s : salaSeleccionada.getSubsalas()) {
                panelSubsalas.getListModel().addElement(s.getNombre() + " - " + "[" + s.getId() + "]");
            }

        } else {
            for (Sala s : salaSeleccionada.getPadre().getSubsalas()) {
                panelSubsalas.getListModel().addElement(s.getNombre() + " - " + "[" + s.getId() + "]");
            }
        }

    }
}
