package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.BindingProvider;

import co.edu.eam.ingesoft.banco.entidades.AsociacionCuentas;
import co.edu.eam.ingesoft.banco.entidades.Banco;
import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.excepciones.ExcepcionNegocio;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociada;
import co.edu.eam.pa.clientews.RegistrarCuentaAsociadaResponse;

@Stateless
@LocalBean
public class AsociacionEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void crearAsociacion(AsociacionCuentas asoci){
		
				
		AsociacionCuentas aso = buscarAsociacion(asoci.getNumeroId());
		if(aso == null){
				em.persist(asoci);
		}else{
			throw new ExcepcionNegocio("La asociacion ya existe");
		}
	}

	
	public AsociacionCuentas buscarAsociacion(String numero){
		return em.find(AsociacionCuentas.class, numero);
		
	}
	
	public void eliminarAsociacion(AsociacionCuentas aso){
		aso = buscarAsociacion(aso.getNumeroId());
		em.remove(aso);
	}
	
	public List<AsociacionCuentas> listarAsociaciones(Customer cliente){
		
		Query q = em.createNamedQuery(AsociacionCuentas.LISTAR_ASOCIACIONES);
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
	
	
	public void verificar(AsociacionCuentas aso){
			em.merge(aso);

	}
	
	/**
	 * Lita de cuentas Asociadas de un cliente
	 * @param cliente
	 * @return
	 */
	public List<AsociacionCuentas> listaAsociadaVeri(Customer cliente) {
		return em.createNamedQuery(AsociacionCuentas.LISTRA_ASOCIACIONES_VERIFICADA)
				.setParameter(1, cliente).setParameter(2, "ASOCIADA").getResultList();
		
	}
	
	
}
