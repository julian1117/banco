package co.edu.eam.ingesoft.pa.banco.web.servicios.dto;

import java.util.Date;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;

public class TransaccionDTO {
	
	private String cedula;
	
	private String numeroCedula;	
		
	private String asociacionCuenta;
	
	private double monto;
	
	private int codigo;
	
	private String banco;

	
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNumeroCedula() {
		return numeroCedula;
	}

	public void setNumeroCedula(String numeroCedula) {
		this.numeroCedula = numeroCedula;
	}

	public String getAsociacionCuenta() {
		return asociacionCuenta;
	}

	public void setAsociacionCuenta(String asociacionCuenta) {
		this.asociacionCuenta = asociacionCuenta;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

}
