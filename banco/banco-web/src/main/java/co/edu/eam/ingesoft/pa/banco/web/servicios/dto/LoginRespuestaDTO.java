package co.edu.eam.ingesoft.pa.banco.web.servicios.dto;

public class LoginRespuestaDTO {

	private String token;
	private String identificacion;
	
	public LoginRespuestaDTO() {
		// TODO Auto-generated constructor stub
	}
		
	public LoginRespuestaDTO(String token, String identificacion) {
		super();
		this.token = token;
		this.identificacion = identificacion;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	
}
