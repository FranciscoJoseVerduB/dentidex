package com.controladoras;

import com.Main;
import com.modelos.hibernate.Diente;   
import javafx.beans.property.SimpleIntegerProperty; 
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML; 
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField; 

public class DientesListadoController {
	Main main; 
 
	@FXML TableColumn<Diente, Integer> col_IdDiente;
	@FXML TableColumn<Diente, String> col_Codigo; 
	@FXML TableColumn<Diente, String> col_Cuadrante;
	@FXML TableColumn<Diente, String> col_Nombre;
	@FXML TableColumn<Diente, String> col_TipoDenticion; 
	
	@FXML TableView<Diente> tvDatos;
	FilteredList<Diente> filtro;

	@FXML TextField txtBusqueda;

    
	public void setMain(Main main) {
		this.main = main;		
	} 

	@FXML
	private void initialize() { 
		this.cargarListaDatos();	  
		 
		// Ininicializamos la tabla
		col_IdDiente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
		col_Cuadrante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		col_Nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		col_TipoDenticion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getTipoDenticion()));
		
		 //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
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

 
}
