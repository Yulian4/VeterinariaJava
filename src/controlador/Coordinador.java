package controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.conexion.ConexionBD;
import modelo.dao.MascotaDAO;
import modelo.dao.PersonaDao;
import modelo.dto.MascotaDTO;
import modelo.dto.PersonaDTO;
import vistas.VentanaMascotas;
import vistas.VentanaPersonas;
import vistas.Principal;

public class Coordinador {
	private Principal venPrincipal;
	private VentanaMascotas venMascotas;
	private VentanaPersonas venPersonas;
	
	private PersonaDao miPersonaDAO;
	private MascotaDAO miMascotaDAO;
	private ConexionBD miConexionBD;

	
	
	public void setVenPrincipal(Principal ventanaPrincipal) {
		this.venPrincipal = ventanaPrincipal;
	}

	public void setVenMascotas(VentanaMascotas venMascotas) {
		this.venMascotas = venMascotas;
	}
	public void setVenPersonas(VentanaPersonas venPersonas) {
		this.venPersonas = venPersonas;
	}
	
	
	
	public void setPersonaDao(PersonaDao personaDao) {
		this.miPersonaDAO = personaDao;
	}
	public void setMascotaDao(MascotaDAO mascotaDao) {
		this.miMascotaDAO = mascotaDao;
	}

	
	
	public void mostrarVentanaPrincipal() {
		venPrincipal.setVisible(true);
	}
	public void mostrarVentanaMascotas() {
		venMascotas.setVisible(true);
	}
	public void mostrarVentanaPersonas() {
		venPersonas.setVisible(true);
	}
	
	
	
	
	
	
	public boolean registrarpersona(PersonaDTO persona) throws SQLException {
			return miPersonaDAO.registrar(persona);
		
	}
	public boolean registrarMascota(MascotaDTO mascota) throws SQLException {
		return miMascotaDAO.registrar(mascota);
	}
	public String eliminarPersona(String text) throws SQLException {
		return miPersonaDAO.eliminarPersona(text);
	}

	public String eliminarMascota(String idDueno,String nombreMascota) {
		return miMascotaDAO.eliminarMascota(idDueno, nombreMascota);
	}
	public String actualizarPersona(PersonaDTO personaNueva) throws SQLException {

		return miPersonaDAO.actualizarPersona(personaNueva);
	}
	public String actualizarMascota(MascotaDTO mascotaNueva,String nombreoriginal) {
		return miMascotaDAO.actualizarMascota(mascotaNueva, nombreoriginal);
	}
	public ArrayList<MascotaDTO> consultarMascotas(String idDueno) throws SQLException {
	    return miMascotaDAO.consultarMascotas(idDueno);
	}
	public PersonaDTO consultarPersona(String text) throws SQLException {
		return miPersonaDAO.consultarPersona(text);
	}
	public ArrayList<PersonaDTO> consultarListaPersonas() {
		return miPersonaDAO.consultarListaPersonas();
	}






	
	
	
	
	public void setMiPersonaDAO(PersonaDao miPersonaDAO) {
		this.miPersonaDAO = miPersonaDAO;
	}
	public void setMiMascotaDAO(MascotaDAO miMascotaDAO) {
		this.miMascotaDAO = miMascotaDAO;
	}

	
	public void setMiConexionBD(ConexionBD miConexionBD) {
		this.miConexionBD = miConexionBD;
	}

	
	
	

	
	

	

	
	



}
