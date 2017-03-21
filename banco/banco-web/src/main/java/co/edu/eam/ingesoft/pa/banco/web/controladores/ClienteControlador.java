package co.edu.eam.ingesoft.pa.banco.web.controladores;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Named(value = "clienteController")
@ViewScoped
public class ClienteControlador implements Serializable {

	@Pattern(regexp="[0-9]*",message="Solo numeros")
	@Length(min=1,max=10,message="Longitus de 1 a 10")
	private String cedula;

	private String tipoDocumento;

	@Pattern(regexp="[A-Za-z]*",message="Solo letras")
	@Length(min=3,max=20,message="Longitus de 3 a 20")
	private String nombre;

	@Pattern(regexp="[A-Za-z]*",message="Solo letras")
	@Length(min=3,max=20,message="Longitus de 3 a 20")
	private String apellido;

	private List<Customer> clientes;

	public List<Customer> getClientes() {
		return clientes;
	}

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@EJB
	private ClienteEJB clienteEJB;

	public void creCliente() {
		try {

			Customer Client = new Customer(nombre, apellido, tipoDocumento, cedula);
			clienteEJB.crearCLiente(Client);
			nombre = "";
			apellido = "";
			cedula = "";
			tipoDocumento = null;
			clientes = clienteEJB.listaClientes();

			Messages.addFlashGlobalInfo("Cliente registrado con exito");
		} catch (ExcepcionNegocio e) {
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	public void buscarCliente() {
		Customer cliente = clienteEJB.buscarCliente(cedula, tipoDocumento);
		if (cliente != null) {
			nombre = cliente.getName();
			apellido = cliente.getLastName();

		} else {
			Messages.addFlashGlobalError("El usuario no esta registrado");
		}
	}

	@PostConstruct
	public void inicializar() {
		clientes = clienteEJB.listaClientes();
	}
	
	

}
