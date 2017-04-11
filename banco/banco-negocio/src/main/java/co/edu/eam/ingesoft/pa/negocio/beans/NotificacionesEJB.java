	
package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;

import co.edu.eam.pa.clientews.Mail;
import co.edu.eam.pa.clientews.Notificaciones;
import co.edu.eam.pa.clientews.NotificacionesService;
import co.edu.eam.pa.clientews.RespuestaNotificacion;
import co.edu.eam.pa.clientews.Sms;

@Stateless
public class NotificacionesEJB {

	NotificacionesService cliente = new NotificacionesService();
	Notificaciones servicio=cliente.getNotificacionesPort();
	
	/**
	 * Evio de mensajes por correo electronico
	 * @param remitente
	 * @param msj
	 * @param para
	 * @param asunto
	 */
	public void correoELectronico(String msj,String para,String asunto){
		
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Mail correo = new Mail();
		correo.setBody(msj);
		correo.setFrom("noreply@gmail.com");
		correo.setTo(para);
		correo.setSubject(asunto);
		
		RespuestaNotificacion resp = servicio.enviarMail(correo);
		System.out.println(resp.getMensaje());
	
	}
	
	/**
	 * Envio de mensajes de texto
	 * @param para
	 * @param text
	 */
	public void mensaje(String para,String text){
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Sms mesj = new Sms();
		mesj.setTo(para);
		mesj.setTexto(text);
		
		RespuestaNotificacion resp = servicio.enviarSMS(mesj);
		System.out.println(resp.getMensaje());
	}
	
	/**
	 * Validar transaccion
	 * @param para
	 * @param text
	 */
	public void mensajeValidar(String para,String text){
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Sms mesj = new Sms();
		mesj.setTo(para);
		mesj.setTexto(text);
		
		RespuestaNotificacion resp = servicio.enviarSMS(mesj);
		System.out.println(resp.getMensaje());
	}
	
	
	/**
	 * Validar transaccion
	 * @param para
	 * @param text
	 */
	public void correoValidar(String msj,String para,String asunto){
		String endpointURL = "http://104.197.238.134:8080/notificaciones/notificacionesService";
		BindingProvider bp = (BindingProvider)servicio;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		Mail correo = new Mail();
		correo.setBody(msj);
		correo.setFrom("noreply@gmail.com");
		correo.setTo(para);
		correo.setSubject(asunto);
		
		RespuestaNotificacion resp = servicio.enviarMail(correo);
		System.out.println(resp.getMensaje());
	}
	
	
	

}
