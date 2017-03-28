package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardConsume;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoConsumoEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoPagoConsumoRemote;

@Named(value = "controllerAvance")
@ViewScoped
public class ControladorAvances implements Serializable{

	private Credicart tarjetaCredi;

	private SavingAccount cuentaAh;

	private List<Credicart> listnumTarjeta;

	private List<SavingAccount> listaCuentaAhorro;

//	@Pattern(regexp = "[0-9]*", message = "Solo numeros")
//	@Length(min = 1, max = 10, message = "Longitus de 1 a 10")
	private double cantidad;
	
	
	
	public Credicart getTarjetaCredi() {
		return tarjetaCredi;
	}

	public void setTarjetaCredi(Credicart tarjetaCredi) {
		this.tarjetaCredi = tarjetaCredi;
	}

	public SavingAccount getCuentaAh() {
		return cuentaAh;
	}

	public void setCuentaAh(SavingAccount cuentaAh) {
		this.cuentaAh = cuentaAh;
	}

	public List<Credicart> getListnumTarjeta() {
		return listnumTarjeta;
	}

	public void setListnumTarjeta(List<Credicart> listnumTarjeta) {
		this.listnumTarjeta = listnumTarjeta;
	}

	public List<SavingAccount> getListaCuentaAhorro() {
		return listaCuentaAhorro;
	}

	public void setListaCuentaAhorro(List<SavingAccount> listaCuentaAhorro) {
		this.listaCuentaAhorro = listaCuentaAhorro;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	@EJB
	private TarjetaCreditoConsumoEJB consumoTarjeta;
	
	@EJB
	private CuentaAhorrosEJB cuentaAhorrosEjb;

	@EJB
	private TarjetaCreditoPagoConsumoRemote pagoEjb;

	@Inject
	private SessionController usuarioSesion;

	@PostConstruct
	public void inicializar() {
		listnumTarjeta = pagoEjb.listaTarjetaCredito(usuarioSesion.getUse().getCustomer().getNumeroIndentificacion(),
				usuarioSesion.getUse().getCustomer().getTipoIdentificacion());

		listaCuentaAhorro = pagoEjb.listaCuentaAhorros(usuarioSesion.getUse().getCustomer().getNumeroIndentificacion(),
				usuarioSesion.getUse().getCustomer().getTipoIdentificacion());

	}
	
	public void avance(){
		try{
			consumoTarjeta.avanceCuentaAhorro(tarjetaCredi.getNumero(), cantidad, cuentaAh.getNumero());
			Messages.addFlashGlobalInfo("Avance realizado con exito");
		}catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	public String cancelar(){
		return "../Inicio.xhtml";
	}
	
}
