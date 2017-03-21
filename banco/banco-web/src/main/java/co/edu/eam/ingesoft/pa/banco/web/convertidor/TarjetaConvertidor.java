package co.edu.eam.ingesoft.pa.banco.web.convertidor;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoEJB;

@FacesConverter(value="tarjConverter",forClass=Credicart.class)
@Named("tarjConverter")
public class TarjetaConvertidor implements Converter {

	@EJB
	private TarjetaCreditoEJB tarjeta;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.trim().length()==0 || value.equals("Seleccion...")){
			return null;
		}
		return tarjeta.buscarTarjetaCRedito(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof Credicart){
			Credicart credi = (Credicart) value;
			return credi.getNumero();
		}
		return null;
	}

	
}
