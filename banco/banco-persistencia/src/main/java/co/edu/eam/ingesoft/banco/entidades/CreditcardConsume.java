package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_CREDITCARD_CONSUME")
@NamedQuery(name=CreditcardConsume.LIST_CONSUMO_TARJETA,query="select c from CreditcardConsume c where c.creditcard_number.numero=?1 AND c.is_payed=false")
public class CreditcardConsume implements Serializable{

	public static final String LIST_CONSUMO_TARJETA = "CreditcardConsume.listaTarjetaConsumo";
	
	@ManyToOne
	@JoinColumn(name="creditcard_number")
	private Credicart creditcard_number;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_consume")
	private Date dateconsume;
	
	@Column(name="number_shares")
	private int numbershares;
	
	@Column(name="ammount")
	private double ammount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="interest")
	private double interest;
	
	@Column(name="remaining_ammount")
	private double remaining_ammount;
	
	@Column(name="is_payed")
	private boolean is_payed;

	@Column(name="remaining_rets")
	private int cuotaRestante;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={},mappedBy="consume")
	private List<CreditcardPaymentConsume> creditcardPaymentConsume;
	
	public CreditcardConsume() {
		super();
	}

	public CreditcardConsume(Credicart creditcard_number, Date date_consume, int number_shares, double ammount, int id,
			double interest, double remaining_ammount, boolean is_payed,int cuotaRestante) {
		super();
		this.creditcard_number = creditcard_number;
		this.dateconsume = date_consume;
		this.numbershares = number_shares;
		this.ammount = ammount;
		this.id = id;
		this.interest = interest;
		this.remaining_ammount = remaining_ammount;
		this.is_payed = is_payed;
		this.cuotaRestante = cuotaRestante;
	}

	
	
	public int getCuotaRestante() {
		return cuotaRestante;
	}

	public void setCuotaRestante(int cuotaRestante) {
		this.cuotaRestante = cuotaRestante;
	}

	public Credicart getCreditcard_number() {
		return creditcard_number;
	}

	public void setCreditcard_number(Credicart creditcard_number) {
		this.creditcard_number = creditcard_number;
	}

	public Date getDate_consume() {
		return dateconsume;
	}

	public void setDate_consume(Date date_consume) {
		this.dateconsume = date_consume;
	}

	public int getNumber_shares() {
		return numbershares;
	}

	public void setNumber_shares(int number_shares) {
		this.numbershares = number_shares;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getRemaining_ammount() {
		return remaining_ammount;
	}

	public void setRemaining_ammount(double remaining_ammount) {
		this.remaining_ammount = remaining_ammount;
	}

	public boolean isIs_payed() {
		return is_payed;
	}

	public void setIs_payed(boolean is_payed) {
		this.is_payed = is_payed;
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
		CreditcardConsume other = (CreditcardConsume) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
	
	
}
