package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="Banco")
@NamedQuery(name=Banco.LISTAR_BANCO,query= "SELECT b FROM Banco b")
public class Banco implements Serializable{
	
	public static final String LISTAR_BANCO = "Banco.listarBanco";
	
	@Id
	@Column(name="Id_Banco")
	private String idBanco;
	
	@Column(name="Nombre_Banco")
	private String nombre;

	public Banco() {
		super();
	}

	public Banco(String idBanco, String nombre) {
		super();
		this.idBanco = idBanco;
		this.nombre = nombre;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static String getListarBanco() {
		return LISTAR_BANCO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBanco == null) ? 0 : idBanco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banco other = (Banco) obj;
		if (idBanco == null) {
			if (other.idBanco != null)
				return false;
		} else if (!idBanco.equals(other.idBanco))
			return false;
		return true;
	}

	
	

}
