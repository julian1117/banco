package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Usuario;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.UsuarioEJB;

@Named("usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {

	@Pattern(regexp = "[0-9]*", message = "Solo numeros")
	@Length(min = 1, max = 10, message = "Longitus de 1 a 10")
	private String cedula;

	private String tipoDocumento;

	@Length(min = 3, max = 20, message = "Longitus de 3 a 20")
	private String nombreUs;

	@Length(min = 1, max = 20, message = "Longitus de 3 a 20")
	private String contrasena;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombreUs() {
		return nombreUs;
	}

	public void setNombreUs(String nombreUs) {
		this.nombreUs = nombreUs;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@EJB
	private UsuarioEJB useEjb;

	@EJB
	private ClienteEJB clienteEJB;

	public void crearUsuario() {
		try {

			Customer cliente = clienteEJB.buscarCliente(cedula, tipoDocumento);
			if (cliente != null) {
				Usuario usuario = new Usuario(nombreUs, contrasena, cliente);
				useEjb.crearUser(usuario);

				Messages.addFlashGlobalInfo("Usuario creado con éxito");
			} else {
				Messages.addFlashGlobalError("El cliente no existe");

			}

		} catch (Exception e) {
			Messages.addFlashGlobalError(e.getMessage());
		}

	}

}
