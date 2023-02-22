package com.controladoras;

import com.Main; 
import com.modelos.hibernate.FamiliaArticulo;
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

public class FamiliasArticuloShowController {
	Main main;
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;
	@FXML TextField txtBusqueda;
	@FXML TextField txtNombre; 
	@FXML Label lbId_Registro; 
	@FXML TableView<FamiliaArticulo> dtDatos; 
	@FXML TableColumn<FamiliaArticulo, Integer> col_IdFamiliaArticulo;
	@FXML TableColumn<FamiliaArticulo, String> col_Nombre; 	
	@FXML TableColumn<FamiliaArticulo, String> col_Codigo;
	
    FilteredList<FamiliaArticulo> filtro;
    FamiliaArticulo familiaArticuloActual;
	@FXML GridPane gridPane;
	@FXML TextField txtCodigo; 
    
	public void setMain(Main main) {
		this.main = main;		
	}


	@FXML
	private void initialize() { 
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(txtCodigo, 20);
		com.util.Eventos.setLimiteCaracteres(txtNombre, 50); 
		
		
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.gridPane.setDisable(true);
		 
		this.cargarListaDatos();	  
		
		  
		// Ininicializamos la tabla
		col_IdFamiliaArticulo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Codigo.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getCodigo()));
		col_Nombre.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNombre()));
		  
		 
        //Actualizamos los datos del jugador seleccionado
        dtDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.familiaArticuloActual = nv;
            	this.gridPane.setDisable(true);
        		this.btnNuevo.setDisable(false);
        		this.btnBorrar.setDisable(false);
        		this.btnModificar.setDisable(false);
        		this.btnGuardarCambios.setDisable(true);
        		this.btnCancelarCambios.setDisable(true); 
        		
            	this.lbId_Registro.setText(String.valueOf(nv.getId()));
            	this.txtCodigo.setText(nv.getCodigo()); 
            	this.txtNombre.setText(nv.getNombre()); 
            } else { 
            	this.limpiarDatos();
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getNombre().toLowerCase().contains(nv.toLowerCase()))
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	 

	@FXML public void btnNuevo() {
		this.limpiarDatos();
		this.familiaArticuloActual = new FamiliaArticulo();
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
		FamiliaArticulo p = this.familiaArticuloActual;  
		

		//Comprobamos que no haya códigos duplicados
		if(p.existeConMismoCodigo(this.txtCodigo.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe una Famiilia con ese Código."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		p.setCodigo(this.txtCodigo.getText());  
		p.setNombre(this.txtNombre.getText());  
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La famiila se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "La familia se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = dtDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!dtDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "La familia no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            dtDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "familia"); 
        }
	}

	private void cargarListaDatos() {
		//Añadimos un filtro a la lista
        filtro = new FilteredList<FamiliaArticulo>(FXCollections.observableArrayList(new FamiliaArticulo().getLista()));
        dtDatos.setItems(filtro);  
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {Informes.mostrarListadoFamiliasArticulo(main);}
	
	private void limpiarDatos() { 
		this.familiaArticuloActual = null;
		this.dtDatos.getSelectionModel().clearSelection();
		this.btnNuevo.setDisable(false);
		this.btnBorrar.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true); 
		this.gridPane.setDisable(true);
		
		this.lbId_Registro.setText("000000");
		this.txtCodigo.setText(null); 
		this.txtNombre.setText(null); 
	}

	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la entidad
        if (txtCodigo.getText() == null || txtCodigo.getText().length() <5) 
        	errorMessage += "El campo Nombre debe tener 5 o más carácteres.\n";  
        if (txtNombre.getText() == null || txtNombre.getText().length() <3) 
        	errorMessage += "El campo Nombre debe tener 3 o más carácteres.\n";  
        
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}
}
