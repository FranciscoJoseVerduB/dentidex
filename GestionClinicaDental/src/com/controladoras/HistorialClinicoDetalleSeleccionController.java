package com.controladoras;

import java.time.LocalDate;
import java.util.Date;

import com.Main; 
import com.modelos.hibernate.HistorialClinicoDetalle;
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

public class HistorialClinicoDetalleSeleccionController {
	Main main;  
	
	@FXML TableView<HistorialClinicoDetalle> 	tvDatos;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdHistorialClinicoDetalle;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdMedico; 
	@FXML TableColumn<HistorialClinicoDetalle, Paciente> col_Paciente;
	@FXML TableColumn<HistorialClinicoDetalle, Medico> 	col_Medico;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdPaciente;  
	@FXML TableColumn<HistorialClinicoDetalle, String> 	col_Telefono;
	@FXML TableColumn<HistorialClinicoDetalle, String>	col_Poblacion;
	@FXML TableColumn<HistorialClinicoDetalle, String>	col_Provincia; 
	@FXML TableColumn<HistorialClinicoDetalle, Date> 	col_FechaVisita;
	@FXML TableColumn<HistorialClinicoDetalle, String> 	col_ObservacionesVisita;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_NumeroTratamientos;
	
	@FXML TextField txtBusqueda;
    FilteredList<HistorialClinicoDetalle> filtro;
    HistorialClinicoDetalle registroSeleccionado = null;
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
		col_IdHistorialClinicoDetalle.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCita().getMedico().getId()).asObject());
		col_Medico.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>(cellData.getValue().getCita().getMedico()));
		col_IdPaciente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCita().getPaciente().getId()).asObject());
		col_Paciente.setCellValueFactory(cellData -> new SimpleObjectProperty<Paciente>(cellData.getValue().getCita().getPaciente()));
		col_FechaVisita.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha())); 
		col_ObservacionesVisita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCita().getPaciente().getSujeto().getTelefono()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCita().getPaciente().getSujeto().getDireccion().getPoblacion()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCita().getPaciente().getSujeto().getDireccion().getProvincia()));
		col_NumeroTratamientos.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHistorialClinicoDetalle_Tratamiento().size()).asObject());
		
		
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getCita().getPaciente().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getFecha().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getCita().getMedico().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getCita().getPaciente().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getCita().getMedico().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getCita().getMedico().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getCita().getPaciente().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
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
		filtro = new FilteredList<HistorialClinicoDetalle>(FXCollections.observableArrayList(new HistorialClinicoDetalle().getListaVisitasPendientesFacturar(com.util.Tiempo.convertirADate(fechaDesde), com.util.Tiempo.convertirADate(fechaHasta))));
        this.tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "visita"); 
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	} 

	public HistorialClinicoDetalle getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
 
}
