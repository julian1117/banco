package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="T_VERIFICACION")
@NamedQuery(name=Verificacion.LISTA_VERIFICAICON,query="SELECT v FROM Verificacion v WHERE v.usuario=?1 ")
public class Verificacion implements Serializable{

	public static final String LISTA_VERIFICAICON = "Verificacion.listaVerificacion";
	
	@Id
	@OneToOne
	@JoinColumn(name="USUARIO")
	private Usuario usuario;
	
	@Column(name="CODIGO",nullable=false)
	private long codigo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	
	
	public Verificacion() {
		super();
	}



	public Verificacion(Usuario usuario, long codigo, Date fecha) {
		super();
		this.usuario = usuario;
		this.codigo = codigo;
		this.fecha = fecha;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public long getCodigo() {
		return codigo;
	}



	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	
	
}
