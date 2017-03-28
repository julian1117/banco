package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardConsume;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoConsumo;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
@Remote(ITarjetaCreditoConsumoRemote.class)
public class TarjetaCreditoConsumoEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private TarjetaCreditoEJB tarjetaEJB;

	@EJB
	private CuentaAhorrosEJB cuentaAhorros;

	@EJB
	private TarjetaCreditoPagoConsumoRemote tarjetaPago;

	/**
	 * Registrar compras con la tarjeta de credito
	 * 
	 * @param consumoTarjeta
	 *            que recibe
	 * @param numTarjeta
	 *            numero de la tarjeta de credito
	 */
	public void registrarConsumo(CreditcardConsume consumoTarjeta, String numTarjeta, TipoConsumo tipoConsumo) {

		Credicart tarjeta = tarjetaEJB.buscarTarjetaCRedito(numTarjeta);

		if (tarjeta != null) {
			if (consumoTarjeta.getNumber_shares() < 25) {
				if (tarjeta.getMonto() >= consumoTarjeta.getAmmount()) {

					if (tipoConsumo == TipoConsumo.AVANCE) {
						consumoTarjeta.setNumber_shares(24);
					}
					double suma = tarjeta.getSaldoConsumido() + consumoTarjeta.getAmmount();
					consumoTarjeta.setCreditcard_number(tarjeta);
					consumoTarjeta.setDate_consume(tarjetaEJB.generarFechaActual());
					consumoTarjeta.setInterest(0.036);
					consumoTarjeta.setRemaining_ammount(consumoTarjeta.getAmmount());
					consumoTarjeta.setCuotaRestante(consumoTarjeta.getNumber_shares());

					tarjeta.setSaldoConsumido(suma);
					em.merge(tarjeta);
					em.persist(consumoTarjeta);

				} else {
					throw new ExcepcionNegocio("La compra es mayor al saldo disponible");
				}
			} else {
				throw new ExcepcionNegocio("Numero de cuotas no valido 'Maximo 24'");
			}
		} else {
			throw new ExcepcionNegocio("Tarjeta de credito no registrada");
		}

	}

	/**
	 * Consulta que me retorna la suma de los saldos de una tarjeta
	 * 
	 * @param num
	 *            de la tarjeta
	 * @return saldo consumido de la tarjeta
	 */
	public double saldoConsumido(String num) {
		Query q = em.createNamedQuery(CreditcardConsume.LIST_CONSUMO_TARJETA);
		q.setParameter(1, num);

		double total = (Double) q.getSingleResult();

		return total;
	}

	public CreditcardConsume BuscarConsumo(int consumo) {
		return em.find(CreditcardConsume.class, consumo);
	}

	/**
	 * Realizar avance de una tarjeta de credito a una cuenta de ahorros
	 * 
	 * @param numero
	 * @param cantidad
	 */
	public void avanceCuentaAhorro(String numeroT, double cantidad, String numeroCuentaAh) {
		Credicart tarjeta = tarjetaEJB.buscarTarjetaCRedito(numeroT);
		SavingAccount cuentaAh = cuentaAhorros.buscarCuentaAhorro(numeroCuentaAh);

		double montoMaximo = tarjeta.getMonto() * 0.3;
		double disponible = tarjeta.getMonto() - tarjeta.getSaldoConsumido();

		CreditcardConsume consumoTarjeta = new CreditcardConsume();

		if (cantidad <= montoMaximo && cantidad <= disponible) {

			consumoTarjeta.setNumber_shares(24);
			double suma = tarjeta.getSaldoConsumido() + cantidad;
			double sumarCuentaAh = cuentaAh.getAmmount() + cantidad;
			consumoTarjeta.setCreditcard_number(tarjeta);
			consumoTarjeta.setDate_consume(tarjetaEJB.generarFechaActual());
			consumoTarjeta.setInterest(0.036);
			consumoTarjeta.setRemaining_ammount(cantidad);
			consumoTarjeta.setAmmount(cantidad);
			consumoTarjeta.setCuotaRestante(consumoTarjeta.getNumber_shares());

			tarjeta.setSaldoConsumido(suma);

			cuentaAh.setAmmount(sumarCuentaAh);

			 em.merge(tarjeta);
			 em.persist(consumoTarjeta);
			 em.merge(cuentaAh);

		} else {
			throw new ExcepcionNegocio("La cantidad es mayor a lo permitido: su maximo es de: " + montoMaximo);
		}
	}

}
