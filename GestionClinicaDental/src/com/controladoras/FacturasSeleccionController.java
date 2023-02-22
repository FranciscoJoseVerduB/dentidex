package com.controladoras;

import java.util.Date;

import com.Main; 
import com.modelos.hibernate.Factura; 
import com.modelos.hibernate.Medico;
import com.modelos.hibernate.Paciente;

import javafx.beans.property.SimpleDoubleProperty;
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

public class FacturasSeleccionController {
	Main main;  
	
	@FXML TableView<Factura> 	tvDatos;
	@FXML TableColumn<Factura, Integer> 	col_IdFactura; 
	@FXML TableColumn<Factura, String> 		col_Serie;
	@FXML TableColumn<Factura, Integer> 	col_Numero;
	@FXML TableColumn<Factura, Date> 		col_FechaFactura;
	@FXML TableColumn<Factura, Integer> 	col_IdPaciente;
	@FXML TableColumn<Factura, Paciente> 	col_Paciente;
	@FXML TableColumn<Factura, Integer> 	col_IdMedico;
	@FXML TableColumn<Factura, Medico>		col_Medico;
	@FXML TableColumn<Factura, Double> 		col_BaseImponible;
	@FXML TableColumn<Factura, Double> 		col_Importe;
	@FXML TableColumn<Factura, Double> 		col_Cobrado;
	@FXML TableColumn<Factura, Double> 		col_PorcentajeIva;
	@FXML TableColumn<Factura, Double> 		col_PorcentajeDescuento;
	@FXML TableColumn<Factura, String> 		col_Telefono;
	@FXML TableColumn<Factura, String> 		col_Poblacion;
	@FXML TableColumn<Factura, String> 		col_Provincia;
	@FXML TableColumn<Factura, String> 		col_ObservacionesFactura;
	
	@FXML TextField txtBusqueda;
    FilteredList<Factura> filtro;
    Factura registroSeleccionado = null;
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
		col_IdFactura.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Serie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSerie()));
		col_Numero.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());
		col_FechaFactura.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha()));
		col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedico().getId()).asObject());
		col_Medico.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>(cellData.getValue().getMedico()));
		col_IdPaciente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaciente().getId()).asObject());
		col_Paciente.setCellValueFactory(cellData -> new SimpleObjectProperty<Paciente>(cellData.getValue().getPaciente()));
		 
		col_ObservacionesFactura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getTelefono()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getDireccion().getPoblacion()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaciente().getSujeto().getDireccion().getProvincia()));
		col_BaseImponible.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBaseImponible()).asObject());
		col_Importe.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getImporteFactura()).asObject());
		col_Cobrado.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalImporteCobrado()).asObject());
		col_PorcentajeIva.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentajeIva()).asObject());
		col_PorcentajeDescuento.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPorcentajeDescuento()).asObject());
		
		  
		
		
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) this.registroSeleccionado = nv;
            else this.registroSeleccionado = null;
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
        	tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getPaciente().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getPaciente().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getFecha().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getMedico().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getMedico().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSerie().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getNumero()).toLowerCase().contains(nv.toLowerCase()) || 
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
		filtro = new FilteredList<Factura>(FXCollections.observableArrayList(new Factura().getLista()));
        this.tvDatos.setItems(filtro); 
	}
  
	@FXML public void btnOk() {
		int indice = this.tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {   
        	//Se ha seleccionado un registro
            this.isOk = true; 
            this.stage.hide();
        } else com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "factura"); 
	}


	@FXML public void btnCancelar() {
		this.isOk = false;
		this.stage.hide();
	}

	public boolean isOk() {
		return isOk;
	} 

	public Factura getRegistroSeleccionado() {
		return registroSeleccionado;
	}
 
 
}
