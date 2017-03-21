package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import co.edu.eam.ingesoft.banco.entidades.Transaction;

public interface ITransaccionRemote {

	public void crearTransaccion (Transaction transaccion,String numero,String cuentaB);
	
}
