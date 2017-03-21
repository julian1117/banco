package co.edu.eam.ingesoft.controlador;

import java.util.List;

import javax.naming.NamingException;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardConsume;
import co.edu.eam.ingesoft.banco.entidades.CreditcardPaymentConsume;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Franchise;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Transaction;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoConsumo;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ICuentaAhorrosRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoPagoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITransaccionRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.IclienteRemote;
import util.ServiceLocator;

public class ControladorVentanaCliente {

	private IclienteRemote iClienteRetmote;
	private ITarjetaCreditoRemote iTarjetaCredi;
	private ICuentaAhorrosRemote iCuentaAhorros;
	private ITransaccionRemote iTransaccion;
	private ITarjetaCreditoConsumoRemote iTarjetaConsumo;
	private ITarjetaCreditoPagoConsumoRemote iTarjetaPago;

	public ControladorVentanaCliente() throws NamingException {

		iClienteRetmote = (IclienteRemote) ServiceLocator.buscarEJB("ClienteEJB",
				IclienteRemote.class.getCanonicalName());

		iTarjetaCredi = (ITarjetaCreditoRemote) ServiceLocator.buscarEJB("TarjetaCreditoEJB",
				ITarjetaCreditoRemote.class.getCanonicalName());

		iCuentaAhorros = (ICuentaAhorrosRemote) ServiceLocator.buscarEJB("CuentaAhorrosEJB",
				ICuentaAhorrosRemote.class.getCanonicalName());

		iTransaccion = (ITransaccionRemote) ServiceLocator.buscarEJB("TransaccionEJB",
				ITransaccionRemote.class.getCanonicalName());

		iTarjetaConsumo = (ITarjetaCreditoConsumoRemote) ServiceLocator.buscarEJB("TarjetaCreditoConsumoEJB",
				ITarjetaCreditoConsumoRemote.class.getCanonicalName());
		
		iTarjetaPago = (ITarjetaCreditoPagoConsumoRemote) ServiceLocator.buscarEJB("TarjetaCreditoPagoConsumoRemote",
				ITarjetaCreditoPagoConsumoRemote.class.getCanonicalName());
	}

	/**
	 * Buscar cliente
	 * 
	 * @param idNumber
	 *            numero de identificacion que recibe
	 * @param idType
	 *            tipo de identificacion que recibe
	 * @return cliente si lo entonctro, de lo contrario null
	 */
	public Customer buscarCliente(String idNumber, String idType) {
		return iClienteRetmote.buscarCliente(idNumber, idType);
	}

	/**
	 * instancia de crear cliente
	 * 
	 * @param cliente
	 *            que recibe
	 */
	public void crearCliente(Customer cliente) {
		iClienteRetmote.crearCLiente(cliente);
	}

	/**
	 * lista de franquicias
	 * 
	 * @return lista de franquicias
	 */
	public List<Franchise> listaFranquicia() {
		return iTarjetaCredi.listaFranquicia();
	}

	/**
	 * instancia de crear tarjeta
	 * 
	 * @param tarjetaCredito
	 *            que recibe
	 */
	public void crearTarjetaCredito(Credicart tarjetaCredito, String numeroId, String tipoId) {
		iTarjetaCredi.crearTarjetaCredito(tarjetaCredito, numeroId, tipoId);
	}

	/**
	 * instancia de crear una cuenta de ahorros
	 * 
	 * @param cuentaAhorro
	 *            que recibe
	 */
	public void crearCuentaAhorro(SavingAccount cuentaAhorro, String numeroId, String tipoId) {
		iCuentaAhorros.crearCuentaAhorro(cuentaAhorro, numeroId, tipoId);
	}

	/**
	 * instancia de crear transaccion
	 * 
	 * @param transaccion
	 *            que recibe
	 */
	public void crearTransaccion(Transaction transaccion, String numero, String cuentaB) {
		iTransaccion.crearTransaccion(transaccion, numero, cuentaB);
	}
	
	public void registrarConsumo(CreditcardConsume consumoTarjeta,String numTarjeta,TipoConsumo tipoConsumo){
		iTarjetaConsumo.registrarConsumo(consumoTarjeta,numTarjeta,tipoConsumo);
	}

	public void pagoTarjeta(double saldo,String numTarjeta){
		iTarjetaPago.pagoTarjeta(saldo, numTarjeta);
	}
	
	public double consultarCuota(String numTarjeta){
		return iTarjetaPago.consultarCuota(numTarjeta);
	}
}
