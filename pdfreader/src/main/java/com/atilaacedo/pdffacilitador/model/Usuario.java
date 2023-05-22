package com.atilaacedo.pdffacilitador.model;

public class Usuario {
	private int cod_usuario;
	private String login;
	private String senha;
	private String nome;
	private int cod_sala;
	private String sts_ativo;
	private String cod_empresa;
	private String email;
	private int cod_papel;
	
	
	public int getCod_usuario() {
		return cod_usuario;
	}
	public void setCod_usuario(int cod_usuario) {
		this.cod_usuario = cod_usuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCod_sala() {
		return cod_sala;
	}
	public void setCod_sala(int cod_sala) {
		this.cod_sala = cod_sala;
	}
	public String getSts_ativo() {
		return sts_ativo;
	}
	public void setSts_ativo(String sts_ativo) {
		this.sts_ativo = sts_ativo;
	}
	public String getCod_empresa() {
		return cod_empresa;
	}
	public void setCod_empresa(String cod_empresa) {
		this.cod_empresa = cod_empresa;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCod_papel() {
		return cod_papel;
	}
	public void setCod_papel(int cod_papel) {
		this.cod_papel = cod_papel;
	}
	
	
	
	
}
