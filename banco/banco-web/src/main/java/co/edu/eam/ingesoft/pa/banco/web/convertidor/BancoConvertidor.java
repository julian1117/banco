package co.edu.eam.ingesoft.pa.banco.web.convertidor;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;

@FacesConverter(value="bancoConver",forClass=Banco.class)
@Named("bancoConver")
public class BancoConvertidor implements Converter{
	
	@EJB
	private AsociacionEJB asoEJB;
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if(value == null || value.trim().length()==0 || value.equals("Seleccion...")){
			return null;
		}
		return asoEJB.buscarBanco(value);
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		// TODO Auto-generated method stub
		if(value instanceof Banco){
			Banco ban = (Banco) value;
			return ban.getIdBanco();
		}
		return null;
	}

}
