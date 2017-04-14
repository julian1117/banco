package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="Asociacion_Cuentas")
@NamedQuery(name= AsociacionCuentas.LISTRA_ASOCIACIONES, query = "SELECT a FROM AsociacionCuentas a WHERE a.cliente=?1")
public class AsociacionCuentas  implements Serializable{
	
	public static final String LISTRA_ASOCIACIONES = "AsociacionCuentas.listar";
	
	@Id
	@Column(name="numero_Id")
	private String numeroId;

	@Column(name="Tipo_Id")
	private String tipoId;
	
	@Column(name="nombre")
	private String nombreTitular;
	
	
	@Column(name="banco_i")
	private Banco banco;
	
	@Column(name="Numero")
	private String numero;
	
	@Column(name="Nombre_asociacion")
	private String nombreAs;
	
	@Column(name="Verrificado")
	private String verificado;
	
	
	@Column(name="cliente_i")
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





	public static String getListraAsociaciones() {
		return LISTRA_ASOCIACIONES;
	}

	
	
	
	
	
}
