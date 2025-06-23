package vistas;

import javax.swing.*;

import controlador.Coordinador;
import modelo.dto.PersonaDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPersonas extends JFrame implements ActionListener {
	private JTextField txtDocumento, txtNombre, txtTelefono;
	private JButton btnRegistrar, btnConsultar, btnActualizar, btnEliminar, btnConsultarLista;
	private JTextArea areaLista;
	private Coordinador miCoordinador;
	private JTable tablaNombres;
	private JScrollPane scrollTabla;

	public VentanaPersonas() {
		setTitle("Gestionar Personas");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel lblTitulo = new JLabel("Personas", JLabel.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(80, 10, 250, 30);
		add(lblTitulo);

		JLabel lblDocumento = new JLabel("Documento:");
		lblDocumento.setBounds(20, 50, 80, 25);
		add(lblDocumento);

		txtDocumento = new JTextField();
		txtDocumento.setBounds(100, 50, 100, 25);
		add(txtDocumento);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(210, 50, 60, 25);
		add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(270, 50, 100, 25);
		add(txtTelefono);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 85, 80, 25);
		add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(100, 85, 270, 25);
		add(txtNombre);

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
		tablaNombres = new JTable();
		scrollTabla = new JScrollPane(tablaNombres);
		scrollTabla.setBounds(20, 240, 350, 100);
		add(scrollTabla);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRegistrar) {
			try {
				registrarPersonas();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnConsultar) {
			try {
				consultaPersona();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == btnConsultarLista) {
			consultarListaPersonas();
		}
		if (e.getSource() == btnActualizar) {
			try {
				actualizarPersona();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		if (e.getSource() == btnEliminar) {
			try {
				eiminarPersona();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private boolean validarCampos() {
		boolean camposValidos = true;

		if (txtDocumento.getText().trim().isEmpty()) {
			txtDocumento.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else if (!txtDocumento.getText().matches("\\d+")) {
			txtDocumento.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
			JOptionPane.showMessageDialog(this, "El documento debe contener solo números.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		} else {
			txtDocumento.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}

		if (txtNombre.getText().trim().isEmpty()) {
			txtNombre.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else {
			txtNombre.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}
		String telefono = txtTelefono.getText().trim();
		if (txtTelefono.getText().trim().isEmpty()) {
			txtTelefono.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
		} else if (!telefono.matches("\\d{10}")) {
			txtTelefono.setBorder(BorderFactory.createLineBorder(Color.RED));
			camposValidos = false;
			JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos numéricos.", "Error de Validación",
					JOptionPane.ERROR_MESSAGE);
		} else {
			txtTelefono.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
		}

		if (!camposValidos) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Campos vacíos",
					JOptionPane.WARNING_MESSAGE);
		}

		return camposValidos;

	}

	private boolean registrarPersonas() throws SQLException {
		if (!validarCampos()) {
			return false;
		}
		PersonaDTO persona = new PersonaDTO();

		persona.setDocumento(txtDocumento.getText());
		persona.setTelefono(txtTelefono.getText());
		persona.setNombre(txtNombre.getText());
		boolean res = miCoordinador.registrarpersona(persona);
		if (res) {
			JOptionPane.showMessageDialog(this, "Persona registrada con éxito.");
		} else {
			JOptionPane.showMessageDialog(this, "No se pudo registrar la persona.");
		}

		return res;
	}

	private void consultaPersona() throws SQLException {
		PersonaDTO miPersona = miCoordinador.consultarPersona(txtDocumento.getText());

		if (miPersona != null) {
			txtNombre.setText(miPersona.getNombre());
			txtTelefono.setText(miPersona.getTelefono());
		} else {
			JOptionPane.showMessageDialog(null, "NO SE ENCUENTRA LA PERSONA", "NO REGISTRA", JOptionPane.ERROR_MESSAGE);
			txtNombre.setText("");
			txtTelefono.setText("");
		}
	}

	

	private void consultarListaPersonas() {
		ArrayList<PersonaDTO> listaPersonas = miCoordinador.consultarListaPersonas();
		String msj = "";
		if (listaPersonas.size() > 0) {
			llenarTabla(listaPersonas);

		} else {
//			lblSeleccion.setText("No hay personas registradas");
		}
	}

	private void llenarTabla(ArrayList<PersonaDTO> listaPersonas) {
		String titulos[] = { "Documento", "Nombre", "Teléfono" };

		String info[][] = new String[listaPersonas.size()][3];

		for (int x = 0; x < info.length; x++) {
			info[x][0] = listaPersonas.get(x).getDocumento();
			info[x][1] = listaPersonas.get(x).getNombre();
			info[x][2] = listaPersonas.get(x).getTelefono();
		}

		tablaNombres = new JTable(info, titulos);

		int[] anchos = { 100, 150, 150 };
		for (int i = 0; i < tablaNombres.getColumnCount(); i++) {
			tablaNombres.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}

		scrollTabla.setViewportView(tablaNombres);

		scrollTabla.revalidate();
		scrollTabla.repaint();
	}

	private void actualizarPersona() throws SQLException {
		if (!validarCampos()) {
			return;
		}
		PersonaDTO personaNueva = miCoordinador.consultarPersona(txtDocumento.getText());
		personaNueva.setNombre(txtNombre.getText());
		personaNueva.setTelefono(txtTelefono.getText());

		String resp = miCoordinador.actualizarPersona(personaNueva);

		if (resp.equals("ok")) {
			JOptionPane.showMessageDialog(null, "Se actualiza exitosamente", "Actualizado",
					JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo actualizar", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void eiminarPersona() throws SQLException {
		String resp = miCoordinador.eliminarPersona(txtDocumento.getText());
		if (resp.equals("ok")) {
			JOptionPane.showMessageDialog(null, "Se elimina exitosamente", "Eliminado", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}
}
