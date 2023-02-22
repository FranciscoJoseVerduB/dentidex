package com.controladoras;

import org.controlsfx.control.SearchableComboBox;

import com.Main; 
import com.modelos.hibernate.TipoTratamiento;
import com.modelos.hibernate.Tratamiento;
import com.util.Informes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty; 
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

public class TratamientosShowController {
	Main main;
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;
	@FXML TextField txtBusqueda;
	@FXML TextField txtNombre;
	@FXML TextField txtPrecio;
	@FXML Label lbId_Registro;
	@FXML SearchableComboBox<TipoTratamiento> cbTipoTratamiento = new SearchableComboBox<TipoTratamiento>();
	@FXML TableView<Tratamiento> tvDatos;  
	@FXML TableColumn<Tratamiento, Integer> col_IdTratamiento;
	@FXML TableColumn<Tratamiento, Tratamiento> col_Nombre;
	@FXML TableColumn<Tratamiento, Double> col_Precio;
	@FXML TableColumn<Tratamiento, TipoTratamiento> col_TipoTratamiento;
	

    FilteredList<Tratamiento> filtro;
    Tratamiento tratamientoActual;
	@FXML GridPane dtDatos;
	
	 
	
	public void setMain(Main main) {
		this.main = main;		
	}
	

	@FXML
	private void initialize() { 
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(txtNombre, 30);
		com.util.Eventos.setLimiteCaracteres(txtPrecio, 6);
		com.util.Eventos.setMascaraDouble(txtPrecio);
		
		
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.dtDatos.setDisable(true);
		
		
		//Inicializamos los combobox
		this.cbTipoTratamiento.getItems().add(new TipoTratamiento());
		this.cbTipoTratamiento.getItems().addAll(new TipoTratamiento().getLista());
		this.cargarListaDatos();	  
		
		  
		// Ininicializamos la tabla
		col_IdTratamiento.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Tratamiento>( cellData.getValue()));
		col_Precio.setCellValueFactory(cellData -> new SimpleDoubleProperty( cellData.getValue().getPrecio()).asObject());
		col_TipoTratamiento.setCellValueFactory(cellData -> new SimpleObjectProperty<TipoTratamiento>( cellData.getValue().getTipoTratamiento()));
		 
		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.tratamientoActual = nv;
            	this.dtDatos.setDisable(true);
        		this.btnNuevo.setDisable(false);
        		this.btnBorrar.setDisable(false);
        		this.btnModificar.setDisable(false);
        		this.btnGuardarCambios.setDisable(true);
        		this.btnCancelarCambios.setDisable(true); 
        		
            	this.lbId_Registro.setText(String.valueOf(nv.getId()));
            	this.txtNombre.setText(nv.getNombre());
        		this.txtPrecio.setText(nv.getPrecio().toString()); 
        		this.cbTipoTratamiento.setValue(nv.getTipoTratamiento());
            } else { 
            	this.limpiarDatos();
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getTipoTratamiento().getNombre().toLowerCase().contains(nv.toLowerCase())
	        			)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	} 
	
	
	
	

	@FXML public void btnNuevo() {
		this.limpiarDatos();
		this.tratamientoActual = new Tratamiento();
		this.btnNuevo.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.dtDatos.setDisable(false);
	}
 

	@FXML public void btnModificar() {
		this.btnNuevo.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.dtDatos.setDisable(false);
	}

	@FXML public void btnGuardarCambios() {
		//Comprobamos que los datos son válidos.
		if(!isValido()) return; 
		
		//Mapeamos el objecto y lo guardamos en base de datos
		Tratamiento p = this.tratamientoActual;  
		

		//Comprobamos que no haya ningún medico más con el NIF indicado
		//Solo puede haber un único medico con el mismo NIF
		if(p.existeConMismoNombre(this.txtNombre.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Tratamiento con ese Nombre."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		p.setNombre(this.txtNombre.getText());
		if(this.cbTipoTratamiento.getValue() != null) p.setTipoTratamiento(this.cbTipoTratamiento.getValue());  
		p.setPrecio(Double.parseDouble(this.txtPrecio.getText()));
		
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El tratamiento se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El tratamiento se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!tvDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El Tratamiento no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            tvDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "tratamiento"); 
        }
	}

	private void cargarListaDatos() {
		//Añadimos un filtro a la lista
        filtro = new FilteredList<Tratamiento>(FXCollections.observableArrayList(new Tratamiento().getLista()));
        tvDatos.setItems(filtro);  
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {
		Informes.mostrarListadoTratamientos(main);
	}
	
	private void limpiarDatos() { 
		this.tratamientoActual = null;
		this.tvDatos.getSelectionModel().clearSelection();
		this.btnNuevo.setDisable(false);
		this.btnBorrar.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true); 
		this.dtDatos.setDisable(true);
		
		this.lbId_Registro.setText("000000"); 
		this.txtNombre.setText(null);
		this.txtPrecio.setText(null); 
		this.cbTipoTratamiento.setValue(null); 
	}

	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la persona
        if (txtNombre.getText() == null || txtNombre.getText().length() <3) 
        	errorMessage += "El campo Nombre debe tener 3 o más carácteres.\n"; 
        if (txtPrecio.getText() == null || txtPrecio.getText().length() == 0) 
        	errorMessage += "Debe indicar un precio.\n"; 
        
        if(this.cbTipoTratamiento.getValue() == null || this.cbTipoTratamiento.getSelectionModel().getSelectedIndex() == 0)
        	errorMessage += "Debe indicar un tipo de tratamiento\n";   
        
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}
}
