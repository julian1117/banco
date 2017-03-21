package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_CREDITCART")
public class Credicart extends Product implements Serializable {


	
	@Column(name = "cvc", length = 3)
	private String cvc;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiration_date")
	private Date fechaExpiracion;

	@ManyToOne
	@JoinColumn(name = "franchise")
	private Franchise franchise;

	@Column(name = "monto")
	private double monto;

	@Column(name = "saldo_cosumido")
	private double saldoConsumido;

	public Credicart() {
		super();
	}

	public Credicart(String cvc, Date fechaExpiracion, Franchise franchise, double monto, double saldoConsumido) {
		super();
		this.cvc = cvc;
		this.fechaExpiracion = fechaExpiracion;
		this.franchise = franchise;
		this.monto = monto;
		this.saldoConsumido = saldoConsumido;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public Franchise getFranchise() {
		return franchise;
	}

	public void setFranchise(Franchise franchise) {
		this.franchise = franchise;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getSaldoConsumido() {
		return saldoConsumido;
	}

	public void setSaldoConsumido(double saldoConsumido) {
		this.saldoConsumido = saldoConsumido;
	}

	

}
