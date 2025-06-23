package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

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
	public boolean registrar(Object dto) throws SQLException {
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
		} catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Ya existeuna personacon ese documento");

			return false;
		} catch (Exception e) {
			System.out.println("No se pudo registrar el dato: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

	public PersonaDTO consultarPersona(String documento) throws SQLException {

		PersonaDTO persona = null;
		connection = conexion.getConnection();
		ResultSet resultSet = null;
		String consulta = "select documento,nombre,telefono from persona where documento = ? ";

		try {
			preparedStatement = connection.prepareStatement(consulta);

			preparedStatement.setString(1, documento);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				persona = new PersonaDTO();
				persona.setDocumento(resultSet.getString("documento"));
				persona.setNombre(resultSet.getString("nombre"));
				persona.setTelefono(resultSet.getString("telefono"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("hay un error en la consulta" + e.getMessage());
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return persona;
	}
	public ArrayList<PersonaDTO> consultarListaPersonas() {
		PreparedStatement statement = null;
		ResultSet result = null;
		connection = conexion.getConnection();
		ArrayList<PersonaDTO> listaPersonas = new ArrayList<PersonaDTO>();
		String consulta = "select * from persona ";

		try {
			if (connection != null) {
				statement = connection.prepareStatement(consulta);
				result = statement.executeQuery();

				while (result.next()) {
					PersonaDTO miUsuario = new PersonaDTO();
					miUsuario.setDocumento(result.getString("documento"));
					miUsuario.setNombre(result.getString("nombre"));
					miUsuario.setTelefono(result.getString("telefono"));

					listaPersonas.add(miUsuario);
				}

				conexion.desconectar();
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta de personas: " + e.getMessage());
		}

		return listaPersonas;
	}

	public String actualizarPersona(PersonaDTO persona) throws SQLException {

		String resp = "";
		connection = conexion.getConnection();
		String consulta = "UPDATE persona SET nombre = ?, telefono = ? WHERE documento = ?";
		try {
			if (connection != null) {
				preparedStatement = connection.prepareStatement(consulta);

				preparedStatement.setString(1, persona.getNombre());
				preparedStatement.setString(2, persona.getTelefono());
				preparedStatement.setString(3, persona.getDocumento());
				preparedStatement.executeUpdate();
				resp = "ok";
				conexion.desconectar();
			}
		} catch (Exception e) {
			System.out.println(e);
			resp = "error";
		}

		return resp;

	}

	public String eliminarPersona(String documento) throws SQLException {

		connection = conexion.getConnection();
		try {
			String consulta = "delete from persona where documento = ?";
			preparedStatement = connection.prepareStatement(consulta);
			preparedStatement.setString(1, documento);
			preparedStatement.executeUpdate();
			return "ok";

		} catch (Exception e) {
			System.out.println(e);
			return "error";
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	@Override
	public Object consultar(String text) throws SQLException {

		return null;
	}

}
