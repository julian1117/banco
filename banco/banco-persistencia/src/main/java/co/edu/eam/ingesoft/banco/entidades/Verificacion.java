package co.edu.eam.ingesoft.banco.entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@NamedQuery(name=Verificacion.LISTA_VERIFICAICON,query="SELECT v FROM Verificacion v WHERE v.usuario.usuario=?1")
public class Verificacion implements Serializable{

	public static final String LISTA_VERIFICAICON = "Verificacion.listaVerificacion";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cod")
	private int cod;	
	
	@ManyToOne
	@JoinColumn(name="USUARIO")
	private Usuario usuario;
	
	@Column(name="CODIGO",nullable=false)
	private int codigo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fecha;

	
	
	public Verificacion() {
		super();
	}



	public Verificacion(int cod, Usuario usuario, int codigo, Calendar fecha) {
		super();
		this.cod = cod;
		this.usuario = usuario;
		this.codigo = codigo;
		this.fecha = fecha;
	}



	public int getCod() {
		return cod;
	}



	public void setCod(int cod) {
		this.cod = cod;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}



	public Calendar getFecha() {
		return fecha;
	}



	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
}
