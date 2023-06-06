package excelbanco.model;

import java.io.Serializable;

public class Usuario implements Serializable{

	private static final long serialVersionUID = 1062521165614549998L;
	
	
	private Long codUsuario;
	
	private String desLogin;
	
	private String desSenha;
	
	private String desEmail;
	
	private String nomUsuario;
	
	private Integer codEmpresa;
	
	private Integer codPapel;
	
	private Integer codSala;
	
	

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getDesLogin() {
		return desLogin;
	}

	public void setDesLogin(String desLogin) {
		this.desLogin = desLogin;
	}

	public String getDesSenha() {
		return desSenha;
	}

	public void setDesSenha(String desSenha) {
		this.desSenha = desSenha;
	}

	public String getDesEmail() {
		return desEmail;
	}

	public void setDesEmail(String desEmail) {
		this.desEmail = desEmail;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public Integer getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Integer codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Integer getCodPapel() {
		return codPapel;
	}

	public void setCodPapel(Integer codPapel) {
		this.codPapel = codPapel;
	}

	public Integer getCodSala() {
		return codSala;
	}

	public void setCodSala(Integer codSala) {
		this.codSala = codSala;
	}
	
	
	
	
	
}
