package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="Asociacion_Cuentas")
@NamedQueries({
	@NamedQuery(name= AsociacionCuentas.LISTAR_ASOCIACIONES, query = "SELECT a FROM AsociacionCuentas a WHERE a.cliente=?1"),
	@NamedQuery(name= AsociacionCuentas.LISTRA_ASOCIACIONES_VERIFICADA, query = "SELECT a FROM AsociacionCuentas a WHERE a.cliente=?1 and a.verificado=?2")
})
public class AsociacionCuentas  implements Serializable{
	
	public static final String LISTAR_ASOCIACIONES = "AsociacionCuentas.listar";
	public static final String LISTRA_ASOCIACIONES_VERIFICADA = "AsociacionCuentas.listarVeri";

	
	@Id
	@Column(name="numero_Id")
	private String numeroId;

	@Column(name="Tipo_Id")
	private String tipoId;
	
	@Column(name="nombre")
	private String nombreTitular;
	
	@ManyToOne
	@JoinColumn(name="banco_i")
	private Banco banco;
	
	@Column(name="Numero")
	private String numero;
	
	@Column(name="Nombre_asociacion")
	private String nombreAs;
	
	@Column(name="Verrificado")
	private String verificado;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="holder_idtype",referencedColumnName="identification_type"),
		@JoinColumn(name="holder_idnumber",referencedColumnName="identification_number")
	})
	private Customer cliente;

	public AsociacionCuentas() {
		super();
	}



	

	public AsociacionCuentas(String numeroId, String tipoId, String nombreTitular, Banco banco, String numero,
			String nombreAs, String verificado, Customer cliente) {
		super();
		this.numeroId = numeroId;
		this.tipoId = tipoId;
		this.nombreTitular = nombreTitular;
		this.banco = banco;
		this.numero = numero;
		this.nombreAs = nombreAs;
		this.verificado = verificado;
		this.cliente = cliente;
	}





	public String getNumeroId() {
		return numeroId;
	}





	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}





	public String getTipoId() {
		return tipoId;
	}





	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}





	public String getNombreTitular() {
		return nombreTitular;
	}





	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}





	public Banco getBanco() {
		return banco;
	}





	public void setBanco(Banco banco) {
		this.banco = banco;
	}





	public String getNumero() {
		return numero;
	}





	public void setNumero(String numero) {
		this.numero = numero;
	}





	public String getNombreAs() {
		return nombreAs;
	}





	public void setNombreAs(String nombreAs) {
		this.nombreAs = nombreAs;
	}





	public String getVerificado() {
		return verificado;
	}





	public void setVerificado(String verificado) {
		this.verificado = verificado;
	}





	public Customer getCliente() {
		return cliente;
	}





	public void setCliente(Customer cliente) {
		this.cliente = cliente;
	}



	
	
	
	
	
}
