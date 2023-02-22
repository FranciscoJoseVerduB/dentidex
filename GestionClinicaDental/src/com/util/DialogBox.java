package com.util;

import java.io.IOException;

import com.Main;
import com.modelos.hibernate.Cita;
import com.modelos.hibernate.CitaHora;
import com.modelos.hibernate.Diente;
import com.modelos.hibernate.Factura;
import com.modelos.hibernate.HistorialClinicoDetalle;
import com.modelos.hibernate.Medicamento;
import com.modelos.hibernate.Medico;
import com.modelos.hibernate.Paciente;
import com.modelos.hibernate.Proveedor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogBox {

	public static Paciente seleccionarPaciente(Main main) {
		Paciente p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/PacientesSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Pacientes");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.PacientesSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Medico seleccionarMedico(Main main) {
		Medico p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/MedicosSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Médicos");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.MedicosSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}

	
	public static CitaHora seleccionarHoraCita(Main main) {
		CitaHora p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/CitasHoraSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Citas");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.CitasHoraSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Diente seleccionarDiente(Main main) {
		Diente p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/DientesSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Dientes");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.DientesSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Medicamento seleccionarMedicamento(Main main) {
		Medicamento p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/MedicamentosSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Medicamentos");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.MedicamentosSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Proveedor seleccionarProveedor(Main main) {
		Proveedor p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/ProveedoresSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Proveedores");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.ProveedoresSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Cita seleccionarCitaPendiente(Main main) {
		Cita p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/CitasPendientesSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Citas Pendientes de Tramitar");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.CitasPendientesSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	public static HistorialClinicoDetalle seleccionarHistorialClinicoDetallePendienteFacturar(Main main) {
		HistorialClinicoDetalle p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/HistorialClinicoDetalleSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Visitas Pendientes de Facturar");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.HistorialClinicoDetalleSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static Factura seleccionarFactura(Main main) {
		Factura p = null;
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/FacturasSeleccion.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario de Selección de Facturas");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.FacturasSeleccionController controller = loader.getController(); 
	        controller.setMain(main);  
	        controller.setStage(stage);
	        stage.showAndWait();
	        //Si el usuario ha seleccionado un registro, se obtendrá la entidad asociada
	        if(controller.isOk()) p = controller.getRegistroSeleccionado();  
	        
	        stage.close();		//Cerramos la ventana
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
		return p; 
	}
	
	public static boolean reprogramarCita(Main main, Cita cita) {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/GestionarCitasReprogramacionShow.fxml")); 
            Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Formulario para reprogramar la Cita nº:" + cita.getId() );
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(main.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
            com.controladoras.GestionarCitasReprogramacionShowController controller = loader.getController(); 
            controller.setMain(main); 
            controller.setCita(cita);
            controller.setStage(stage);
            stage.showAndWait();   
	        stage.close();		//Cerramos la ventana
	        return controller.isOk();
        } catch (IOException e) {
        	e.printStackTrace();
        } 			
		return false;
	}

}
