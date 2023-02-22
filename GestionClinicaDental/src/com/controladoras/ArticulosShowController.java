package com.controladoras;

import com.Main;
import com.modelos.hibernate.Articulo;
import com.modelos.hibernate.FamiliaArticulo; 
import com.modelos.hibernate.Medicamento;
import com.util.Informes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.controlsfx.control.SearchableComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

public class ArticulosShowController {
	Main main;
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;
	@FXML TextField txtBusqueda; 
	@FXML Label lbId_Registro; 
	@FXML TableView<Articulo> dtDatos; 

    FilteredList<Articulo> filtro;
    Articulo articuloActual;
	@FXML GridPane gridPane;   
	@FXML TextField txtCodigo;
	@FXML TextField txtNombre;
	@FXML TextField txtPrecioCompra;
	@FXML TextField txtCantidadEnExistencias;
	@FXML SearchableComboBox<FamiliaArticulo> cbFamiliaArticulo = new SearchableComboBox<FamiliaArticulo>();;
	@FXML SearchableComboBox<Medicamento> cbMedicamento = new SearchableComboBox<Medicamento>(); 
	
	@FXML TableColumn<Articulo, Integer> col_IdArticulo;
	@FXML TableColumn<Articulo, String> col_Codigo;
	@FXML TableColumn<Articulo, String> col_Nombre;
	@FXML TableColumn<Articulo, Double> col_PrecioCompra;
	@FXML TableColumn<Articulo, Integer> col_CantidadExistencias;
	@FXML TableColumn<Articulo, FamiliaArticulo> col_FamiliaArticulo;
	@FXML TableColumn<Articulo, Medicamento> col_Medicamento;
    
	public void setMain(Main main) {
		this.main = main;		
	}


	@FXML
	private void initialize() { 
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(txtCodigo, 50); 
		com.util.Eventos.setLimiteCaracteres(txtNombre, 50);
		com.util.Eventos.setLimiteCaracteres(txtPrecioCompra, 6);
		com.util.Eventos.setMascaraDouble(txtPrecioCompra);
		com.util.Eventos.setLimiteCaracteres(txtCantidadEnExistencias, 5);
		com.util.Eventos.setMascaraInteger(txtCantidadEnExistencias); 

		//Inicializamos los combobox
		this.cbFamiliaArticulo.getItems().add(new FamiliaArticulo());
		this.cbFamiliaArticulo.getItems().addAll(new FamiliaArticulo().getLista()); 
		this.cbMedicamento.getItems().add(new Medicamento());
		this.cbMedicamento.getItems().addAll(new Medicamento().getLista()); 
		
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.gridPane.setDisable(true);
		 
		this.cargarListaDatos();	  
		
		  
		// Ininicializamos la tabla
		col_IdArticulo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
		col_Nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
		col_PrecioCompra.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecioCompra()).asObject());
		col_CantidadExistencias.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCantidadExistencia()).asObject());
		col_FamiliaArticulo.setCellValueFactory(cellData -> new SimpleObjectProperty<FamiliaArticulo>(cellData.getValue().getFamiliaArticulo()));
		col_Medicamento.setCellValueFactory(cellData -> new SimpleObjectProperty<Medicamento>(cellData.getValue().getMedicamento()));
		  
		 
        //Actualizamos los datos del jugador seleccionado
        dtDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.articuloActual = nv;
            	this.gridPane.setDisable(true);
        		this.btnNuevo.setDisable(false);
        		this.btnBorrar.setDisable(false);
        		this.btnModificar.setDisable(false);
        		this.btnGuardarCambios.setDisable(true);
        		this.btnCancelarCambios.setDisable(true); 
        		
            	this.lbId_Registro.setText(String.valueOf(nv.getId()));
            	this.txtCodigo.setText(nv.getCodigo());
            	this.txtNombre.setText(nv.getNombre());
            	this.txtCantidadEnExistencias.setText(nv.getCantidadExistencia().toString());
            	this.txtPrecioCompra.setText(nv.getPrecioCompra().toString()); 
            	this.cbFamiliaArticulo.setValue(nv.getFamiliaArticulo());
            	this.cbMedicamento.setValue(nv.getMedicamento());
            } else { 
            	this.limpiarDatos();
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getCodigo().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getFamiliaArticulo().toString().toLowerCase().contains(nv.toLowerCase())
	        				)
	        			return true;
	        		else return false; 
	        	}); 
        });  
	}   
	

	@FXML public void btnNuevo() {
		this.limpiarDatos();
		this.articuloActual = new Articulo();
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
		Articulo p = this.articuloActual;  
		
		//Comprobamos que no haya ningún registro con el mismo código
		if(p.existeConMismoCodigo(this.txtCodigo.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Articulo con ese Código"); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		//Mapeamos el objeto
		p.setCodigo(this.txtCodigo.getText().trim());
		p.setNombre(this.txtNombre.getText().trim());
		p.setCantidadExistencia(Integer.parseInt(this.txtCantidadEnExistencias.getText()));
		p.setPrecioCompra(Double.parseDouble(this.txtPrecioCompra.getText()));
		p.setFamiliaArticulo(this.cbFamiliaArticulo.getValue());
		p.setMedicamento(this.cbMedicamento.getValue());
		 
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El articulo se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El articulo se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = dtDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!dtDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El articulo no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            dtDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "articulo"); 
        }
	}

	private void cargarListaDatos() {
		//Añadimos un filtro a la lista
        filtro = new FilteredList<Articulo>(FXCollections.observableArrayList(new Articulo().getLista()));
        dtDatos.setItems(filtro);  
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {Informes.mostrarListadoArticulos(main);}
	
	private void limpiarDatos() { 
		this.articuloActual = null;
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
    	this.txtCantidadEnExistencias.setText(null);
    	this.txtPrecioCompra.setText(null); 
    	this.cbFamiliaArticulo.setValue(null);
    	this.cbMedicamento.setValue(null); 
	}

	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la persona
        if (txtCodigo.getText() == null || txtCodigo.getText().length() <5) 
        	errorMessage += "El campo Código debe tener 5 o más carácteres.\n";  
        if (txtNombre.getText() == null || txtNombre.getText().length() <3) 
        	errorMessage += "El campo Nombre debe tener 3 o más carácteres.\n";
        if (txtPrecioCompra.getText() == null || txtPrecioCompra.getText().length() == 0) 
        	errorMessage += "Debe rellenar el precio de Compra.\n";
        if (txtCantidadEnExistencias.getText() == null || txtCantidadEnExistencias.getText().length() == 0) 
        	errorMessage += "Debe rellenar la cantidad en existencias del artículo.\n";
        
        if(this.cbFamiliaArticulo.getValue() == null || this.cbFamiliaArticulo.getSelectionModel().getSelectedIndex() == 0) 
        	errorMessage += "Debe indicar una familia de artículo para el artículo actual.\n";
        
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}
}
