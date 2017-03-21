package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.CustomerPK;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.IclienteRemote;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
@Remote(IclienteRemote.class)
public class ClienteEJB {

	@PersistenceContext
	private EntityManager em;

	/**
	 * metodo para crear cliente
	 * 
	 * @param cliente
	 */
	public void crearCLiente(Customer cliente) {

		Customer buscar = buscarCliente(cliente.getNumeroIndentificacion(), cliente.getTipoIdentificacion());

		if (buscar == null) {
			em.persist(cliente);
		} else {
			throw new ExcepcionNegocio("Ya esta  registrado");
		}
	}

	/**
	 * Metodo para buscar cliente
	 * 
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Customer buscarCliente(String idNumber, String idType) {
		CustomerPK cusPk = new CustomerPK(idNumber, idType);
		Customer custo = em.find(Customer.class, cusPk);
//		if (custo != null) {
//			custo.setProducto(null);
//		}
		return custo;
	}
	
	public List<Customer> listaClientes(){
		Query q = em.createNamedQuery(Customer.LIST_CLIENTES);
		List<Customer> listaCliente = q.getResultList();
		return listaCliente;
	}

}
