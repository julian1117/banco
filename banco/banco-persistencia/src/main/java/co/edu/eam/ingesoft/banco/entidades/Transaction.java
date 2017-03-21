package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;

@Entity
@Table(name="T_TRANSACTION")
@NamedQuery(name=Transaction.LISTA_TRANSACCION,query="SELECT t FROM Transaction t WHERE t.tipoTransaccion=?1 and t.savingAcountNumber.numero=?2")
public class Transaction implements Serializable{

	public static final String LISTA_TRANSACCION = "Transaction.listaTransaccion";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="saving_account_number")
	private SavingAccount savingAcountNumber;
	
	@Column(name="ammount")
	private Double ammount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="transaction_date")
	private Date transationDate;
	
	@Column(name="source_transaction")
	private String sourceTransaction;
	
	@Enumerated(EnumType.STRING)
	private TipoTransacion tipoTransaccion;
	
	public Transaction() {
		super();
	}

	
	public Transaction(int id, SavingAccount savingAcountNumber, Double ammount, Date transationDate,
			String sourceTransaction, TipoTransacion tipoTransaccion) {
		super();
		this.id = id;
		this.savingAcountNumber = savingAcountNumber;
		this.ammount = ammount;
		this.transationDate = transationDate;
		this.sourceTransaction = sourceTransaction;
		this.tipoTransaccion = tipoTransaccion;
	}

	
	

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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Transaction other = (Transaction) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
