package com.controladoras;

import com.Main;
import com.modelos.hibernate.CitaHora; 

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty; 
import javafx.collections.FXCollections; 
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView; 
import javafx.scene.control.TableColumn; 

public class CitasHoraSeleccionController {
	Main main;  
	
	@FXML TableView<CitaHora> tvDatos;
	@FXML TextField txtBusqueda;

	@FXML TableColumn<CitaHora, Integer> col_IdCitaHora;
	@FXML TableColumn<CitaHora, CitaHora> col_Hora; 
	
    FilteredList<CitaHora> filtro;
	CitaHora registroSeleccionado = null;
	boolean isOk = false;
	Stage stage;

	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	 
	
	@FXML
	private void initialize() {  
		this.cargarListaDatos();	  
		 
		// Ininicializamos la tabla
		col_IdCitaHora.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Hora.setCellValueFactory(cellData -> new SimpleObjectProperty<CitaHora>( cellData.getValue()));
		
		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getHora().toLowerCase().contains(nv.toLowerCase()))
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	  
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<CitaHora>(FXCollections.observableArrayList(new CitaHora().getLista()));
        tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "hora"); 
        }
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
	}

	public boolean isOk() {
		return isOk;
	} 
 
	public CitaHora getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
}
