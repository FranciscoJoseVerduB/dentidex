package com.controladoras;

import com.Main;
import com.modelos.hibernate.Diente; 

import javafx.beans.property.SimpleIntegerProperty; 
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections; 
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView; 
import javafx.scene.control.TableColumn; 

public class DientesSeleccionController {
	Main main;  
	
	@FXML TableView<Diente> tvDatos;
	@FXML TextField txtBusqueda;
 
	
    FilteredList<Diente> filtro;
	Diente registroSeleccionado = null;
	boolean isOk = false;
	Stage stage;

	@FXML TableColumn<Diente, Integer> col_IdDiente;
	@FXML TableColumn<Diente, String> col_Codigo;
	@FXML TableColumn<Diente, String> col_Cuadrante;
	@FXML TableColumn<Diente, String> col_Nombre;
	@FXML TableColumn<Diente, String> col_TipoDenticion;

	 

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
		col_IdDiente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Codigo.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getCodigo()));
		col_Cuadrante.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getCuadrante()));
		col_Nombre.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNombre()));
		col_TipoDenticion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getTipoDenticion()));
	 
		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getCodigo().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getCuadrante().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getTipoDenticion().toLowerCase().contains(nv.toLowerCase())
	        			)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	  
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<Diente>(FXCollections.observableArrayList(new Diente().getLista()));
        tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "diente"); 
        }
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	}

	public Diente getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
}
