package com.modelos.hibernate;
// Generated 5 may. 2021 19:19:57 by Hibernate Tools 5.4.27.Final

/**
 * Usuario generated by hbm2java
 */
@SuppressWarnings("serial")
public class Usuario implements java.io.Serializable {

	private int id; 
	private String codigo;
	private String contrasenia;

	public Usuario() {
		this.id = 0;
		this.codigo = "";
		this.contrasenia = "";
	}

	public Usuario(int id, String codigo, String contrasenia) {
		this.id = id; 
		this.codigo = codigo;
		this.contrasenia = contrasenia;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
 
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	

	@Override
	public String toString() {
		return getCodigo();
	}

}
