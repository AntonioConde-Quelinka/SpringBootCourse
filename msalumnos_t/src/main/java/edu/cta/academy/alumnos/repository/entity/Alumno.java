package edu.cta.academy.alumnos.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ParameterMode;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;


@Entity						// -> Clase asociada a BBDD
@Table(name = "alumnos")	// -> Asociada a la tabla 'alumno'
@NamedStoredProcedureQueries (
{
	@NamedStoredProcedureQuery(
			name="Alumno.alumnosRegistradosHoy", 
			procedureName = "obtenerAlumnosRegistradosHoy", 
			resultClasses = edu.cta.academy.alumnos.repository.entity.Alumno.class),
	
	// Por un BUG en el Driver de MySQL, obliga que los parámetros de salida sean INOUT
	@NamedStoredProcedureQuery(
			name="Alumno.alumnosEdadMediaMinMax", 
			procedureName = "calcular_max_min_media_edad",
			parameters = {
				@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmax"),
				@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadmin"),
				@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadmedia")
			})
})
public class Alumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// Autonumérico de MySql
	private Long id;
	
	@Size(min = 3, max = 30)
	private String nombre;
	
	@NotEmpty
	@NotBlank
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(120)
	private int edad;
	
	@Lob
	@JsonIgnore				// Evita que serialice / deserialice autom. Lo haremos a mano
	private byte[] foto;
	
	public String getFotoUri() {
		String resul = "";
		if (this.foto != null && this.foto.length > 0) {
			resul = "http://localhost:8090/api/alumnos/foto/" + id.toString();
		}
		return resul;
	}
	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Column(name = "creado_en")			// Mapeo personalizado de campo
	private LocalDateTime creadoEn;
	
	@PrePersist							// Antes de insertar un registro
	private void generarFechaCreacion() {
		this.creadoEn = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}

	public Alumno(Long id, String nombre, String apellido, String email, int edad, LocalDateTime creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}
	
	public Alumno() {
		super();
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}

	
	
}
