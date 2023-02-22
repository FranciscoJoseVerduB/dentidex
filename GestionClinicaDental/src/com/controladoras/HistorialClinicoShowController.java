package com.controladoras;

import java.util.ArrayList;

import com.Main;
import com.modelos.hibernate.Factura; 
import com.modelos.hibernate.HistorialClinicoDetalle;
import com.modelos.hibernate.Medico;
import com.modelos.hibernate.Paciente;
import com.util.Informes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button; 
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage; 

public class HistorialClinicoShowController {
	Main main;
	private Paciente paciente;
	@SuppressWarnings("unused")
	private Stage stage;
	

	FilteredList<HistorialClinicoDetalle> filtro;
	ArrayList<HistorialClinicoDetalle> listaHistorialClinicoDetalle;
	@FXML TextField txtNombre;
	@FXML TextField txtNif;
	@FXML TextField txtTelefono;
	@FXML Label lbId_Paciente;
	@FXML TextField txtFechaNacimiento;
	@FXML TextField txtGrupoSanguineo;
	@FXML TextField txtGenero;
	@FXML TextField txtCalle;
	@FXML TextField txtPoblacion;
	@FXML TextField txtCodigoPostal;
	@FXML TextField txtProvincia;
	@FXML TextField txtPais;
	@FXML TextArea txtEnfermedadesFamiliares;
	@FXML TextArea txtAncetedentesPatologicos;
	@FXML TextArea txtAlergias;
	@FXML Button btnGuardarHistorialClinico;
	@FXML Button btnVerVisitaSeleccionada;
	@FXML Button btnVerFacturaSeleccionada;
	
	@FXML TableView<HistorialClinicoDetalle> tvHistorialClinicoDetalle;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdVisita;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdCita;
	@FXML TableColumn<HistorialClinicoDetalle, String> col_FechaCita;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdMedico;
	@FXML TableColumn<HistorialClinicoDetalle, Medico> col_Medico;
	@FXML TableColumn<HistorialClinicoDetalle, Integer> col_IdFactura;
	@FXML TableColumn<HistorialClinicoDetalle, Factura> col_Factura;
	@FXML TableColumn<HistorialClinicoDetalle, Double> col_ImporteFactura;
	@FXML TextField txtBusqueda;
	@FXML Button btnVerInformeHistorialClinico;
	

	
 
	public void setMain(Main main) {
		this.main = main;		
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
		this.cargarPaciente(paciente);
	}
  
	 

	@FXML
	private void initialize() { 
		try {
			//Establecemos el limite de caracteres
			com.util.Eventos.setLimiteCaracteres(this.txtEnfermedadesFamiliares, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtAncetedentesPatologicos, 300);
			com.util.Eventos.setLimiteCaracteres(this.txtAlergias, 300); 
			
			// Ininicializamos la tabla de tratamientos 
			col_IdVisita.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			col_IdCita.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCita().getId()).asObject());
			col_FechaCita.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCita().getFecha().toString()));
			col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCita().getMedico().getId()).asObject());
			col_Medico.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>(cellData.getValue().getCita().getMedico()));
			col_IdFactura.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue() == null || cellData.getValue().getFactura() == null? 0 : cellData.getValue().getFactura().getId()).asObject());
			col_Factura.setCellValueFactory(cellData -> new SimpleObjectProperty<Factura>(cellData.getValue() == null? null :  cellData.getValue().getFactura()));
			col_ImporteFactura.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue() == null || cellData.getValue().getFactura() == null? 0 : cellData.getValue().getFactura().getImporteFactura()).asObject());
		 
			 
	        //Configuramos el filtro de la tabla
	        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
		        	filtro.setPredicate(obj -> { 
		        		if (obj.getFecha().toString().toLowerCase().contains(nv.toLowerCase()) ||
		        				obj.getCita().getMedico().toString().toLowerCase().contains(nv.toLowerCase()))
		        			return true;
		        		else return false; 
		        	}); 
	        });  
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}          
	}
	  
 
 
	
	private void cargarPaciente(Paciente paciente) { 
		//Establecemos los datos de la cita
		if(this.paciente != null) { 
			this.lbId_Paciente.textProperty().setValue(String.valueOf(paciente.getId()));
			this.txtNombre.textProperty().setValue(paciente.getSujeto().getNombreCompleto());
			this.txtNif.textProperty().setValue(paciente.getSujeto().getNif()); 
			this.txtTelefono.textProperty().setValue(paciente.getSujeto().getTelefono());
			this.txtFechaNacimiento.textProperty().setValue(String.valueOf(paciente.getSujeto().getFechaNacimiento())); 
			this.txtGrupoSanguineo.textProperty().setValue(paciente.getGrupoSanguineo().toString());
			this.txtGenero.textProperty().setValue(paciente.getGenero().toString()); 
			this.txtCalle.textProperty().setValue(paciente.getSujeto().getDireccion().getDireccion());
			this.txtCodigoPostal.textProperty().setValue(paciente.getSujeto().getDireccion().getCodigoPostal());
			this.txtProvincia.textProperty().setValue(paciente.getSujeto().getDireccion().getProvincia());
			this.txtPoblacion.textProperty().setValue(paciente.getSujeto().getDireccion().getPoblacion());
			this.txtPais.textProperty().setValue(paciente.getSujeto().getDireccion().getPais());
			this.txtAncetedentesPatologicos.textProperty().setValue(paciente.getHistorialClinico().getAntecedentesPatologicos());
			this.txtAlergias.textProperty().setValue(paciente.getHistorialClinico().getAlergias());
			this.txtEnfermedadesFamiliares.textProperty().setValue(paciente.getHistorialClinico().getEnfermedadesFamiliares());
		}
		else {
			this.lbId_Paciente.textProperty().setValue(null);
			this.txtNombre.textProperty().setValue(null);
			this.txtNif.textProperty().setValue(null); 
			this.txtTelefono.textProperty().setValue(null);
			this.txtFechaNacimiento.textProperty().setValue(null); 
			this.txtGrupoSanguineo.textProperty().setValue(null);
			this.txtGenero.textProperty().setValue(null); 
			this.txtCalle.textProperty().setValue(null);
			this.txtCodigoPostal.textProperty().setValue(null);
			this.txtProvincia.textProperty().setValue(null);
			this.txtPoblacion.textProperty().setValue(null);
			this.txtPais.textProperty().setValue(null);
			this.txtAncetedentesPatologicos.textProperty().setValue(null);
			this.txtAlergias.textProperty().setValue(null);
			this.txtEnfermedadesFamiliares.textProperty().setValue(null);
		}
		
		
		filtro = new FilteredList<HistorialClinicoDetalle>(FXCollections.observableArrayList(paciente.getHistorialClinico().getHistorialClinicoDetalle()));
		this.tvHistorialClinicoDetalle.setItems(filtro);   
		 
	}
 
	 
 

	@FXML public void btnGuardarHistorialClinico() { 
		//Parametrizamos los datos
		this.paciente.getHistorialClinico().setAlergias(this.txtAlergias.textProperty().getValue());
		this.paciente.getHistorialClinico().setAntecedentesPatologicos(this.txtAncetedentesPatologicos.textProperty().getValue());
		this.paciente.getHistorialClinico().setEnfermedadesFamiliares(this.txtEnfermedadesFamiliares.textProperty().getValue());
		
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!this.paciente.getHistorialClinico().guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		
		//Guardamos el estado del historial clinico en los objetos del detalle
		for (HistorialClinicoDetalle hcd : paciente.getHistorialClinico().getHistorialClinicoDetalle())
			hcd.setHistorialClinico(this.paciente.getHistorialClinico());
		
		
		com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El historial clínico se ha modificado exitosamente.");  
	}

	@FXML public void btnVerVisitaSeleccionada() {
		int indice = this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {  
             this.main.iniciarFormularioHistorialClinicoDetalle(this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedItem().getCita());
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "registro"); 
        }
	}

	@FXML public void btnVerFacturaSeleccionada() {
		int indice = this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) {  
        	
        	//Verificamos si la visita actual está facturada
    		if(this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedItem().getFactura() == null || 
    				this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedItem().getFactura().getId() == 0) {
    			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La visita seleccionada no tiene ninguna factura asociada"); 
    	        return;
    		}; 
             this.main.iniciarFormularioFactura(this.tvHistorialClinicoDetalle.getSelectionModel().getSelectedItem().getFactura());
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "registro"); 
        }
	}

	@FXML public void btnVerInformeHistorialClinico() {
		Informes.mostrarInformeHistorialClinico(main, paciente.getHistorialClinico());
	}


 
 
 
}
