package modelo.dto;

public class MascotaDTO extends AnimalDTO {
	private String idDueno, nombre, raza, sexo, nombreDueño;

	public String getNombreDueño() {
	    return nombreDueño;
	}

	public void setNombreDueño(String nombreDueño) {
	    this.nombreDueño = nombreDueño;
	}

	public String getIdDueno() {
		return idDueno;
	}

	public void setIdDueno(String idDueno) {
		this.idDueno = idDueno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
