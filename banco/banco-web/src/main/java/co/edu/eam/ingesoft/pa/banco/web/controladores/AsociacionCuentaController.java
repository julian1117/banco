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
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Verificacion;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.VerificarEJB;
import co.edu.eam.pa.clientews.RespuestaServicio;
import co.edu.eam.pa.clientews.TipoDocumentoEnum;

@Named("asociacionControler")
@ViewScoped
public class AsociacionCuentaController implements Serializable {

	private String numeroId;

	private String tipoId;

	private String nombreTitular;

	private Banco bancoNom;
	
	private String bancoId;

	private String numero;

	private String nombreAs;

	private String verificado;

	private Customer cliente;

	private AsociacionCuentas asociacion;

	private List<AsociacionCuentas> asociacionesLis;

	private List<Banco> listarBanco;

	private RespuestaServicio resServicio;

	@Inject
	private SessionController sesionController;

	@EJB
	private ClienteEJB clienteEJB;

	@EJB
	private AsociacionEJB asociacionEJB;
	

	@EJB
	private VerificarEJB verificarEJB;


	@PostConstruct
	public void inicializar() {
		asociacionesLis = asociacionEJB.listarAsociaciones(sesionController.getUse().getCustomer());
		listarBanco = asociacionEJB.listarBancos();

	}

	public void crearAs() {
		try {
			Customer busCliente = clienteEJB.buscarCliente(
					sesionController.getUse().getCustomer().getNumeroIndentificacion(),
					sesionController.getUse().getCustomer().getTipoIdentificacion());

			if (busCliente != null) {
				if (busCliente.getTipoIdentificacion().equals("Cedula")) {
					TipoDocumentoEnum cedu = TipoDocumentoEnum.CC;
					tipoId = cedu.toString();
					String id = tipoId;
					
					Banco idBanco = asociacionEJB.buscarBanco(bancoNom.getIdBanco());
					if (idBanco.getIdBanco() != null) {		
											
						if(idBanco.getIdBanco().equals("01")){
							verificarEJB.verificar(asociacion);
						}
						
						AsociacionCuentas asociacionCuenta = new AsociacionCuentas(numeroId, id, nombreTitular,
								idBanco, numero, nombreAs,"PENDIENTE" , busCliente);
						
						asociacionEJB.crearAsociacion(asociacionCuenta);
						asociacionesLis = asociacionEJB.listarAsociaciones(busCliente);

						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Creado Con Exito!!", "La asociacion fue creada exitosamente!!"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

		}
	}

	public void eliminar(AsociacionCuentas asoci) {
		asociacionEJB.eliminarAsociacion(asoci);
		asociacionesLis = asociacionEJB.listarAsociaciones(sesionController.getUse().getCustomer());
	}
	
	public void verificarCuenta(AsociacionCuentas asociacionVerificar){

//		String numeroVeri = asociacionVerificar.getVerificado();
//		String idcliente = asociacionVerificar.getTipoId();
//		String numeroIdcliente = asociacionVerificar.getNumeroId();
//		String nombreAsoci = asociacionVerificar.getNombreAs();
//		String numeroCuentaAsociacion = asociacionVerificar.getNumero();
//		String banco = asociacionVerificar.getBanco().getNombre();
//		
//		if(numeroVeri.equals("PENDIENTE")){
//			if(idcliente.equals("CC")){
//				TipoDocumentoEnum tipo = TipoDocumentoEnum.CC;
//				asociacionVerificar.setVerificado("VERIFICADA");
				verificarEJB.verificar(asociacionVerificar);
				
				asociacionEJB.verificar(asociacionVerificar);
				Messages.addFlashGlobalInfo(asociacionVerificar.getVerificado()+"");
				asociacionEJB.verificar(asociacionVerificar);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Verificado!", "La asociacion fue verificada exitosamente!!"));

//			}
//			}
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

	public String getVerificado() {
		return verificado;
	}

	public void setVerificado(String verificado) {
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

	public AsociacionCuentas getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(AsociacionCuentas asociacion) {
		this.asociacion = asociacion;
	}

}
