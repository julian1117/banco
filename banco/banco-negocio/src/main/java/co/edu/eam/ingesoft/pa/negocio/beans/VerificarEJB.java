package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.EJB;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;

public class VerificarEJB {
	
	@EJB
	private AsociacionEJB asociacionEJB;
	
	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS  servicios = cliente.getInterbancarioWSPort();
	
	String endpointURL = "http://104.197.238.134:8080/interbancario/InterbancarioWS";
	
	public void verificar(String banco, String tipoId, String numeriId,String nombre,String numerocuenta){
		BindingProvider bp = (BindingProvider)servicios;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Banco buscarBanco = asociacionEJB.buscarBanco(banco);
		
		
		
		
		
	}
	
	

}
