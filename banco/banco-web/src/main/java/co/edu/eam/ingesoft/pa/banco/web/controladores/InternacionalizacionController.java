package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.Locale;


import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "internacionalizacionCont")
public class InternacionalizacionController implements Serializable {
	
	
	
	public void cambiarIngles(){
		FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
	}
	
	public void cambiarEspanol(){
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new  Locale("es", "co"));
	}

}
