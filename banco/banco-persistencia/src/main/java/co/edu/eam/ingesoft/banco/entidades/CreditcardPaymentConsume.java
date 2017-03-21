package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="T_CREDITCARD_PAYMENT_CONSUME")
@NamedQuery(name=CreditcardPaymentConsume.LISTA_CONSUMO,query="SELECT c FROM CreditcardPaymentConsume c WHERE c.consume.creditcard_number.numero=?1")
public class CreditcardPaymentConsume implements Serializable{

	public static final String LISTA_CONSUMO = "CreditcardPaymentConsume.ListaConsumo";
	
	@ManyToOne
	@JoinColumn(name="id_consume")
	private CreditcardConsume consume;
	
	@Column(name="payment_date")
	private Date paymentDate;
	
	@Column(name="share")
	private int share;
	
	@Column(name="ammount")
	private double ammount;
	
	@Column(name="capital_ammount")
	private double capitalAmmount;
	
	@Column(name="interest_ammount")
	private double interestAmmount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	public CreditcardPaymentConsume() {
		super();
	}

	public CreditcardPaymentConsume(CreditcardConsume consume, Date paymentDate, int share, double ammount,
			double capitalAmmount, double interestAmmount, int id) {
		super();
		this.consume = consume;
		this.paymentDate = paymentDate;
		this.share = share;
		this.ammount = ammount;
		this.capitalAmmount = capitalAmmount;
		this.interestAmmount = interestAmmount;
		this.id = id;
	}

	public CreditcardConsume getIdConsume() {
		return consume;
	}

	public void setIdConsume(CreditcardConsume idConsume) {
		this.consume = idConsume;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

	public double getCapitalAmmount() {
		return capitalAmmount;
	}

	public void setCapitalAmmount(double capitalAmmount) {
		this.capitalAmmount = capitalAmmount;
	}

	public double getInterestAmmount() {
		return interestAmmount;
	}

	public void setInterestAmmount(double interestAmmount) {
		this.interestAmmount = interestAmmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		CreditcardPaymentConsume other = (CreditcardPaymentConsume) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
	
	
}
