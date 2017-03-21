package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import co.edu.eam.ingesoft.banco.entidades.SavingAccount;

public interface ICuentaAhorrosRemote {

	public void crearCuentaAhorro(SavingAccount cuentaAhorro,String numeroId, String tipoId);

	public SavingAccount buscarCuentaAhorro(String numero);
}
