package modelo.conexion;

import java.util.HashMap;

import controlador.Coordinador;
import modelo.dto.MascotaDTO;
import modelo.dto.PersonaDTO;

public class ConexionBD {
	public static HashMap<String, PersonaDTO> personasMap;
	public static HashMap<String, MascotaDTO> mascotasMap;
	private Coordinador miCoordinador;
	
	public ConexionBD() {
		personasMap=new HashMap<String, PersonaDTO>();
		mascotasMap = new HashMap<String, MascotaDTO>();
	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}

}
