package vistas;

import javax.swing.*;

import controlador.Coordinador;
import modelo.dto.MascotaDTO;
import modelo.dto.PersonaDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaMascotas extends JFrame implements ActionListener {
	private JTextField txtDocumento, txtSexo, txtRaza, txtNombre;
	private JButton btnRegistrar, btnConsultar, btnActualizar, btnEliminar, btnConsultarLista, btnSiguiente;;
	private JTextArea areaLista;
	private JTable tablaNombres;
	private JLabel lblSeleccion;
	private JScrollPane scrollTabla;
	private Coordinador miCoordinador;
	private ArrayList<MascotaDTO> mascotasDelDueno;
	private JComboBox<String> comboSexo;
	private String nombreOriginal;
	private int indiceActual = 0;

	public VentanaMascotas() {
		setTitle("Gestionar Personas");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel lblTitulo = new JLabel("Mascotas", JLabel.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(80, 10, 250, 30);
		add(lblTitulo);

		JLabel lblDocumento = new JLabel("ID Dueño:");
		lblDocumento.setBounds(20, 50, 80, 25);
		add(lblDocumento);

		txtDocumento = new JTextField();
		txtDocumento.setBounds(100, 50, 100, 25);
		add(txtDocumento);

		JLabel lblRaza = new JLabel("Raza:");
		lblRaza.setBounds(210, 50, 60, 25);
		add(lblRaza);

		txtRaza = new JTextField();
		txtRaza.setBounds(270, 50, 100, 25);
		add(txtRaza);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 85, 80, 25);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(100, 85, 100, 25);
		add(txtNombre);

		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(210, 85, 80, 25);
		add(lblSexo);
		
		comboSexo = new JComboBox<>(new String[] { "Macho", "Hembra" });
		comboSexo.setBounds(270, 85, 100, 25);
		add(comboSexo);


		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(40, 120, 120, 30);
		add(btnRegistrar);
		btnRegistrar.addActionListener(this);

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(200, 120, 120, 30);
		add(btnConsultar);
		btnConsultar.addActionListener(this);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(40, 160, 120, 30);
		add(btnActualizar);
		btnActualizar.addActionListener(this);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(200, 160, 120, 30);
		add(btnEliminar);
		btnEliminar.addActionListener(this);

		btnConsultarLista = new JButton("ConsultarLista");
		btnConsultarLista.setBounds(40, 200, 280, 30);
		add(btnConsultarLista);
		btnConsultarLista.addActionListener(this);

//		areaLista = new JTextArea();
//		JScrollPane scrollPane = new JScrollPane(areaLista);
//		scrollPane.setBounds(40, 240, 280, 100);
//		add(scrollPane);

		tablaNombres = new JTable();
		scrollTabla = new JScrollPane(tablaNombres);
		scrollTabla.setBounds(20, 240, 350, 100);
		add(scrollTabla);
		lblSeleccion = new JLabel();
		lblSeleccion.setBounds(260, 270, 200, 20);
		add(lblSeleccion);

		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(40, 350, 280, 30);
		btnSiguiente.setEnabled(false);
		btnSiguiente.addActionListener(this);
		add(btnSiguiente);

	}

	public static void main(String[] args) {
		new VentanaMascotas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegistrar) {
			try {
				registrarMascotas();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getSource() == btnConsultar) {
			consultarMascota();
		}
		if (e.getSource() == btnActualizar) {
			try {
				actualizarMascota();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getSource() == btnEliminar) {
			eliminarMacota();

		}
		if (e.getSource() == btnConsultarLista) {
			try {
				consultarListaMascotas();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getSource() == btnSiguiente) {
			if (mascotasDelDueno != null && indiceActual + 1 < mascotasDelDueno.size()) {
				indiceActual++;
				mostrarMascota(mascotasDelDueno.get(indiceActual));
				if (indiceActual == mascotasDelDueno.size() - 1) {
					btnSiguiente.setEnabled(false);
				}
			}
		}
	}

	private boolean registrarMascotas() throws SQLException {
		if (!validarCampos()) {
			return false;
		}
		MascotaDTO mascota = new MascotaDTO();

		mascota.setIdDueno(txtDocumento.getText());
		mascota.setNombre(txtNombre.getText());
		mascota.setRaza(txtRaza.getText());
	
		mascota.setSexo(comboSexo.getSelectedItem().toString());
		boolean res = miCoordinador.registrarMascota(mascota);
		if (res) {
			JOptionPane.showMessageDialog(this, "mascota registrada con éxito.");
		} 

		return res;

	}

	private void consultarMascota() {
		try {
			String idDueno = txtDocumento.getText();
			mascotasDelDueno = miCoordinador.consultarMascotas(idDueno);
			indiceActual = 0;

			if (mascotasDelDueno != null && !mascotasDelDueno.isEmpty()) {
				mostrarMascota(mascotasDelDueno.get(indiceActual));
				btnSiguiente.setEnabled(mascotasDelDueno.size() > 1);
			} else {
				JOptionPane.showMessageDialog(this, "No se encontraron mascotas.");
				btnSiguiente.setEnabled(false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error en la consulta.");
		}
	}

	private void actualizarMascota() throws SQLException {
	    if (mascotasDelDueno != null && !mascotasDelDueno.isEmpty()) {
	        MascotaDTO mascotaActual = mascotasDelDueno.get(indiceActual);

	        mascotaActual.setNombre(txtNombre.getText());
	        mascotaActual.setRaza(txtRaza.getText());
	        mascotaActual.setSexo(txtSexo.getText());

	
	        String resp = miCoordinador.actualizarMascota(mascotaActual, nombreOriginal);

	        if (resp.equals("ok")) {
	            JOptionPane.showMessageDialog(null, "Se actualizó exitosamente", "Actualizado", JOptionPane.WARNING_MESSAGE);
	        } else if (resp.equals("no_update")) {
	            JOptionPane.showMessageDialog(null, "No se encontró la mascota para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Ocurrió un error durante la actualización", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "No hay mascota seleccionada", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	private void eliminarMacota() {
    String idDueno = txtDocumento.getText();
    String nombreMascota = txtNombre.getText();
    String resp = miCoordinador.eliminarMascota(idDueno, nombreMascota);

    if (resp.equals("ok")) {
        JOptionPane.showMessageDialog(this, "Mascota eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } else if (resp.equals("no_delete")) {
        JOptionPane.showMessageDialog(this, "No se encontró la mascota para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Error al eliminar la mascota", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}


	private void consultarListaMascotas() throws SQLException {
		ArrayList<MascotaDTO> listaMascotas = miCoordinador.consultarMascotas(null);;
		String msj = "";
		if (listaMascotas.size() > 0) {
			llenarTabla(listaMascotas);

		} else {
			lblSeleccion.setText("No hay personas registradas");
		}

	}

	private void llenarTabla(ArrayList<MascotaDTO> listaMascotas) {
		String titulos[] = { "Nombre_Dueño", "Documento_Dueño", "Nombre", "Raza", "Sexo" };

		String info[][] = new String[listaMascotas.size()][5];

		for (int x = 0; x < listaMascotas.size(); x++) {
			info[x][0] = listaMascotas.get(x).getNombreDueño();
			info[x][1] = listaMascotas.get(x).getIdDueno();
			info[x][2] = listaMascotas.get(x).getNombre();
			info[x][3] = listaMascotas.get(x).getRaza();
			info[x][4] = listaMascotas.get(x).getSexo();
		}

		tablaNombres = new JTable(info, titulos);
		scrollTabla.setViewportView(tablaNombres);
		int[] anchos = { 100, 100, 100, 100, 50 };
		for (int i = 0; i < tablaNombres.getColumnCount(); i++) {
			tablaNombres.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
	}
	
	private boolean validarCampos() {
		boolean camposValidos = true;

		String documento = txtDocumento.getText().trim();
		if (documento.isEmpty()) {
			txtDocumento.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else if (!documento.matches("\\d+")) {
			txtDocumento.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
			JOptionPane.showMessageDialog(this, "El ID del dueño debe contener solo números.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
		} else {
			txtDocumento.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}

		if (txtNombre.getText().trim().isEmpty()) {
			txtNombre.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else {
			txtNombre.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}


		if (txtRaza.getText().trim().isEmpty()) {
			txtRaza.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else {
			txtRaza.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}

		if (!camposValidos) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos correctamente.", "Campos inválidos", JOptionPane.WARNING_MESSAGE);
		}

		return camposValidos;
	}


	private void mostrarMascota(MascotaDTO mascota) {
		txtNombre.setText(mascota.getNombre());
		txtRaza.setText(mascota.getRaza());
		comboSexo.setSelectedItem(mascota.getSexo());
		nombreOriginal = mascota.getNombre();
	}

	public void setCoordinador(Coordinador coordinador) {
		this.miCoordinador = coordinador;
	}

}
