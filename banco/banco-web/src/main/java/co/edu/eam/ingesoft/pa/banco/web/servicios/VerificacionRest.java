package co.edu.eam.ingesoft.pa.banco.web.servicios;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.pa.banco.web.convertidor.CuentaAhorrosConvertidor;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;

@Path("/verificacion")
public class VerificacionRest {

	@EJB
	private AsociacionEJB asociacionEJB;
	@EJB
	private CuentaAhorrosEJB ahorrosEJB;

	@Path("/buscarAsociacion")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public boolean buscarAsociacion(@FormParam("numero") String numero, @FormParam("cedula") String cedula,
			@FormParam("tipo") String tipo) {

		AsociacionCuentas aso = asociacionEJB.buscarAsociacion(numero);
		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		if (aso != null && aso.getVerificado().equals("VERIFICADA")
				&& aso.getCliente().getNumeroIndentificacion().equals(cedula)
				&& aso.getCliente().getTipoIdentificacion().equals(tipoDocumento)) {
			return true;
		} else {
			return false;
		}
	}

	@Path("/otroBanco")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String dineroOtroBanco(@FormParam("numeroCuenta") String numeroCuenta, @FormParam("monto") double monto) {
		SavingAccount cuenta = ahorrosEJB.buscarCuentaAhorro(numeroCuenta);

		if (cuenta != null) {
			cuenta.setAmmount(cuenta.getAmmount() + monto);
			ahorrosEJB.editarCuenta(cuenta);
			return "Ok: se puede";
		} else {
			return "ERROR: no se puede";
		}

	}

}
