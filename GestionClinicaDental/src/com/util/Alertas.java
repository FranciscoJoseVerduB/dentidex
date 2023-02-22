package com.util;
 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Alertas { 
 
	public static void alertaDatosInvalidos(Stage stage, String mensajeError) {
		Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage); 
        alert.setContentText(mensajeError);
        alert.titleProperty().setValue("Datos incompletos.");
        alert.headerTextProperty().setValue("Datos no válidos"); 
        alert.showAndWait(); 
	}
	
	public static void alertaDatosErroneos(Stage stage, String mensajeError) {
		Alert alert = new Alert(AlertType.ERROR); 
        alert.initOwner(stage); 
        alert.setContentText(mensajeError);
        alert.titleProperty().setValue("Datos erróneos.");
        alert.headerTextProperty().setValue("Ha ocurrido un error."); 
        alert.showAndWait(); 
	}
	
	public static void alertaDatosCorrectos(Stage stage, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(stage); 
        alert.setContentText(mensaje);
        alert.titleProperty().setValue("Datos correctos.");
        alert.headerTextProperty().setValue("Ha sucedido de manera exitosa."); 
        alert.showAndWait(); 
	}
	
	public static void alertaNadaSeleccionado(Stage stage, String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(stage);  
        alert.titleProperty().setValue("Nada seleccionado");
        alert.headerTextProperty().setValue("No ha seleccionado un " +  mensaje);
        alert.contentTextProperty().setValue("Por favor, selecciona un " + mensaje); 
        alert.showAndWait(); 
	}
	
	
	public static void alertaListaDatosDuplicados(Stage stage, String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(stage);  
        alert.titleProperty().setValue("Elemento duplicado");
        alert.headerTextProperty().setValue("Está intentando añadir un " +  mensaje + " de nuevo");
        alert.contentTextProperty().setValue("Por favor, selecciona otro " + mensaje); 
        alert.showAndWait(); 
	}
	
	
	public static ButtonType alertaConfirmacionUsuario(Stage stage, String mensaje) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(stage); 
        alert.titleProperty().setValue("Alerta");
        alert.headerTextProperty().setValue("Debe indicar una opción.");
        alert.contentTextProperty().setValue(mensaje); 
        alert.showAndWait();
        return alert.getResult();
	}
	
	public static void alertaErrorInesperado(String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeight(400);
		alert.setWidth(400);
        alert.titleProperty().setValue("Error inesperado");
        alert.headerTextProperty().setValue("Error inesperado");
        alert.contentTextProperty().setValue(mensaje); 
        alert.showAndWait();
	}

}
