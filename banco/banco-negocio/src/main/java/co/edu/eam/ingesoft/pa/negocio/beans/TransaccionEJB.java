package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Transaction;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ICuentaAhorrosRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITransaccionRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

/**
 * Clase que permite administrar todas las transacciones de una cuenta de
 * ahorros
 * 
 * @author CAMILO
 *
 */
@Stateless
@LocalBean
@Remote(ITransaccionRemote.class)
public class TransaccionEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private CuentaAhorrosEJB cuentaEJB;
	
	@EJB
	private NotificacionesEJB notificacion;

	/**
	 * Permite crear las transaciones por el tipo de transaccion
	 * 
	 * @param transaccion
	 *            objeto que recibe
	 * @param numero
	 *            de cuenta de ahorros
	 * @param cuentaB
	 *            numero de cuenta de ahorros dos
	 */
	public void crearTransaccion(Transaction transaccion, String numero, String cuentaB) {

		SavingAccount cuentaAho = cuentaEJB.buscarCuentaAhorro(numero);
		transaccion.setTransationDate(cuentaEJB.generarFechaActual());
		transaccion.setSavingAcountNumber(cuentaAho);

		if (cuentaAho != null) {
			if (transaccion.getTipoTransaccion() == TipoTransacion.Transaccion) {

				transaccion(transaccion, cuentaB);
				
				 notificacion.mensaje(cuentaAho.getHolder().getNumeroTelefono(),
				 "Se realizo una transaccion por: $"
				 + transaccion.getAmmount() + ", De Cuenta ahorros # " +
				 cuentaAho.getNumero());

				notificacion.correoELectronico("Se realizo transaccion por: $" + transaccion.getAmmount() + ", Nuemro Cuenta ahorros "
								+numero,
						cuentaAho.getHolder().getCorreoELectronico(), "Transaccion de cuenta de ahorros");

			} else if (transaccion.getTipoTransaccion() == TipoTransacion.Consignar) {
				consignar(transaccion);
				em.persist(transaccion);
				
				 notificacion.mensaje(cuentaAho.getHolder().getNumeroTelefono(),
				 "Se realizo una Consignacion por: $"
				 + transaccion.getAmmount() + ", De Cuenta ahorros # " +
				 cuentaAho.getNumero());

				notificacion.correoELectronico("Se realizo Consignacion por: $" + transaccion.getAmmount() + ", Nuemro Cuenta ahorros "
								+numero,
						cuentaAho.getHolder().getCorreoELectronico(), "Consignacion cuenta de ahorros");
				
			} else if (transaccion.getTipoTransaccion() == TipoTransacion.Retirar) {
				retiro(transaccion);
				
				em.persist(transaccion);
				
				 notificacion.mensaje(cuentaAho.getHolder().getNumeroTelefono(),
				 "Se realizo un retiro por: $"
				 + transaccion.getAmmount() + ", De cuenta ahorros # " +
				 cuentaAho.getNumero());

				notificacion.correoELectronico("Se realizo un retiro por: $" + transaccion.getAmmount() + ", Nuemro Cuenta ahorros "
								+numero,
						cuentaAho.getHolder().getCorreoELectronico(), "Retiro cuenta de ahorros");
				
			}else if(transaccion.getTipoTransaccion() == TipoTransacion.Pago){
				retiroPago(transaccion);
				em.persist(transaccion);
			}
		} else {
			throw new ExcepcionNegocio("La cuenta no existe");
		}
	}

	/**
	 * permite realizar las transaciones entre cuentas del mismo usuario
	 * 
	 * @param transacion
	 *            objeto transacion que recibe
	 * @param cuentaB
	 *            numero de la cuenta de ahorros a la que va a realizar la
	 *            transaccion
	 */
	public void transaccion(Transaction transacion, String cuentaB) {

		SavingAccount cuentadestino = em.find(SavingAccount.class, cuentaB);

		if (transacion.getSavingAcountNumber().getHolder().equals(cuentadestino.getHolder())) {
			if (transacion.getSavingAcountNumber().getAmmount() > transacion.getAmmount()) {
				if (transacion.getSavingAcountNumber().getNumero() != (cuentadestino.getNumero())) {
					double entra = transacion.getAmmount();
					double destino = cuentadestino.getAmmount();
					double actual = transacion.getSavingAcountNumber().getAmmount();

					double descuento = actual - entra;
					double aumenta = destino + entra;

					transacion.getSavingAcountNumber().setAmmount(descuento);
					cuentadestino.setAmmount(aumenta);
					em.merge(transacion);
					em.merge(cuentadestino);
				} else {
					throw new ExcepcionNegocio("No puede transferir saldos entre la misma cuenta");
				}
			} else {
				throw new ExcepcionNegocio("la cuenta: " + transacion.getSavingAcountNumber().getNumero()
						+ " No tiene saldo suficiente para esta transacion");
			}
		} else {
			throw new ExcepcionNegocio("Solo puede realizar transacciones entre cuentas del mismo cliente");
		}

	}

	/**
	 * Permite realizar retiros de la cuenta de ahorros
	 * 
	 * @param retiro
	 *            objeto transaccion que recibe
	 */
	public void retiro(Transaction retiro) {
		Date dia = retiro.getTransationDate();
		double montoAct = retiro.getAmmount();
		if (dia.getDay() == retiro.getSavingAcountNumber().getFechaExpedicion().getDay()) {
			if (montoAct <= retiro.getSavingAcountNumber().getAmmount()) {
				int validarTrans = listaTransacion(retiro.getTipoTransaccion(),
						retiro.getSavingAcountNumber().getNumero());
				if (validarTrans < 1) {
					double restante = retiro.getSavingAcountNumber().getAmmount() - montoAct;
					retiro.getSavingAcountNumber().setAmmount(restante);
					em.merge(retiro.getSavingAcountNumber());
				} else {
					throw new ExcepcionNegocio("Solo puede retirar una vez el mismo dia");
				}
			} else {
				throw new ExcepcionNegocio("Monto no valido");
			}
		} else {
			throw new ExcepcionNegocio("Dia no valido para retirar");
		}

	}

	public void retiroPago(Transaction retiro) {
		double montoAct = retiro.getAmmount();

		if (montoAct <= retiro.getSavingAcountNumber().getAmmount()) {
			double restante = retiro.getSavingAcountNumber().getAmmount() - montoAct;
			retiro.getSavingAcountNumber().setAmmount(restante);
			em.merge(retiro.getSavingAcountNumber());
		} else {
			throw new ExcepcionNegocio("Monto no valido");
		}

	}

	/**
	 * Permite consignar a una cuenta de ahorros determinada
	 * 
	 * @param consignar
	 *            objeto transaccion que recibe
	 */
	public void consignar(Transaction consignar) {
		if (consignar.getAmmount() >= 0) {
			double anterior = consignar.getSavingAcountNumber().getAmmount();
			double actual = consignar.getAmmount();

			double sumarCuenta = anterior + actual;
			consignar.getSavingAcountNumber().setAmmount(sumarCuenta);

			em.merge(consignar.getSavingAcountNumber());
		} else {
			throw new ExcepcionNegocio("El monto inresado no es valido");
		}
	}

	/**
	 * lista de transaciones por tipo de transaccion y numero de cuenta
	 * 
	 * @param tipoTrans
	 *            tipo transaccion
	 * @param cuenta
	 *            numero de cuenta de ahorros
	 * @return
	 */
	public int listaTransacion(TipoTransacion tipoTrans, String cuenta) {
		Query q = em.createNamedQuery(Transaction.LISTA_TRANSACCION);
		q.setParameter(1, tipoTrans);
		q.setParameter(2, cuenta);

		int ban = 0;

		List<Transaction> listaTrans = q.getResultList();
		for (int i = 0; i < listaTrans.size(); i++) {
			if (listaTrans.get(i).getTransationDate().getDay() == cuentaEJB.generarFechaActual().getDay()) {
				ban++;
			}
		}
		return ban;
	}

}
