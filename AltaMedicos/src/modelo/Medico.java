package modelo;

public class Medico {
	private int	id;
	private String nombre;
	private String apellido;
	private String matricula;
	private Especialidad especialidad; //Dentro de la base de datos se tiene una entidad (tabla) "Especialidad" y la misma se representará mediante una clase, por eso el atributo es de tipo "Especialidad"
	private String dni;
	
	public Medico() {
	}

	public Medico(int id, String nombre, String apellido, String matricula, Especialidad especialidad, String dni) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
		this.especialidad = especialidad;
		this.dni = dni;
	}

	//Constructor sin ID por si se lo desconoce. Puede ser implementado para la inserción en base de datos.
	public Medico(String nombre, String apellido, String matricula, Especialidad especialidad, String dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;
		this.especialidad = especialidad;
		this.dni = dni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Nombre: " + this.nombre + " Apellido: " + this.apellido + " Especialidad: " + especialidad.getDescripcion() + "ID especialidad: " + especialidad.getId();
	}
}
