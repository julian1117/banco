package main;

import javax.naming.NamingException;

import co.edu.eam.ingesoft.banco.entidades.Customer;
import co.edu.eam.ingesoft.pa.negocio.beans.remote.IclienteRemote;
import util.ServiceLocator;

public class Main {

	public static void main(String[] args) throws NamingException {

		IclienteRemote iCliente = (IclienteRemote) ServiceLocator.buscarEJB("ClienteEJB",
				IclienteRemote.class.getCanonicalName());
		
		Customer cliente = new Customer();
		cliente.setName("Julian Camilo");
		cliente.setLastName("Henao Florez");
		cliente.setTipoIdentificacion("Cedula");
		cliente.setNumeroIndentificacion("1094931286");
		
		iCliente.crearCLiente(cliente);
	}

}
