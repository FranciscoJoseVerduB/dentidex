package com;
 
import java.io.IOException;
import java.nio.file.Path; 

import com.conexion.ConexionBBDD;
import com.modelos.hibernate.Cita;
import com.modelos.hibernate.Factura;
import com.modelos.hibernate.HistorialClinicoDetalle;
import com.modelos.hibernate.Paciente;

import javafx.application.Application; 
import javafx.fxml.FXMLLoader; 
import javafx.scene.Scene;
import javafx.scene.image.Image; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Modality;
import javafx.stage.Stage; 
 
public class Main extends Application {

	private Stage stagePrincipal;
	private Scene scenePrincipal;
    private BorderPane formularioBase; 
    @SuppressWarnings("unused")
	private com.conexion.ConexionBBDD conn;
    private static String rutaGuardadoImagenes = Path.of("").toAbsolutePath().toString() + "\\Imagenes\\";
     
	public static void main(String[] args) {
		launch(args); 
	}

	@Override
	public void start(Stage stagePrincipal)   {
		 this.stagePrincipal = stagePrincipal; 
		 this.stagePrincipal.getIcons().add(new Image(Main.class.getResource("resources/icono2.jpeg").toExternalForm())); 
		 this.stagePrincipal.titleProperty().set("Software de Gestión de Clínica Dental");

		 //Inicializamos la conexion con base de datos
		 conn = new ConexionBBDD();
		 if(com.conexion.ConexionBBDD.getCon() == null) {
			 com.util.Alertas.alertaErrorInesperado("Ha ocurrido un error inesperado. No se ha podido conectar a la base de datos."); 
	         return;
		 }
		 
		 this.iniciarFormularioBase(); 
	}
	private void iniciarFormularioBase() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/FormularioBase.fxml")); 
	            formularioBase = (BorderPane) loader.load();

	            // Show the scene containing the root layout.
	            scenePrincipal = new Scene(getFormularioBase()); 
	            getStagePrincipal().setScene(getScenePrincipal()); 
	            getStagePrincipal().setMaximized(true);				//Maximizamos la ventana 
	            
	            com.controladoras.FormularioBaseController controller = loader.getController();
	            controller.setMain(this); 
	            this.iniciarFormularioInicio();			// Iniciamos el formulario Inicio
	            getStagePrincipal().show();
	        } catch (IOException e) {
	        	e.printStackTrace();
	     } 
	}
	
	public void iniciarFormularioInicio() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/FormularioInicio.fxml")); 
	            getFormularioBase().setCenter(loader.load()); 
	            com.controladoras.FormularioInicioController controller = loader.getController(); 
	            controller.setMain(this);  
	        } catch (IOException e) {
	        	e.printStackTrace();
	     } 	
	}
	
	
	public BorderPane getFormularioBase() {
		return formularioBase;
	} 
	public Stage getStagePrincipal() {
		return stagePrincipal;
	} 
	public Scene getScenePrincipal() {
		return scenePrincipal;
	} 

	public static String getRutaGuardadoImagenes() {
		return rutaGuardadoImagenes;
	}


	
	/**
	 * Insertamos el menu de pacientes en la pantalla
	 */
	public void iniciarFormularioPacientes() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/PacientesShow.fxml")); 
	            getFormularioBase().setCenter(loader.load());
	            com.controladoras.PacientesShowController controller = loader.getController(); 
	            controller.setMain(this);  
	        } catch (IOException e) {
	        	e.printStackTrace();
	     } 		
	}

	public void iniciarFormularioMedicos() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/MedicosShow.fxml")); 
	            getFormularioBase().setCenter(loader.load()); 
	            com.controladoras.MedicosShowController controller = loader.getController(); 
	            controller.setMain(this);  
	        } catch (IOException e) {
	        	e.printStackTrace();
	     } 		
	}
	
	public void iniciarFormularioTratamientos() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/TratamientosShow.fxml")); 
	            getFormularioBase().setCenter(loader.load()); 
	            com.controladoras.TratamientosShowController controller = loader.getController(); 
	            controller.setMain(this);  
	        } catch (IOException e) {
	        	e.printStackTrace();
	     } 		
	}

	public void iniciarFormularioTiposDeTratamiento() {
		 try {   
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/TiposTratamientoShow.fxml")); 
	            getFormularioBase().setCenter(loader.load()); 
	            com.controladoras.TiposTratamientoShowController controller = loader.getController(); 
	            controller.setMain(this);  
	        } catch (IOException e) {
	        	e.printStackTrace();
	     }
	}  
	
 
	public void iniciarFormularioMedicamentos() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/MedicamentosShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.MedicamentosShowController controller = loader.getController(); 
            controller.setMain(this);  
        } catch (IOException e) {
        	e.printStackTrace();
        } 		 
	}

	public void iniciarFormularioEspecialidades() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/EspecialidadesShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.EspecialidadesShowController controller = loader.getController(); 
            controller.setMain(this);  
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}

	public void iniciarFormularioArticulos() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/ArticulosShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.ArticulosShowController controller = loader.getController(); 
            controller.setMain(this);  
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}

	public void iniciarFormularioFamiliasArticulo() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/FamiliasArticuloShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.FamiliasArticuloShowController controller = loader.getController(); 
            controller.setMain(this);
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}

	public void iniciarFormularioProveedores() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/ProveedoresShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.ProveedoresShowController controller = loader.getController(); 
            controller.setMain(this);
        } catch (IOException e) {
        	e.printStackTrace();
        } 	
	}
	 

	public void iniciarFormularioHistorialClinico() {
		Paciente paciente = com.util.DialogBox.seleccionarPaciente(this);
		if(paciente != null) { 
			try {    
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/HistorialClinicoShow.fxml")); 
	            Stage stage = new Stage(); 
	            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
	            stage.titleProperty().set("Historial Clínico del Paciente: " + paciente.getSujeto().getNombreCompleto());
		        stage.setScene(new Scene(loader.load())); 
		        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
		        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
		        
	            com.controladoras.HistorialClinicoShowController controller = loader.getController(); 
	            controller.setMain(this);  
	            paciente.getHistorialClinico().cargar();
	            controller.setPaciente(paciente); 
	            controller.setStage(stage);
	            stage.showAndWait();    
	        } catch (IOException e) {
	        	e.printStackTrace();
	        } 	
		}
	}
 

	public void mostrarListaGruposSanguineo() {
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/GruposSanguineoListado.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Listado de Grupos Sanguíneos");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.GruposSanguineoListadoController controller = loader.getController(); 
	        controller.setMain(this);  
	        stage.showAndWait();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 
	}

	public void mostrarListaDientes() {
		try {
	        FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(Main.class.getResource("vistas/DientesListado.fxml")); 
	        Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/portafolio.png")));
   		 	stage.titleProperty().set("Listado de Dientes");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        com.controladoras.DientesListadoController controller = loader.getController(); 
	        controller.setMain(this);  
	        stage.showAndWait();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } 	
	}

	 
	public void iniciarFormularioReservarCitas() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/ReservarCitasShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.ReservarCitasShowController controller = loader.getController(); 
            controller.setMain(this);  
        } catch (IOException e) {
        	e.printStackTrace();
        } 		 		
	}
	public void iniciarFormularioGestionarCitas() {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/GestionarCitasShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.GestionarCitasShowController controller = loader.getController(); 
            controller.setMain(this);  
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}
	
	public void iniciarFormularioReprogramarCitas(Cita cita) {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/GestionarCitasReprogramacionShow.fxml")); 
            getFormularioBase().setCenter(loader.load()); 
            com.controladoras.GestionarCitasReprogramacionShowController controller = loader.getController(); 
            controller.setMain(this); 
            controller.setCita(cita);
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}

	public void iniciarFormularioCitasPendientes() {
		Cita citaPendiente = com.util.DialogBox.seleccionarCitaPendiente(this);
		if(citaPendiente != null) {
			iniciarFormularioHistorialClinicoDetalle(citaPendiente);
		}
	}
	
	public void iniciarFormularioHistorialClinicoDetalle(Cita cita) {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/HistorialClinicoDetalleShow.fxml")); 
            Stage stage = new Stage();  
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Tratamiento de la Cita en curso.");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        
            com.controladoras.HistorialClinicoDetalleShowController controller = loader.getController(); 
            controller.setMain(this); 
            controller.setStage(stage);
            //Obtenemos el historialClinicoDetalle (Visita de la cita)
            HistorialClinicoDetalle historialClinicoDetalle = new HistorialClinicoDetalle().cargar(cita);
            controller.setHistorialClinicoDetalle(historialClinicoDetalle); 
            stage.showAndWait();    
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}

	
	public void iniciarFormularioPresupuestoVisita(HistorialClinicoDetalle historialClinicoDetalle) { 
			try {    
	            FXMLLoader loader = new FXMLLoader(); 
	            loader.setLocation(Main.class.getResource("vistas/FacturasShow.fxml")); 
	            Stage stage = new Stage(); 
	            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
	   		 	stage.titleProperty().set("Selección de visitas para crear el presupuesto");
		        stage.setScene(new Scene(loader.load())); 
		        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
		        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
		        
	            com.controladoras.FacturasShowController controller = loader.getController(); 
	            controller.setMain(this); 
	            //Obtenemos el presupuesto a mostrar
	            Factura factura = new Factura().getPresupuesto(historialClinicoDetalle);
	            controller.setFactura(factura); 
	            controller.setStage(stage);
	            stage.showAndWait();    
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	}

	public void iniciarFormularioVisitasPendientesFacturar() {
		HistorialClinicoDetalle visitaPendiente = com.util.DialogBox.seleccionarHistorialClinicoDetallePendienteFacturar(this);
		if(visitaPendiente != null) {
			iniciarFormularioPresupuestoVisita(visitaPendiente);
		}
	}
	
	public void iniciarFormularioFactura(Factura factura) {
		try {    
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(Main.class.getResource("vistas/FacturasShow.fxml")); 
            Stage stage = new Stage(); 
            stage.getIcons().add(new Image(Main.class.getResource("resources/portafolio.png").toExternalForm()));
   		 	stage.titleProperty().set("Gestión de la factura en curso");
	        stage.setScene(new Scene(loader.load())); 
	        stage.initOwner(this.getStagePrincipal());		//Indicamos el propietario del formulario
	        stage.initModality(Modality.WINDOW_MODAL);		//Indicamos que la ventana será modal
	        
            com.controladoras.FacturasShowController controller = loader.getController(); 
            controller.setMain(this);  
            controller.setFactura(factura); 
            controller.setStage(stage);
            stage.showAndWait();    
        } catch (IOException e) {
        	e.printStackTrace();
        } 		
	}	

	public void iniciarFormularioSeleccionFacturas() {
		Factura factura = com.util.DialogBox.seleccionarFactura(this);
		if(factura != null) {
			iniciarFormularioFactura(factura);
		}
	}

	
}

 
