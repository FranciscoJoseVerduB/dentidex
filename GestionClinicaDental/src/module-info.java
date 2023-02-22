module GestionClinicaDental {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	requires java.desktop;   
	requires java.sql;    
	requires jasperreports;
	requires javafx.swing;  
	requires org.controlsfx.controls; 
  
	opens com;
	opens com.vistas;
	opens com.controladoras;
	opens com.conexion;
	opens com.informes; 
	opens com.util;
	opens com.modelos.hibernate;
	opens com.resources;
	opens com.vistas.css;
}