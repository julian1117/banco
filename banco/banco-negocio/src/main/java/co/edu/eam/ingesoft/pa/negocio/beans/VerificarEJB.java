package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Verificacion;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociada;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociadaResponse;
import co.edu.eam.pa.clientews.RespuestaServicio;
import co.edu.eam.pa.clientews.TipoDocumentoEnum;

@Stateless
public class VerificarEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private AsociacionEJB asociacionEJB;

	@EJB
	private ClienteEJB clienteEJB;
	
	@EJB
	private CuentaAhorrosEJB cuentaAHEJB;
	
	

	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS servicios = cliente.getInterbancarioWSPort();

	String endpointURL = "http://104.155.128.249:8080/interbancario/InterbancarioWS/InterbancarioWS";

	
	public void verificar(AsociacionCuentas asociacion) {
		try {

			BindingProvider bp = (BindingProvider) servicios;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

			TipoDocumentoEnum tipoIdd = TipoDocumentoEnum.CC;
			
			Banco bancoBus = asociacionEJB.buscarBanco(asociacion.getBanco().getIdBanco());

			if (bancoBus.getIdBanco().equals("01")) {				
				Customer cliente = clienteEJB.buscarCliente(asociacion.getCliente().getNumeroIndentificacion(), asociacion.getCliente().getTipoIdentificacion());
				if(cliente != null){
					SavingAccount cuentaAhorro = cuentaAHEJB.buscarCuentaAhorro(asociacion.getNumero());					
					
					if(cuentaAhorro!= null){
						if(cuentaAhorro.getHolder().equals(cliente)){
							asociacion.setVerificado("ASOCIADA");
							em.persist(asociacion);
						}
						
					}
				}
			
			} else {
				
					
				RespuestaServicio respuestaServicio = servicios.registrarCuentaAsociada(
						asociacion.getBanco().getIdBanco(), tipoIdd, asociacion.getNumeroId(),
						asociacion.getNombreTitular(), asociacion.getNumero());
				
									respuestaServicio.getMensaje();
									
					asociacion.setVerificado(respuestaServicio.getMensaje());
					em.merge(asociacion);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	
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

		BindingProvider bp = (BindingProvider) servicios;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		List<Verificacion> verif = em.createNamedQuery(Verificacion.LISTA_VERIFICAICON).setParameter(1, use)
				.getResultList();

		String fechaInicio = verif.get(verif.size() - 1).getFecha().getTime().getHours() + ""
				+ verif.get(verif.size() - 1).getFecha().getTime().getMinutes() + ""
				+ verif.get(verif.size() - 1).getFecha().getTime().getSeconds() + "";

		Calendar c1 = Calendar.getInstance();
		String fechaActual = c1.getTime().getHours() + "" + c1.getTime().getMinutes() + "" + c1.getTime().getSeconds();

		int tiempoValidado = Integer.parseInt(fechaActual) - Integer.parseInt(fechaInicio);

		if (tiempoValidado <= 30) {
			if (verif.get(verif.size() - 1).getCodigo() == codigo) {
				
				//Servicio
				RespuestaServicio transf = servicios.transferirMonto(idbanco, numero, monto);

				if (transf.equals(0000)) {
					SavingAccount cuentaBuscada = cuentaAHEJB.buscarCuentaAhorro(numero);
					cuentaBuscada.setAmmount(cuentaBuscada.getAmmount() - monto);
					em.merge(cuentaBuscada);
				} else if (transf.equals(0002)) {
					throw new ExcepcionNegocio("Cuenta no asociada aun");

				} else if (transf.equals(0004)) {
					throw new ExcepcionNegocio("Cuenta no existe en el banco de destino");

				}


			} else {
				throw new ExcepcionNegocio("El codigo no coincide");
			}

		} else {
			throw new ExcepcionNegocio("El codigo a caducado");
		}

	}
	

}
