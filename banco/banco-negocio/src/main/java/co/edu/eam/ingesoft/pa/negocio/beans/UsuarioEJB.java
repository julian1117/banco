package co.edu.eam.ingesoft.pa.negocio.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
