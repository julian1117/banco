package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;

@Stateless
@LocalBean
public class AsociacionEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	public void crearAsociacion(AsociacionCuentas asoci){
		AsociacionCuentas aso = buscarAsociacion(asoci.getNumero());
		if(aso == null){
				em.persist(asoci);
		}else{
			throw new ExcepcionNegocio("La asociacion ya existe");
		}
	}

	
	public AsociacionCuentas buscarAsociacion(String numero){
		AsociacionCuentas asociacion = em.find(AsociacionCuentas.class, numero);
		return asociacion;
	}
	
	public void eliminarAsociacion(AsociacionCuentas aso){
		aso = buscarAsociacion(aso.getNumero());
		em.remove(aso);
	}
	
	public List<AsociacionCuentas> listarAsociaciones(Customer cliente){
		
		Query q = em.createNamedQuery(AsociacionCuentas.LISTRA_ASOCIACIONES);
		q.setParameter(1,cliente);
		List<AsociacionCuentas> lista = q.getResultList();
		return lista;
		
//		return em.createNamedQuery(AsociacionCuentas.LISTRA_ASOCIACIONES).getResultList();
	}
	
	public Banco buscarBanco(String id){
		Banco buscarBan = em.find(Banco.class, id);
		return buscarBan;
	}
	
	public List<Banco> listarBancos(){
		return em.createNamedQuery(Banco.LISTAR_BANCO).getResultList();
		
	}
	
}
