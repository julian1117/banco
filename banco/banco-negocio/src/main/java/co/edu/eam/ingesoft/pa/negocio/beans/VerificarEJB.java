package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Usuario;
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
	
	@EJB
	private UsuarioEJB usuarioEjb;
	
	@EJB
	private NotificacionesEJB notificaiconEjb;


	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS servicios = cliente.getInterbancarioWSPort();

	String endpointURL = "http://104.198.67.149:8080/interbancario/InterbancarioWS/InterbancarioWS";

	public void verificar(AsociacionCuentas asociacion) {
		try {

			BindingProvider bp = (BindingProvider) servicios;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

			TipoDocumentoEnum tipoIdd = TipoDocumentoEnum.CC;

			Banco bancoBus = asociacionEJB.buscarBanco(asociacion.getBanco().getIdBanco());

			if (bancoBus.getIdBanco().equals("01")) {
				Customer cliente = clienteEJB.buscarCliente(asociacion.getCliente().getNumeroIndentificacion(),
						asociacion.getCliente().getTipoIdentificacion());
				if (cliente != null) {
					SavingAccount cuentaAhorro = cuentaAHEJB.buscarCuentaAhorro(asociacion.getNumero());

					if (cuentaAhorro != null) {
						if (cuentaAhorro.getHolder().equals(cliente)) {
							asociacion.setVerificado("Asociada");
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

				if (asociacion.getVerificado().equals("Asociada")) {
					respuestaServicio.setMensaje("EXITO");

					asociacion.setVerificado(respuestaServicio.getMensaje());
					em.merge(asociacion);
					
					Customer cliente = clienteEJB.buscarCliente(asociacion.getCliente().getNumeroIndentificacion()
							, asociacion.getCliente().getTipoIdentificacion());
					Usuario usu = usuarioEjb.usuacioC(cliente);
					
					
					notificaiconEjb.mensajeValidar(usu.getCustomer().getNumeroTelefono(),
							"Su cuenta de asociacion a sido verificada con exito!!! ");
					notificaiconEjb.correoValidar("Su cuenta de asociacion a sido verificada con exito!!! " ,
							usu.getCustomer().getCorreoELectronico(), "Validacion de transferencia");
				} else {
					respuestaServicio.setMensaje("ERROR");
					asociacion.setVerificado(respuestaServicio.getMensaje());
					em.merge(asociacion);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
