package co.edu.eam.ingesoft.pa.negocio.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.Bindable;
import javax.xml.ws.BindingProvider;

import co.edu.eam.pa.clientews.Banco;
import co.edu.eam.pa.clientews.InterbancarioWS;
import co.edu.eam.pa.clientews.InterbancarioWS_Service;

@Startup
@Singleton
public class IniciarServicios {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private AsociacionEJB asociaEJB;

	InterbancarioWS_Service cliente = new InterbancarioWS_Service();
	InterbancarioWS servicios = cliente.getInterbancarioWSPort();

	String endpointURL = "http://104.197.238.134:8080/interbancario/InterbancarioWS";

	@PostConstruct
	public void inicializar() {
		BindingProvider bp = (BindingProvider) servicios;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

		List<Banco> bancos = servicios.listarBancos();
		List<co.edu.eam.ingesoft.banco.entidades.Banco> bancosBD = asociaEJB.listarBancos();
		for (Banco banWS : bancos) {
			boolean estado = true;
			for (co.edu.eam.ingesoft.banco.entidades.Banco banBD : bancosBD) {
				if (banWS.getCodigo().equals(banBD.getIdBanco())) {
					estado = false;
				}
			}
			if (estado) {
				co.edu.eam.ingesoft.banco.entidades.Banco banAgre = new co.edu.eam.ingesoft.banco.entidades.Banco(
						banWS.getCodigo(), banWS.getNombre());
				em.persist(banAgre);
			} else {
				System.out.println("Ya Existe el Banco");
			}
		}

	}

}
