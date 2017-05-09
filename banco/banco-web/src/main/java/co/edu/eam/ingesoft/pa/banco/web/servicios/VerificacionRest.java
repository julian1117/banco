package co.edu.eam.ingesoft.pa.banco.web.servicios;

import java.util.List;
import java.util.function.Consumer;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Transaction;
import co.edu.eam.ingesoft.banco.entidades.Usuario;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoTransacion;
import co.edu.eam.ingesoft.pa.banco.web.convertidor.CuentaAhorrosConvertidor;
import co.edu.eam.ingesoft.pa.banco.web.servicios.dto.AsociacionCuentaDTO;
import co.edu.eam.ingesoft.pa.banco.web.servicios.dto.LoginDTO;
import co.edu.eam.ingesoft.pa.banco.web.servicios.dto.TransaccionDTO;
import co.edu.eam.ingesoft.pa.banco.web.servicios.seguridad.Secured;
import co.edu.eam.ingesoft.pa.negocio.beans.AsociacionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.ClienteEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.CuentaAhorrosEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TarjetaCreditoPagoConsumoRemote;
import co.edu.eam.ingesoft.pa.negocio.beans.TransaaccionServiEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.TransaccionEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.UsuarioEJB;
import co.edu.eam.ingesoft.pa.negocio.beans.VerificarEJB;


@Path("/verificacion")
public class VerificacionRest {

	@EJB
	private AsociacionEJB asociacionEJB;

	@EJB
	private CuentaAhorrosEJB ahorrosEJB;

	@EJB
	private ClienteEJB clienteEJB;

	@EJB
	private UsuarioEJB usuarioEJB;

	@EJB
	private TarjetaCreditoPagoConsumoRemote tarjetaConsumer;

	@EJB
	private TransaccionEJB transaccionEJB;

	@EJB
	private VerificarEJB verificarEJB;

	@EJB
	private TransaaccionServiEJB transaccionSerEJB;
	
	@EJB
	private UsuarioEJB usEJB;

	
	
	/**
	 * Verificar que una cuenta de ahorros exita en la bd y que pertenesca a ese
	 * cliente
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

		// cuentaAH.getHolder().equals(cliente)

		if (cliente != null && cuentaAH != null && cuentaAH.getHolder().equals(cliente)) {
			return "EXITO";
		} else {
			return "ERROR";
		}
	}

	/**
	 * recibir dinero de otro banco
	 * 
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
	public RespuestaDTO listarCuentas(@QueryParam("numero") String numero, @QueryParam("tipo") String tipo) {

		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		Customer cliente = clienteEJB.buscarCliente(numero, tipoDocumento);
		
		List<AsociacionCuentas> listaAsoVeri = asociacionEJB.listarAsociaciones(cliente);
		
		
		
		if (cliente != null) {
			return new RespuestaDTO("Lista de cuentas Asociadas", 0, listaAsoVeri);
		} else {
			return new RespuestaDTO("No hay Cuentas Asociadas", 1, null);
		}
		
		
		
	}

	@Path("/listarBancos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO listarBancos() {
		List<Banco> lista = asociacionEJB.listarBancos();
		if (lista.isEmpty()) {
			return new RespuestaDTO("No hay bancos", 1, null);
		} else {
			return new RespuestaDTO("Se han encontrado bancos", 0, lista);
		}
	}

	@POST
	@Path("/asociarCuenta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO asociarCuenta(AsociacionCuentaDTO asoDTO) {
		AsociacionCuentas cuentaAso = new AsociacionCuentas();

		cuentaAso.setBanco(asoDTO.getBanco());
		cuentaAso.setCliente(asoDTO.getCliente());
		cuentaAso.setNombreAs(asoDTO.getNombreAs());
		cuentaAso.setNombreTitular(asoDTO.getNombreTitular());
		cuentaAso.setNumero(asoDTO.getNumero());
		cuentaAso.setNumeroId(asoDTO.getNumeroId());
		cuentaAso.setTipoId(asoDTO.getTipoId());
		cuentaAso.setVerificado("PENDIENTE");

		asociacionEJB.crearAsociacion(cuentaAso);
		return new RespuestaDTO("se creo exitosamente", 0, true);

	}

	@GET
	@Path("/listaCuentaAsociada")
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO listaCuentasAsociadas(@QueryParam("numero") String numero, @QueryParam("tipo") String tipo) {

		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		Customer cliente = clienteEJB.buscarCliente(numero, tipoDocumento);

		List<AsociacionCuentas> listaAsoVeri = asociacionEJB.listaAsociadaVeri(cliente);

		for (AsociacionCuentas asociacionCuentas : listaAsoVeri) {
			asociacionCuentas.getCliente().setProducto(null);
		}
		return new RespuestaDTO("Lista de cuentas Asociadas", 0, listaAsoVeri);

	}

	@GET
	@Path("/listaCuentaAhorros")
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO listaCuentaCliente(@QueryParam("numero") String numero, @QueryParam("tipo") String tipo) {

		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		List<SavingAccount> cuentaAH = tarjetaConsumer.listaCuentaAhorros(numero, tipoDocumento);

		for (SavingAccount savingAccount : cuentaAH) {
			savingAccount.getHolder().setProducto(null);
		}

		return new RespuestaDTO("Lista de cuentas ahorro", 0, cuentaAH);

	}

	@GET
	@Path("/enviarMensajeVeri")
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO enviarCod(@QueryParam("numero") String numero, @QueryParam("tipo") String tipo) {

		String tipoDocumento = "";

		if (tipo.equals("1")) {
			tipoDocumento = "Cedula";
		} else if (tipo.equals("2")) {
			tipoDocumento = "Pasaporte";
		}

		Customer cliente = clienteEJB.buscarCliente(numero, tipoDocumento);

		Usuario usC = usuarioEJB.usuacioC(cliente);

		ahorrosEJB.validarTransaaccion(usC);

		return new RespuestaDTO("Mensaje enviado", 0, true);
	}

	@POST
	@Path("/transferir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RespuestaDTO transferir(TransaccionDTO dto) {

		Customer cliente = clienteEJB.buscarCliente(dto.getNumeroCedula(),dto.getCedula());

		Usuario us = usuarioEJB.usuacioC(cliente);

		transaccionSerEJB.confirmarTransaccion(us.getUsuario(), dto.getCodigo(), dto.getAsociacionCuenta(),
				dto.getMonto(), dto.getBanco());

		return new RespuestaDTO("Mensaje enviado", 0, true);

	}

}
