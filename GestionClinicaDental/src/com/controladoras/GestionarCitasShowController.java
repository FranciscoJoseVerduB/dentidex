package com.controladoras;
 
import java.time.LocalDate;
import java.util.Date;

import com.Main;
import com.modelos.hibernate.Cita; 
import com.modelos.hibernate.Enumerados.CitaEstado;  

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty; 
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType; 
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane; 

public class GestionarCitasShowController {
	Main main;
	
	enum TipoRecarga{PENDIENTE, ANULADO, EXPIRADO };
	TipoRecarga tipoRecargaActual;
	TableView<Cita> tableViewActual;
	
	Cita citaActual = null;
	@FXML TextField txtBusqueda; 
    FilteredList<Cita> filtro;
	@FXML AnchorPane panelDatos;
	@FXML DatePicker dtpFechaCitaDesde; 
	@FXML DatePicker dtpFechaCitaHasta;

	@FXML Button btnAnularCita;
	@FXML Button btnCambiarAPendiente;
	@FXML Button btnReprogramarCita;
	@FXML Button btnEliminarCita;
	@FXML TextField lbId_Cita;
	@FXML TextField lbFechaCita;
	@FXML TextField lbHoraCita;
	@FXML TextField lbDireccionCalle;
	@FXML TextField lbDireccionMunicipio;
	@FXML TextField txt_IdMedico;
	@FXML TextField lbTelefono;
	@FXML TextField txtMedicoNombre;
	@FXML TextField txt_IdPaciente;
	@FXML TextField txtPacienteNombre;
	
	@FXML TableView<Cita> 				tvCitasPendientes;
	@FXML TableColumn<Cita, Integer> 	colIdCita_Pendiente;
	@FXML TableColumn<Cita, Integer> 	colIdMedico_Pendiente;
	@FXML TableColumn<Cita, Integer> 	colIdPaciente_Pendiente;
	@FXML TableColumn<Cita, Date> 		colFechaCita_Pendiente;
	@FXML TableColumn<Cita, String> 	colHoraCita_Pendiente;
	@FXML TableColumn<Cita, String>  	colObservacionesCita_Pendiente;
	
	
	@FXML TableView<Cita> 				tvCitasAnuladas;
	@FXML TableColumn<Cita, Integer> 	colIdCita_Anulada;
	@FXML TableColumn<Cita, Integer> 	colIdPaciente_Anulada;
	@FXML TableColumn<Cita, Integer> 	colIdMedico_Anulada;
	@FXML TableColumn<Cita, Date> 		colFechaCita_Anulada;
	@FXML TableColumn<Cita, String> 	colHoraCita_Anulada;
	@FXML TableColumn<Cita, String>  	colObservacionesCita_Anulada;
	
	
	@FXML TableView<Cita> 				tvCitasExpiradas;
	@FXML TableColumn<Cita, Integer> 	colIdCita_Expirada;
	@FXML TableColumn<Cita, Integer> 	colIdPaciente_Expirada;
	@FXML TableColumn<Cita, Integer> 	colIdMedico_Expirada;
	@FXML TableColumn<Cita, Date> 		colFechaCita_Expirada;
	@FXML TableColumn<Cita, String> 	colHoraCita_Expirada;
	@FXML TableColumn<Cita, String>  	colObservacionesCita_Expirada;
	



     
	public void setMain(Main main) {
		this.main = main;
	}
	
	
	@FXML
	private void initialize() { 
		//Establecemos los valores por defecto 
		this.dtpFechaCitaDesde.setValue(LocalDate.now());
		this.dtpFechaCitaHasta.setValue(LocalDate.now());
		this.btnAnularCita.setDisable(true);
		this.btnCambiarAPendiente.setDisable(true);
		this.btnEliminarCita.setDisable(true);   
		this.btnReprogramarCita.setDisable(true); 
		
		//Inicializamos los combobox 
		this.cargarListaDatos(TipoRecarga.PENDIENTE);	   
		
		// Ininicializamos la tabla 		 
		colIdCita_Pendiente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		colIdMedico_Pendiente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedico().getId()).asObject());
		colIdPaciente_Pendiente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaciente().getId()).asObject());
		colFechaCita_Pendiente.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha()));
		colHoraCita_Pendiente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
		colObservacionesCita_Pendiente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));


		colIdCita_Anulada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		colIdPaciente_Anulada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedico().getId()).asObject());
		colIdMedico_Anulada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaciente().getId()).asObject());
		colFechaCita_Anulada.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha()));
		colHoraCita_Anulada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
		colObservacionesCita_Anulada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));


		colIdCita_Expirada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		colIdPaciente_Expirada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMedico().getId()).asObject());
		colIdMedico_Expirada.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaciente().getId()).asObject());
		colFechaCita_Expirada.setCellValueFactory(cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getFecha()));
		colHoraCita_Expirada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
		colObservacionesCita_Expirada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));
		
		  
        //Actualizamos los datos del jugador seleccionado
        this.tvCitasPendientes.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { this.changedItemCita(o, ov, nv); });
        this.tvCitasExpiradas.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { this.changedItemCita(o, ov, nv); });
        this.tvCitasAnuladas.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { this.changedItemCita(o, ov, nv); });
        
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> {  
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getHora().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getFecha().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getHora().toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getMedico().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getPaciente().getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getMedico().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
	        				String.valueOf(obj.getPaciente().getId()).toString().toLowerCase().contains(nv.toLowerCase()) ||
							String.valueOf(obj.getId()).toString().toLowerCase().contains(nv.toLowerCase())
	        			)
	        			return true;
	        		else return false; 
	        	}); 
        }); 
	} 
	 
	public void changedItemCita(ObservableValue<? extends Cita> o, Cita ov, Cita nv) { 
	    if (nv != null) {
	    	this.btnAnularCita.setDisable(false);
	    	this.btnEliminarCita.setDisable(false);
	    	this.btnReprogramarCita.setDisable(false);
	    	this.citaActual = nv;
	    	this.txt_IdMedico.setText(String.valueOf(nv.getMedico().getId()));
	    	this.txt_IdPaciente.setText(String.valueOf(nv.getPaciente().getId()));
	    	this.txtMedicoNombre.setText(nv.getMedico().getSujeto().getNombre());
	    	this.txtPacienteNombre.setText(nv.getPaciente().getSujeto().getNombre());
	    	this.lbId_Cita.setText(String.valueOf(nv.getId()));
	    	this.lbFechaCita.setText(nv.getFecha().toString());
	    	this.lbHoraCita.setText(nv.getHora());
	    	this.lbTelefono.setText(nv.getPaciente().getSujeto().getTelefono());
	    	this.lbDireccionCalle.setText(nv.getPaciente().getSujeto().getDireccion().getDireccion());
	    	this.lbDireccionMunicipio.setText(nv.getPaciente().getSujeto().getDireccion().getPoblacion());
	    	
	    	//Bloqueamos ambas opciones si la cita esta anulada 
	    	if(this.tipoRecargaActual == TipoRecarga.ANULADO) { 
	    		this.btnAnularCita.setDisable(true);
	    		this.btnReprogramarCita.setDisable(true);
	    		this.btnCambiarAPendiente.setDisable(false);
	    	}else { 
	    		this.btnAnularCita.setDisable(false);
	    		this.btnReprogramarCita.setDisable(false);
	    		this.btnCambiarAPendiente.setDisable(true);
	    	}
	    } else this.limpiarDatos(); 
	}
	
	
	
	private void limpiarDatos() { 
		this.citaActual = null; 
		this.btnAnularCita.setDisable(true);
		this.btnEliminarCita.setDisable(true);
		this.btnReprogramarCita.setDisable(true);
		this.btnCambiarAPendiente.setDisable(true);
		this.lbId_Cita.setText(null);
		this.lbFechaCita.setText(null);
		this.lbHoraCita.setText(null);
		this.lbDireccionCalle.setText(null);
		this.lbDireccionMunicipio.setText(null);
		this.txt_IdMedico.setText(null);
		this.lbTelefono.setText(null);
		this.txtMedicoNombre.setText(null);
		this.txt_IdPaciente.setText(null);
		this.txtPacienteNombre.setText(null); 
	}
	 
	
	private void cargarListaDatos(TipoRecarga tipoRecarga) { 
	    this.limpiarDatos();
	    if(this.dtpFechaCitaHasta.getValue() == null | this.dtpFechaCitaDesde.getValue() == null) return;	 //Si no tiene ningun valor establecido, se hara un return
		//Añadimos un filtro a la lista
	    switch(tipoRecarga) {
	    	case PENDIENTE:
	    		this.tableViewActual = this.tvCitasPendientes;
	            filtro = new FilteredList<Cita>(FXCollections.observableArrayList(new Cita().getListaCitasPendientes(com.util.Tiempo.convertirADate(this.dtpFechaCitaDesde.getValue()), com.util.Tiempo.convertirADate(this.dtpFechaCitaHasta.getValue()))));
	            this.tvCitasPendientes.setItems(filtro); 
	    	break;
	    	case EXPIRADO:  
	    		this.tableViewActual = this.tvCitasExpiradas;
	            filtro = new FilteredList<Cita>(FXCollections.observableArrayList(new Cita().getListaCitasExpiradas(com.util.Tiempo.convertirADate(this.dtpFechaCitaDesde.getValue()), com.util.Tiempo.convertirADate(this.dtpFechaCitaHasta.getValue()))));
	            this.tvCitasExpiradas.setItems(filtro); 
	    		break;
	    	case ANULADO: 
	    		this.tableViewActual = this.tvCitasAnuladas;
	            filtro = new FilteredList<Cita>(FXCollections.observableArrayList(new Cita().getListaCitasAnuladas(com.util.Tiempo.convertirADate(this.dtpFechaCitaDesde.getValue()), com.util.Tiempo.convertirADate(this.dtpFechaCitaHasta.getValue()))));
	            this.tvCitasAnuladas.setItems(filtro); 
	    		break;
	    	default:
	    		break;
	    }
	}
	
	 
	 
  

	@FXML public void btnEliminarCita() {
		int indice = this.tableViewActual.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!this.tableViewActual.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La Cita no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            this.tableViewActual.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos(tipoRecargaActual);
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "cita"); 
        }
	}


	@FXML public void btnReprogramarCita() {
		int indice = this.tableViewActual.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            //Verificamos que no este anulada la cita previamente
            if(this.tableViewActual.getSelectionModel().getSelectedItem().getCitaEstado() == CitaEstado.ANULADO) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La Cita ya está anulada. No se hará nada.");
            	return;
            }
            
            //Abrimos un formulario para reprogramar la cita y recargaremos los datos si ha guardado los datos
            if(com.util.DialogBox.reprogramarCita(main, citaActual)) this.cargarListaDatos(tipoRecargaActual);
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "cita"); 
        }
	}


	@FXML public void tabCitasExpiradas() {this.cargarListaDatos(tipoRecargaActual = TipoRecarga.EXPIRADO);}
	@FXML public void tabCitasAnuladas() {this.cargarListaDatos(tipoRecargaActual = TipoRecarga.ANULADO);}
	@FXML public void tabCitasPendientes() {this.cargarListaDatos(tipoRecargaActual = TipoRecarga.PENDIENTE);}


	@FXML public void btnAnularCita() {
		int indice = this.tableViewActual.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            //Verificamos que no este anulada la cita previamente
            if(this.tableViewActual.getSelectionModel().getSelectedItem().getCitaEstado() == CitaEstado.ANULADO) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La Cita ya está anulada. No se hará nada.");
            	return;
            }
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va a anular la cita seleccionada, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea anular el registro seleccionado
            
            if(!this.tableViewActual.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La Cita no se puede anular, tiene registros asociados");
            	return;
            }
            
            //Cambiamos el estado a anulado y guardamos el registro
            this.tableViewActual.getSelectionModel().getSelectedItem().setCitaEstado(CitaEstado.ANULADO);
            this.tableViewActual.getSelectionModel().getSelectedItem().guardar();
            this.cargarListaDatos(tipoRecargaActual);
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "cita"); 
        }
	}


	  void setCita(Cita cita) {
		this.citaActual = cita;
	}


	@FXML public void btnCambiarAPendiente() {
		int indice = this.tableViewActual.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            //Verificamos que no este anulada la cita previamente
            if(this.tableViewActual.getSelectionModel().getSelectedItem().getCitaEstado() == CitaEstado.PENDIENTE) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La Cita ya está pendiente. No se hará nada.");
            	return;
            }
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va a establecer en Pendiente la cita seleccionada, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea anular el registro seleccionado
             
            //Cambiamos el estado a anulado y guardamos el registro
            this.tableViewActual.getSelectionModel().getSelectedItem().setCitaEstado(CitaEstado.PENDIENTE);
            this.tableViewActual.getSelectionModel().getSelectedItem().guardar();
            this.cargarListaDatos(tipoRecargaActual);
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "cita"); 
        }
	}

 
}
