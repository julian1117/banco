package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.banco.entidades.Usuario;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
public class UsuarioEJB {

	@PersistenceContext
	private EntityManager em;

	public void crearUser(Usuario use) {

		Usuario usuario = buscarUs(use.getUsuario());
		if (usuario == null) {

			em.persist(use);

		} else {
			throw new ExcepcionNegocio("El usuario ya existe");
		}
	}

	public Usuario buscarUs(String nombreUs) {
		return em.find(Usuario.class, nombreUs);
	}

	public Usuario usuacioC (Customer cliente){
		
		List<Usuario> use =	em.createNamedQuery(Usuario.USUARIO_C).setParameter(1, cliente).getResultList();
		
		return use.get(0);
	}
	
	public List<Usuario> listaUs (String nombreUs, String passw){
		return em.createNamedQuery(Usuario.USUARIO_LIST).setParameter(1, nombreUs).setParameter(2, passw).getResultList();
	}
	
}
