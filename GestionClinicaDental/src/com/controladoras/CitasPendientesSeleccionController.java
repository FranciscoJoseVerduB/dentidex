package com.controladoras;

import java.time.LocalDate;
import java.util.Date;

import com.Main;
import com.modelos.hibernate.Cita;
import com.modelos.hibernate.Medico;
import com.modelos.hibernate.Paciente;

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

public class CitasPendientesSeleccionController {
	Main main;  
	
	@FXML TableView<Cita> 				tvDatos;
	@FXML TableColumn<Cita, Integer> 	col_IdCita;
	@FXML TableColumn<Cita, Integer> 	col_IdMedico; 
	@FXML TableColumn<Cita, Paciente> 	col_Paciente;
	@FXML TableColumn<Cita, Medico> 	col_Medico;
	@FXML TableColumn<Cita, Integer> 	col_IdPaciente;
	@FXML TableColumn<Cita, Date> 		col_FechaCita;
	@FXML TableColumn<Cita, String> 	col_HoraCita;
	@FXML TableColumn<Cita, String> 	col_Telefono;
	@FXML TableColumn<Cita, String>	 	col_Poblacion;
	@FXML TableColumn<Cita, String>	 	col_Provincia;
	@FXML TableColumn<Cita, String>  	col_ObservacionesCita;
	@FXML TextField txtBusqueda;
    FilteredList<Cita> filtro;
	Cita registroSeleccionado = null;
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
		col_IdCita.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedico().getId()).asObject());
		col_Medico.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>(cellData.getValue().getMedico()));
		col_IdPaciente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaciente().getId()).asObject());
		col_Paciente.setCellValueFactory(cellData -> new SimpleObjectProperty<Paciente>(cellData.getValue().getPaciente()));
		col_FechaCita.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha()));
		col_HoraCita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
		col_ObservacionesCita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getTelefono()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getDireccion().getPoblacion()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getDireccion().getProvincia()));
 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getHora().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getFecha().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getHora().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getPaciente().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getMedico().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getMedico().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getPaciente().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
							String.valueOf(obj.getId()).toString().toLowerCase().contains(nv.toLowerCase())
	        			)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	  
	/**
	 * Buscamos Desde el dia actual hasta 7 dias en el futuro
	 */
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
		LocalDate fechaDesde = LocalDate.now();
		LocalDate fechaHasta = (LocalDate.now()).plusDays(7);
		filtro = new FilteredList<Cita>(FXCollections.observableArrayList(new Cita().getListaCitasPendientes(com.util.Tiempo.convertirADate(fechaDesde), com.util.Tiempo.convertirADate(fechaHasta))));
        this.tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "cita"); 
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	} 

	public Cita getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
 
}
