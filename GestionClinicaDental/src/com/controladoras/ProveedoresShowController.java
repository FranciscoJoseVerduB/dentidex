package com.controladoras;

import com.Main; 
import com.modelos.hibernate.Proveedor;
import com.util.Informes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

public class ProveedoresShowController {
	Main main;
	

	@FXML TabPane tabPaneDatos; 
	@FXML TextField txtCalle;
	@FXML TextField txtPoblacion;
	@FXML TextField txtCodigoPostal;
	@FXML TextField txtProvincia;
	@FXML TextField txtPais;
	@FXML ImageView imgFotografia;  
	@FXML TextField txtTelefono;
	@FXML Label lbFechaCreacion;
	@FXML Label lbId_Registro;
	@FXML DatePicker dtpFechaNacimiento;
	@FXML TextField txtEmail;
	@FXML TextField txtNif;
	@FXML TextField txtApellidos;
	@FXML TextField txtNombre;
	@FXML TextField txtPersonaContacto;
	@FXML TextField txtPaginaWeb;
	
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;


	@FXML TableView<Proveedor> tvDatos;
	@FXML TextField txtBusqueda;
	FilteredList<Proveedor> filtro;


	@FXML TableColumn<Proveedor, Integer> col_IdProveedor;
	@FXML TableColumn<Proveedor, Proveedor> col_Nombre;
	@FXML TableColumn<Proveedor, String> col_Nif;
	@FXML TableColumn<Proveedor, String> col_FechaNacimiento;
	@FXML TableColumn<Proveedor, String> col_Email;
	@FXML TableColumn<Proveedor, String> col_Telefono;
	@FXML TableColumn<Proveedor, String> col_PersonaContacto;
	@FXML TableColumn<Proveedor, String> col_PaginaWeb;
	@FXML TableColumn<Proveedor, String> col_Direccion;
	@FXML TableColumn<Proveedor, String> col_Poblacion;
	@FXML TableColumn<Proveedor, String> col_CP;
	@FXML TableColumn<Proveedor, String> col_Provincia;
	@FXML TableColumn<Proveedor, String> col_Pais;  
	
	private Proveedor proveedorActual;

	public void setMain(Main main) {
		this.main = main;		
	}
	
	@FXML
	private void initialize() {
		//Establecemos el limite de caracteres de los textfields
		com.util.Eventos.setLimiteCaracteres(txtNombre, 100);
		com.util.Eventos.setLimiteCaracteres(txtApellidos, 50);
		com.util.Eventos.setLimiteCaracteres(txtNif, 20);
		com.util.Eventos.setLimiteCaracteres(txtEmail, 100);
		com.util.Eventos.setLimiteCaracteres(txtTelefono, 30);
		com.util.Eventos.setLimiteCaracteres(txtCalle, 50);
		com.util.Eventos.setLimiteCaracteres(txtPoblacion, 20);
		com.util.Eventos.setLimiteCaracteres(txtCodigoPostal, 5);
		com.util.Eventos.setLimiteCaracteres(txtProvincia, 20);
		com.util.Eventos.setLimiteCaracteres(txtPais, 20);
		com.util.Eventos.setLimiteCaracteres(txtPersonaContacto, 20);
		com.util.Eventos.setLimiteCaracteres(txtPaginaWeb, 20); 
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.tabPaneDatos.setDisable(true);
		  
		this.cargarListaDatos();
		
		
		// Ininicializamos la tabla
		col_IdProveedor.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Proveedor>( cellData.getValue()));
		col_Nif.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getNif()));
		col_FechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getFechaNacimiento().toString()));
		col_Email.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getEmail()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getTelefono()));
		col_PersonaContacto.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPersonaContacto()));
		col_PaginaWeb.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getPaginaWeb())); 
		col_Direccion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getDireccion()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPoblacion()));
		col_CP.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getCodigoPostal()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getProvincia()));
		col_Pais.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPais()));
		 
		
		
		//Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.proveedorActual = nv;
            	this.tabPaneDatos.setDisable(true);
        		this.btnNuevo.setDisable(false);
        		this.btnBorrar.setDisable(false);
        		this.btnModificar.setDisable(false);
        		this.btnGuardarCambios.setDisable(true);
        		this.btnCancelarCambios.setDisable(true); 
        		
            	this.lbId_Registro.setText(String.valueOf(nv.getId()));
            	this.lbFechaCreacion.setText(nv.getSujeto().getFechacreacion().toString());
        		this.dtpFechaNacimiento.setValue(com.util.Tiempo.convertirALocalDateMilisegundos(nv.getSujeto().getFechaNacimiento())); 
        		this.txtNombre.setText(nv.getSujeto().getNombre());
        		this.txtApellidos.setText(nv.getSujeto().getApellidos());
        		this.txtNif.setText(nv.getSujeto().getNif());
        		this.txtEmail.setText(nv.getSujeto().getEmail());
        		this.txtTelefono.setText(nv.getSujeto().getTelefono()); 
        		if(nv.getSujeto().getFoto() == null || nv.getSujeto().getFoto().isEmpty()) {
        			this.imgFotografia.setImage(null);
	        		this.imgFotografia.setUserData(null);
        		}else {
	        		try {
	        			//Si el fichero existe, se cargará la imagen
	        			if(nv.getSujeto().getFoto() != null && new File(nv.getSujeto().getFoto()).exists()) {
							this.imgFotografia.setImage(new Image(new FileInputStream(nv.getSujeto().getFoto()))); 
			        		this.imgFotografia.setUserData(nv.getSujeto().getFoto());
		        		}else {
							this.imgFotografia.setImage(null); 
			        		this.imgFotografia.setUserData(null); 
		        		}
					} catch (FileNotFoundException e) {
						this.imgFotografia.setImage(null); 
		        		this.imgFotografia.setUserData(null);
						e.printStackTrace();
					}
        		}
        		this.txtPaginaWeb.setText(nv.getPaginaWeb());
        		this.txtPersonaContacto.setText(nv.getPersonaContacto());
        		this.txtCalle.setText(nv.getSujeto().getDireccion().getDireccion());
        		this.txtPoblacion.setText(nv.getSujeto().getDireccion().getPoblacion());
        		this.txtProvincia.setText(nv.getSujeto().getDireccion().getProvincia());
        		this.txtCodigoPostal.setText(nv.getSujeto().getDireccion().getCodigoPostal());
        		this.txtPais.setText(nv.getSujeto().getDireccion().getPais());  
            } else { 
            	this.limpiarDatos();
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getSujeto().getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getApellidos().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getNif().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getTelefono().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getEmail().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getDireccion().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getPoblacion().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getPais().toLowerCase().contains(nv.toLowerCase())	||
	        				obj.getSujeto().getDireccion().getProvincia().toLowerCase().contains(nv.toLowerCase())
	        				
	        				)
	        			return true;
	        		else return false; 
	        	}); 
        });  
		
	}
	
	

	private void limpiarDatos() {
		this.proveedorActual = null;
		this.tvDatos.getSelectionModel().clearSelection();
		this.btnNuevo.setDisable(false);
		this.btnBorrar.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true); 
		this.tabPaneDatos.setDisable(true);
		
		this.lbId_Registro.setText("000000");
		this.dtpFechaNacimiento.setValue(null);
		this.lbFechaCreacion.setText(null);
		this.txtNombre.setText(null);
		this.txtApellidos.setText(null);
		this.txtNif.setText(null);
		this.txtEmail.setText(null);
		this.txtTelefono.setText(null);
		this.imgFotografia.setImage(null);
		this.imgFotografia.setUserData(null);
		this.txtPersonaContacto.setText(null);
		this.txtPaginaWeb.setText(null);
		this.txtCalle.setText(null);
		this.txtPoblacion.setText(null);
		this.txtProvincia.setText(null);
		this.txtCodigoPostal.setText(null);
		this.txtPais.setText(null); 
	}

	private void cargarListaDatos() {
		 // Add observable list data to the table 
        filtro = new FilteredList<Proveedor>(FXCollections.observableArrayList(new Proveedor().getLista()));
        tvDatos.setItems(filtro);  
	}
	

	private boolean isValido() { 
        String errorMessage = "";
  
	    //Datos de la persona
        if (txtNombre.getText() == null || txtNombre.getText().length() <3) 
        	errorMessage += "El campo Nombre debe tener 3 o más carácteres.\n"; 
        if (txtApellidos.getText() == null || txtApellidos.getText().length() < 3) 
        	errorMessage += "El campo Apellidos debe tener 3 o más carácteres.\n"; 
        if (txtNif.getText() == null || txtNif.getText().length() < 9)
        	errorMessage += "El campo Nif debe tener 9 o más carácteres.\n";              
        if (txtTelefono.getText() == null || txtTelefono.getText().length() < 9) 
            errorMessage += "El campo Teléfono debe tener 9 o más carácteres.\n";  
        if (txtEmail.getText() == null || txtEmail.getText().length() == 0) 
            errorMessage += "Debe escribir un email\n";   
        
         
        if(this.dtpFechaNacimiento.getValue() == null)
        	errorMessage += "Debe indicar una fecha de nacimiento\n";
        
        //Datos de la direccion
        if (txtCodigoPostal.getText() == null || txtCodigoPostal.getText().length() < 5) 
            errorMessage += "El campo Código Postal debe tener 5 carácteres\n";         
        if (txtCalle.getText() == null || txtCalle.getText().length() == 0) 
            errorMessage += "Debe indicar una calle\n"; 
        if (txtPoblacion.getText() == null || txtPoblacion.getText().length() == 0) 
            errorMessage += "Debe indicar una población\n"; 
        if (txtProvincia.getText() == null || txtProvincia.getText().length() == 0) 
            errorMessage += "Debe indicar una provincia\n"; 
        if (txtPais.getText() == null || txtPais.getText().length() == 0) 
            errorMessage += "Debe indicar un país\n"; 
           
        
        if (errorMessage.length() == 0) return true;
        else {
            // Mostramos el mensaje de error
            com.util.Alertas.alertaDatosInvalidos(this.main.getStagePrincipal(), errorMessage);
            return false;
        } 
	}


	@FXML public void btnBuscarImagen() {
		//Buscamos una imagen y la cargamos
		ImageView imageView = com.util.Imagenes.buscarImagen(this.main.getStagePrincipal());
		if(imageView != null) {
			this.imgFotografia.setImage(imageView.getImage());
			this.imgFotografia.setUserData(imageView.getUserData());
		}
	}

	@FXML public void btnNuevo() {
		this.limpiarDatos();
		this.proveedorActual = new Proveedor();
		this.btnNuevo.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.tabPaneDatos.setDisable(false);
	}

	@FXML public void btnModificar() {
		this.btnNuevo.setDisable(true);
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(false);
		this.btnCancelarCambios.setDisable(false); 
		this.tabPaneDatos.setDisable(false);
	}

	@FXML public void btnGuardarCambios() {
		//Comprobamos que los datos son válidos.
		if(!isValido()) return; 
		
		//Mapeamos la entidad y lo guardamos en base de datos
		Proveedor p = this.proveedorActual;  //Asignamos la entidad actual en caso de que sea el medico seleccionado por el usuario
		

		//Comprobamos que no haya ninguna entidad más con el NIF indicado
		//Solo puede haber un unica entidad con el mismo NIF
		if(p.existeSujetoConNif(this.txtNif.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Proveedor con ese Nif."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		p.setPaginaWeb(this.txtPaginaWeb.getText());
		p.setPersonaContacto(this.txtPersonaContacto.getText());
		 
		p.getSujeto().getDireccion().setDireccion(this.txtCalle.getText());
		p.getSujeto().getDireccion().setCodigoPostal(this.txtCodigoPostal.getText());
		p.getSujeto().getDireccion().setPoblacion(this.txtPoblacion.getText());
		p.getSujeto().getDireccion().setProvincia(this.txtProvincia.getText());
		p.getSujeto().getDireccion().setPais(this.txtPais.getText()); 
		p.getSujeto().setNombre(this.txtNombre.getText());
		p.getSujeto().setApellidos(this.txtApellidos.getText());
		p.getSujeto().setNif(this.txtNif.getText()); 
		p.getSujeto().setEmail(this.txtEmail.getText());
		p.getSujeto().setTelefono(this.txtTelefono.getText());
		//Si la foto no es NULL, obtenemos la ruta de la imagen
		if(this.imgFotografia.getImage() != null) p.getSujeto().setFoto(this.imgFotografia.getUserData().toString()); 
		p.getSujeto().setFechaNacimiento(java.sql.Date.valueOf(this.dtpFechaNacimiento.getValue()));  
 		
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El proveedor se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El proveedor se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!tvDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El Proveedor no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            tvDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "proveedor"); 
        }
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {Informes.mostrarListadoProveedores(main);}
 

}
