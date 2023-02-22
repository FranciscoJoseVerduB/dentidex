package com.controladoras;
 
import java.time.LocalDate;

import com.Main;
import com.modelos.hibernate.Cita;
import com.modelos.hibernate.CitaHora;
import com.modelos.hibernate.Medico;
import com.modelos.hibernate.Paciente;

import javafx.beans.property.SimpleObjectProperty; 
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane; 

public class ReservarCitasShowController {
	Main main;


	@FXML TextField txtBusqueda;  
	@FXML Button btnNuevo;  
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Label lbId_Paciente;
	@FXML TextField txtTelefono;
	@FXML TextField txtDireccion;
	@FXML TextField txtNif;
	@FXML TextField txtNombre;
	@FXML Label lbId_Medico;
	@FXML DatePicker dtpFechaCita;
	@FXML TextField txtObservacionesCita;
	@FXML TableColumn<CitaHora, CitaHora> col_Hora;
	@FXML TableView<CitaHora> tvDatos;
	@FXML AnchorPane panelDatos;
	
	FilteredList<CitaHora> filtro;
    Cita citaActual = null; 
 

 
     
	public void setMain(Main main) {
		this.main = main;
	}
	 
	
	@FXML
	private void initialize() { 
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(this.txtObservacionesCita, 200);   

		this.citaActual = new Cita();
		this.btnNuevo.setDisable(false);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);
		this.panelDatos.setDisable(true);
		
		//Inicializamos los combobox 
		this.cargarListaDatos();	
		
		
		// Ininicializamos la tabla
		col_Hora.setCellValueFactory(cellData -> new SimpleObjectProperty<CitaHora>(cellData.getValue()));
		  
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) {  
            	this.citaActual.setHora(nv.getHora()); 
            } else { 
            	this.citaActual.setHora("");
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
    		this.tvDatos.getSelectionModel().clearSelection();
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getHora().toLowerCase().contains(nv.toLowerCase()))
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	 
	
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
       filtro = new FilteredList<CitaHora>(FXCollections.observableArrayList(new CitaHora().getLista()));
       tvDatos.setItems(filtro); 
	}
	
	
	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la persona
        if (this.citaActual.getMedico() == null || this.citaActual.getMedico().getId() == 0) 
        	errorMessage += "Debe seleccionar un médico.\n"; 
        if (this.citaActual.getPaciente() == null || this.citaActual.getPaciente().getId() == 0) 
        	errorMessage += "Debe seleccionar un paciente.\n";
        
        if(this.dtpFechaCita.getValue() == null)
        	errorMessage += "Debe indicar una fecha para la cita\n";
        
        if(this.tvDatos.getSelectionModel().getSelectedItem() == null)
        	errorMessage += "Debe indicar una hora para la cita \n";
         
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}
	 

	@FXML public void btnGuardarCambios() { 
		//Comprobamos que los datos son válidos.
		if(!isValido()) return;
		
		//Asociamos los datos a la entidad 
		this.citaActual.setFecha(com.util.Tiempo.convertirADate(this.dtpFechaCita.getValue()));
		this.citaActual.setObservaciones(this.txtObservacionesCita.getText());
		
		//Guardamos los datos de la cita y limpiamos los datos
		if(!citaActual.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		 
		com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La cita ha sido reservado exitosamente");
		this.limpiarDatos();
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}
 

	@FXML public void btnBuscarMedico() {
		Medico medico = com.util.DialogBox.seleccionarMedico(this.main);
		if(medico == null) return;
		//Asociamos el medico
		this.citaActual.setMedico(medico);
		if(this.citaActual.getMedico()!= null && this.citaActual.getMedico().getId() > 0) {
			this.lbId_Medico.setText(String.valueOf(this.citaActual.getMedico().getId())); 
		}
	}

	@FXML public void dtpFechaCita() {this.dtpFechaCita.show();}

	@FXML public void btnNuevo() { 
		this.limpiarDatos();  
		this.btnNuevo.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false);
		this.panelDatos.setDisable(false);
	}

	@FXML public void btnBuscarPaciente() {
		Paciente paciente = com.util.DialogBox.seleccionarPaciente(this.main);
		if(paciente == null) return;
		//Asociamos el medico
		this.citaActual.setPaciente(paciente);
		if(this.citaActual.getPaciente()!= null && this.citaActual.getPaciente().getId() > 0) { 
			this.lbId_Paciente.setText(String.valueOf(this.citaActual.getPaciente().getId()));
			this.txtNombre.setText(this.citaActual.getPaciente().toString());
			this.txtNif.setText(this.citaActual.getPaciente().getSujeto().getNif());
			this.txtDireccion.setText(this.citaActual.getPaciente().getSujeto().getDireccion().getDireccion());
			this.txtTelefono.setText(this.citaActual.getPaciente().getSujeto().getTelefono());
		}
	}


	private void limpiarDatos() { 
		this.citaActual = new Cita(); 
		this.panelDatos.setDisable(true);
		this.btnNuevo.setDisable(false);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true); 
		this.lbId_Paciente.setText(null);
		this.dtpFechaCita.setValue(LocalDate.now());
		this.lbId_Medico.setText(null);
		this.txtNif.setText(null);
		this.txtNombre.setText(null);
		this.txtDireccion.setText(null);
		this.txtTelefono.setText(null); 
	}
}
