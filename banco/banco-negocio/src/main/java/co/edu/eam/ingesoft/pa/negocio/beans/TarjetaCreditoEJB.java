package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Franchise;
import co.edu.eam.ingesoft.banco.entidades.Product;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.ITarjetaCreditoRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
@Remote(ITarjetaCreditoRemote.class)
public class TarjetaCreditoEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ClienteEJB clienteEjb;

	/**
	 * Crea una tarjeta de credito
	 * 
	 * @param tarjetaCredito
	 */
	public void crearTarjetaCredito(Credicart tarjetaCredito, String numeroId, String tipoId) {

		tarjetaCredito.setCvc(generarAleatorioCvc() + "");
		tarjetaCredito.setFechaExpedicion(generarFechaActual());
		tarjetaCredito.setFechaExpiracion(generarFechaSuma(generarFechaActual(), 1461));

		Customer cliente = clienteEjb.buscarCliente(numeroId, tipoId);
		if (cliente != null) {
			tarjetaCredito.setHolder(cliente);
			tarjetaCredito.getHolder().setNumeroIndentificacion(cliente.getNumeroIndentificacion());
			tarjetaCredito.getHolder().setTipoIdentificacion(cliente.getTipoIdentificacion());
			tarjetaCredito.setSaldoConsumido(0);
			Credicart tarjeta = null;
			if (listProducto(tarjetaCredito.getHolder()) == true) {
				if (listProductoTarjeta(tarjetaCredito.getHolder()).size() < 2) {
					do {
						Long numero = generarAleatorioTarjeta();
						tarjetaCredito.setNumero(numero + "");

						tarjeta = buscarTarjetaCRedito(numero + "");

					} while (tarjeta != null);

					em.persist(tarjetaCredito);
				} else {
					throw new ExcepcionNegocio("El cliente solo  puede tener 2 tarjetas como maximo");
				}
			} else {
				throw new ExcepcionNegocio("El cliente ya tiene 5 productos");
			}
		} else {
			throw new ExcepcionNegocio("El cliente no existe");
		}

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

	/**
	 * Verifica que un cliente no tenga mas de dos tarjetas
	 * 
	 * @param cliente
	 *            ingresado
	 * @return lista de productos
	 */
	public ArrayList<Credicart> listProductoTarjeta(Customer cliente) {

		ArrayList<Credicart> listaCredit = new ArrayList<Credicart>();

		Query q = em.createNamedQuery(Product.LISTA_PRODUCTOS);
		q.setParameter(1, cliente.getTipoIdentificacion());
		q.setParameter(2, cliente.getNumeroIndentificacion());

		List<Product> listaP = q.getResultList();

		for (int i = 0; i < listaP.size(); i++) {
			if (listaP.get(i) instanceof Credicart) {
				Credicart cre = (Credicart) listaP.get(i);
				listaCredit.add(cre);
			}
		}
		return listaCredit;
	}

	/**
	 * Genera numero aleatorio de 16 digitos para la tarjeta
	 * 
	 * @return
	 */
	public long generarAleatorioTarjeta() {

		long aleatorio = ThreadLocalRandom.current().nextLong(9000000000000000L) + 1000000000000000L;
		return aleatorio;
	}

	/**
	 * Genera un numero aleatorio para la tarjeta (cvc)
	 * 
	 * @return
	 */
	public int generarAleatorioCvc() {
		int aleatorioCvc = ThreadLocalRandom.current().nextInt(100, 999);
		return aleatorioCvc;
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
	 * Obtiene la fecha actualual y le suma 4 a�os
	 * 
	 * @param fecha
	 * @param dias
	 * @return
	 */
	public Date generarFechaSuma(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);

		calendar.add(Calendar.DAY_OF_YEAR, dias);

		return calendar.getTime();

	}

	/**
	 * Busca una tarjeta de credito por su numero
	 * 
	 * @param id
	 * @return
	 */
	public Credicart buscarTarjetaCRedito(String numero) {
		return em.find(Credicart.class, numero);
	}

	/**
	 * Lista de franquicias
	 * 
	 * @return lista de las franquicias consultadas
	 */
	public List<Franchise> listaFranquicia() {

		Query q = em.createNamedQuery(Franchise.LISTA_FRANQUICIA);

		List<Franchise> listaFranqui = q.getResultList();

		return listaFranqui;
	}

}
