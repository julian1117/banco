package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.DefaultEditorKit.CutAction;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.Product;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoEJB;

@Named("inicioController")
@SessionScoped
public class InicioControlador implements Serializable{
	
	
	private List<Credicart> listaTarjeta;
	
	@EJB
	private TarjetaCreditoEJB tarjetaEJB;	
	
	@Inject
	private SessionController sesionUs;
	
	
	
	
	
	public List<Credicart> getListaTarjeta() {
		return listaTarjeta;
	}

	public void setListaTarjeta(List<Credicart> listaTarjeta) {
		this.listaTarjeta = listaTarjeta;
	}

	public void cargarDatosCliente(){
		
		listaTarjeta = tarjetaEJB.listTarjetaCliente(sesionUs.getUse().getCustomer());
		
	}
	
	@PostConstruct
	public void inicializar(){
		cargarDatosCliente();
	}
	

}
