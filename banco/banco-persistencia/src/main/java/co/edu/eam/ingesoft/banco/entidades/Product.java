package co.edu.eam.ingesoft.banco.entidades;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_PRODUCT")
@NamedQuery(name=Product.LISTA_PRODUCTOS,query="SELECT p FROM Product p WHERE p.holder.tipoIdentificacion=?1 AND p.holder.numeroIndentificacion=?2")
@Inheritance(strategy=InheritanceType.JOINED)
public class Product implements Serializable{

	public static final String LISTA_PRODUCTOS ="Product.listaProducto";
	
	@Id
	@Column(name="number",length=16)
	private String numero;
	
	@Temporal(TemporalType.DATE)
	@Column(name="expedition_date")
	private Date fechaExpedicion;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="holder_idtype",referencedColumnName="identification_type"),
		@JoinColumn(name="holder_idnumber",referencedColumnName="identification_number")
	})
	private Customer holder;

	public Product() {
		super();
	}

	
	public Product(String numero, Date fechaExpedicion, Customer holder) {
		super();
		this.numero = numero;
		this.fechaExpedicion = fechaExpedicion;
		this.holder = holder;
	}




	public String getNumero() {
		return numero;
	}




	public void setNumero(String numero) {
		this.numero = numero;
	}




	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}




	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}




	public Customer getHolder() {
		return holder;
	}




	public void setHolder(Customer holder) {
		this.holder = holder;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Product other = (Product) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	
	
}
	