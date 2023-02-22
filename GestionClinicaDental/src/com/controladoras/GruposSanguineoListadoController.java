package com.controladoras;

import com.Main; 
import com.modelos.hibernate.GrupoSanguineo; 
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML; 
import javafx.scene.control.TextField; 
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn; 


public class GruposSanguineoListadoController {
	Main main; 
 
	@FXML TableColumn<GrupoSanguineo, String> col_Codigo;
	@FXML TableColumn<GrupoSanguineo, String> col_PuedeDonarA;
	@FXML TableColumn<GrupoSanguineo, String> col_PuedeRecibirDe;

	@FXML TableView<GrupoSanguineo> tvDatos;
	FilteredList<GrupoSanguineo> filtro;

	@FXML TextField txtBusqueda;
 
    
	public void setMain(Main main) {
		this.main = main;		
	} 

	@FXML
	private void initialize() { 
		this.cargarListaDatos();	  
		 
		// Ininicializamos la tabla
		col_Codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
		col_PuedeDonarA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPuedeDonarA()));
		col_PuedeRecibirDe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPuedeRecibirDe()));
		

		 //Configuramos el filtro de la tabla
       this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getCodigo().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getPuedeDonarA().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getPuedeRecibirDe().toLowerCase().contains(nv.toLowerCase()) 
	        				)
	        			return true;
	        		else return false; 
	        	}); 
       });  
	} 
	
	
	 

	private void cargarListaDatos() {  
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<GrupoSanguineo>(FXCollections.observableArrayList(new GrupoSanguineo().getLista()));
        tvDatos.setItems(filtro);     
	}

 
}
