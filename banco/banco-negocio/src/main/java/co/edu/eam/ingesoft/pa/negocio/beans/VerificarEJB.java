package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociada;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociadaResponse;
import co.edu.eam.pa.clientews.RespuestaServicio;
import co.edu.eam.pa.clientews.TipoDocumentoEnum;

@Stateless
@LocalBean
public class VerificarEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private AsociacionEJB asociacionEJB;
	
	@EJB
	private ClienteEJB clienteEJB;
	
	
	
	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS  servicios = cliente.getInterbancarioWSPort();
	
	String endpointURL = "http://104.197.238.134:8080/interbancario/InterbancarioWS";
	
	public void verificar(AsociacionCuentas asociacion){
		BindingProvider bp = (BindingProvider)servicios;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		TipoDocumentoEnum tipoIdd = TipoDocumentoEnum.CC;
		
			
//		if(asociacion.getBanco().getIdBanco().equals(000)){
//			RespuestaServicio respuestaServicio= servicios.registrarCuentaAsociada(asociacion.getBanco().getIdBanco(),
//					tipoIdd,asociacion.getNumeroId(), asociacion.getNombreTitular(),asociacion.getNumero());
//			
//			respuestaServicio.setCodigo("0000");
//			respuestaServicio.getMensaje();
//			
//			em.persist(respuestaServicio);
//			
//		}else{
			RespuestaServicio respuestaServicio= servicios.registrarCuentaAsociada(asociacion.getBanco().getIdBanco(),
					tipoIdd,asociacion.getNumeroId(), asociacion.getNombreTitular(),asociacion.getNumero());
			if(respuestaServicio.getCodigo().equals("0001")){
				
				
				
				respuestaServicio.setCodigo("0000");
				respuestaServicio.getMensaje();
				
				em.persist(respuestaServicio);
//			}
		
			
		}
		
		
		
	}
	
	

}
