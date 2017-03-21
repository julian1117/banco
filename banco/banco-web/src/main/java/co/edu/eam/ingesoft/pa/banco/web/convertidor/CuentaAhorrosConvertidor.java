package co.edu.eam.ingesoft.pa.banco.web.convertidor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;

@FacesConverter(value="cuentConverter",forClass=SavingAccount.class)
@Named("cuentConverter")
public class CuentaAhorrosConvertidor implements Converter{
	
	private CuentaAhorrosEJB cuentaAho;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.trim().length()==0 || value.equals("Seleccion...")){
			return null;
		}
		return cuentaAho.buscarCuentaAhorro(value);
		
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value instanceof Credicart){
			Credicart credi = (Credicart) value;
			return credi.getNumero();
		}
		return null;
	}

}
