package com.modelos.hibernate;
// Generated 5 may. 2021 19:19:57 by Hibernate Tools 5.4.27.Final
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.conexion.ConexionBBDD;

/**
 * FacturaDetalle generated by hbm2java
 */
@SuppressWarnings("serial")
public class FacturaDetalle implements java.io.Serializable, IOperable {

	private int id;
	private Factura factura;
	private HistorialClinicoDetalle_Tratamiento historialClinicoDetalle_Tratamiento;
	private Double precio;
	private Double cobrado;

	public FacturaDetalle() {
		this.id = 0;
		this.historialClinicoDetalle_Tratamiento = new HistorialClinicoDetalle_Tratamiento();
	}
	public FacturaDetalle(int id) {
		this.id = id;
	}

	public FacturaDetalle(int id, Factura factura, HistorialClinicoDetalle_Tratamiento historialClinicoDetalle_Tratamiento, Double precio, Double cobrado) {
		this.id = id;
		this.factura = factura;
		this.historialClinicoDetalle_Tratamiento = historialClinicoDetalle_Tratamiento;
		this.precio = precio;
		this.cobrado = cobrado;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public HistorialClinicoDetalle_Tratamiento getHistorialClinicoDetalle_Tratamiento() {
		return this.historialClinicoDetalle_Tratamiento;
	}

	public void setHistorialClinicoDetalle_Tratamiento(HistorialClinicoDetalle_Tratamiento historialClinicoDetalle_Tratamiento) {
		this.historialClinicoDetalle_Tratamiento = historialClinicoDetalle_Tratamiento;
	}

	public Double getPrecio() {
		return this.precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Double getCobrado() {
		return this.cobrado;
	}
	 

	public void setCobrado(Double cobrado) {
		this.cobrado = cobrado;
	}
	public ArrayList<FacturaDetalle> getLista(Factura factura) {
		ArrayList<FacturaDetalle> lista = new ArrayList<FacturaDetalle>();
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM FacturaDetalle WHERE Id_Factura = " + factura.getId());
			while(rs.next()) {
				FacturaDetalle p = new FacturaDetalle(); 
				p.setFactura(factura);
				HistorialClinicoDetalle_Tratamiento historialClinico_tratamiento = new HistorialClinicoDetalle_Tratamiento(rs.getInt("Id_HistorialClinicoDetalle_Tratamiento"));
				historialClinico_tratamiento.cargar();
				p.setHistorialClinicoDetalle_Tratamiento(historialClinico_tratamiento);
				p.setId(rs.getInt("Id")); 
				p.setPrecio(rs.getDouble("Precio"));
				p.setCobrado(rs.getDouble("Cobrado"));
				lista.add(p); 
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		return lista;
	}
	@Override
	public boolean cargar() {
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM FacturaDetalle where id = " + this.getId());
			if(rs.next() == false) return false;
			else {    
				Factura factura = new Factura(rs.getInt("Id_Factura"));
				factura.cargar();
				setFactura(factura);
				HistorialClinicoDetalle_Tratamiento historialClinico_tratamiento = new HistorialClinicoDetalle_Tratamiento(rs.getInt("Id_HistorialClinicoDetalle_Tratamiento"));
				historialClinico_tratamiento.cargar();
				setId(rs.getInt("Id")); 
				setPrecio(rs.getDouble("Precio"));
				setCobrado(rs.getDouble("Cobrado"));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	@Override
	public ArrayList<FacturaDetalle> getLista() {
		ArrayList<FacturaDetalle> lista = new ArrayList<FacturaDetalle>();
		try {
			ResultSet rs = ConexionBBDD.getDatos("SELECT * FROM FacturaDetalle");
			while(rs.next()) {
				FacturaDetalle p = new FacturaDetalle(); 
				Factura factura = new Factura(rs.getInt("Id_Factura"));
				factura.cargar();
				p.setFactura(factura);
				HistorialClinicoDetalle_Tratamiento historialClinico_tratamiento = new HistorialClinicoDetalle_Tratamiento(rs.getInt("Id_HistorialClinicoDetalle_Tratamiento"));
				historialClinico_tratamiento.cargar();
				p.setId(rs.getInt("Id")); 
				p.setPrecio(rs.getDouble("Precio"));
				p.setCobrado(rs.getDouble("Cobrado"));
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
			sql = "INSERT INTO FacturaDetalle (Id_Factura, Id_HistorialClinicoDetalle_Tratamiento, Precio, Cobrado)"
					+ "	VALUES (" + getFactura().getId() + "," + 
									getHistorialClinicoDetalle_Tratamiento().getId() + "," + 
									getPrecio() + "," +
									getCobrado() + 
								")"; 
		}else {
			sql = "UPDATE FacturaDetalle " +
					 " SET Precio = " + getPrecio() + "," +
						 "  Cobrado = " + getCobrado() + 
					" WHERE Id = " + this.getId();			
		} 	
		return ConexionBBDD.setDatos(sql); 
	}
	@Override
	public boolean esEliminable() {
		//De momento, tal y como se ha implementado, no se podr� eliminar solo una linea de factura. Para ello debe borrarse completamente la factura
		return false;
	}
	@Override
	public boolean borrar() {
		String sql = 
				 "DELETE "
				+ "	FROM FacturaDetalle" 
				+ "	WHERE ID = " + this.getId();
		return ConexionBBDD.setDatos(sql); 
	}

}