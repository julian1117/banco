package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

@Named("interBancariaController")
@ViewScoped
public class TransInterBancariaControlador implements Serializable{

	private double monto;
	
	

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	
	
	
}
