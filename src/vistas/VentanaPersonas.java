package vistas;

import javax.swing.*;

import controlador.Coordinador;
import modelo.dto.PersonaDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VentanaPersonas extends JFrame implements ActionListener {
	private JTextField txtDocumento, txtNombre, txtTelefono;
	private JButton btnRegistrar, btnConsultar, btnActualizar, btnEliminar, btnConsultarLista;
	private JTextArea areaLista;
	private Coordinador miCoordinador;

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

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(40, 160, 120, 30);
		add(btnActualizar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(200, 160, 120, 30);
		add(btnEliminar);

		btnConsultarLista = new JButton("ConsultarLista");
		btnConsultarLista.setBounds(40, 200, 280, 30);
		add(btnConsultarLista);

		areaLista = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(areaLista);
		scrollPane.setBounds(40, 240, 280, 100);
		add(scrollPane);

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

	}

	private boolean registrarPersonas() throws SQLException {
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

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

}
