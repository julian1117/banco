package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import co.edu.eam.ingesoft.banco.entidades.Customer;

public interface IclienteRemote {
	
	public void crearCLiente(Customer cliente);
	
	public Customer buscarCliente(String idNumber,String idType);


}
