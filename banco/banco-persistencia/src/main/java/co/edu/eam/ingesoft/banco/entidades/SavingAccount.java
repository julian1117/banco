package co.edu.eam.ingesoft.banco.entidades;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="T_SAVING_ACCOUNT")
public class SavingAccount extends Product implements Serializable{

	
	@Column(name="saving_interest")
	private double saving_interest;
	
	@Column(name="ammount")
	private double ammount;

	public SavingAccount() {
		super();
	}

	public SavingAccount(double saving_interest, double ammount) {
		super();
		this.saving_interest = saving_interest;
		this.ammount = ammount;
	}

	public double getSaving_interest() {
		return saving_interest;
	}

	public void setSaving_interest(double saving_interest) {
		this.saving_interest = saving_interest;
	}

	public double getAmmount() {
		return ammount;
	}

	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}

		
}
