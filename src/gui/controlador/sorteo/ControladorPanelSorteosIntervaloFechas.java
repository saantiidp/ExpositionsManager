package gui.controlador.sorteo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import exposicion.Exposicion;
import exposicion.ExposicionPermanente;
import exposicion.ExposicionTemporal;
import gui.Ventana;
import gui.exposicion.DatePicker;
import sistema.Sistema;

public class ControladorPanelSorteosIntervaloFechas implements ActionListener {

	Ventana ventana;

	public ControladorPanelSorteosIntervaloFechas(Ventana ventana) {
		this.ventana = ventana;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("crearSorteo")) {
			JTextField txtNumEntradas = this.ventana.getPanelSorteoIntervaloFechas().getTxtNumeroEntradas();
			JTable tabla = this.ventana.getPanelSorteoAdmin().getTable();

			int fila = tabla.getSelectedRow();
			long idExpo = (Long) tabla.getValueAt(fila, 0);
			Sistema s = Sistema.getInstance();
			String tipo = (String) tabla.getValueAt(fila, 5);
			Exposicion exposicion = tipo == "Permanente" ? s.getExposicionesPermanentes().get(idExpo) : s.getExposicionesTemporales().get(idExpo);
			DatePicker inicioInscr = ventana.getPanelSorteoIntervaloFechas().getFechaInicio();
			DatePicker finInscr = ventana.getPanelSorteoIntervaloFechas().getFechaFin();
			LocalDate inicio = inicioInscr.getSelectedDate();
			LocalDate fin = finInscr.getSelectedDate();
			LocalDate inicioSorteo = ventana.getPanelSorteoIntervaloFechas().getFechaInicioSorteo().getSelectedDate();
			LocalDate finSorteo = ventana.getPanelSorteoIntervaloFechas().getFechaFinSorteo().getSelectedDate();

			if (txtNumEntradas.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Por favor ingrese el número de entradas a sortear.");
				return;
			}
			if (Integer.parseInt(txtNumEntradas.getText()) <= 0) {
				JOptionPane.showMessageDialog(null, "Por favor ingrese un número de entradas válido.");
				return;
			}

			if(exposicion.esTemporal()){
				ExposicionTemporal expo = (ExposicionTemporal) exposicion;
				if(expo.getFechaInicio().isAfter(LocalDate.now())){

					JOptionPane.showMessageDialog(null, "La exposición aún no comenzó, no se pueden sortear entradas.");
					return;
				}
				if(expo.getFechaFin().isBefore(LocalDate.now())){
					JOptionPane.showMessageDialog(null, "La exposición ya finalizó, no se pueden sortear entradas.");
					return;
				}
				s.crearSorteoUsoIntervaloFechas(inicio, fin, Integer.parseInt(txtNumEntradas.getText()), true, expo, inicioSorteo, finSorteo);
				JOptionPane.showMessageDialog(null, "Sorteo creado con éxito.");
				this.limpiarCampos();
			}

			else{
				ExposicionPermanente expo = (ExposicionPermanente) exposicion;
				s.crearSorteoUsoIntervaloFechas(inicio, fin, Integer.parseInt(txtNumEntradas.getText()), true, expo, inicioSorteo, finSorteo);
				JOptionPane.showMessageDialog(null, "Sorteo creado con éxito.");
				this.limpiarCampos();
			}

		}

		else if (e.getActionCommand().equals("atras")) {
			limpiarCampos();
			ventana.mostrarPanel("panelSorteosAdmin");
		}   
	

	}

	private void limpiarCampos() {
		ventana.getPanelSorteoIntervaloFechas().getTxtNumeroEntradas().setText("");
		ventana.getPanelSorteoIntervaloFechas().getFechaInicio().setValue(LocalDate.now());
		ventana.getPanelSorteoIntervaloFechas().getFechaFin().setValue(LocalDate.now());
		ventana.getPanelSorteoIntervaloFechas().getFechaInicioSorteo().setValue(LocalDate.now());
		ventana.getPanelSorteoIntervaloFechas().getFechaFinSorteo().setValue(LocalDate.now());
	}

}
