package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;

@Entity
@Table(name="T_CUSTOMER")
@IdClass(CustomerPK.class)
@NamedQuery(name=Customer.LIST_CLIENTES,query="SELECT c FROM Customer c")
public class Customer implements Serializable{

	public static final String LIST_CLIENTES = "Customer.listaCliente";
	
	@Column(name="NAME",length=50)
	private String name;
	
	@Column(name="LASTNAME",length=50)
	private String lastName;
	
	@Id	
	@Column(name="identification_type")
	private String tipoIdentificacion;
	
	@Id
	@Column(name="identification_number")
	private String numeroIndentificacion;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={},mappedBy="holder")
	private List<Product> producto;
	
	public Customer() {
		super();
	}

	

	public Customer(String name, String lastName, String tipoIdentificacion, String numeroIndentificacion) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIndentificacion = numeroIndentificacion;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}



	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}



	public String getNumeroIndentificacion() {
		return numeroIndentificacion;
	}



	public void setNumeroIndentificacion(String numeroIndentificacion) {
		this.numeroIndentificacion = numeroIndentificacion;
	}



	public List<Product> getProducto() {
		return producto;
	}



	public void setProducto(List<Product> producto) {
		this.producto = producto;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroIndentificacion == null) ? 0 : numeroIndentificacion.hashCode());
		result = prime * result + ((tipoIdentificacion == null) ? 0 : tipoIdentificacion.hashCode());
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
		Customer other = (Customer) obj;
		if (numeroIndentificacion == null) {
			if (other.numeroIndentificacion != null)
				return false;
		} else if (!numeroIndentificacion.equals(other.numeroIndentificacion))
			return false;
		if (tipoIdentificacion == null) {
			if (other.tipoIdentificacion != null)
				return false;
		} else if (!tipoIdentificacion.equals(other.tipoIdentificacion))
			return false;
		return true;
	}


	
	
}
