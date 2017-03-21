package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;

public class CustomerPK implements Serializable {
	
	private String numeroIndentificacion;
	
	private String tipoIdentificacion;
	
	

	public CustomerPK() {
		super();
	}

	

	public CustomerPK(String numeroIndentificacion, String tipoIdentificacion) {
		super();
		this.numeroIndentificacion = numeroIndentificacion;
		this.tipoIdentificacion = tipoIdentificacion;
	}



	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIndentificacion() {
		return numeroIndentificacion;
	}

	public void setNumeroIndentificacion(String numeroIndentificacion) {
		this.numeroIndentificacion = numeroIndentificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroIndentificacion == null) ? 0 : numeroIndentificacion.hashCode());
		result = prime * result + ((tipoIdentificacion == null) ? 0 : tipoIdentificacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPK other = (CustomerPK) obj;
		if (numeroIndentificacion == null) {
			if (other.numeroIndentificacion != null)
				return false;
		} else if (!numeroIndentificacion.equals(other.numeroIndentificacion))
			return false;
		if (tipoIdentificacion == null) {
			if (other.tipoIdentificacion != null)
				return false;
		} else if (!tipoIdentificacion.equals(other.tipoIdentificacion))
			return false;
		return true;
	}
	
	
	
	
	
}
