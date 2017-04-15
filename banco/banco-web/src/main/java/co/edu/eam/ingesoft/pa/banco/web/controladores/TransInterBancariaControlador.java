package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Verificacion;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoPagoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.VerificarEJB;

@Named("interBancariaController")
@ViewScoped
public class TransInterBancariaControlador implements Serializable {

	private double monto;

	private int validarCod;

	private AsociacionCuentas asociacion;

	private List<AsociacionCuentas> asociacionesLis;

	private SavingAccount cuentaAhorros;

	private List<SavingAccount> listCuentaAhorros;

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getValidarCod() {
		return validarCod;
	}

	public void setValidarCod(int validarCod) {
		this.validarCod = validarCod;
	}

	public AsociacionCuentas getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(AsociacionCuentas asociacion) {
		this.asociacion = asociacion;
	}

	public List<AsociacionCuentas> getAsociacionesLis() {
		return asociacionesLis;
	}

	public void setAsociacionesLis(List<AsociacionCuentas> asociacionesLis) {
		this.asociacionesLis = asociacionesLis;
	}

	public SavingAccount getCuentaAhorros() {
		return cuentaAhorros;
	}

	public void setCuentaAhorros(SavingAccount cuentaAhorros) {
		this.cuentaAhorros = cuentaAhorros;
	}

	public List<SavingAccount> getListCuentaAhorros() {
		return listCuentaAhorros;
	}

	public void setListCuentaAhorros(List<SavingAccount> listCuentaAhorros) {
		this.listCuentaAhorros = listCuentaAhorros;
	}

	@EJB
	private AsociacionEJB asociacionEJB;

	@EJB
	private TarjetaCreditoPagoConsumoRemote pagoEjb;

	@EJB
	private CuentaAhorrosEJB cuentaAhEjb;
	
	@EJB
	private VerificarEJB veryEJB;

	@Inject
	private SessionController sesionController;

	@PostConstruct
	public void inicializar() {
		listCuentaAhorros = pagoEjb.listaCuentaAhorros(
				sesionController.getUse().getCustomer().getNumeroIndentificacion(),
				sesionController.getUse().getCustomer().getTipoIdentificacion());
		asociacionesLis = asociacionEJB.listaAsociadaVeri(sesionController.getUse().getCustomer());
	}

	/**
	 * Msj de validaciond e transacion
	 */
	public void validarTranferencia() {
		cuentaAhEjb.validarTransaaccion(sesionController.getUse());
	}

	/**
	 * Realizar transferencia
	 */
	public void transferencia() {
		try {
			if(monto > 0){
				veryEJB.confirmarTransaccion(sesionController.getUsuario(), validarCod, cuentaAhorros.getNumero(),
					monto, asociacion.getBanco().getIdBanco());
			Messages.addGlobalInfo("Transaccion interbancaria con exito");
			}else{
				Messages.addGlobalError("Favor ingresar un valor mayor a ' 0 '");
			}
		} catch (Exception e) {
			Messages.addGlobalError(e.getMessage());
		}

	}

}
