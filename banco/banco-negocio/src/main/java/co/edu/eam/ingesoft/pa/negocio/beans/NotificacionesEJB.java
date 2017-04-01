																																																																																																																																																																																						package co.edu.eam.ingesoft.pa.negocio.beans;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;

import co.edu.eam.pa.clientews.Mail;
import co.edu.eam.pa.clientews.Notificaciones;
import co.edu.eam.pa.clientews.NotificacionesService;
import co.edu.eam.pa.clientews.RespuestaNotificacion;

@Stateless	
public class NotificacionesEJB{
	
	NotificacionesService cliente = new NotificacionesService();
	Notificaciones servicio = cliente.getNotificacionesPort();
	
	public void correoMensajes(String mensaje, String mail, String from,String asunto){
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Mail correo = new Mail();
		correo.setBody(mensaje);
		correo.setTo(mail);
		correo.setFrom(from);
		correo.setSubject(asunto);
		
		RespuestaNotificacion res = servicio.enviarMail(correo);
		System.out.println(res.getMensaje());
	}
	
	
	public void mensajeTexto(){
		
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	}
	
	
	
	

}
