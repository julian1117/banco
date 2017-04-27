package co.edu.eam.ingesoft.pa.banco.web.servicios;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Provider
public class manejoExepciones {

	public Response toResponse(Throwable exc) {
		if(exc instanceof ExcepcionNegocio){
			RespuestaDTO resp=new RespuestaDTO(exc.getMessage(), -200, null);
			return Response.status(500).entity(resp).build();
		}else{
			exc.printStackTrace();
			RespuestaDTO resp=new RespuestaDTO("Error general:"+exc.getMessage(), -100, null);
			return Response.status(500).entity(resp).build();
		}
		
	}

	
}
