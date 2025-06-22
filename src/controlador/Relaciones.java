package controlador;

import modelo.conexion.ConexionBD;
import modelo.dao.PersonaDao;
import modelo.dao.MascotaDAO;
import vistas.Principal;
import vistas.VentanaMascotas;
import vistas.VentanaPersonas;

public class Relaciones{
	public Relaciones(){
		
	Principal ventanaPrincipal=new Principal();
	VentanaPersonas ventanaPersonas = new VentanaPersonas();
	VentanaMascotas ventanaMascotas = new VentanaMascotas();
	
	
	
	 PersonaDao miPersonaDAO=new PersonaDao();
	 MascotaDAO miMascotaDAO = new MascotaDAO();
     ConexionBD miConexionBD=new ConexionBD();
     Coordinador miCoordinador = new Coordinador();
     
     
     
     ventanaPrincipal.setCoordinador(miCoordinador);
     ventanaPersonas.setCoordinador(miCoordinador);
     ventanaMascotas.setCoordinador(miCoordinador);
     
     miPersonaDAO.setCoordinador(miCoordinador);
     miMascotaDAO.setCoordinador(miCoordinador);
     miConexionBD.setCoordinador(miCoordinador);
     
     miCoordinador.setVenPrincipal(ventanaPrincipal);
     miCoordinador.setVenPersonas(ventanaPersonas);
     miCoordinador.setVenMascotas(ventanaMascotas);
     
     
     miCoordinador.setMiPersonaDAO(miPersonaDAO);
     miCoordinador.setMiMascotaDAO(miMascotaDAO);
     miCoordinador.setMiConexionBD(miConexionBD);
     
     
     miCoordinador.mostrarVentanaPrincipal();;
	}
}
