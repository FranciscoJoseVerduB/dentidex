package com.controladoras;

import com.Main;
import com.modelos.hibernate.Medicamento;
import com.util.Informes;
 
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label; 
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

public class MedicamentosShowController {
	Main main;
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;
	@FXML TextField txtBusqueda; 
	@FXML Label lbId_Registro; 
	@FXML TableView<Medicamento> dtDatos; 

    FilteredList<Medicamento> filtro;
    Medicamento medicamentoActual;
	@FXML GridPane gridPane;
	@FXML TextField txtFarmaco;
	@FXML TextField txtNombreComercial;
	@FXML TextField txtPresentacion;
	@FXML TextField txtViaAdministracion;
	@FXML TextField txtIndicacion;
	@FXML TableColumn<Medicamento, Integer> col_IdMedicamento;
	@FXML TableColumn<Medicamento, String> col_Farmaco;
	@FXML TableColumn<Medicamento, String> col_NombreComercial;
	@FXML TableColumn<Medicamento, String> col_Presentacion; 
	@FXML TableColumn<Medicamento, String> col_Indicacion;
	@FXML TableColumn<Medicamento, String> col_ViaAdministracion; 
    
	public void setMain(Main main) {
		this.main = main;		
	}


	@FXML
	private void initialize() { 
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(txtFarmaco, 200); 
		com.util.Eventos.setLimiteCaracteres(txtNombreComercial, 200);
		com.util.Eventos.setLimiteCaracteres(txtIndicacion, 300);
		com.util.Eventos.setLimiteCaracteres(txtPresentacion, 500);
		com.util.Eventos.setLimiteCaracteres(txtViaAdministracion, 500);
		
		
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.gridPane.setDisable(true);
		 
		this.cargarListaDatos();	  
		
		  
		// Ininicializamos la tabla
		col_IdMedicamento.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Farmaco.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getFarmaco()));
		col_NombreComercial.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNombreComercial()));
		col_Indicacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getIndicacion()));
		col_Presentacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPresentacion()));
		col_ViaAdministracion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getViaAdministracion()));
		  
		 
        //Actualizamos los datos del jugador seleccionado
        dtDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.medicamentoActual = nv;
            	this.gridPane.setDisable(true);
        		this.btnNuevo.setDisable(false);
        		this.btnBorrar.setDisable(false);
        		this.btnModificar.setDisable(false);
        		this.btnGuardarCambios.setDisable(true);
        		this.btnCancelarCambios.setDisable(true); 
        		
            	this.lbId_Registro.setText(String.valueOf(nv.getId()));
            	this.txtFarmaco.setText(nv.getFarmaco());
            	this.txtNombreComercial.setText(nv.getNombreComercial());
            	this.txtNombreComercial.setText(nv.getNombreComercial());
            	this.txtIndicacion.setText(nv.getIndicacion());
            	this.txtPresentacion.setText(nv.getPresentacion());
            	this.txtViaAdministracion.setText(nv.getViaAdministracion());
            } else { 
            	this.limpiarDatos();
            }
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
	
	
	
	

	@FXML public void btnNuevo() {
		this.limpiarDatos();
		this.medicamentoActual = new Medicamento();
		this.btnNuevo.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.gridPane.setDisable(false);
	}
 

	@FXML public void btnModificar() {
		this.btnNuevo.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.gridPane.setDisable(false);
	}

	@FXML public void btnGuardarCambios() {
		//Comprobamos que los datos son válidos.
		if(!isValido()) return; 
		
		//Mapeamos el objecto y lo guardamos en base de datos
		Medicamento p = this.medicamentoActual;  
		
 
		p.setFarmaco(this.txtFarmaco.getText().trim());
		p.setNombreComercial(this.txtNombreComercial.getText().trim());
		p.setPresentacion(this.txtPresentacion.getText().trim());
		p.setIndicacion(this.txtIndicacion.getText().trim());
		p.setViaAdministracion(this.txtViaAdministracion.getText().trim());
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El medicamento se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El medicamento se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = dtDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!dtDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El medicamento no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            dtDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "medicamento"); 
        }
	}

	private void cargarListaDatos() {
		//Añadimos un filtro a la lista
        filtro = new FilteredList<Medicamento>(FXCollections.observableArrayList(new Medicamento().getLista()));
        dtDatos.setItems(filtro);  
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {Informes.mostrarListadoMedicamentos(main);}
	
	private void limpiarDatos() { 
		this.medicamentoActual = null;
		this.dtDatos.getSelectionModel().clearSelection();
		this.btnNuevo.setDisable(false);
		this.btnBorrar.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true); 
		this.gridPane.setDisable(true);
		
		this.lbId_Registro.setText("000000"); 
		this.txtFarmaco.setText(null);
		this.txtNombreComercial.setText(null);
		this.txtIndicacion.setText(null);
		this.txtPresentacion.setText(null);
		this.txtViaAdministracion.setText(null);
	}

	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la persona
        if (txtFarmaco.getText() == null || txtFarmaco.getText().length() <3) 
        	errorMessage += "El campo Fármaco debe tener 3 o más carácteres.\n";  
        if (txtNombreComercial.getText() == null || txtNombreComercial.getText().length() <3) 
        	errorMessage += "El campo Nombre Comercial debe tener 3 o más carácteres.\n";
        if (txtIndicacion.getText() == null || txtIndicacion.getText().length() <3) 
        	errorMessage += "El campo Indicacion debe tener 3 o más carácteres.\n";
        if (txtPresentacion.getText() == null || txtPresentacion.getText().length() <3) 
        	errorMessage += "El campo Presentación debe tener 3 o más carácteres.\n";
        if (txtViaAdministracion.getText() == null || txtViaAdministracion.getText().length() <3) 
        	errorMessage += "El campo Via Administración debe tener 3 o más carácteres.\n";
        
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}
}
