package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import org.omnifaces.cdi.ViewScoped;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;


@Named("asociacionControler")
@ViewScoped
public class AsociacionCuentaController implements Serializable{

	private String numeroId;

	private String tipoId;
	
	private String nombreTitular;
	
	private Banco bancoNom;
	
	private String numero;
	
	private String nombreAs;
	
	private boolean verificado;
	
	private Customer cliente;
	
	private List<AsociacionCuentas> asociacionesLis;
	
	private List<Banco> listarBanco;
	
	




	@Inject
	private SessionController sesionController;
	
	@EJB
	private ClienteEJB clienteEJB;

	@EJB
	private AsociacionEJB asociacionEJB;

	@PostConstruct
	public void inicializar(){
		asociacionesLis = asociacionEJB.listarAsociaciones(sesionController.getUse().getCustomer());
		listarBanco = asociacionEJB.listarBancos();
		
			}


	
	public void crearAs(){
		try{
			Customer busCliente = clienteEJB.buscarCliente(sesionController.getUse().getCustomer().getNumeroIndentificacion(),
					sesionController.getUse().getCustomer().getTipoIdentificacion());
			if(busCliente!=null){
				if(bancoNom!=null){
					AsociacionCuentas asociacionCuenta = new AsociacionCuentas(numeroId, tipoId, nombreTitular, bancoNom, numero, nombreAs,true,busCliente);
					asociacionEJB.crearAsociacion(asociacionCuenta);
					asociacionesLis = asociacionEJB.listarAsociaciones(busCliente);
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Creado Con Exito!!",
							"La asociacion fue creada exitosamente!!"));
				}
				
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), null));
		
		}
	}



	public String getNumeroId() {
		return numeroId;
	}



	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}



	public String getTipoId() {
		return tipoId;
	}



	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}



	public String getNombreTitular() {
		return nombreTitular;
	}



	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}



	public Banco getBancoNom() {
		return bancoNom;
	}



	public void setBancoNom(Banco bancoNom) {
		this.bancoNom = bancoNom;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getNombreAs() {
		return nombreAs;
	}



	public void setNombreAs(String nombreAs) {
		this.nombreAs = nombreAs;
	}



	public boolean isVerificado() {
		return verificado;
	}



	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}



	public Customer getCliente() {
		return cliente;
	}



	public void setCliente(Customer cliente) {
		this.cliente = cliente;
	}



	public List<AsociacionCuentas> getAsociacionesLis() {
		return asociacionesLis;
	}



	public void setAsociacionesLis(List<AsociacionCuentas> asociacionesLis) {
		this.asociacionesLis = asociacionesLis;
	}



	public List<Banco> getListarBanco() {
		return listarBanco;
	}



	public void setListarBanco(List<Banco> listarBanco) {
		this.listarBanco = listarBanco;
	}



	public SessionController getSesionController() {
		return sesionController;
	}



	public void setSesionController(SessionController sesionController) {
		this.sesionController = sesionController;
	}



	public ClienteEJB getClienteEJB() {
		return clienteEJB;
	}



	public void setClienteEJB(ClienteEJB clienteEJB) {
		this.clienteEJB = clienteEJB;
	}



	public AsociacionEJB getAsociacionEJB() {
		return asociacionEJB;
	}



	public void setAsociacionEJB(AsociacionEJB asociacionEJB) {
		this.asociacionEJB = asociacionEJB;
	}
	

	
}
