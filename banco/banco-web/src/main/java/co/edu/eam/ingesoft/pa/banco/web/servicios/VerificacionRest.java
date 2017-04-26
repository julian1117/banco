package co.edu.eam.ingesoft.pa.banco.web.servicios;

import java.util.List;

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
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Transaction;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;
import co.edu.eam.ingesoft.pa.banco.web.convertidor.CuentaAhorrosConvertidor;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TransaccionEJB;

@Path("/verificacion")
public class VerificacionRest {

	@EJB
	private AsociacionEJB asociacionEJB;
	
	@EJB
	private CuentaAhorrosEJB ahorrosEJB;
	
	@EJB
	private ClienteEJB clienteEJB;

	@EJB
	private TransaccionEJB transaccionEJB;
	/**
	 * Verificar que una cuenta de ahorros exita en la bd y que pertenesca a ese cliente
	 * 
	 * @param numero
	 * @param cedula
	 * @param tipo
	 * @return
	 */
	@Path("/buscarAsociacion")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String buscarAsociacion(@FormParam("numero") String numero, @FormParam("cedula") String cedula,
			@FormParam("tipo") String tipo) {

		
		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		SavingAccount cuentaAH = ahorrosEJB.buscarCuentaAhorro(numero);
		
		Customer cliente = clienteEJB.buscarCliente(cedula, tipoDocumento);
		
		//cuentaAH.getHolder().equals(cliente)
		
		if (cliente != null && cuentaAH != null && cuentaAH.getHolder().equals(cliente)) {
			return "EXITO";
		} else {
			return "ERROR";
		}
	}

	
	
	/**
	 * recibir dinero de otro banco
	 * @param numeroCuenta
	 * @param monto
	 * @return
	 */
	@Path("/otroBanco")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String dineroOtroBanco(@FormParam("numeroCuenta") String numeroCuenta, @FormParam("monto") double monto) {
		SavingAccount cuenta = ahorrosEJB.buscarCuentaAhorro(numeroCuenta);

		Transaction trans = new Transaction();
		trans.setAmmount(monto);
		trans.setSavingAcountNumber(cuenta);
		trans.setTipoTransaccion(TipoTransacion.Consignar);
			
		if (cuenta != null) {
			cuenta.setAmmount(cuenta.getAmmount() + monto);
			transaccionEJB.consignar(trans);
			return "Ok: se puede";
		} else {
			return "ERROR: no se puede";
		}

	}
	
	@Path("/listarCuentas")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AsociacionCuentas> listarCuentas(@QueryParam("numero")String numero, @QueryParam("tipo") String tipo){
		
		Customer cliente = clienteEJB.buscarCliente(numero, tipo);
		if(cliente!=null){
		return asociacionEJB.listarAsociaciones(cliente);
		}else{
			return null;
		}
	}
	
	@Path("/listarBancos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Banco> listarBancos(){
		return asociacionEJB.listarBancos();
	}
	
	
	
	

}
