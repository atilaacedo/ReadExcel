package excelbanco.model;

import java.io.Serializable;

public class Sala implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private int codSala;
	
	private String desSala;
	
	private String email;
	
	private int codLocal;
	
	private int cod_usuario;
	
	
	

	public int getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(int codLocal) {
		this.codLocal = codLocal;
	}

	public int getCod_usuario() {
		return cod_usuario;
	}

	public void setCod_usuario(int cod_usuario) {
		this.cod_usuario = cod_usuario;
	}

	public int getCodSala() {
		return codSala;
	}

	public void setCodSala(int codSala) {
		this.codSala = codSala;
	}

	public String getDesSala() {
		return desSala;
	}

	public void setDesSala(String desSala) {
		this.desSala = desSala;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Sala [codSala=" + codSala + ", desSala=" + desSala + ", email=" + email + ", codLocal=" + codLocal
				+ ", cod_usuario=" + cod_usuario + "]";
	}
	
	
	
}
