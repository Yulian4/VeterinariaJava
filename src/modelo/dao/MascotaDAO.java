package modelo.dao;

import modelo.dto.MascotaDTO;
import modelo.dto.PersonaDTO;

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

public class MascotaDAO extends ModeloDAO {
	Connection connection = null;
	Conexion conexion = Conexion.getInstancia();
	PreparedStatement preparedStatement = null;
	private Coordinador miCoordinador;


	@Override
	public boolean registrar(Object dto) throws SQLException {
		connection = conexion.getConnection();
		try {
			if (dto instanceof MascotaDTO) {
				MascotaDTO mascota = (MascotaDTO) dto;
				if (ConexionBD.mascotasMap.containsKey(mascota.getIdDueno()) == false) {
					String consulta = "insert into mascota(id_dueno,nombre,raza,sexo) values(?,?,?,?)";
					System.out.println("Insertando mascota: " + mascota.getNombre() + ", " + mascota.getRaza() + ", "
							+ mascota.getSexo());
					preparedStatement = connection.prepareStatement(consulta);
					preparedStatement.setString(1, mascota.getIdDueno());
					preparedStatement.setString(2, mascota.getNombre());
					preparedStatement.setString(3, mascota.getRaza());
					preparedStatement.setString(4, mascota.getSexo());
					preparedStatement.execute();

					return true;

				}
			} else {
				throw new IllegalArgumentException("Se esperaba un PersonaDTO");
			}
			return false;
			
		} catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "No existe ningun \n dueño con ese documento");
			
			return false;
		}
		

	}

	@Override
	public Object consultar(String text) throws SQLException {
		MascotaDTO mascota = null;
		connection = conexion.getConnection();
		ResultSet resultSet = null;
		String consulta = "select id_dueno,nombre,raza,sexo from mascota where id_dueno = ? ";

		try {
			preparedStatement = connection.prepareStatement(consulta);

			preparedStatement.setString(1, text);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mascota = new MascotaDTO();
				mascota.setIdDueno(resultSet.getNString("id_dueno"));
				mascota.setNombre(resultSet.getString("nombre"));
				mascota.setRaza(resultSet.getString("raza"));
				mascota.setSexo(resultSet.getString("sexo"));
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

		return mascota;
	}

	public String eliminarMascota(String idDueno, String nombreMascota) {
    PreparedStatement preparedStatement = null;
    String resp = "";
    connection = conexion.getConnection();

    try {
        if (connection != null) {
            String consulta = "delete from mascota where id_dueno = ? and nombre = ?";
            preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setString(1, idDueno);
            preparedStatement.setString(2, nombreMascota);

            int filas = preparedStatement.executeUpdate();
            resp = (filas > 0) ? "ok" : "no_delete";

            conexion.desconectar();
        }
    } catch (Exception e) {
        System.out.println(e);
        resp = "error";
    }

    return resp;
}


	public String actualizarMascota(MascotaDTO mascota, String nombreOriginal) {
    PreparedStatement preparedStatement = null;
    String resp = "";
    connection = conexion.getConnection();

    try {
        if (connection != null) {
            String consulta = "update mascota set nombre = ?, raza = ?, sexo = ? where id_dueno = ? and nombre = ?";
            preparedStatement = connection.prepareStatement(consulta);

            
            preparedStatement.setString(1, mascota.getNombre());
            preparedStatement.setString(2, mascota.getRaza());
            preparedStatement.setString(3, mascota.getSexo());
            preparedStatement.setString(4, mascota.getIdDueno());
            preparedStatement.setString(5, nombreOriginal); 
            int filas = preparedStatement.executeUpdate();
            resp = (filas > 0) ? "ok" : "no_update";
            conexion.desconectar();
        }
    } catch (Exception e) {
        System.out.println(e);
        resp = "error";
    }

    return resp;
}

	public ArrayList<MascotaDTO> consultarMascotas(String idDueno) throws SQLException {
	    ArrayList<MascotaDTO> lista = new ArrayList<>();
	    connection = conexion.getConnection();
	    ResultSet resultSet = null;

	    String consulta = "select p.nombre as nombre_dueño, p.documento, m.nombre as nombre_mascota, m.raza, m.sexo " +
	                      "from mascota m join persona p ON m.id_dueno = p.documento";

	    if (idDueno != null && !idDueno.isEmpty()) {
	        consulta += " WHERE m.id_dueno = ?";
	    }

	    try {
	        preparedStatement = connection.prepareStatement(consulta);

	        if (idDueno != null && !idDueno.isEmpty()) {
	            preparedStatement.setString(1, idDueno);
	        }

	        resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            MascotaDTO mascota = new MascotaDTO();
	            mascota.setNombreDueño(resultSet.getString("nombre_dueño"));
	            mascota.setIdDueno(resultSet.getString("documento"));
	            mascota.setNombre(resultSet.getString("nombre_mascota"));
	            mascota.setRaza(resultSet.getString("raza"));
	            mascota.setSexo(resultSet.getString("sexo"));
	            lista.add(mascota);
	        }

	    } finally {
	        if (resultSet != null) resultSet.close();
	        if (preparedStatement != null) preparedStatement.close();
	        if (connection != null) connection.close();
	    }

	    return lista;
	}


	
	
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}


}
