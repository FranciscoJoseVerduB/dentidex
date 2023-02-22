package com.controladoras;

import com.Main; 
import com.modelos.hibernate.Proveedor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections; 
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView; 
import javafx.scene.control.TableColumn; 

public class ProveedoresSeleccionController {
	Main main;  
	

	@FXML TableView<Proveedor> tvDatos;
	@FXML TextField txtBusqueda;
	FilteredList<Proveedor> filtro;


	@FXML TableColumn<Proveedor, Integer> col_IdProveedor;
	@FXML TableColumn<Proveedor, Proveedor> col_Nombre;
	@FXML TableColumn<Proveedor, String> col_Nif;
	@FXML TableColumn<Proveedor, String> col_FechaNacimiento;
	@FXML TableColumn<Proveedor, String> col_Email;
	@FXML TableColumn<Proveedor, String> col_Telefono;
	@FXML TableColumn<Proveedor, String> col_PersonaContacto;
	@FXML TableColumn<Proveedor, String> col_PaginaWeb;
	@FXML TableColumn<Proveedor, String> col_Direccion;
	@FXML TableColumn<Proveedor, String> col_Poblacion;
	@FXML TableColumn<Proveedor, String> col_CP;
	@FXML TableColumn<Proveedor, String> col_Provincia;
	@FXML TableColumn<Proveedor, String> col_Pais;  
	
	
	 
	Proveedor registroSeleccionado = null;
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
		col_IdProveedor.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Proveedor>( cellData.getValue()));
		col_Nif.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getNif()));
		col_FechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getFechaNacimiento().toString()));
		col_Email.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getEmail()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getTelefono()));
		col_PersonaContacto.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPersonaContacto()));
		col_PaginaWeb.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPaginaWeb())); 
		col_Direccion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getDireccion()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPoblacion()));
		col_CP.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getCodigoPostal()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getProvincia()));
		col_Pais.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPais()));
		 
		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getSujeto().getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getApellidos().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getNif().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getTelefono().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getEmail().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getDireccion().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getPoblacion().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getPais().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getProvincia().toLowerCase().contains(nv.toLowerCase())
	        				
	        				)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	  
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<Proveedor>(FXCollections.observableArrayList(new Proveedor().getLista()));
        tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "proveedor"); 
        }
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	}

 
	public Proveedor getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	@FXML public void onMouseClicked() {
		//this.btnOk();
	}
 
}
