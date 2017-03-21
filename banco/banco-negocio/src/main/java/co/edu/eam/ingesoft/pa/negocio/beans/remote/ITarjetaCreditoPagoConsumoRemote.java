package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import co.edu.eam.ingesoft.banco.entidades.CreditcardPaymentConsume;

public interface ITarjetaCreditoPagoConsumoRemote {
	
	public void pagoTarjeta(double saldo,String numTarjeta);
	
	public double consultarCuota(String numTarjeta);	
}
