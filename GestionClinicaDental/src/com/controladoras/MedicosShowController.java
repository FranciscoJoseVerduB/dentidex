package com.controladoras;

import com.Main;
import com.modelos.hibernate.Especialidad; 
import com.modelos.hibernate.Medico; 
import com.modelos.hibernate.Usuario;
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

import org.controlsfx.control.SearchableComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

public class MedicosShowController {
	Main main;
	

	@FXML TabPane tabPaneDatos;
	@FXML TextField txtUsuarioCodigo;
	@FXML TextField txtUsuarioContrasenia;
	@FXML TextField txtCalle;
	@FXML TextField txtPoblacion;
	@FXML TextField txtCodigoPostal;
	@FXML TextField txtProvincia;
	@FXML TextField txtPais;
	@FXML ImageView imgFotografia;
	@FXML SearchableComboBox<Especialidad> cbEspecialidad =  new SearchableComboBox<Especialidad>();
	@FXML TextField txtNumeroColegiado;
	@FXML TextField txtTelefono;
	@FXML Label lbFechaCreacion;
	@FXML Label lbId_Registro;
	@FXML DatePicker dtpFechaNacimiento;
	@FXML TextField txtEmail;
	@FXML TextField txtNif;
	@FXML TextField txtApellidos;
	@FXML TextField txtNombre;
	
	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;


	@FXML TableView<Medico> tvDatos;
	@FXML TextField txtBusqueda;
	FilteredList<Medico> filtro;


	@FXML TableColumn<Medico, Integer> col_IdMedico;
	@FXML TableColumn<Medico, Medico> col_Nombre;
	@FXML TableColumn<Medico, String> col_Nif;
	@FXML TableColumn<Medico, String> col_FechaNacimiento;
	@FXML TableColumn<Medico, String> col_Email;
	@FXML TableColumn<Medico, String> col_Telefono;
	@FXML TableColumn<Medico, String> col_NumeroColegiado;
	@FXML TableColumn<Medico, Especialidad> col_Especialidad;
	@FXML TableColumn<Medico, String> col_Direccion;
	@FXML TableColumn<Medico, String> col_Poblacion;
	@FXML TableColumn<Medico, String> col_CP;
	@FXML TableColumn<Medico, String> col_Provincia;
	@FXML TableColumn<Medico, String> col_Pais; 
	@FXML TableColumn<Medico, Usuario> col_Usuario;
	
	private Medico medicoActual;

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
		com.util.Eventos.setLimiteCaracteres(txtNumeroColegiado, 20);
		com.util.Eventos.setLimiteCaracteres(txtUsuarioCodigo, 20);
		com.util.Eventos.setLimiteCaracteres(txtUsuarioContrasenia, 20);
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.tabPaneDatos.setDisable(true);
		
		//Inicializamos los combobox
		this.cbEspecialidad.getItems().add(new Especialidad());
		this.cbEspecialidad.getItems().addAll(new Especialidad().getLista());  
		this.cargarListaDatos();
		
		
		// Ininicializamos la tabla
		col_IdMedico.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Medico>( cellData.getValue()));
		col_Nif.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getNif()));
		col_FechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getFechaNacimiento().toString()));
		col_Email.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getEmail()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getTelefono()));
		col_NumeroColegiado.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getNumeroColegiado()));
		col_Especialidad.setCellValueFactory(cellData -> new SimpleObjectProperty<Especialidad>( cellData.getValue().getEspecialidad())); 
		col_Direccion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getDireccion()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPoblacion()));
		col_CP.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getCodigoPostal()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getProvincia()));
		col_Pais.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPais()));
		col_Usuario.setCellValueFactory(cellData -> new SimpleObjectProperty<Usuario>( cellData.getValue().getSujeto().getUsuario()));
		
		
		//Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.medicoActual = nv;
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
        		this.txtNumeroColegiado.setText(nv.getNumeroColegiado());
        		this.txtCalle.setText(nv.getSujeto().getDireccion().getDireccion());
        		this.txtPoblacion.setText(nv.getSujeto().getDireccion().getPoblacion());
        		this.txtProvincia.setText(nv.getSujeto().getDireccion().getProvincia());
        		this.txtCodigoPostal.setText(nv.getSujeto().getDireccion().getCodigoPostal());
        		this.txtPais.setText(nv.getSujeto().getDireccion().getPais());
        		this.cbEspecialidad.setValue(nv.getEspecialidad());
        		this.txtUsuarioCodigo.setText(nv.getSujeto().getUsuario().getCodigo());
        		this.txtUsuarioContrasenia.setText(nv.getSujeto().getUsuario().getContrasenia());
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
	        				obj.getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
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
		this.medicoActual = null;
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
		this.txtNumeroColegiado.setText(null);
		this.txtCalle.setText(null);
		this.txtPoblacion.setText(null);
		this.txtProvincia.setText(null);
		this.txtCodigoPostal.setText(null);
		this.txtPais.setText(null);
		this.cbEspecialidad.setValue(null);
		this.txtUsuarioCodigo.setText(null);
		this.txtUsuarioContrasenia.setText(null); 
	}

	private void cargarListaDatos() {
		 // Add observable list data to the table 
        filtro = new FilteredList<Medico>(FXCollections.observableArrayList(new Medico().getLista()));
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
        
        if(this.cbEspecialidad.getValue() == null || this.cbEspecialidad.getSelectionModel().getSelectedIndex() == 0)
        	errorMessage += "Debe indicar una especialidad civil\n";  
        
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
		this.medicoActual = new Medico();
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
		
		//Mapeamos el medico y lo guardamos en base de datos
		Medico p = this.medicoActual;  //Asignamos el medico actual en caso de que sea el medico seleccionado por el usuario
		

		//Comprobamos que no haya ningún medico más con el NIF indicado
		//Solo puede haber un único medico con el mismo NIF
		if(p.existeSujetoConNif(this.txtNif.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Médico con ese Nif."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		//Comprobamos si existe algun usuario con ese codigo
		if(p.existeUsuarioConCodigo(this.txtUsuarioCodigo.getText() == null? "" : this.txtUsuarioCodigo.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Usuario con ese Código."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		p.setNumeroColegiado(this.txtNumeroColegiado.getText());
		if(this.cbEspecialidad.getValue() != null) p.setEspecialidad(this.cbEspecialidad.getValue());  
		 
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
		p.getSujeto().getUsuario().setCodigo(this.txtUsuarioCodigo.getText());
		p.getSujeto().getUsuario().setContrasenia(this.txtUsuarioContrasenia.getText()); 
 		
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El médico se ha modificado exitosamente.");  
        else com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El médico se ha sido creado exitosamente.");
	}

	@FXML public void btnBorrar() {
		int indice = tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!tvDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El Médico no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            tvDatos.getSelectionModel().getSelectedItem().borrar();
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "médico"); 
        }
	}

	@FXML public void btnCancelarCambios() {this.limpiarDatos();}

	@FXML public void btnListar() {Informes.mostrarListadoMedicos(main);}
 

}
