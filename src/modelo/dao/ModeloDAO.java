package modelo.dao;

import java.sql.SQLException;

import modelo.dto.PersonaDTO;

public abstract class ModeloDAO {

		public abstract Object consultar(String text)throws SQLException;
		public abstract  boolean registrar(Object dto) throws SQLException;
		
}
