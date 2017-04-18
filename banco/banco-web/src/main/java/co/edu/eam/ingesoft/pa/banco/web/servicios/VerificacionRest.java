package co.edu.eam.ingesoft.pa.banco.web.servicios;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;

@Path("/verificacion")
public class VerificacionRest {
	
	
	@EJB
	private AsociacionEJB asociacionEJB;
	
	
	@Path("/buscarAsociacion")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public  boolean buscarAsociacion(@QueryParam("numero")String numero){
		AsociacionCuentas aso = asociacionEJB.buscarAsociacion(numero);
		
		if(aso!= null && aso.getVerificado().equals("VERIFICADA")){
			return true;
		}else{
			return false;
		}
	}
	
	
}
