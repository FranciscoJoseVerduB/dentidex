package com.controladoras;

import com.Main; 
import com.modelos.hibernate.EstadoCivil;
import com.modelos.hibernate.Genero;
import com.modelos.hibernate.GrupoSanguineo; 
import com.modelos.hibernate.Paciente;
import com.util.Informes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections; 
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField; 
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;

import org.controlsfx.control.SearchableComboBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType; 
import javafx.scene.control.TabPane; 

public class PacientesShowController {
	Main main; 
	
	@FXML TextField txtNombre;
	@FXML TextField txtApellidos;
	@FXML TextField txtNif;
	@FXML TextField txtEmail;
	@FXML DatePicker dtpFechaNacimiento;
	@FXML Label lbId_Registro;
	@FXML Label lbFechaCreacion;
	@FXML TextField txtTelefono;
	@FXML TextField txtProfesion;
	@FXML SearchableComboBox<Genero> cbGenero = new SearchableComboBox<Genero>();
	@FXML SearchableComboBox<EstadoCivil> cbEstadoCivil = new SearchableComboBox<EstadoCivil>();
	@FXML SearchableComboBox<GrupoSanguineo> cbGrupoSanguineo = new SearchableComboBox<GrupoSanguineo>(); 
	@FXML ImageView imgFotografia;
	@FXML TextField txtCalle;
	@FXML TextField txtPoblacion;
	@FXML TextField txtCodigoPostal;
	@FXML TextField txtProvincia;
	@FXML TextField txtPais;
	@FXML TableView<Paciente> tvDatos;
	@FXML TextField txtBusqueda;

	@FXML TableColumn<Paciente, Integer> col_IdPaciente;
	@FXML TableColumn<Paciente, Paciente> col_Nombre; 
	@FXML TableColumn<Paciente, String> col_Nif;
	@FXML TableColumn<Paciente, String> col_FechaNacimiento;
	@FXML TableColumn<Paciente, String> col_Email;
	@FXML TableColumn<Paciente, String> col_Telefono;
	@FXML TableColumn<Paciente, String> col_Profesion;
	@FXML TableColumn<Paciente, Genero> col_Genero;
	@FXML TableColumn<Paciente, EstadoCivil> col_EstadoCivil;
	@FXML TableColumn<Paciente, GrupoSanguineo> col_GrupoSanguineo;
	@FXML TableColumn<Paciente, String> col_Direccion;
	@FXML TableColumn<Paciente, String> col_Poblacion;
	@FXML TableColumn<Paciente, String> col_CP;
	@FXML TableColumn<Paciente, String> col_Provincia;
	@FXML TableColumn<Paciente, String> col_Pais;
	
    FilteredList<Paciente> filtro;

	@FXML Button btnNuevo;
	@FXML Button btnModificar;
	@FXML Button btnBorrar;
	@FXML Button btnGuardarCambios;
	@FXML Button btnCancelarCambios;
	@FXML Button btnListar;

	Paciente pacienteActual = null;

	@FXML TabPane tabPaneDatos;


	
	 

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
		com.util.Eventos.setLimiteCaracteres(txtProfesion, 50);
		
		
		
		this.btnModificar.setDisable(true);
		this.btnBorrar.setDisable(true);
		this.btnGuardarCambios.setDisable(true);
		this.btnCancelarCambios.setDisable(true);  
		this.tabPaneDatos.setDisable(true);
		
		
		//Inicializamos los combobox   
		this.cbGenero.getItems().add(new Genero());
		this.cbGenero.getItems().addAll(new Genero().getLista()); 
		this.cbEstadoCivil.getItems().add(new EstadoCivil());
		this.cbEstadoCivil.getItems().addAll(new EstadoCivil().getLista());
		this.cbGrupoSanguineo.getItems().add(new GrupoSanguineo());
		this.cbGrupoSanguineo.getItems().addAll(new GrupoSanguineo().getLista());
		this.cargarListaDatos();	 
		 
		// Ininicializamos la tabla
		col_IdPaciente.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		col_Nombre.setCellValueFactory(cellData -> new SimpleObjectProperty<Paciente>( cellData.getValue()));
		col_Nif.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getNif()));
		col_FechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getFechaNacimiento().toString()));
		col_Email.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getEmail()));
		col_Telefono.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getTelefono()));
		col_Profesion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getProfesion()));
		col_Genero.setCellValueFactory(cellData -> new SimpleObjectProperty<Genero>( cellData.getValue().getGenero()));
		col_EstadoCivil.setCellValueFactory(cellData -> new SimpleObjectProperty<EstadoCivil>( cellData.getValue().getEstadoCivil()));
		col_GrupoSanguineo.setCellValueFactory(cellData -> new SimpleObjectProperty<GrupoSanguineo>( cellData.getValue().getGrupoSanguineo()));
		col_Direccion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getDireccion()));
		col_Poblacion.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPoblacion()));
		col_CP.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getCodigoPostal()));
		col_Provincia.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getProvincia()));
		col_Pais.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getSujeto().getDireccion().getPais()));

		 
        //Actualizamos los datos del jugador seleccionado
        tvDatos.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> { 
            if (nv != null) { 
            	this.pacienteActual = nv;
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
        		this.txtProfesion.setText(nv.getProfesion());
        		this.txtCalle.setText(nv.getSujeto().getDireccion().getDireccion());
        		this.txtPoblacion.setText(nv.getSujeto().getDireccion().getPoblacion());
        		this.txtProvincia.setText(nv.getSujeto().getDireccion().getProvincia());
        		this.txtCodigoPostal.setText(nv.getSujeto().getDireccion().getCodigoPostal());
        		this.txtPais.setText(nv.getSujeto().getDireccion().getPais());
        		this.cbEstadoCivil.setValue(nv.getEstadoCivil());
        		this.cbGenero.setValue(nv.getGenero());
        		this.cbGrupoSanguineo.setValue(nv.getGrupoSanguineo());
            } else { 
            	this.limpiarDatos();
            }
    	});
         
        //Configuramos el filtro de la tabla
        this.txtBusqueda.textProperty().addListener( (o, ov, nv) -> { 
	        	filtro.setPredicate(obj -> { 
	        		if (obj.getSujeto().getNombre().toLowerCase().contains(nv.toLowerCase()) ||
	        				obj.getSujeto().getNombreCompleto().toLowerCase().contains(nv.toLowerCase()) ||
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
		this.pacienteActual = new Paciente();
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

	@FXML public void btnBorrar() {
		int indice = tvDatos.getSelectionModel().getSelectedIndex(); 
        if (indice >= 0) { 
            if(!com.util.Alertas.alertaConfirmacionUsuario(this.main.getStagePrincipal(), "Va eliminar el registro seleccionado, ¿continuar?").equals(ButtonType.OK)) 
            	return;	//Preguntamos al usuario si desea eliminar el registro seleccionado
            
            if(!tvDatos.getSelectionModel().getSelectedItem().esEliminable()) {
            	com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "El Paciente no se puede eliminar, tiene registros asociados");
            	return;
            }
            
            tvDatos.getSelectionModel().getSelectedItem().borrar();
            com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El paciente se ha eliminado exitosamente.");  
            this.cargarListaDatos();
        } else {
            com.util.Alertas.alertaNadaSeleccionado(this.main.getStagePrincipal(), "paciente"); 
        }
	}

	@FXML public void btnGuardarCambios() {
		//Comprobamos que los datos son válidos.
		if(!isValido()) return; 
		
		//Mapeamos el paciente y lo guardamos en base de datos
		Paciente p = this.pacienteActual;  //Asignamos el paciente actual en caso de que sea el paciente seleccionado por el usuario
		

		//Comprobamos que no haya ningún paciente más con el NIF indicado
		//Solo puede haber un único paciente con el mismo NIF
		if(p.existeSujetoConNif(this.txtNif.getText().trim())) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ya existe un Paciente con ese Nif."); 
			limpiarDatos();
			this.cargarListaDatos();
			return;
		}
		
		p.setProfesion(this.txtProfesion.getText());
		if(this.cbGenero.getValue() != null) p.setGenero(this.cbGenero.getValue());
		if(this.cbEstadoCivil.getValue() != null) p.setEstadoCivil(this.cbEstadoCivil.getValue());
		if(this.cbGrupoSanguineo.getValue() != null) p.setGrupoSanguineo(this.cbGrupoSanguineo.getValue()); 
		 
		p.getSujeto().getDireccion().setDireccion(this.txtCalle.getText());
		p.getSujeto().getDireccion().setCodigoPostal(this.txtCodigoPostal.getText());
		p.getSujeto().getDireccion().setPoblacion(this.txtPoblacion.getText());
		p.getSujeto().getDireccion().setProvincia(this.txtProvincia.getText());
		p.getSujeto().getDireccion().setPais(this.txtPais.getText()); 
		p.getSujeto().setNombre(this.txtNombre.getText());
		p.getSujeto().setApellidos(this.txtApellidos.getText());
		p.getSujeto().setNif(this.txtNif.getText().trim()); 
		p.getSujeto().setEmail(this.txtEmail.getText());
		p.getSujeto().setTelefono(this.txtTelefono.getText());
		//Si la foto no es NULL, obtenemos la ruta de la imagen
		if(this.imgFotografia.getImage() != null) p.getSujeto().setFoto(this.imgFotografia.getUserData().toString()); 
		p.getSujeto().setFechaNacimiento(Date.valueOf(this.dtpFechaNacimiento.getValue()));
		 
		
		//Guardamos los datos del paciente y recargamos los datos
		if(!p.guardar()) {
			com.util.Alertas.alertaDatosErroneos(this.main.getStagePrincipal(), "Ha ocurrido un error al guardar los cambios."); 
	        return;
		}; 
		limpiarDatos();
		this.cargarListaDatos();
		 
		//Avisamos al usuario del proceso
        if(p.getId() > 0) com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El paciente se ha modificado exitosamente.");  
        else  com.util.Alertas.alertaDatosCorrectos(this.main.getStagePrincipal(), "El paciente se ha sido creado exitosamente."); 
	}
	 
	
	private void limpiarDatos() { 
		this.pacienteActual = null;
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
		this.txtProfesion.setText(null);
		this.txtCalle.setText(null);
		this.txtPoblacion.setText(null);
		this.txtProvincia.setText(null);
		this.txtCodigoPostal.setText(null);
		this.txtPais.setText(null);
		this.cbEstadoCivil.setValue(null);
		this.cbGenero.setValue(null);
		this.cbGrupoSanguineo.setValue(null);
	}
	
	private void cargarListaDatos() { 
		 // Añadimos un filtro a la lista
        filtro = new FilteredList<Paciente>(FXCollections.observableArrayList(new Paciente().getLista()));
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
        
        if(this.cbEstadoCivil.getValue() == null || this.cbEstadoCivil.getSelectionModel().getSelectedIndex() == 0)
        	errorMessage += "Debe indicar un estado civil\n"; 
        if(this.cbGenero.getValue() == null || this.cbGenero.getSelectionModel().getSelectedIndex() == 0)
        	errorMessage += "Debe indicar un género\n"; 
        if(this.cbGrupoSanguineo.getValue() == null || this.cbGrupoSanguineo.getSelectionModel().getSelectedIndex() == 0)
        	errorMessage += "Debe indicar un grupo sanguíneo\n";
        
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

	@FXML public void btnCancelarCambios() { 
		this.limpiarDatos();
	}

	@FXML public void btnListar() {
		Informes.mostrarListadoPacientes(main);
	}
 
}
