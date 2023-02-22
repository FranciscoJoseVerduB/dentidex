package com.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class ConexionBBDD {
	static String driver = "jdbc:postgresql://";
	static String ip = "localhost";
	static String puerto = "5432";
	static String baseDatos = "ClinicaDental";
	static String usuario = "postgres";
	static String password = "teleco";
	static Connection con;  
	
	@SuppressWarnings("static-access")
	public ConexionBBDD () {
		con = this.abrirConexion();		//Inicializamos la conexion
	}
	
	private static Connection abrirConexion() {
		try {
			return DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + puerto + "/" + baseDatos, usuario, password);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getCon() {
		return con;
	}
	
	public static Object getDato(String sql) { 
		try {
			ResultSet rs = getDatos(sql);		//Obtenemos el set de datos
			if(rs == null) return null;
			rs.next();
			return rs.getObject(1);		//Devolvemos el 1º item de la select
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getDatos(String sql) {
		try {
			return getCon().createStatement().executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  
	public static void cerrarConexion() {
		try {
			getCon().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public static boolean setDatos(String sql) {
		try {
			getCon().setAutoCommit(false); 
			getCon().createStatement().executeUpdate(sql);
			getCon().commit();
			return true;
		} catch (SQLException e) { 
			try {
				getCon().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	
	
 
}
