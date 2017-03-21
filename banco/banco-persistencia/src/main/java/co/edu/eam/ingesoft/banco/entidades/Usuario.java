package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="T_USUARIO")
@NamedQuery(name=Usuario.USUARIO,query="SELECT u FROM Usuario u WHERE u.usuario=?1")
public class Usuario implements Serializable{

	public static final String USUARIO = "Usuario.listUs";
	
	@Id
	@Column(name="NAME_US")
	private String usuario;

	@Column(name="CONTRASENA")
	private String contrasena;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="holder_idtype",referencedColumnName="identification_type"),
		@JoinColumn(name="holder_idnumber",referencedColumnName="identification_number")
	})
	private Customer customer;

	@ManyToOne
	@JoinColumn(name="ID_ROL")
	private Rol rol;

	public Usuario(String usuario, String contrasena, Customer customer, Rol rol) {
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.customer = customer;
		this.rol = rol;
	}

	public Usuario() {
		super();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
}
