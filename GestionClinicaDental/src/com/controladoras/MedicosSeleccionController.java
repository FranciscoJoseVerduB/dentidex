package com.controladoras;

import com.Main;
import com.modelos.hibernate.Especialidad; 
import com.modelos.hibernate.Medico; 
import com.modelos.hibernate.Usuario;

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

public class MedicosSeleccionController {
	Main main;  
	
	@FXML TableView<Medico> tvDatos;
	@FXML TextField txtBusqueda;

	@FXML TableColumn<Medico, Integer> col_IdMedico;
	@FXML TableColumn<Medico, Medico> col_Nombre;
	@FXML TableColumn<Medico, String> col_Nif;
	@FXML TableColumn<Medico, String> col_FechaNacimiento;
	@FXML TableColumn<Medico, String> col_Email;
	@FXML TableColumn<Medico, String> col_Telefono;
	@FXML TableColumn<Medico, String> col_NumeroColegiado;
	@FXML TableColumn<Medico, Especialidad> col_Especialidad;
	@FXML TableColumn<Medico, String> col_Direccion;
	@FXML TableColumn<Medico, String> col_Poblacion;
	@FXML TableColumn<Medico, String> col_CP;
	@FXML TableColumn<Medico, String> col_Provincia;
	@FXML TableColumn<Medico, String> col_Pais; 
	@FXML TableColumn<Medico, Usuario> col_Usuario;
	
    FilteredList<Medico> filtro;
	Medico registroSeleccionado = null;
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
		col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>( cellData.getValue()));
		col_Nif.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getNif()));
		col_FechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getFechaNacimiento().toString()));
		col_Email.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getEmail()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getTelefono()));
		col_NumeroColegiado.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNumeroColegiado()));
		col_Especialidad.setCellValueFactory(cellData -> new SimpleObjectProperty<Especialidad>( cellData.getValue().getEspecialidad())); 
		col_Direccion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getDireccion()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPoblacion()));
		col_CP.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getCodigoPostal()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getProvincia()));
		col_Pais.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPais()));
		col_Usuario.setCellValueFactory(cellData -> new SimpleObjectProperty<Usuario>( cellData.getValue().getSujeto().getUsuario()));
		
		 
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
	        				obj.getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getNif().toLowerCase().contains(nv.toLowerCase()) ||
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
        filtro = new FilteredList<Medico>(FXCollections.observableArrayList(new Medico().getLista()));
        tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "médico"); 
        }
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	}

 
	public Medico getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	@FXML public void onMouseClicked() {
		//this.btnOk();
	}
 
}
