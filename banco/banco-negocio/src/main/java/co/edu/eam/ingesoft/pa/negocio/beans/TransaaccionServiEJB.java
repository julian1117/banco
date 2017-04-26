package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Verificacion;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;
import co.edu.eam.pa.clientews.RespuestaServicio;

@Stateless
@LocalBean
public class TransaaccionServiEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private CuentaAhorrosEJB cuentaAHEJB;

	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS servicios = cliente.getInterbancarioWSPort();

	String endpointURL = "http://104.155.128.249:8080/interbancario/InterbancarioWS/InterbancarioWS";

	/**
	 * realiza transaccion interbancaria y valida con codigo de seguridad
	 * 
	 * @param use
	 *            usuario que realiza la transaccion
	 * @param codigo
	 *            de seguridad
	 * @param numero
	 *            cuenta ahorros
	 * @param monto
	 *            que retira
	 * @throws Exception
	 *             si hay error por algo
	 */
	public void confirmarTransaccion(String use, int codigo, String numero, double monto, String idbanco) {
		try {
			BindingProvider bp = (BindingProvider) servicios;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

			List<Verificacion> verif = em.createNamedQuery(Verificacion.LISTA_VERIFICAICON).setParameter(1, use)
					.getResultList();

			String fechaInicio = verif.get(verif.size() - 1).getFecha().getTime().getHours() + ""
					+ verif.get(verif.size() - 1).getFecha().getTime().getMinutes() + ""
					+ verif.get(verif.size() - 1).getFecha().getTime().getSeconds() + "";

			Calendar c1 = Calendar.getInstance();
			String fechaActual = c1.getTime().getHours() + "" + c1.getTime().getMinutes() + ""
					+ c1.getTime().getSeconds();

			int tiempoValidado = Integer.parseInt(fechaActual) - Integer.parseInt(fechaInicio);

			if (tiempoValidado <= 60) {
				if (verif.get(verif.size() - 1).getCodigo() == codigo) {
					
					// Servicio
					RespuestaServicio transf = servicios.transferirMonto(idbanco, numero, monto);
					
					if (transf.getMensaje().equals(0000)) {
						SavingAccount cuentaBuscada = cuentaAHEJB.buscarCuentaAhorro(numero);
						cuentaBuscada.setAmmount(cuentaBuscada.getAmmount() - monto);
						em.merge(cuentaBuscada);
					} else if (transf.getMensaje().equals(0002)) {
						throw new ExcepcionNegocio("Cuenta no asociada aun");
					} else if (transf.getMensaje().equals(0004)) {
						throw new ExcepcionNegocio("Cuenta no existe en el banco de destino");
					}

				} else {
					throw new ExcepcionNegocio("El codigo no coincide");
				}

			} else {
				throw new ExcepcionNegocio("El codigo a caducado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
