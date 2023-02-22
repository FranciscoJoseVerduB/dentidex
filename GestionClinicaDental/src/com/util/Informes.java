package com.util;

import java.util.HashMap;
import java.util.Map;

import com.Main;
import com.modelos.hibernate.Factura;
import com.modelos.hibernate.HistorialClinico;
import com.modelos.hibernate.HistorialClinicoDetalle;
import com.modelos.hibernate.Paciente;
 
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;  
import net.sf.jasperreports.engine.design.JasperDesign; 
import net.sf.jasperreports.engine.xml.JRXmlLoader; 
import net.sf.jasperreports.view.JasperViewer;

public class Informes {

	
	 public static JasperReport getCabeceraDocumento(Main main) {
		 JasperReport jr = null;	
		 try {
				// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(Main.class.getResourceAsStream("informes/CabeceraDocumento.jasper"));
	   	 		JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/CabeceraDocumento.jrxml")); 
				jr = JasperCompileManager.compileReport(jd);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	 	return jr;
		}
	 
	 public static void visualizarListadoSimple(Main main, String nombreRecursoSinExtension) {
		 try {
			 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/" + nombreRecursoSinExtension + ".jrxml")); 
	    	 JasperReport jr = JasperCompileManager.compileReport(jd); 
	    	 /*JRDesignQuery newQuery = new JRDesignQuery();
	    	 newQuery.setText(sql);
	    	 jd.setQuery(newQuery);*/

	    	 JasperPrint jp = JasperFillManager.fillReport(jr, null, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false); 
		 }catch(Exception ex) {
			 ex.printStackTrace();
		 }
	 }
	
	public static void mostrarListadoPacientes(Main main) {
		visualizarListadoSimple(main, "ListadoPacientes");  
	}
	public static void mostrarListadoPacientesPorProvincia(Main main) {
		 visualizarListadoSimple(main, "ListadoPacientesPorProvincia" );
	}

	public static void mostrarListadoPacientesPorGenero(Main main) {
		 visualizarListadoSimple(main, "ListadoPacientesPorGenero" );
	}
	public static void mostrarListadoPacientesPorTipoSanguineo(Main main) {
		 visualizarListadoSimple(main, "ListadoPacientesPorTipoSanguineo");
	} 	
	
	public static void mostrarListadoMedicos(Main main) {
		 visualizarListadoSimple(main, "ListadoMedicos");
	}
	public static void mostrarListadoMedicosPorEspecialidad(Main main) {
		 visualizarListadoSimple(main, "ListadoMedicosPorEspecialidad");
	}

	public static void mostrarListadoMedicosPorPoblacion(Main main) {
		 visualizarListadoSimple(main, "ListadoMedicosPorPoblacion");
	}

 
	public static void mostrarListadoProveedores(Main main) { 
    	 visualizarListadoSimple(main, "ListadoProveedores");  
	}
	public static void mostrarListadoArticulos(Main main) {
		visualizarListadoSimple(main, "ListadoArticulos"); 
	}
	public static void mostrarListadoFamiliasArticulo(Main main) {
		visualizarListadoSimple(main, "ListadoFamiliasArticulo"); 
	}
	public static void mostrarListadoEspecialidades(Main main) {   
		visualizarListadoSimple(main, "ListadoEspecialidades");
	}
	public static void mostrarListadoTratamientos(Main main) { 
    	 visualizarListadoSimple(main, "ListadoTratamientos"); 
	}
	public static void mostrarListadoTiposDeTratamiento(Main main) {   
    	 visualizarListadoSimple(main, "ListadoTiposTratamiento");
	}
	public static void mostrarListadoMedicamentos(Main main) {  
		visualizarListadoSimple(main, "ListadoMedicamentos");
	}

	public static void mostrarListadoPacientesCitasPorMedico(Main main) {
		visualizarListadoSimple(main, "ListadoCitasPorMedico");
	}
	
	public static void mostrarHistorialClinicoPorPaciente(Main main, Paciente paciente) {
		
	}

	public static void mostrarListadoPacientesDeudores(Main main) { 
		visualizarListadoSimple(main, "ListadoPacientesDeudores");
	}

	public static void mostrarListadoRecetasMedicasPorMedico(Main main) {
		visualizarListadoSimple(main, "ListadoRecetasMedicasPorMedico");
	}

	public static void mostrarListadoRecetasMedicasPorPaciente(Main main) {
		visualizarListadoSimple(main, "ListadoRecetasMedicasPorPaciente");
	}

	public static void mostrarListadoFacturas(Main main) { 
		try { 
	    	 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoFacturas.jrxml")); 
	    	 JasperReport jr = JasperCompileManager.compileReport(jd); 
	    	  
	    	 JasperDesign jdetalle = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoFacturasDetalle.jrxml")); 
	    	 JasperReport jasperSubReport =  JasperCompileManager.compileReport(jdetalle);
	    	  
	    	 Map<String, Object> parameters = new HashMap<String, Object>(); 
	    	 parameters.put("SUBREPORTE", jasperSubReport); 
	    	 
	    	 JasperPrint jp = JasperFillManager.fillReport(jr, parameters, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void mostrarInformeFactura(Main main, Factura factura) {
		try {
			//Si la factura introducida tiene id 0 o es nulo, no hará nada
			if(factura == null || factura.getId() == 0) return;
			
	    	 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/InformeFactura.jrxml")); 
	    	 JasperReport jr = JasperCompileManager.compileReport(jd); 
	    	  
	    	 JasperDesign jdetalle = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoFacturasDetalle.jrxml")); 
	    	 JasperReport jasperSubReport =  JasperCompileManager.compileReport(jdetalle);
	    	  
	    	 Map<String, Object> parameters = new HashMap<String, Object>(); 
	    	 parameters.put("SUBREPORTE", jasperSubReport);
	    	 parameters.put("Id_Factura", factura.getId()); 
	    	 
	    	 JasperPrint jp = JasperFillManager.fillReport(jr, parameters, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
 
 
	public static void mostrarListadoHistorialesClinicos(Main main) { 
		try { 
	    	 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinico.jrxml")); 
	    	 JasperReport jr = JasperCompileManager.compileReport(jd); 
	    	  
	    	 //Detalle
	    	 JasperDesign jdetalle = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalle.jrxml")); 
	    	 JasperReport jasperSubReport =  JasperCompileManager.compileReport(jdetalle);
	    	 
	    	 //Tratamientos del detalle
	    	 JasperDesign jdetalleTratamientos = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleTratamientos.jrxml")); 
	    	 JasperReport jasperSubReportTratamientos =  JasperCompileManager.compileReport(jdetalleTratamientos);

	    	 //Recetas del detalle
	    	 JasperDesign jdetalleRecetas = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleRecetas.jrxml")); 
	    	 JasperReport jasperSubReportRecetas =  JasperCompileManager.compileReport(jdetalleRecetas);
	    	 
	    	 Map<String, Object> parameters = new HashMap<String, Object>(); 
	    	 parameters.put("SUBREPORTE", jasperSubReport);
	    	 parameters.put("SUBREPORTE_TRATAMIENTOS", jasperSubReportTratamientos); 
	    	 parameters.put("SUBREPORTE_RECETAS", jasperSubReportRecetas); 
	    	 
	    	 JasperPrint jp = JasperFillManager.fillReport(jr, parameters, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void mostrarInformeHistorialClinico(Main main, HistorialClinico historialClinico) {
		try { 

			//Si la factura introducida tiene id 0 o es nulo, no hará nada
			if(historialClinico == null || historialClinico.getId() == 0) return;
			 
			 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/InformeHistorialClinico.jrxml")); 
	    	 JasperReport jr = JasperCompileManager.compileReport(jd); 
	    	  
	    	 //Detalle
	    	 JasperDesign jdetalle = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalle.jrxml")); 
	    	 JasperReport jasperSubReport =  JasperCompileManager.compileReport(jdetalle);
	    	 
	    	 //Tratamientos del detalle
	    	 JasperDesign jdetalleTratamientos = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleTratamientos.jrxml")); 
	    	 JasperReport jasperSubReportTratamientos =  JasperCompileManager.compileReport(jdetalleTratamientos);

	    	 //Recetas del detalle
	    	 JasperDesign jdetalleRecetas = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleRecetas.jrxml")); 
	    	 JasperReport jasperSubReportRecetas =  JasperCompileManager.compileReport(jdetalleRecetas);
	    	 
	    	 Map<String, Object> parameters = new HashMap<String, Object>(); 
	    	 parameters.put("SUBREPORTE", jasperSubReport);
	    	 parameters.put("SUBREPORTE_TRATAMIENTOS", jasperSubReportTratamientos); 
	    	 parameters.put("SUBREPORTE_RECETAS", jasperSubReportRecetas); 
	    	 parameters.put("Id_HistorialClinico", historialClinico.getId()); 
	    	 
	    	 JasperPrint jp = JasperFillManager.fillReport(jr, parameters, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	
	
	public static void mostrarInformeHistorialClinicoDetalle(Main main, HistorialClinicoDetalle historialClinicoDetalle) {
		try { 

			//Si la factura introducida tiene id 0 o es nulo, no hará nada
			if(historialClinicoDetalle == null || historialClinicoDetalle.getId() == 0) return;
			 
	    	 //Detalle
	    	 JasperDesign jd = JRXmlLoader.load(Main.class.getResourceAsStream("informes/InformeHistorialClinicoDetalle.jrxml")); 
	    	 JasperReport jr =  JasperCompileManager.compileReport(jd);
	    	 
	    	 //Tratamientos del detalle
	    	 JasperDesign jdetalleTratamientos = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleTratamientos.jrxml")); 
	    	 JasperReport jasperSubReportTratamientos =  JasperCompileManager.compileReport(jdetalleTratamientos);

	    	 //Recetas del detalle
	    	 JasperDesign jdetalleRecetas = JRXmlLoader.load(Main.class.getResourceAsStream("informes/ListadoHistorialClinicoDetalleRecetas.jrxml")); 
	    	 JasperReport jasperSubReportRecetas =  JasperCompileManager.compileReport(jdetalleRecetas);
	    	 
	    	 Map<String, Object> parameters = new HashMap<String, Object>(); 
	    	 parameters.put("SUBREPORTE_TRATAMIENTOS", jasperSubReportTratamientos); 
	    	 parameters.put("SUBREPORTE_RECETAS", jasperSubReportRecetas); 
	    	 parameters.put("Id_HistorialClinicoDetalle", historialClinicoDetalle.getId()); 
	    	 
	    	 JasperPrint jp = JasperFillManager.fillReport(jr, parameters, com.conexion.ConexionBBDD.getCon());
	    	 JasperViewer.viewReport(jp, false);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	


	
	
	
}
