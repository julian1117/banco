package co.edu.eam.ingesoft.pa.banco.web.convertidor;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;

@FacesConverter(value = "asociacionConvertidor", forClass = AsociacionCuentas.class)
@Named("asociacionConvertidor")
public class AsociacionConvertidor implements Converter {

	@EJB
	private AsociacionEJB asociacionEJB;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.trim().length()==0 || value.equals("Seleccion...")){
				return null;
			}
		return asociacionEJB.buscarAsociacion(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof AsociacionCuentas){
			AsociacionCuentas aso = (AsociacionCuentas) value;
			return aso.getNumeroId();
		}
		return null;
	}

}
