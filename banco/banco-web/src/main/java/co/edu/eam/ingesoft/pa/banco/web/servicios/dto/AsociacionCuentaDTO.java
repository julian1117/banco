package co.edu.eam.ingesoft.pa.banco.web.servicios.dto;

import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;

public class AsociacionCuentaDTO {
	
	private String numeroId;

	private String tipoId;

	private String nombreTitular;

	private String banco;

	private String numero;

	private String nombreAs;
	
	private String clienteCedula;
	
	private String clienteTipoCedula;

	public AsociacionCuentaDTO(String numeroId, String tipoId, String nombreTitular, String banco, String numero,
			String nombreAs, String clienteCedula, String clienteTipoCedula) {
		super();
		this.numeroId = numeroId;
		this.tipoId = tipoId;
		this.nombreTitular = nombreTitular;
		this.banco = banco;
		this.numero = numero;
		this.nombreAs = nombreAs;
		this.clienteCedula = clienteCedula;
		this.clienteTipoCedula = clienteTipoCedula;
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

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
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

	public String getClienteCedula() {
		return clienteCedula;
	}

	public void setClienteCedula(String clienteCedula) {
		this.clienteCedula = clienteCedula;
	}

	public String getClienteTipoCedula() {
		return clienteTipoCedula;
	}

	public void setClienteTipoCedula(String clienteTipoCedula) {
		this.clienteTipoCedula = clienteTipoCedula;
	}
	
}

