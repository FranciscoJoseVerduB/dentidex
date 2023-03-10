package com.modelos.hibernate;
// Generated 5 may. 2021 19:19:57 by Hibernate Tools 5.4.27.Final

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
 
import com.conexion.ConexionBBDD;

/**
 * EstadoCivil generated by hbm2java
 */
@SuppressWarnings("serial")
public class EstadoCivil implements java.io.Serializable, ICargaDatos {

	private int id;
	private String nombre; 

	public EstadoCivil() {
		this.id = 0;
		this.nombre = "";
	}
	
	public EstadoCivil(int id) {
		this.id = id;
	}

	public EstadoCivil(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
 

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
 
	 
	@Override
	public String toString() {
		return getNombre();
	}

	@Override
	public boolean cargar() {
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM EstadosCivil where id = " + this.getId());
			if(rs.next() == false) return false;
			else { 
				setNombre(rs.getString("Nombre")); 
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	@Override
	public ArrayList<EstadoCivil> getLista() {
		ArrayList<EstadoCivil> lista = new ArrayList<EstadoCivil>();
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM EstadosCivil");
			while(rs.next()) {
				EstadoCivil g = new EstadoCivil(rs.getInt("Id"), rs.getString("Nombre"));
				lista.add(g); 
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		return lista;
	}
}
