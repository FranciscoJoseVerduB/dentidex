package com.controladoras;

import com.Main; 
import com.modelos.hibernate.Medicamento;

import javafx.beans.property.SimpleIntegerProperty; 
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections; 
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView; 
import javafx.scene.control.TableColumn; 

public class MedicamentosSeleccionController {
	Main main;  
	
	@FXML TableView<Medicamento> tvDatos;
	@FXML TextField txtBusqueda;
 
	
    FilteredList<Medicamento> filtro;
	Medicamento registroSeleccionado = null;
	boolean isOk = false;
	Stage stage;


	@FXML TableColumn<Medicamento, Integer> col_IdMedicamento;
	@FXML TableColumn<Medicamento, String> col_Farmaco;
	@FXML TableColumn<Medicamento, String> col_NombreComercial;
	@FXML TableColumn<Medicamento, String> col_Presentacion; 
	@FXML TableColumn<Medicamento, String> col_Indicacion;
	@FXML TableColumn<Medicamento, String> col_ViaAdministracion; 

	 

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
		col_IdMedicamento.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Farmaco.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getFarmaco()));
		col_NombreComercial.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNombreComercial()));
		col_Indicacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getIndicacion()));
		col_Presentacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPresentacion()));
		col_ViaAdministracion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getViaAdministracion()));
				  
		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getFarmaco().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getNombreComercial().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getIndicacion().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getPresentacion().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getViaAdministracion().toLowerCase().contains(nv.toLowerCase())
	        			)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	  
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<Medicamento>(FXCollections.observableArrayList(new Medicamento().getLista()));
        tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "medicamento"); 
        }
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	}

	public Medicamento getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
}
