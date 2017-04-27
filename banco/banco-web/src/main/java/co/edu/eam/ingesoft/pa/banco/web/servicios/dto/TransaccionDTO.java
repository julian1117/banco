package co.edu.eam.ingesoft.pa.banco.web.servicios.dto;

import java.util.Date;

import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;

public class TransaccionDTO {
	
	
	private int id;
	

	private SavingAccount savingAcountNumber;
	
	
	private Double ammount;
	
	
	private Date transationDate;
	

	private String sourceTransaction;
	

	private TipoTransacion tipoTransaccion;
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public SavingAccount getSavingAcountNumber() {
		return savingAcountNumber;
	}


	public void setSavingAcountNumber(SavingAccount savingAcountNumber) {
		this.savingAcountNumber = savingAcountNumber;
	}


	public Double getAmmount() {
		return ammount;
	}


	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}


	public Date getTransationDate() {
		return transationDate;
	}


	public void setTransationDate(Date transationDate) {
		this.transationDate = transationDate;
	}


	public String getSourceTransaction() {
		return sourceTransaction;
	}


	public void setSourceTransaction(String sourceTransaction) {
		this.sourceTransaction = sourceTransaction;
	}


	public TipoTransacion getTipoTransaccion() {
		return tipoTransaccion;
	}


	public void setTipoTransaccion(TipoTransacion tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}



}
