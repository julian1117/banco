package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.BusyConversationException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardConsume;
import co.edu.eam.ingesoft.banco.entidades.CreditcardPaymentConsume;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Product;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Transaction;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoPagoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
@Remote(ITarjetaCreditoPagoConsumoRemote.class)
public class TarjetaCreditoPagoConsumoRemote {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private TarjetaCreditoConsumoEJB tConsuoTarjeta;

	@EJB
	private TarjetaCreditoEJB tarjeta;

	@EJB
	private TransaccionEJB transaEjb;

	public void pagoTarjeta(double saldo, String numTarjeta) {
		List<CreditcardConsume> listaCons = listaConsumo(numTarjeta);

		for (int i = 0; i < listaCons.size(); i++) {

			CreditcardPaymentConsume tarjetaPagoConsumo = new CreditcardPaymentConsume();
			Credicart tar = tarjeta.buscarTarjetaCRedito(numTarjeta);
			CreditcardConsume consumo = new CreditcardConsume();

			consumo = tConsuoTarjeta.BuscarConsumo(listaCons.get(i).getId());

			double cuotaTt = totalConsumo(numTarjeta);
			double cuota = consultarCuota(numTarjeta);
			double verificar = saldo - cuota;
			// verificar condicion para q no pase de cero
			if (cuota <= saldo && verificar >= 0) {

				double diferenci = 0;
				if (cuota < saldo && diferenci >= 0) {
					diferenci = (saldo - cuota) / listaCons.size();
				}

				double capital = consumo.getAmmount() / consumo.getNumber_shares();

				// valida que si la cuota es menor a la cuota fija
				double pagoFinal = 0;
				pagoFinal += listaCons.get(i).getRemaining_ammount();

				if (cuota < capital || cuotaTt == saldo) {
					consumo.setRemaining_ammount(0);
					consumo.setCuotaRestante(0);
					tarjetaPagoConsumo.setAmmount(saldo);
					tarjetaPagoConsumo.setCapitalAmmount(saldo - (saldo * consumo.getInterest()));
					tarjetaPagoConsumo.setInterestAmmount(saldo * consumo.getInterest());

					tarjetaPagoConsumo.setPaymentDate(tarjeta.generarFechaActual());

					tarjetaPagoConsumo.setShare(consumo.getNumber_shares() - consumo.getCuotaRestante());
					tarjetaPagoConsumo.setIdConsume(consumo);
				} else {

					consumo.setRemaining_ammount(consumo.getRemaining_ammount() - (capital + diferenci));
					consumo.setCuotaRestante(consumo.getCuotaRestante() - 1);

					tarjetaPagoConsumo
							.setCapitalAmmount((consumo.getAmmount() / consumo.getNumber_shares()) + diferenci);
					tarjetaPagoConsumo.setAmmount(
							(capital + (consumo.getAmmount() / consumo.getNumber_shares()) * consumo.getInterest())
									+ diferenci);
					tarjetaPagoConsumo.setInterestAmmount(
							(consumo.getAmmount() / consumo.getNumber_shares()) * consumo.getInterest());

					tarjetaPagoConsumo.setPaymentDate(tarjeta.generarFechaActual());

					tarjetaPagoConsumo.setShare(consumo.getNumber_shares() - consumo.getCuotaRestante());
					tarjetaPagoConsumo.setIdConsume(consumo);
				}
				tar.setSaldoConsumido(tar.getSaldoConsumido() - (capital + diferenci));

				if (consumo.getCuotaRestante() == 0) {
					consumo.setIs_payed(true);
				} else {
					consumo.setIs_payed(false);
				}

				em.persist(tarjetaPagoConsumo);
				em.merge(consumo);
				em.merge(tar);

			} else {
				throw new ExcepcionNegocio(
						"El saldo es insuficiente para el pago \n el valor de cuota es de: " + cuota);
			}
		}
	}

	/**
	 * calcular cuota
	 * 
	 * @param monto
	 * @param numCuotas
	 * @return
	 */
	public double calcularCuota(Double monto, int numCuotas) {

		double interes = 0.036;
		double valorCuota = monto / numCuotas;
		double capital = valorCuota * interes;
		double valorTotalCuota = valorCuota + capital;

		return Math.round(valorTotalCuota);

	}

	/**
	 * Consultar el valor de la cuota a pagar
	 * 
	 * @param numTarjeta
	 * @return
	 */
	public double consultarCuota(String numTarjeta) {

		List<CreditcardConsume> listaCons = listaConsumo(numTarjeta);
		if (listaCons != null) {
			double valorTotalCuota = 0;
			for (int i = 0; i < listaCons.size(); i++) {
				if (listaCons.get(i).getRemaining_ammount() >= calcularCuota(listaCons.get(i).getAmmount(),
						listaCons.get(i).getNumber_shares())) {
					valorTotalCuota += calcularCuota(listaCons.get(i).getAmmount(),
							listaCons.get(i).getNumber_shares());
				} else if (listaCons.get(i).getRemaining_ammount() < calcularCuota(listaCons.get(i).getAmmount(),
						listaCons.get(i).getNumber_shares())) {
					valorTotalCuota += listaCons.get(i).getRemaining_ammount();
				}
			}

			return Math.round(valorTotalCuota);
		} else {
			throw new ExcepcionNegocio("La cuenta no existe");
		}

	}

	/**
	 * Lita de consumos
	 * 
	 * @param numTarjeta
	 * @return
	 */
	public List<CreditcardConsume> listaConsumo(String numTarjeta) {

		CreditcardPaymentConsume tarjetaPagoConsumo = new CreditcardPaymentConsume();
		Query q = em.createNamedQuery(CreditcardConsume.LIST_CONSUMO_TARJETA);
		q.setParameter(1, numTarjeta);
		List<CreditcardConsume> ListaCons = q.getResultList();
		return ListaCons;

	}

	/**
	 * Total consumo de una tarjeta de credito
	 * 
	 * @param numTarjeta
	 * @return
	 */
	public double totalConsumo(String numTarjeta) {
		List<CreditcardConsume> listaCons = listaConsumo(numTarjeta);
		if (listaCons != null) {
			double valorTotalCuota = 0;
			for (int i = 0; i < listaCons.size(); i++) {
				valorTotalCuota += Math.round(listaCons.get(i).getRemaining_ammount()
						+ (listaCons.get(i).getRemaining_ammount() * listaCons.get(i).getInterest()));
			}
			return Math.round(valorTotalCuota);
		} else {
			throw new ExcepcionNegocio("La cuenta no existe");
		}
	}

	public void descontarCuentaAhorros(String numCuenta, double saldoDesc, String Targeta) {

		Transaction transaccion = new Transaction();
		transaccion.setAmmount(saldoDesc);
		transaccion.setTipoTransaccion(TipoTransacion.Pago);
		transaccion.setSourceTransaction(numCuenta);

		pagoTarjeta(saldoDesc, Targeta);
		transaEjb.crearTransaccion(transaccion, numCuenta, "");

	}

	public List<Product> listaProd(String cedula, String tipoDoc) {
		Query q = em.createNamedQuery(Product.LISTA_PRODUCTOS);
		q.setParameter(1, tipoDoc);
		q.setParameter(2, cedula);
		List<Product> listaP = q.getResultList();
		return listaP;
	}

	/**
	 * Lista de tarjetas de credito de un cliente
	 * @param cedula
	 * @param tipoDoc
	 * @return
	 */
	public List<Credicart> listaTarjetaCredito(String cedula, String tipoDoc) {

		List<Credicart> listaCredit = new ArrayList<Credicart>();

		Query q = em.createNamedQuery(Product.LISTA_PRODUCTOS);
		q.setParameter(1, tipoDoc);
		q.setParameter(2, cedula);

		List<Product> listaP = q.getResultList();

		for (int i = 0; i < listaP.size(); i++) {
			if (listaP.get(i) instanceof Credicart) {
				Credicart cre = (Credicart) listaP.get(i);
				listaCredit.add(cre);
			}
		}
		return listaCredit;
	}
	
	/**
	 * Lista de cuentas de ahorros de un cliente
	 * @param cedula
	 * @param tipoDoc
	 * @return
	 */
	public List<SavingAccount> listaCuentaAhorros(String cedula, String tipoDoc) {

		List<SavingAccount> listaCuen = new ArrayList<SavingAccount>();

		Query q = em.createNamedQuery(Product.LISTA_PRODUCTOS);
		q.setParameter(1, tipoDoc);
		q.setParameter(2, cedula);

		List<Product> listaC = q.getResultList();

		for (int i = 0; i < listaC.size(); i++) {
			if (listaC.get(i) instanceof SavingAccount) {
				SavingAccount cre = (SavingAccount) listaC.get(i);
				listaCuen.add(cre);
				
			}
		}
		return listaCuen;
	}
	
	public void pagoConsumo(CreditcardConsume consumo){
		
		Credicart tar = tarjeta.buscarTarjetaCRedito(consumo.getCreditcard_number().getNumero());
		CreditcardPaymentConsume tarjetaPagoConsumo = new CreditcardPaymentConsume();

		tarjetaPagoConsumo.setAmmount((consumo.getRemaining_ammount()*0.036)+consumo.getRemaining_ammount());
		tarjetaPagoConsumo.setCapitalAmmount(consumo.getRemaining_ammount());
		tarjetaPagoConsumo.setIdConsume(consumo);
		tarjetaPagoConsumo.setInterestAmmount(consumo.getRemaining_ammount()*0.036);
		tarjetaPagoConsumo.setPaymentDate(tarjeta.generarFechaActual());
		tarjetaPagoConsumo.setShare(consumo.getCuotaRestante());
		
		tar.setSaldoConsumido(tar.getSaldoConsumido()-consumo.getRemaining_ammount());
		
		consumo.setRemaining_ammount(0);
		consumo.setCuotaRestante(0);
		consumo.setIs_payed(true);
		
		em.persist(tarjetaPagoConsumo);
		em.merge(tar);
		em.merge(consumo);
		//Falta restar de cuenta de ahorros y ya jajajaja .l.
		
	}
}
