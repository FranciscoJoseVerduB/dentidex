package com.modelos.hibernate;
// Generated 5 may. 2021 19:19:57 by Hibernate Tools 5.4.27.Final

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.conexion.ConexionBBDD;
import com.modelos.hibernate.Enumerados.CitaEstado;
 
/**
 * Cita generated by hbm2java
 */
@SuppressWarnings("serial")
public class Cita implements java.io.Serializable, IOperable {
	 
	private int id;
	private Enumerados.CitaEstado citaEstado; 
	private Medico medico;
	private Paciente paciente;
	private Date fecha;
	private String hora;
	private String observaciones;
	private HistorialClinicoDetalle historialClinicoDetalle;

	public Cita() {
		this.id = 0;
		this.citaEstado = CitaEstado.PENDIENTE; 
		this.medico = new Medico();
		this.paciente = new Paciente();
		this.fecha = new Date();
	}
	
	public Cita(int id) {
		this.id = id;
	}
	

	public Cita(int id, CitaEstado citasestado, Medico medico, Paciente paciente, Date fecha, String hora) {
		this.id = id;
		this.citaEstado = citasestado;
		this.medico = medico;
		this.paciente = paciente;
		this.fecha = fecha;
		this.hora = hora;
	}

	public Cita(int id, CitaEstado citasestado, Medico medico,
			Paciente paciente, Date fecha, String hora, String observaciones) {
		this.id = id;
		this.citaEstado = citasestado; 
		this.medico = medico;
		this.paciente = paciente;
		this.fecha = fecha;
		this.hora = hora;
		this.observaciones = observaciones;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CitaEstado getCitaEstado() {
		return this.citaEstado;
	}

	public void setCitaEstado(CitaEstado citasestado) {
		this.citaEstado = citasestado;
	}
 

	public Medico getMedico() {
		return this.medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Date getFecha() {
		return this.fecha;
	}

	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		if(this.hora == null) return null;
		else if (this.hora.length() <= 3) return this.hora;
		else return this.hora.substring(0, this.hora.length() -3);
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public HistorialClinicoDetalle getHistorialClinicoDetalle() {
		return historialClinicoDetalle;
	}

	public void setHistorialClinicoDetalle(HistorialClinicoDetalle historialClinicoDetalle) {
		this.historialClinicoDetalle = historialClinicoDetalle;
	}

	
	@Override
	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(getFecha()) + " " + getHora();
	}

	@Override
	public boolean cargar() {
		try {
			ResultSet rs = ConexionBBDD.getDatos(
					"SELECT C.*,"
						+ "	HCD.Id			as Id_HistorialClinicoDetalle"
					+ "	FROM Citas C"
							+ "	LEFT JOIN HistorialClinicoDetalle HCD on HCD.ID_Cita = C.ID "
					+ " WHERE C.ID = " + this.getId());
			if(rs.next() == false) return false;
			else { 
				setFecha(rs.getDate("Fecha"));
				setHora(rs.getString("Hora")); 
				Medico med = new Medico(rs.getInt("Id_Medico"));
				med.cargar();
				setMedico(med);
				Paciente pac = new Paciente(rs.getInt("Id_Paciente"));
				pac.cargar();
				setPaciente(pac);
				setCitaEstado(Enumerados.CitaEstado.getCitaEstado(rs.getInt("Id_EstadoCita"))); 

				//Si se ha operado sobre el historial clinico tendr� un registro, sino sera null
				if(rs.getObject("Id_HistorialClinicoDetalle") != null) {
					HistorialClinicoDetalle hcd = new HistorialClinicoDetalle(rs.getInt("Id_HistorialClinicoDetalle"));
					setHistorialClinicoDetalle(hcd);
				}
				 
			}
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	@Override
	public ArrayList<Cita> getLista() {
		return getLista_Filtro(null, null, null);
	}

	public ArrayList<Cita> getListaCitasExpiradas(Date fechaDesde, Date fechaHasta) {
		return this.getLista_Filtro(fechaDesde, fechaHasta, "EXPIRADO");
	}

	public ArrayList<Cita> getListaCitasAnuladas(Date fechaDesde, Date fechaHasta) {
		return this.getLista_Filtro(fechaDesde, fechaHasta, "ANULADO");
	}

	public ArrayList<Cita> getListaCitasPendientes(Date fechaDesde, Date fechaHasta) {
		return this.getLista_Filtro(fechaDesde, fechaHasta, "PENDIENTE");
	}	
	
	
	@SuppressWarnings("static-access")
	private ArrayList<Cita> getLista_Filtro(Date fechaDesde, Date fechaHasta, String tipoBusqueda) {
		ArrayList<Cita> lista = new ArrayList<Cita>();
		try {
			String sql = "SELECT C.*,"
							+ "	HCD.Id			as Id_HistorialClinicoDetalle"
						+ "	FROM Citas C"
							+ "	LEFT JOIN HistorialClinicoDetalle HCD on HCD.ID_Cita = C.ID "
						+ " WHERE 1 = 1 ";
			//Filtramos la b�squeda por fecha
			if(fechaDesde != null && fechaHasta != null ) sql += " AND C.Fecha BETWEEN '" + fechaDesde + "' AND '"  + fechaHasta + "'";
			//Filtramos la b�squeda por estado de cita
			if(tipoBusqueda != null)
				switch(tipoBusqueda) {
					case "PENDIENTE": sql += " AND C.Id_EstadoCita = " + Enumerados.CitaEstado.PENDIENTE.getId() + 
							//				 " AND (C.Fecha + C.Hora) >= NOW()"
							 " AND (C.Fecha) >=  cast(now() as date)" +
							"";
						break;
					case "EXPIRADO": sql += " AND C.Id_EstadoCita = " + Enumerados.CitaEstado.PENDIENTE.getId() + 
										    " AND (C.Fecha + C.Hora) < NOW()";
						break;
					case "ANULADO": sql += " AND C.Id_EstadoCita = " + Enumerados.CitaEstado.ANULADO.getId();
						break;
				} 
			
			ResultSet rs = ConexionBBDD.getDatos(sql);
			while(rs.next()) {
				Cita g = new Cita();
				g.setId(rs.getInt("Id"));
				g.setFecha(rs.getDate("Fecha"));
				g.setHora(rs.getString("Hora")); 
				g.setObservaciones(rs.getString("Observaciones"));
				g.setCitaEstado(citaEstado.getCitaEstado(rs.getInt("Id_EstadoCita"))); 
				Medico med = new Medico(rs.getInt("Id_Medico"));
				med.cargar();
				Paciente pac = new Paciente(rs.getInt("Id_Paciente"));
				pac.cargar();
				g.setMedico(med);
				g.setPaciente(pac);
				
				//Si se ha operado sobre el historial clinico tendr� un registro, sino sera null
				if(rs.getObject("Id_HistorialClinicoDetalle") != null) {
					HistorialClinicoDetalle hcd = new HistorialClinicoDetalle(rs.getInt("Id_HistorialClinicoDetalle"));
					g.setHistorialClinicoDetalle(hcd);
				}
				 
				lista.add(g); 
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
			sql = "INSERT INTO Citas (Fecha, Hora, Observaciones, Id_Medico, Id_Paciente, Id_EstadoCita)"
					+ "	VALUES ('"  + this.getFecha().toString() + "'," 
							+ "'"	+ this.getHora() + "',"
							+ (this.getObservaciones() == null? "null" : "'" + this.getObservaciones().trim() + "'") + "," 
							+ this.getMedico().getId() + "," 
							+ this.getPaciente().getId() + ","
							+ this.getCitaEstado().getId()
						+ ")"; 
		}else {
			sql = "UPDATE Citas "
					+ " SET Fecha = '" + this.getFecha() + "',"
						+ " Hora = '" + this.getHora() + "',"
						+ " Observaciones = " + (this.getObservaciones() == null? "null" : "'" + this.getObservaciones().trim() + "'") + ","
						+ " Id_Medico = " + this.getMedico().getId() + ","
						+ " Id_Paciente = " + this.getPaciente().getId() + ","
						+ " Id_EstadoCita = " + this.getCitaEstado().getId() 
				+ " WHERE Id = " + this.getId();			
		} 	
		return ConexionBBDD.setDatos(sql); 
	}

	@Override
	public boolean esEliminable() {
		//Comprobamos si se ha realizado algun registro con esta entidad.
		if(Integer.parseInt(ConexionBBDD.getDato(
				"SELECT count(*) Existe " +
				 " FROM Citas C " +  
				 " INNER JOIN HistorialClinicoDetalle D on D.Id_Cita = C.ID" + 
				 " WHERE C.ID = " + this.getId()).toString()) > 0) return false;  
		return true;
	}

	@Override
	public boolean borrar() {
		String sql = 
				 "DELETE "
				+ "	FROM Citas" 
				+ "	WHERE ID = " + this.getId();
		return ConexionBBDD.setDatos(sql); 
	}


}