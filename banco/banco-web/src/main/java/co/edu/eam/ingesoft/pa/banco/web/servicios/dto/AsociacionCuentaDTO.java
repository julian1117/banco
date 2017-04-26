package co.edu.eam.ingesoft.pa.banco.web.servicios.dto;

import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;

public class AsociacionCuentaDTO {
	
	private String numeroId;

	private String tipoId;

	private String nombreTitular;

	private Banco banco;

	private String numero;

	private String nombreAs;
	
	private Customer cliente;


	
	

	public Customer getCliente() {
		return cliente;
	}

	public void setCliente(Customer cliente) {
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



}

