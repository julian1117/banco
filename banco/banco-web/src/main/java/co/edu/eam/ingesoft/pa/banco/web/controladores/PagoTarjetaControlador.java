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
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Product;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoPagoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Named(value = "pagoController")
@ViewScoped
public class PagoTarjetaControlador implements Serializable {

	@Pattern(regexp = "[0-9]*", message = "Solo numeros")
	@Length(min = 1, max = 10, message = "Longitus de 1 a 10")
	private String cedula;

	private String tipoDocumento;

	private Credicart tarjeta;

	private String nombreUs;

	private List<Credicart> listnumTarjeta;

	private double pago;

	private SavingAccount cuentaAhorros;

	private List<SavingAccount> listCuentaAhorros;

	private List<CreditcardConsume> listaConsumo;

	public SavingAccount getCuentaAhorros() {
		return cuentaAhorros;
	}

	public void setCuentaAhorros(SavingAccount cuentaAhorros) {
		this.cuentaAhorros = cuentaAhorros;
	}

	public List<SavingAccount> getListCuentaAhorros() {
		return listCuentaAhorros;
	}

	public Credicart getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Credicart tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<CreditcardConsume> getListaConsumo() {
		return listaConsumo;
	}

	public double getPago() {
		return pago;
	}

	public List<Credicart> getListnumTarjeta() {
		return listnumTarjeta;
	}

	public void setPago(double pago) {
		this.pago = pago;
	}

	public String getNombreUs() {
		return nombreUs;
	}

	public void setNombreUs(String nombreUs) {
		this.nombreUs = nombreUs;
	}

	@EJB
	private CuentaAhorrosEJB cuentaAhorrosEjb;

	@EJB
	private TarjetaCreditoPagoConsumoRemote pagoEjb;

	@EJB
	private ClienteEJB clientJ;

	@Inject
	private SessionController usuarioSesion;

	@PostConstruct
	public void inicializar() {
		cargarDatosUsuario();
	}

	/**
	 * Consultar cuota de pago de la tarjeta de credito
	 */
	public void ConsultarCuota() {
		try {
			pago = pagoEjb.consultarCuota(tarjeta.getNumero());
			listaConsumo = pagoEjb.listaConsumo(tarjeta.getNumero());
			Messages.addFlashGlobalInfo("El sado de la cuota es de: " + pago);
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	/**
	 * Pagar con cuenta de ahorros
	 */
	public void realizarPago() {
		try {
			if (pago > 0) {
				pagoEjb.descontarCuentaAhorros(cuentaAhorros.getNumero(), pago, tarjeta.getNumero());
				Messages.addFlashGlobalInfo("Pago registrado con exito");
				pago = 0;
				listaConsumo = pagoEjb.listaConsumo(tarjeta.getNumero());
			} else {
				Messages.addFlashGlobalError("Por favor verifique los campos");
			}

		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	/**
	 * Consultar el total del consumo de una tarjeta de cerdito
	 */
	public void totalConsumo() {
		try {
			if (tarjeta.getNumero() != "") {
				double total = pagoEjb.totalConsumo(tarjeta.getNumero());
				pago = total;
				listaConsumo = pagoEjb.listaConsumo(tarjeta.getNumero());
				Messages.addFlashGlobalInfo("El saldo totoal a pagar es de: " + total);
			} else {
				Messages.addFlashGlobalError("No ha ingresado un numero de tarjeta de credito");
			}
		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	/**
	 * Pagar un solo consumo que se haya seleccionado de la tabla
	 * 
	 * @param consumo
	 */
	public void pagarConsumo(CreditcardConsume consumo) {
		pagoEjb.pagoConsumo(consumo, cuentaAhorros.getNumero());
		Messages.addFlashGlobalInfo("Pago de consumo con exito");
		listnumTarjeta = pagoEjb.listaTarjetaCredito(cedula, tipoDocumento);
		listaConsumo = pagoEjb.listaConsumo(listnumTarjeta.get(0).getNumero());
	}

	/**
	 * Cargar los datos del usuario que inicio seccion para usarlo a nivel
	 * global en la clase
	 */
	public void cargarDatosUsuario() {
		try {
			Customer cliente = clientJ.buscarCliente(usuarioSesion.getUse().getCustomer().getNumeroIndentificacion(),
					usuarioSesion.getUse().getCustomer().getTipoIdentificacion());

			cedula = cliente.getNumeroIndentificacion();
			tipoDocumento = cliente.getTipoIdentificacion();
			listnumTarjeta = pagoEjb.listaTarjetaCredito(cedula, tipoDocumento);
			listaConsumo = pagoEjb.listaConsumo(listnumTarjeta.get(0).getNumero());
			listCuentaAhorros = pagoEjb.listaCuentaAhorros(cedula, tipoDocumento);
			pago = pagoEjb.consultarCuota(listnumTarjeta.get(0).getNumero());
			nombreUs = cliente.getName() + " " + cliente.getLastName();
		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

}
