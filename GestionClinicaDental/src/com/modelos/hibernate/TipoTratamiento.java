package com.modelos.hibernate;
// Generated 5 may. 2021 19:19:57 by Hibernate Tools 5.4.27.Final

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; 

import com.conexion.ConexionBBDD;

/**
 * TipoTratamiento generated by hbm2java
 */
@SuppressWarnings("serial")
public class TipoTratamiento implements java.io.Serializable, IOperable, INombreUnico{

	private int id;
	private String nombre; 

	public TipoTratamiento() {
		this.id = 0;
	}

	public TipoTratamiento(int id, String nombre) {
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
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM TiposTratamiento WHERE Id_Medico = " + this.getId());
			if (rs.next() == false) return false;
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
	public ArrayList<TipoTratamiento> getLista() {
		ArrayList<TipoTratamiento> lista = new ArrayList<TipoTratamiento>();
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM TiposTratamiento");
			while(rs.next()) {
				TipoTratamiento p = new TipoTratamiento();
				p.setId(rs.getInt("Id"));
				p.setNombre(rs.getString("Nombre")); 
				
				lista.add(p); 
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		return lista;
	}

	@Override
	public boolean guardar() {
		String sql = "";
		
		if(this.getId() == 0) {
			sql = "INSERT INTO TiposTratamiento (Nombre)"
					+ "	VALUES ('" + this.getNombre().trim() + "')"; 
		}else {
			sql = "UPDATE TiposTratamiento "
					+ " SET Nombre = '" + this.getNombre().trim() + "'"  
				+ " WHERE Id = " + this.getId();			
		} 	
		return ConexionBBDD.setDatos(sql); 
	}

	@Override
	public boolean esEliminable() {
		//Comprobamos si se ha realizado alguna cita con el registro en cuesti�n.
		if(Integer.parseInt(ConexionBBDD.getDato(
				"SELECT count(*) Existe " +
				 " FROM TiposTratamiento TT " +  
				 " INNER JOIN Tratamientos TA on TA.Id_TipoTratamiento = TT.ID" + 
				 " WHERE TT.ID = " + this.getId()).toString()) > 0) return false;  
		return true;
	}

	@Override
	public boolean borrar() {
		String sql = 
				 "DELETE "
				+ "	FROM TiposTratamiento" 
				+ "	WHERE ID = " + this.getId();
		return ConexionBBDD.setDatos(sql); 
	}

	@Override
	public boolean existeConMismoNombre(String nombre) {
		if(Integer.parseInt(ConexionBBDD.getDato("SELECT count(*) Existe FROM TiposTratamiento Where Nombre = '" + nombre.trim() + "' and Id <>" + this.getId()).toString()) > 0) 
			return true;
			 
		return false;
	}



 
}
