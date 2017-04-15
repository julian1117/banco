package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Product;
import co.edu.eam.ingesoft.banco.entidades.SavingAccount;
import co.edu.eam.ingesoft.banco.entidades.Usuario;
import co.edu.eam.ingesoft.banco.entidades.Verificacion;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ICuentaAhorrosRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;
import co.edu.eam.pa.clientews.RespuestaServicio;
import co.edu.eam.pa.clientews.TransferirMonto;
import co.edu.eam.pa.clientews.TransferirMontoResponse;

@Stateless
@LocalBean
@Remote(ICuentaAhorrosRemote.class)
public class CuentaAhorrosEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ClienteEJB clienteEjb;

	@EJB
	private NotificacionesEJB notificaiconEjb;

	@EJB
	private SeguridadEJB usuarioEjb;

	@EJB
	private AsociacionEJB asociaEjb;

	
	
	/**
	 * Crea cuenta de ahorros
	 * 
	 * @param cuentaAhorro
	 *            objeto que recibe para crear la cuenta de ahorros
	 */
	public void crearCuentaAhorro(SavingAccount cuentaAhorro, String numeroId, String tipoId) {

		cuentaAhorro.setFechaExpedicion(generarFechaActual());
		cuentaAhorro.setSaving_interest(3);

		Customer cliente = clienteEjb.buscarCliente(numeroId, tipoId);
		if (cliente != null) {
			cuentaAhorro.setHolder(cliente);
			cuentaAhorro.getHolder().setNumeroIndentificacion(cliente.getNumeroIndentificacion());
			cuentaAhorro.getHolder().setTipoIdentificacion(cliente.getTipoIdentificacion());

			SavingAccount cuenta = null;

			if (listProducto(cuentaAhorro.getHolder()) == true) {
				do {
					cuentaAhorro.setNumero(calcularNumeroIdentificacion());
					cuenta = buscarCuentaAhorro(cuentaAhorro.getNumero());
				} while (cuenta != null);
				em.persist(cuentaAhorro);
			} else {
				throw new ExcepcionNegocio("El cliente ya tiene 5 productos");
			}
		} else {
			throw new ExcepcionNegocio("El cliente no existe");
		}

	}

	/**
	 * Busca una cuenta de ahorros
	 * 
	 * @param numero
	 *            de la cuenta de ahorros
	 * @return cuenta de ahorros, de lo contrario null
	 */
	public SavingAccount buscarCuentaAhorro(String numero) {
		return em.find(SavingAccount.class, numero);
	}

	/**
	 * Genera numero aleatorio de 10 digitos
	 * 
	 * @return numero aleatorio
	 */
	public long generarAleatorioCuenta() {

		long aleatorio = ThreadLocalRandom.current().nextLong((9000000000L) + 1000000000L);
		return aleatorio;
	}

	/**
	 * Obtener el ultimo numero de la cuenta de ahorros
	 * 
	 * @return digito de verificacion de la cuenta de ahorros
	 */
	public String calcularNumeroIdentificacion() {

		long num = ThreadLocalRandom.current().nextLong(9000000000L) + 1000000000L;
		String numero = String.valueOf(num);
		ArrayList<String> lista = new ArrayList();
		int ultDigito = 1;
		String numeroTotal = "";
		for (int i = 0; i < numero.length(); i++) {
			lista.add(numero.charAt(i) + "");
			ultDigito = ultDigito * Integer.parseInt(numero.charAt(i) + "");
		}

		lista.add("-" + String.valueOf(ultDigito).charAt(String.valueOf(ultDigito).length() - 1) + "");
		for (int i = 0; i < lista.size(); i++) {
			numeroTotal += lista.get(i);
		}
		return numeroTotal;

	}

	/**
	 * Obtiene la fecha actual
	 * 
	 * @return
	 */
	public Date generarFechaActual() {
		Calendar fechaHora = Calendar.getInstance();
		Date fecha = fechaHora.getTime();
		return fecha;
	}

	/**
	 * valida que el cliente no tenga mas de 5 productos
	 * 
	 * @param cliente
	 *            ingresado
	 * @return true si tiene menos de 5 productos, false si tiene 5 productos
	 */
	public boolean listProducto(Customer cliente) {
		Query q = em.createNamedQuery(Product.LISTA_PRODUCTOS);
		q.setParameter(1, cliente.getTipoIdentificacion());
		q.setParameter(2, cliente.getNumeroIndentificacion());
		List<Product> listaP = q.getResultList();

		if (listaP.size() < 5) {
			return true;
		} else {
			return false;
		}
	}

	public void validarTransaaccion(Usuario use) {

		Usuario usuarioBuscado = usuarioEjb.buscarUs(use.getUsuario());

		int aleatorio = ThreadLocalRandom.current().nextInt((int) ((900000L) + 100000L));
		// notificaiconEjb.mensajeValidar(use.getCustomer().getNumeroTelefono(),
		// "Codigo de validacion: " +aleatorio);
		notificaiconEjb.correoValidar("Codigo de validacion: " + aleatorio, use.getCustomer().getCorreoELectronico(),
				"Validacion de transferencia");

		Calendar c1 = Calendar.getInstance();

		Verificacion very = new Verificacion();
		very.setCodigo(aleatorio);
		very.setFecha(c1.getInstance());
		very.setUsuario(usuarioBuscado);

		em.persist(very);

	}

	

}
