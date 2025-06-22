package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import controlador.Coordinador;
import modelo.conexion.Conexion;
import modelo.conexion.ConexionBD;
import modelo.dto.PersonaDTO;

public class PersonaDao extends ModeloDAO {
	Connection connection = null;
	Conexion conexion = Conexion.getInstancia();
	PreparedStatement preparedStatement = null;
	private Coordinador miCoordinador;


	@Override
	public  boolean registrar(Object dto) throws SQLException {
		connection = conexion.getConnection();
		try {
			if (dto instanceof PersonaDTO) {
				PersonaDTO persona = (PersonaDTO) dto;
				if (ConexionBD.personasMap.containsKey(persona.getDocumento()) == false) {
					String Consulta = "insert into persona(documento,telefono,nombre)values(?,?,?)";

					preparedStatement = connection.prepareStatement(Consulta);
					preparedStatement.setString(1, persona.getDocumento());
					preparedStatement.setString(2, persona.getTelefono());
					preparedStatement.setString(3, persona.getNombre());
					preparedStatement.execute();
					return true;

				} else {
					return false;
				}

			} else {
				throw new IllegalArgumentException("No es una persona");
			}
		}  catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Se ha duplicado la clave primaria");
//			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("No se pudo registrar el dato: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			preparedStatement.close();
			connection.close();
			conexion.desconectar();
		}

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	@Override
	public Object consultar(String text) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
