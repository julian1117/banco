package co.edu.eam.ingesoft.pa.negocio.beans.remote;

import java.util.List;

import co.edu.eam.ingesoft.banco.entidades.Credicart;
import co.edu.eam.ingesoft.banco.entidades.CreditcardPaymentConsume;
import co.edu.eam.ingesoft.banco.entidades.Franchise;
import co.edu.eam.ingesoft.banco.entidades.Product;

public interface ITarjetaCreditoRemote {

	public  void crearTarjetaCredito (Credicart tarjetaCredito,String numeroId, String tipoId);
	
	public CreditcardPaymentConsume buscarTarjetaCRedito (int id);
	
	public void CrearProducto(Product producto);
	
	public List<Franchise> listaFranquicia();
	
}
