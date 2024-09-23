package com.upc.petminder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, unique = true)
	private String username;
	@Column(length = 200)
	private String password;
	private Boolean enabled;

	@Column(length = 50) // Agregado para el nombre
	private String nombre;

	@Column(length = 50) // Agregado para los apellidos
	private String apellidos;

	@Column(length = 9) // Agregado para el celular
	private String celular;

	@Column(length = 100, unique = true) // Agregado para el email
	private String email;


	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Recordatorios> recordatorios;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Notificacion> notificaciones;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Mascota> mascotas;

	public Users(String username, String password, Boolean enabled, String nombre, String apellidos, String celular, String email) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.celular = celular;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}