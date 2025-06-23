package vistas;

import javax.swing.*;

import controlador.Coordinador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame implements ActionListener{
	private Coordinador miCoordinador;
    private JButton btnGestionarPersonas, btnGestionarMascotas;
    private JLabel lblImagen;

    public Principal() {
        setTitle("Sistema Veterinaria");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("SISTEMA VETERINARIA", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(100, 10, 300, 30);
        add(lblTitulo);

        iniciarComponentes();
        
    
    }

    private void iniciarComponentes() {

        lblImagen = new JLabel();
        lblImagen.setBounds(50,70,400,330);
        lblImagen.setIcon(new ImageIcon(getClass().
        		getResource("/imagen/pet.jpg")));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        

        btnGestionarPersonas = new JButton("Gestionar Personas");
        btnGestionarPersonas.setBounds(80, 280, 150, 30);
        add(btnGestionarPersonas);
        btnGestionarPersonas.addActionListener(this);

        btnGestionarMascotas = new JButton("Gestionar Mascotas");
        btnGestionarMascotas.setBounds(260, 280, 150, 30);
        add(btnGestionarMascotas);
        add(lblImagen);
        btnGestionarMascotas.addActionListener(this);
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnGestionarPersonas) {
			miCoordinador.mostrarVentanaPersonas();
		}
		if(e.getSource()==btnGestionarMascotas) {
			miCoordinador.mostrarVentanaMascotas();
		}
		
	}
}