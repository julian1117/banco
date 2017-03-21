package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardConsume;
import co.edu.eam.ingesoft.banco.entidades.enumeraciones.TipoConsumo;

public interface ITarjetaCreditoConsumoRemote {

	public void registrarConsumo(CreditcardConsume consumoTarjeta, String numTarjeta,TipoConsumo tipoConsumo);	
	
	public Credicart buscarTarjetaCRedito(String numero);
}
